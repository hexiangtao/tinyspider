package io.github.eno.tinyspider.page;

import io.github.eno.tinyspider.util.Logger;

public class PageProcessorImpl implements PageProcessor {

	private Logger logger = Logger.getLogger(PageProcessorImpl.class);

	public PageProcessorImpl() {
	}

	@Override
	public void process(Page page) {
		logger.info("process:{}", page.title());
	}

}
