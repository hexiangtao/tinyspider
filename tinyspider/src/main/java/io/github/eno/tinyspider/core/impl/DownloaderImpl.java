package io.github.eno.tinyspider.core.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.iyuexian.spider.util.Logger;

import io.github.eno.tinyspider.core.base.Downloader;
import io.github.eno.tinyspider.core.base.Page;
import io.github.eno.tinyspider.core.base.PageCollector;
import io.github.eno.tinyspider.core.base.URLCollector;

public class DownloaderImpl implements Downloader {

	private URLCollector urlCollector;

	private PageCollector<Page> pageCollector;

	public DownloaderImpl(URLCollector urlCollector, PageCollector<Page> pageCollector) {
		this.urlCollector = urlCollector;
		this.pageCollector = pageCollector;
	}

	@Override
	public void run() {
		while (true) {

			if (pageCollector.size() >= pageCollector.limit()) {
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
			pageCollector.put(page);
			return page;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
