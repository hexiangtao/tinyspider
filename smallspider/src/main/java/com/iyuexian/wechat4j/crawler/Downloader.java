package com.iyuexian.wechat4j.crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Downloader implements Runnable {

	private int maxSize = 100000;
	private final int waitMaxCount;
	private int currentWiatCount = 0;
	private List<PageProcessor> pageProcessors;

	public Downloader(List<PageProcessor> processors) {
		this(10, processors);
	}

	public Downloader(int waitMaxCount, List<PageProcessor> pageProcessors) {
		this.waitMaxCount = waitMaxCount;
		this.pageProcessors = pageProcessors;

	}

	public boolean isFetchedFull() {
		return Links.instance().getFetched().size() >= maxSize;
	}

	@Override
	public void run() {
		while (!isFetchedFull() && (currentWiatCount < waitMaxCount)) {
			String link = Links.instance().poll();
			if (link == null) {
				this.currentWiatCount++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			this.currentWiatCount = 0;
			Links.instance().setFetched(link);
			Logger.debug("get:{}", link);
			try {
				Document doc = Jsoup.connect(link).get();
				pubEvent(link, doc);
			} catch (Exception e) {
				Logger.error("抓取页面失败:{}", e.getLocalizedMessage());
			}
		}

	}

	public void pubEvent(String url, Document doc) {
		if (pageProcessors == null || pageProcessors.size() == 0) {
			return;
		}

		List<String> links = new ArrayList<String>();
		Elements eles = doc.select("a[href]");
		for (Element element : eles) {
			String link = element.attr("abs:href").trim();
			if (link == null || link.trim().length() == 0 || Links.instance().isFetched(link)) {
				Logger.debug("去重:{}", url);
				continue;
			}
			links.add(link);

		}

		// TODO 改成异步
		for (PageProcessor processor : pageProcessors) {
			processor.process(url, doc, links);
		}

	}

}
