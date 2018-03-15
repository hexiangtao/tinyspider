package com.iyuexian.spider.demo;

import org.jsoup.nodes.Document;

import com.iyuexian.spider.annotation.Selector;
import com.iyuexian.spider.annotation.UrlPrefix;
import com.iyuexian.spider.core.AbstractPageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.Logger;

@Selector("h2.ContentItem-title")
@UrlPrefix("www.zhihu.com")
public class ZhihuPageProcessor extends AbstractPageProcessor {

	public static void main(String[] args) {
		Spider.site("www.zhihu.com").thread(10).processor(new ZhihuPageProcessor()).start();
	}

	@Override
	public void process(Document doc) {
		Logger.debug("begin process:{}", doc.baseUri());
		select(doc);

	}

}
