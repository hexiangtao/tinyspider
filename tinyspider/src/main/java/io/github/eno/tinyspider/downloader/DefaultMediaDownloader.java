package io.github.eno.tinyspider.downloader;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.ning.http.client.listener.TransferCompletionHandler;
import io.github.eno.tinyspider.util.Logger;
import io.github.eno.tinyspider.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public class DefaultMediaDownloader extends TransferCompletionHandler implements MediaDownloader {

    private static final Logger logger = Logger.getLogger(DefaultMediaDownloader.class);

    private static final Pattern PICTURE_REG_EX = Pattern.compile("\\d{1,3}(x|_)\\d{1,3}\\s\\.(jpg|png|JPG|PNG)$");

    private static final Set<String> COMPLETED = new HashSet<>();
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    protected static final AsyncHttpClient ASYNC_HTTP_CLIENT = new AsyncHttpClient();

    protected final String url;
    protected final String localDir;


    public DefaultMediaDownloader(String url, String localDir) {
        super(true);
        this.url = url;
        this.localDir = localDir;
    }

    @Override
    public void download(String url) {
        if (!support(url)) {
            return;
        }
        if (COMPLETED.contains(url)) {
            logger.info("重复资源，放弃下载,已下载:" + COMPLETED.size());
            return;
        }
        COMPLETED.add(url);
        try {
            executeRequest(url);
        } catch (Exception ex) {
            logger.error("down media failed url:%s,msg:%s", url, ex.getMessage());
        }
    }

    @Override
    public Response onCompleted(Response response) throws Exception {
        File f = new File(localDir, fileName());
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(response.getResponseBodyAsBytes());
        fos.flush();
        fos.close();
        int count = COUNT.incrementAndGet();
        logger.info("当前已完成:" + count);
        return super.onCompleted(response);
    }


    /**
     * 下载
     *
     * @param url
     * @throws Exception
     */
    protected void executeRequest(String url) throws Exception {
        Request req = new RequestBuilder().setUrl(url).build();
        ASYNC_HTTP_CLIENT.executeRequest(req, this);
    }

    protected String fileName() {
        return url.replaceAll("/", "_");
    }

    protected boolean support(String url) {
        return !StringUtil.isBlank(url);
    }
}
