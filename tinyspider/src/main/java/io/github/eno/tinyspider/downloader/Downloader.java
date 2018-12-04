package io.github.eno.tinyspider.downloader;

import java.util.concurrent.Callable;

import io.github.eno.tinyspider.page.Page;
import io.github.eno.tinyspider.page.PageProcessor;

public interface Downloader extends Callable<Page> {

	public static Downloader create(String url,PageProcessor pageProcessor) {
		return new DownloaderImpl(url,pageProcessor);
	}

}
