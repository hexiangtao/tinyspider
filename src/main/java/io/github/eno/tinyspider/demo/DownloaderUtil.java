package io.github.eno.tinyspider.demo;

import io.github.eno.tinyspider.downloader.LocalMediaDownloader;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author hexiangtao
 * @date 2022/6/12 15:30
 */
public class DownloaderUtil {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String path = "C:/Users/edz/Desktop/music/bak/links.txt";
        LocalMediaDownloader localMediaDownloader = new LocalMediaDownloader();
        localMediaDownloader.download(path);
    }


}
