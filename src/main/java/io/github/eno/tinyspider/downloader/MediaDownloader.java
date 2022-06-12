package io.github.eno.tinyspider.downloader;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:07
 */
public interface MediaDownloader {


    /**
     * 下载资源文件
     *
     * @param url 资源文件url
     * @return
     */
    void download(String url);

}
