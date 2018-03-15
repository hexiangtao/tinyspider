package com.iyuexian.spider.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.iyuexian.spider.annotation.Site;
import com.iyuexian.spider.util.CmdArg;
import com.iyuexian.spider.util.Logger;

public class Spider {

	private static final int DEFAULT_THREAD_NUM = 1;

	/**
	 * 下载器，每个下载器是实现了runnable接口的task,由线程池驱动
	 */
	private List<Downloader> tasks;

	private int threadNum = DEFAULT_THREAD_NUM;

	/**
	 * 下载器线池
	 */
	private ExecutorService threadPool;

	private CmdArg cmdArgInfo;

	/**
	 * 要抓取的网站地址
	 */
	private String host;

	private List<Future<?>> results;

	private long startTime;

	/**
	 * 页面处理器
	 */
	private PageProcessor pageProcessor;

	private Spider() {
	}

	public static Spider create() {
		Spider spider = new Spider();
		return spider;
	}

	public static Spider site(String host) {
		Spider spider = new Spider();
		spider.host(host);
		return spider;
	}

	public static Spider create(CmdArg cmdArg) {
		Spider spider = new Spider();
		spider.host(cmdArg.getHost());
		spider.threadNum = cmdArg.getThreadNum();
		spider.cmdArgInfo = cmdArg;
		return spider;
	}

	public Spider host(String host) {
		this.host = host;
		String fisrtUrl = host.startsWith("http") ? host : host.startsWith("https:") ? host : "http://" + host;
		LinkStorage.instance().offer(fisrtUrl);
		return this;
	}

	public Spider thread(int threadNum) {
		this.threadNum = threadNum;
		return this;
	}

	public Spider processor(PageProcessor pageProcessor) {

		initProcessors(pageProcessor);

		return this;
	}

	private Spider initProcessors(PageProcessor pageProcessor) {

		if (pageProcessor == null) {
			throw new IllegalArgumentException("pageProcessor not config");
		}
		this.pageProcessor = pageProcessor;

		if (this.host != null && this.host.trim().length() > 0) {
			return this;
		}

		Site site = this.pageProcessor.getClass().getAnnotation(Site.class);
		if (site == null || site.host() == null || site.host().trim().length() == 0) {
			Logger.warn("host not config");
		} else {
			this.host(site.host());
		}

		return this;
	}

	private Spider initThreads() {

		if (threadNum <= 0) {
			throw new IllegalArgumentException("threadNum  must greater then 0");
		}

		this.tasks = new ArrayList<Downloader>(threadNum);

		this.threadPool = Executors.newScheduledThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			tasks.add(new Downloader(this.pageProcessor));
		}
		return this;

	}

	private void checkInitParameters() {
		initThreads();
		if (cmdArgInfo != null) {
			Logger.info("线程:{},selector:{},filter:{},开始抓取:{}....", cmdArgInfo.getThreadNum(), cmdArgInfo.getSelector(),
					cmdArgInfo.getFilterUrl(), cmdArgInfo.getHost());
		}
	}

	public void start() {

		checkInitParameters();
		this.startTime = System.currentTimeMillis();
		this.results = new ArrayList<Future<?>>(tasks.size());

		for (Downloader task : tasks) {
			Future<?> future = threadPool.submit(task);
			results.add(future);
		}
		getResultAndShutdown();

	}

	public void getResultAndShutdown() {
		try {
			threadPool.shutdown();

			// for (Future<?> future : results) {
			// future.get();
			// }
			// Logger.info("耗时:{}秒,抓取完毕，共{}条网页", (System.currentTimeMillis() - startTime) /
			// 1000,
			// LinkStorage.instance().getFetched().size());
			// for (String item : LinkStorage.instance().getFetched()) {
			// Logger.info("{}", item);
			// }
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public int getThreadNum() {
		return getTasks() != null ? getTasks().size() : 0;
	}

	public List<Downloader> getTasks() {
		return tasks;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public CmdArg getCmdArgInfo() {
		return cmdArgInfo;
	}

	public String getHost() {
		return host;
	}

	public List<Future<?>> getResults() {
		return results;
	}

	public long getStartTime() {
		return startTime;
	}

	public PageProcessor getPageProcessor() {
		return pageProcessor;
	}

}
