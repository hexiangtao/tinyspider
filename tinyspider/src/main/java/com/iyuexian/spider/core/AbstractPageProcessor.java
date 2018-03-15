package com.iyuexian.spider.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iyuexian.spider.annotation.Selector;
import com.iyuexian.spider.annotation.Site;
import com.iyuexian.spider.util.CmdArg;
import com.iyuexian.spider.util.Logger;

public abstract class AbstractPageProcessor implements PageProcessor {

	private CmdArg cmdArg;

	public AbstractPageProcessor(CmdArg cmdArg) {
		this.cmdArg = cmdArg;
	}

	public AbstractPageProcessor() {
	}

	@Override
	public void process(String currentUrl, Document doc, List<String> pageLinks) {
		for (String url : pageLinks) {
			if (!filter(url)) {
				continue;
			}
			LinkStorage.instance().offer(url);
		}
		process(doc);

	}

	private boolean filter(String url) {

		List<String> filters = new ArrayList<String>();

		Site site = this.getClass().getAnnotation(Site.class);
		if (site != null && site.filter() != null) {
			filters.addAll(Arrays.asList(site.filter()));
		}
		if (this.cmdArg != null && cmdArg.getFilterUrl() != null && cmdArg.getFilterUrl().trim().length() > 0) {
			filters.add(cmdArg.getFilterUrl());
		}

		if (filters != null && filters.size() == 0) {
			return true;
		}

		for (String item : filters) {
			if (url.contains(item)) {
				return true;
			}
		}
		return false;
	}

	public List<String> select(Document doc) {
		Selector selector = this.getClass().getAnnotation(Selector.class);

		List<String> selectors = new ArrayList<String>();
		if (selector != null && selector.value() != null) {
			selectors.addAll(Arrays.asList(selector.value()));
		}

		if (cmdArg.getSelector() != null && cmdArg.getSelector().trim().length() > 0) {
			selectors.add(cmdArg.getSelector());
		}

		List<String> contents = new ArrayList<>();
		for (String sel : selectors) {
			Elements eles = doc.select(sel);
			for (Element element : eles) {
				String text = element.text();
				Logger.info("extract content:{}", text);
				contents.add(text);
			}
		}
		return contents;

	}

	public abstract void process(Document doc);

}
