package io.github.eno.tinyspider.core.impl;

import com.iyuexian.spider.util.Logger;

import io.github.eno.tinyspider.core.base.Page;
import io.github.eno.tinyspider.core.base.PageProcessor;
import io.github.eno.tinyspider.core.base.PageStorage;

public class PageProcessorImpl implements PageProcessor {

	private PageStorage<Page> pageCollector;

	public PageProcessorImpl(PageStorage<Page> pageCollector) {
		this.pageCollector = pageCollector;
	}

	@Override
	public void run() {

		while (true) {
			Page page = pageCollector.take();
			 if(page==null) {continue;}
			Logger.debug("begin process page:{}", page.title());
		}
	}

}
