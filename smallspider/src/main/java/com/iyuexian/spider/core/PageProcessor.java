package com.iyuexian.spider.core;

import java.util.List;

import org.jsoup.nodes.Document;

public interface PageProcessor {
	public void process(String currentUrl, Document doc, List<String> pageLinks);

}
