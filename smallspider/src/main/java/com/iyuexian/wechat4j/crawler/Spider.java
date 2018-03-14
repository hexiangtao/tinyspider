package com.iyuexian.wechat4j.crawler;

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
	private CmdArgInfo cmdArgInfo;

	private String host;

	private List<PageProcessor> pageProcessors;

	public Spider site(String host) {
		this.host = host;
		return this;
	}

	public Spider thread(int threadNum) {
		this.threadNum = threadNum;
		return this;
	}

	public Spider processor(PageProcessor... pageProcessors) {

		if (pageProcessors == null || pageProcessors.length == 0) {
			throw new IllegalArgumentException("pageProcessor not config");
		}

		this.tasks = new ArrayList<Downloader>(threadNum);
		this.pageProcessors = Arrays.asList(pageProcessors);
		String firtUrl = host.startsWith("http") ? host : host.startsWith("https:") ? host : "http://" + host;
		Links.instance().offer(firtUrl);
		this.threadPool = Executors.newScheduledThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			tasks.add(new Downloader(this.pageProcessors));
		}
		return this;
	}

	public void start() {
		try {
			if (cmdArgInfo != null) {
				Logger.info("线程数:{}开始抓取:{}....", cmdArgInfo.getThreadNum(), cmdArgInfo.getHost());
			}
			Thread.sleep(2000);
			long timestamp = System.currentTimeMillis();

			List<Future<?>> futureList = new ArrayList<Future<?>>();
			for (Downloader task : tasks) {
				Future<?> future = threadPool.submit(task);
				futureList.add(future);
			}
			for (Future<?> future : futureList) {
				future.get();
			}

			Logger.info("耗时:{}秒,抓取完毕，共{}条网页", (System.currentTimeMillis() - timestamp) / 1000,
					Links.instance().getFetched().size());
			for (String item : Links.instance().getFetched()) {
				Logger.info("{}", item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.shutdown();
		}
	}

	public void shutdown() {
		threadPool.shutdown();
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

}
