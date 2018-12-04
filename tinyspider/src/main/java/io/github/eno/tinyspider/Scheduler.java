package io.github.eno.tinyspider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.github.eno.tinyspider.downloader.Downloader;
import io.github.eno.tinyspider.page.Page;
import io.github.eno.tinyspider.page.PageProcessor;
import io.github.eno.tinyspider.persistence.URLCollector;
import io.github.eno.tinyspider.util.Logger;

public class Scheduler {

	private Logger logger = Logger.getLogger(Scheduler.class);

	static final int DEFAULT_THREAD_NUM = 1;

	static final int DEFAULT_URL_COLLECTOR_CAPACITY = 10;

	private ExecutorService threadPool;

	private URLCollector urlCollector;

	private PageProcessor pageProcessor;

	public Scheduler(String host) {
		this(host, DEFAULT_THREAD_NUM);
	}

	public Scheduler(String host, int nThreads) {
		this(host, nThreads, DEFAULT_URL_COLLECTOR_CAPACITY);
	}

	public Scheduler(String host, int nThreads, int nURLCapacity) {
		this.threadPool = Executors.newFixedThreadPool(nThreads);

		this.urlCollector = URLCollector.instance();
		this.urlCollector.host(host);
	}

	public void start(PageProcessor processor) {
		this.pageProcessor = processor;
		download();
	}

	public void download() {
		while (urlCollector.getUnFetched().size() > 0) {
			try {
				startTask();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		stop();

	}

	private void startTask() throws Exception {
		String url = urlCollector.poll();
		logger.debug("download {},remain:{}", url, urlCollector.getUnFetched().size());
		Downloader downloader = Downloader.create(url, pageProcessor);
		Future<Page> future = threadPool.submit(downloader);
		future.get();

	}

	public void stop() {
		threadPool.shutdown();
		logger.info("scheduler stoped!");
	}

}
