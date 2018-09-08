package io.github.eno.tinyspider.core.base;

import io.github.eno.tinyspider.core.impl.PageProcessorImpl;

public interface PageProcessor extends Runnable {

	public static PageProcessor create(PageCollector<Page> pageCollector) {
		return new PageProcessorImpl(pageCollector);

	}
}
