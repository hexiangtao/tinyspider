package io.github.eno.tinyspider.downloader;

import io.github.eno.tinyspider.page.Page;
import io.github.eno.tinyspider.page.PageProcessor;

import java.io.IOException;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:07
 */
public interface PageDownloader {


    /**
     * 下载页面
     *
     * @param url
     * @return
     * @throws IOException
     */
    Page download(String url) throws IOException;

    /**
     * create a new  Downloader
     *
     * @param pageProcessor
     * @return
     */
    static PageDownloader create(PageProcessor pageProcessor) {
        return new DefaultPageDownloader(pageProcessor);
    }
}
