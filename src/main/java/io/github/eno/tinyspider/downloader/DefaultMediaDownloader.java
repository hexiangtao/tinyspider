package io.github.eno.tinyspider.downloader;

import com.ning.http.client.*;
import com.ning.http.client.listener.TransferCompletionHandler;
import io.github.eno.tinyspider.util.Logger;
import io.github.eno.tinyspider.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public class DefaultMediaDownloader extends TransferCompletionHandler implements MediaDownloader {

    private static final Logger logger = Logger.getLogger(DefaultMediaDownloader.class);
    private static final AtomicInteger THREAD_NUM = new AtomicInteger(0);

    private static final Pattern PICTURE_REG_EX = Pattern.compile("\\d{1,3}(x|_)\\d{1,3}\\s\\.(jpg|png|JPG|PNG)$");

    private static final Set<String> WAIT_DOWNLOAD = Collections.synchronizedSet(new HashSet<>());
    private static final Set<String> COMPLETED = Collections.synchronizedSet(new HashSet<>());
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private static final AsyncHttpClient ASYNC_HTTP_CLIENT;
    private static ThreadPoolExecutor downloadThreadPool = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "downloader-" + THREAD_NUM.incrementAndGet()));

    static {
        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder().setAllowPoolingConnections(true).setMaxConnections(100);
        ASYNC_HTTP_CLIENT = new AsyncHttpClient(builder.build());
    }

    protected final String url;
    protected final String localDir;


    public DefaultMediaDownloader(String url, String localDir) {
        super(false);
        this.url = url;
        this.localDir = localDir;
    }

    @Override
    public void download(String url) {
        if (!support(url)) {
            return;
        }
        if (WAIT_DOWNLOAD.contains(url) || COMPLETED.contains(url)) {
            logger.info("重复资源，放弃下载,待下载队列长度:" + WAIT_DOWNLOAD.size());
            return;
        }
        WAIT_DOWNLOAD.add(url);
        saveLinks(url);
        try {
            downloadThreadPool.submit(() -> executeRequest(url));
        } catch (Exception ex) {
            logger.error("down media failed url:%s,msg:%s", url, ex.getMessage());
        }
    }

    @Override
    public Response onCompleted(Response response) throws Exception {
        File f = new File(localDir, fileName(response));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(response.getResponseBodyAsBytes());
        fos.flush();
        fos.close();
        int count = COUNT.incrementAndGet();
        WAIT_DOWNLOAD.remove(url);
        COMPLETED.add(url);
        logger.info("当前已完成下载:" + count);
        return super.onCompleted(response);
    }


    /**
     * 下载
     *
     * @param url
     * @throws Exception
     */
    protected void executeRequest(String url) {
        Request req = new RequestBuilder().setUrl(url).build();
        ASYNC_HTTP_CLIENT.executeRequest(req, this);
    }

    protected String fileName(Response response) {
        String path = response.getUri().getPath();
        return path.replaceAll("/", "_");
    }

    protected boolean support(String url) {
        return !StringUtil.isBlank(url);
    }

    private void saveLinks(String url) {
        try {
            synchronized (DefaultMediaDownloader.class) {
                Files.writeString(Path.of(localDir + "/links.txt"), url + "\r\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
