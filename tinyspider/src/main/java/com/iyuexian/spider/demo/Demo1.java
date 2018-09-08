package com.iyuexian.spider.demo;

import org.jsoup.nodes.Document;

import com.iyuexian.spider.annotation.Selector;
import com.iyuexian.spider.annotation.Site;
import com.iyuexian.spider.core.AbstractPageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.Logger;

@Selector(value = "img[src]", dir = "C:/doubannv6")
@Site(host = "http://www.doubannv.net/")
//@Site(host = "http://108.170.9.194/forum.php")

public class Demo1 extends AbstractPageProcessor {

	public static void main(String[] args) {
		Spider.create().thread(300).processor(new Demo1()).start();
	}

	@Override
	public void process(Document doc) {
		Logger.info("begin process:{}", doc.baseUri());
		select(doc);

	}

}
