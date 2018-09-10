package io.github.eno.tinyspider.core.base;

import io.github.eno.tinyspider.core.impl.DownloaderImpl;

public interface Downloader extends Runnable {

	public static Downloader create(URLPipeline urlPipeline, PageStorage<Page> pageStorage) {
		return new DownloaderImpl(urlPipeline, pageStorage);
	}

}
