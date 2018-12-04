package io.github.eno.tinyspider.downloader;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.github.eno.tinyspider.page.Page;
import io.github.eno.tinyspider.page.PageProcessor;
import io.github.eno.tinyspider.persistence.URLCollector;

public class DownloaderImpl implements Downloader {

	private String url;

	private PageProcessor processor;

	public DownloaderImpl(String url, PageProcessor processor) {
		this.url = url;
		this.processor = processor;
	}

	private Page downloadByUrl(String url) throws IOException {
		if (url == null || url.trim().length() == 0) {
			return null;
		}
		Document doc = Jsoup.parse(new URL(url), 1000 * 10);
		if (doc == null) {
			return null;
		}
		Page page = Page.of(doc);
		URLCollector.instance().offer(page.links());
		if (processor != null) {
			processor.process(page);
		}
		return page;
	}

	@Override
	public Page call() throws Exception {
		Page page = downloadByUrl(url);
		return page;
	}

}
