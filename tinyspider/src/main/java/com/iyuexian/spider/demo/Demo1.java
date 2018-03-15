package com.iyuexian.spider.demo;

import org.jsoup.nodes.Document;

import com.iyuexian.spider.annotation.Selector;
import com.iyuexian.spider.annotation.Site;
import com.iyuexian.spider.core.AbstractPageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.Logger;

//@Selector("h2.ContentItem-title")
@Selector("title")
@Site(host = "www.baidu.com", filter = "baidu.com")
public class Demo1 extends AbstractPageProcessor {

	public static void main(String[] args) {
		Spider.create().thread(100).processor(new Demo1()).start();
	}

	@Override
	public void process(Document doc) {
		Logger.info("begin process:{}", doc.title());
		// select(doc);

	}

}
