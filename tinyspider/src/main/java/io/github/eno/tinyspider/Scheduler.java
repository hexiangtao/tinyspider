package io.github.eno.tinyspider;

import io.github.eno.tinyspider.annotation.Site;
import io.github.eno.tinyspider.downloader.PageDownloader;
import io.github.eno.tinyspider.page.PageProcessor;
import io.github.eno.tinyspider.persistence.UrlCollector;
import io.github.eno.tinyspider.util.Logger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hexiangtao
 * @date 2022/6/11 15:11
 */
public class Scheduler {

    private final Logger logger = Logger.getLogger(Scheduler.class);


    public static ThreadPoolExecutor threadPoolExecutor;
    private final UrlCollector urlCollector;
    private final PageProcessor pageProcessor;

    private static final AtomicInteger THREAD_NUM = new AtomicInteger(0);


    public Scheduler(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        Site site = pageProcessor.getClass().getAnnotation(Site.class);
        threadPoolExecutor = new ThreadPoolExecutor(site.threads(), site.threads(), 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> {
            return new Thread(r, "tinySpider-" + THREAD_NUM.incrementAndGet());
        });
        this.urlCollector = UrlCollector.instance();
        this.urlCollector.host(site.host());
    }


    public void start() throws Exception {
        loop();
    }


    public void loop() throws Exception {
        while (true) {
            if (urlCollector.isEmpty()) {
                TimeUnit.SECONDS.sleep(5);
            }
            startTask();
        }

    }

    private void startTask() {
        String url = urlCollector.poll();
        if (url == null || url.trim().length() == 0) {
            logger.info("待抓取队列为空");
            return;
        }
        logger.info("已经抓取页面数 {},剩余待抓取数:{}", urlCollector.getHistory().size(), urlCollector.getUnFetched().size());
        PageDownloader downloader = PageDownloader.create(pageProcessor);
        threadPoolExecutor.submit(() -> downloader.download(url));
    }


    public void stop() {
        threadPoolExecutor.shutdown();
        logger.info("scheduler stoped!");
    }

}
