package com.iyuexian.spider;

import org.jsoup.nodes.Document;

import com.iyuexian.spider.core.AbstractPageProcessor;
import com.iyuexian.spider.core.Spider;
import com.iyuexian.spider.util.CmdArg;
import com.iyuexian.spider.util.Logger;

public class App extends AbstractPageProcessor {
	public static void main(String[] args) {
		CmdArg cmdArg = CmdArg.getCmdArgs(args);
		Spider.create(cmdArg).processor(new App(cmdArg)).start();
	}


	public App(CmdArg cmdArg) {
		super(cmdArg);
	}

	@Override
	public void process(Document doc) {
		Logger.debug("begin process:{}", doc.baseUri());
		select(doc);
	}
}
