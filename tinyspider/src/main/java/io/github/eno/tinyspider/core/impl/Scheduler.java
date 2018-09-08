package io.github.eno.tinyspider.core.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.eno.tinyspider.TinySpider;
import io.github.eno.tinyspider.core.base.Downloader;
import io.github.eno.tinyspider.core.base.Page;
import io.github.eno.tinyspider.core.base.PageCollector;
import io.github.eno.tinyspider.core.base.PageProcessor;
import io.github.eno.tinyspider.core.base.URLCollector;

public class Scheduler {

	static final int DEFAULT_THREAD_NUM = 10;

	static final int DEFAULT_URL_COLLECTOR_CAPACITY= 10;

	static final int DEFAULT_PAGE_COLLECTOR_CAPACITY= 10;

	private ExecutorService downloadThreadPool;
	private ExecutorService processThreadPool;

	private URLCollector urlCollector;

	private PageCollector<Page> pageCollector;

	public Scheduler(String host) {
		this(host, DEFAULT_THREAD_NUM);
	}

	public Scheduler(String host, int nThreads) {
		this(host, nThreads, DEFAULT_URL_COLLECTOR_CAPACITY, DEFAULT_PAGE_COLLECTOR_CAPACITY);
	}

	public Scheduler(String host, int nThreads, int nURLCapacity, int nPageCapacity) {
		this.downloadThreadPool = Executors.newFixedThreadPool(nThreads);
		this.processThreadPool = Executors.newFixedThreadPool(nThreads);

		this.urlCollector = URLCollector.create(nURLCapacity, host);
		this.pageCollector = PageCollector.create(nPageCapacity);
	}

	public void start() {
		Downloader downloader = Downloader.create(urlCollector, pageCollector);
		PageProcessor processor = PageProcessor.create(pageCollector);
		processThreadPool.submit(processor);
		downloadThreadPool.submit(downloader);
	}


}
