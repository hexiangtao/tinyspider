package io.github.eno.tinyspider.core.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.iyuexian.spider.util.Logger;

import io.github.eno.tinyspider.core.base.Downloader;
import io.github.eno.tinyspider.core.base.Page;
import io.github.eno.tinyspider.core.base.PageStorage;
import io.github.eno.tinyspider.core.base.URLPipeline;

public class DownloaderImpl implements Downloader {

	private URLPipeline urlCollector;

	private PageStorage<Page> pageStorage;

	public DownloaderImpl(URLPipeline urlCollector, PageStorage<Page> pageStorage) {
		this.urlCollector = urlCollector;
		this.pageStorage = pageStorage;
	}

	@Override
	public void run() {
		while (true) {

			if (pageStorage.size() >= pageStorage.limit()) {
				try {
					continue;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			String url = urlCollector.take();
			download(url);
		}
	}

	private Page download(String url) {
		try {
			if (url == null || url.trim().length() == 0) {
				return null;
			}
			Logger.debug("begin download:{}", url);
			Document doc = Jsoup.connect(url).get();
			Logger.debug("{} download success", url);
			if (doc == null) {
				return null;
			}
			Page page = Page.of(doc);
			urlCollector.put(page.links());
			pageStorage.put(page);
			return page;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
