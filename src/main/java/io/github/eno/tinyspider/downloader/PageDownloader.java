package io.github.eno.tinyspider.downloader;

import io.github.eno.tinyspider.page.Page;
import io.github.eno.tinyspider.page.PageProcessor;

import java.io.IOException;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:07
 */
public interface PageDownloader {
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.115 Safari/537.36";
    String COOKIE = "t=d8bb46422c1c10b758c3e57662c1fdae; r=2450; Hm_lvt_74e638f8ad49305b5cd6ed0a90fec268=1655041471,1655907503; Hm_lvt_e75735c80beab21b0f809fbff6eff1a4=1655907504; Hm_lpvt_e75735c80beab21b0f809fbff6eff1a4=1655908491; playhistory=26740,26752,26751; playdate=1655908544455; Hm_lpvt_74e638f8ad49305b5cd6ed0a90fec268=1655908545; openPlayer=0";


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
