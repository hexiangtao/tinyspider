package com.iyuexian.spider.core;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iyuexian.spider.annotation.Selector;
import com.iyuexian.spider.annotation.UrlPrefix;
import com.iyuexian.spider.util.Logger;

public abstract class AbstractPageProcessor implements PageProcessor {

	
	
	@Override
	public void process(String currentUrl, Document doc, List<String> pageLinks) {
		for (String url : pageLinks) {
			if (!filter(url)) {
				continue;
			}
			LinkStorage.instance().offer(url);
			process(doc);
		}

	}

	private boolean filter(String url) {
		UrlPrefix urlPrefix = this.getClass().getAnnotation(UrlPrefix.class);
		if (urlPrefix == null || urlPrefix.value() == null || urlPrefix.value().length == 0) {
			return true;
		}
		String[] urls = urlPrefix.value();
		for (String item : urls) {
			if (url.contains(item)) {
				return true;
			}
		}
		return false;
	}

	public List<String> select(Document doc) {
		Selector selector = this.getClass().getAnnotation(Selector.class);
		if (selector == null || selector.value() == null || selector.value().trim().length() == 0) {
			Logger.warn("未配置selector,{}", this.getClass());
		}
		Elements eles = doc.select(selector.value());
		List<String> contents = new ArrayList<>();
		for (Element element : eles) {
			String text = element.text();
			Logger.info("extract content:{}", text);
			contents.add(text);
		}
		return contents;

	}

	public abstract void process(Document doc);

}
