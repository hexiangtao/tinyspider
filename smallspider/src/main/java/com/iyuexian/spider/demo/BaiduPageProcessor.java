package com.iyuexian.spider.demo;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iyuexian.spider.core.LinkStorage;
import com.iyuexian.spider.core.PageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.Logger;

public class BaiduPageProcessor implements PageProcessor {

	private String urlReg = "www.zhihu.com";

	public static void main(String[] args) {

		Spider.site("www.zhihu.com").thread(10).processor(new BaiduPageProcessor()).start();

	}

	@Override
	public void process(String currentUrl, Document doc, List<String> urls) {

		select(doc, "h2.ContentItem-title");

		Logger.debug("begin process:{}", currentUrl);
		for (String url : urls) {
			if (!url.contains(urlReg)) {
				continue;
			}
			LinkStorage.instance().offer(url);
		}
	}

	public void select(Document doc, String select) {
		Elements eles = doc.select(select);
		for (Element element : eles) {
			System.out.println(element.text());

		}
	}

}
