package com.iyuexian.spider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Spider {

	private int threadNum = 3;
	private List<Downloader> tasks;
	private ExecutorService threadPool;
	private CmdArg cmdArgInfo;
	private String host;
	private List<Future<?>> results;

	private long startTime;

	private List<PageProcessor> pageProcessors;

	private Spider() {
	}

	
	public static Spider site(String host) {
		Spider spider = new Spider();
		spider.host = host;
		String fisrtUrl = host.startsWith("http") ? host : host.startsWith("https:") ? host : "http://" + host;
		Links.instance().offer(fisrtUrl);
		return spider;
	}

	public Spider thread(int threadNum) {
		this.threadNum = threadNum;
		return this;
	}

	public Spider processor(PageProcessor... pageProcessors) {

		initProcessors(pageProcessors);

		return this;
	}

	private Spider initProcessors(PageProcessor... pageProcessors) {

		if (pageProcessors == null || pageProcessors.length == 0) {
			throw new IllegalArgumentException("pageProcessor not config");
		}
		this.pageProcessors = Arrays.asList(pageProcessors);
		return this;
	}

	private Spider initThreads() {

		if (threadNum <= 0) {
			throw new IllegalArgumentException("threadNum  must greater then 0");
		}

		this.tasks = new ArrayList<Downloader>(threadNum);

		this.threadPool = Executors.newScheduledThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			tasks.add(new Downloader(this.pageProcessors));
		}
		return this;

	}

	public void start() {

		initThreads();
		if (cmdArgInfo != null) {
			Logger.info("线程数:{}开始抓取:{}....", cmdArgInfo.getThreadNum(), cmdArgInfo.getHost());
		}
		startTime = System.currentTimeMillis();
		results = new ArrayList<Future<?>>(tasks.size());

		for (Downloader task : tasks) {
			Future<?> future = threadPool.submit(task);
			results.add(future);
		}

		getResultAndShutdown();

	}

	public void getResultAndShutdown() {

		try {
			threadPool.shutdown();

			for (Future<?> future : results) {
				future.get();
			}
			Logger.info("耗时:{}秒,抓取完毕，共{}条网页", (System.currentTimeMillis() - startTime) / 1000,
					Links.instance().getFetched().size());
			for (String item : Links.instance().getFetched()) {
				Logger.info("{}", item);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public int getThreadNum() {
		return threadNum;
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

	public List<PageProcessor> getPageProcessors() {
		return pageProcessors;
	}

}
