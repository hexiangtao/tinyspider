package io.github.eno.tinyspider.page;

public interface PageProcessor {

	public static PageProcessor create() {
		return new PageProcessorImpl();
	}
	
	public void process(Page page);
}
