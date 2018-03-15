package com.iyuexian.spider.demo;

import java.util.List;

import org.jsoup.nodes.Document;

import com.iyuexian.spider.core.LinkStorage;
import com.iyuexian.spider.core.PageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.Logger;

public class BaiduPageProcessor implements PageProcessor {

	private String urlReg = "www.baidu.com";

	public static void main(String[] args) {

		Spider.site("www.baidu.com").thread(10).processor(new BaiduPageProcessor()).start();

	}

	@Override
	public void process(String currentUrl, Document doc, List<String> urls) {
		Logger.debug("begin process:{}", currentUrl);
		for (String url : urls) {
			if (!url.contains(urlReg)) {
				continue;
			}
			LinkStorage.instance().offer(url);
		}
	}

}
