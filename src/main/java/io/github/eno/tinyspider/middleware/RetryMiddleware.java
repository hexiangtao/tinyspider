package io.github.eno.tinyspider.middleware;

import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import io.github.eno.tinyspider.spider.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryMiddleware implements DownloaderMiddleware {

    private static final Logger logger = LoggerFactory.getLogger(RetryMiddleware.class);
    public static final String RETRY_TIMES_KEY = "retry_times";

    private final int maxRetryTimes;

    public RetryMiddleware(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public void processRequest(Request request, Spider spider) {
        // Do nothing
    }

    @Override
    public Response processResponse(Response response, Spider spider) {
        return response;
    }

    @Override
    public Request processException(Request request, Exception exception, Spider spider) {
        int retryTimes = (int) request.getMeta().getOrDefault(RETRY_TIMES_KEY, 0);
        if (retryTimes < maxRetryTimes) {
            retryTimes++;
            request.putMeta(RETRY_TIMES_KEY, retryTimes);
            logger.info("Retrying request [{} time(s)]: {}", retryTimes, request.getUrl());
            return request;
        } else {
            logger.error("Gave up retrying request after {} attempts: {}", maxRetryTimes, request.getUrl(), exception);
            return null;
        }
    }
}
