package com.iyuexian.spider.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iyuexian.spider.util.Logger;

public class Downloader implements Runnable {

	private static final int DEFAULT_WAIT_MAX_COUNT = 10;
	private static final int DEFAULT_WAIT_TIME = 500;

	private final int waitMaxCount;
	private int currentWiatCount = 0;

	private PageProcessor pageProcessor;

	private LinkStorage linksStorage = LinkStorage.instance();
	private int waitTime = DEFAULT_WAIT_TIME;

	public Downloader(PageProcessor processor) {
		this(DEFAULT_WAIT_MAX_COUNT, processor);
	}

	public Downloader(int waitMaxCount, PageProcessor pageProcessor) {
		this.waitMaxCount = waitMaxCount;
		this.pageProcessor = pageProcessor;

	}

	private Document download(String url) {
		linksStorage.putFetched(url);
		Logger.debug("download:{}", url);
		try {
			Document doc = Jsoup.connect(url).get();
			return doc;
		} catch (IOException e) {
			Logger.error("download  {} fail,cause by:{}", url, e.getMessage());
			linksStorage.removeFetched(url);
			return null;
		}
	}

	private void handleTask(String link) throws InterruptedException {
		if (link == null) {
			this.currentWiatCount++;
			Thread.sleep(waitTime);
			return;
		}

		this.currentWiatCount = 0;
		linksStorage.putFetched(link);
		Document doc = download(link);
		processPage(link, doc);
		return;

	}

	@Override
	public void run() {
		while (linksStorage.isEnableFetch() && (currentWiatCount < waitMaxCount)) {
			String link = linksStorage.poll();
			try {
				handleTask(link);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private List<String> selectLinks(String currentUrl, Document doc) {
		List<String> links = new ArrayList<String>();
		Elements eles = doc.select("a[href]");
		for (Element element : eles) {
			String link = element.attr("abs:href").trim();

			if (link == null || link.trim().length() == 0) {
				continue;
			}
			link = link.contains("#") ? link.substring(0, link.indexOf("#")) : link;
			if (linksStorage.isFetched(link)) {
				Logger.debug("去重:{}", currentUrl);
				continue;
			}
			links.add(link);
		}
		return links;
	}

	public void processPage(String url, Document doc) {
		if (doc == null) {
			return;
		}
		if (pageProcessor == null) {
			Logger.warn("pageProcessor not found");
			return;
		}
		List<String> links = selectLinks(url, doc);
		pageProcessor.process(url, doc, links);

	}

}
