package io.github.eno.tinyspider.core.base;

import io.github.eno.tinyspider.core.impl.DownloaderImpl;

public interface Downloader extends Runnable {

	public static Downloader create(URLCollector urlCollection, PageCollector<Page> pageCollector) {
		return new DownloaderImpl(urlCollection, pageCollector);
	}

}
