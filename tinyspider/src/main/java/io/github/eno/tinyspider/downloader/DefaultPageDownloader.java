package io.github.eno.tinyspider.downloader;

import io.github.eno.tinyspider.page.Page;
import io.github.eno.tinyspider.page.PageProcessor;
import io.github.eno.tinyspider.persistence.UrlCollector;
import io.github.eno.tinyspider.util.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:07
 */
public class DefaultPageDownloader implements PageDownloader {

    private final PageProcessor processor;

    public DefaultPageDownloader(PageProcessor processor) {
        this.processor = processor;
    }

    @Override
    public Page download(String url) throws IOException {
        if (url == null || url.trim().length() == 0) {
            return null;
        }
        Document doc = Jsoup.parse(new URL(url), 1000 * 10);
        if (doc == null) {
            return null;
        }
        Page page = Page.of(doc);
        try {
            Logger.getLogger().info("已抓取:" + UrlCollector.instance().getHistory().size() + ",剩余:" + UrlCollector.instance().getUnFetched().size());
            processor.process(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}
