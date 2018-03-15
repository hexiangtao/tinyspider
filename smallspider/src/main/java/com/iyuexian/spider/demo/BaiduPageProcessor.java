package com.iyuexian.spider.demo;

import java.util.List;

import org.jsoup.nodes.Document;

import com.iyuexian.spider.core.LinkStorage;
import com.iyuexian.spider.core.PageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.Logger;

public class BaiduPageProcessor implements PageProcessor {

	public static void main(String[] args) {

		Spider.site("www.baidu.com").processor(new BaiduPageProcessor()).start();

	}

	@Override
	public void process(String currentUrl, Document doc, List<String> urls) {
		for (String url : urls) {
			Logger.info("{}", url);
			if (!url.contains("www.baidu.com")) {
				continue;
			}
			LinkStorage.instance().offer(url);
		}
	}

}
