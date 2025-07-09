package io.github.eno.tinyspider.engine;

import io.github.eno.tinyspider.downloader.DefaultDownloader;
import io.github.eno.tinyspider.downloader.Downloader;
import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import io.github.eno.tinyspider.middleware.DownloaderMiddleware;
import io.github.eno.tinyspider.pipeline.ItemPipeline;
import io.github.eno.tinyspider.scheduler.DefaultScheduler;
import io.github.eno.tinyspider.scheduler.Scheduler;
import io.github.eno.tinyspider.spider.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Engine implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    private final Spider spider;
    private final Downloader downloader;
    private final Scheduler scheduler;
    private final List<ItemPipeline> pipelines = new ArrayList<>();
    private final List<DownloaderMiddleware> middlewares = new ArrayList<>();
    private final ExecutorService executorService;
    private final CompletionService<Response> completionService;
    private final AtomicInteger activeRequests = new AtomicInteger(0);
    private long downloadDelayMillis = 0;

    public Engine(Spider spider, Downloader downloader, Scheduler scheduler, int threadCount) {
        this.spider = spider;
        this.downloader = downloader;
        this.scheduler = scheduler;
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.completionService = new ExecutorCompletionService<>(executorService);
    }

    public static Engine create(Spider spider) {
        return new Engine(spider, new DefaultDownloader(), new DefaultScheduler(), 5);
    }

    public Engine setDownloadDelay(long millis) {
        this.downloadDelayMillis = millis;
        return this;
    }

    public Engine addPipeline(ItemPipeline pipeline) {
        this.pipelines.add(pipeline);
        return this;
    }

    public Engine addMiddleware(DownloaderMiddleware middleware) {
        this.middlewares.add(middleware);
        return this;
    }

    public void start() {
        logger.info("Engine started for spider: {}", spider.getName());
        spider.startRequests().forEach(this::scheduleRequest);

        while (activeRequests.get() > 0 || scheduler.hasNext()) {
            // Poll for new requests to download
            Request request = scheduler.poll();
            if (request != null) {
                submitRequest(request);
            }

            // Poll for completed responses
            try {
                Future<Response> future = completionService.poll(1, TimeUnit.SECONDS);
                if (future != null) {
                    Response response = future.get();
                    handleResponse(response);
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error waiting for response", e);
            }
        }
        logger.info("Engine stopped.");
    }

    private void submitRequest(Request request) {
        activeRequests.incrementAndGet();
        executorService.submit(() -> {
            try {
                if (downloadDelayMillis > 0) {
                    TimeUnit.MILLISECONDS.sleep(downloadDelayMillis);
                }
                // Apply request middlewares
                for (DownloaderMiddleware middleware : middlewares) {
                    middleware.processRequest(request, spider);
                }
                Future<Response> future = downloader.download(request);
                completionService.submit(future::get);
            } catch (Exception e) {
                handleException(request, e);
            }
        });
    }

    private void handleResponse(Response response) {
        activeRequests.decrementAndGet();
        try {
            // Apply response middlewares
            for (DownloaderMiddleware middleware : middlewares) {
                response = middleware.processResponse(response, spider);
            }

            // Parse response and handle new requests/items
            spider.parse(response).forEach(item -> {
                if (item instanceof Request) {
                    scheduleRequest((Request) item);
                } else if (item instanceof io.github.eno.tinyspider.core.Item) {
                    processItem((io.github.eno.tinyspider.core.Item) item);
                }
            });
        } catch (Exception e) {
            handleException(response.getRequest(), e);
        }
    }

    private void handleException(Request request, Exception e) {
        activeRequests.decrementAndGet();
        Request newRequest = null;
        for (DownloaderMiddleware middleware : middlewares) {
            newRequest = middleware.processException(request, e, spider);
            if (newRequest != null) {
                break;
            }
        }
        if (newRequest != null) {
            scheduleRequest(newRequest);
        } else {
            logger.error("Error processing request: {}", request.getUrl(), e);
        }
    }

    private void scheduleRequest(Request request) {
        scheduler.push(request);
    }

    private void processItem(io.github.eno.tinyspider.core.Item item) {
        for (ItemPipeline pipeline : pipelines) {
            pipeline.processItem(item, spider);
        }
    }

    @Override
    public void close() {
        downloader.close();
        executorService.shutdown();
    }
}
