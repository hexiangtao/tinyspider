package io.github.eno.tinyspider.core.impl;

import com.iyuexian.spider.util.Logger;

import io.github.eno.tinyspider.core.base.Page;
import io.github.eno.tinyspider.core.base.PageCollector;
import io.github.eno.tinyspider.core.base.PageProcessor;

public class PageProcessorImpl implements PageProcessor {

	private PageCollector<Page> pageCollector;

	public PageProcessorImpl(PageCollector<Page> pageCollector) {
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
