package io.github.eno.tinyspider.demo;

import io.github.eno.tinyspider.downloader.DefaultMediaDownloader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public class Dj72MediaDownloader extends DefaultMediaDownloader {


    public Dj72MediaDownloader(String url, String localDir) {
        super(url, localDir);
    }

    @Override
    public void executeRequest(String url) throws Exception {
        String mediaUrl = Dj72ElementParser.PREFIX + url;
        super.executeRequest(mediaUrl);
        Files.writeString(Path.of(localDir + "/" + "links.txt"), mediaUrl + "\r\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
    }

}
