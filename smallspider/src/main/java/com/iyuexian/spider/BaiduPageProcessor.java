package com.iyuexian.spider;

import java.util.List;

import org.jsoup.nodes.Document;

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
			Links.instance().offer(url);
		}
	}

}
