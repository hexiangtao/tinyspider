package io.github.eno.tinyspider.downloader;

import cn.hutool.http.HttpRequest;
import io.github.eno.tinyspider.demo.DownloaderUtil;
import io.github.eno.tinyspider.util.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author hexiangtao
 * @date 2022/6/12 19:29
 */
public class LocalMediaDownloader {

    private static final AtomicInteger THREAD_NUM = new AtomicInteger(0);

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "downloader-" + THREAD_NUM.incrementAndGet()));

    private static final Logger logger = Logger.getLogger(DownloaderUtil.class);

    public void download(String mediaFilePath) throws IOException {
        File file = new File(mediaFilePath);
        if (!file.exists()) {
            logger.error("本地文件不存在,path:%s", mediaFilePath);
            return;
        }
        File dir = file.getParentFile();
        List<String> lines = readUrls(mediaFilePath).stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < lines.size(); i++) {
            String url = lines.get(i);
            submitDownloadTask(dir.getAbsolutePath(), url, i + 1);
        }
    }

    private void submitDownloadTask(String dir, String url, int index) {
        THREAD_POOL_EXECUTOR.submit(() -> doDownload(dir, url, index));
    }

    private void doDownload(String dir, String url, int index) {
        try {
            String fileName = fileName(url);
            File f = new File(dir + "/download", fileName);
            if (f.exists()) {
                logger.info("文件已经存在，直接跳过,url:%s", url);
                return;
            }
            logger.info("开始下载第:%s个文件,url:%s", index, url);
            long begin = System.currentTimeMillis();
            byte[] body = HttpRequest.get(url).execute().bodyBytes();
            Files.write(f.toPath(), body, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            long usedSec = (System.currentTimeMillis() - begin) / 1000;
            logger.info("成功下载第%s个文件,用时:%s sec,url:%s", index, usedSec, url);
        } catch (Exception ex) {
            logger.error("下载异常,url:%s,message:%s", url, ex.getMessage());
        }
    }

    public List<String> readUrls(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath)).stream().distinct().collect(Collectors.toList());
    }

    public String fileName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String path = uri.getPath().substring(1).replace("m4adj", "").substring(1);
        return path.replaceAll("/", "_");
    }


}
