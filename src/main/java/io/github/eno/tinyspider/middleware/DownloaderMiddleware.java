package io.github.eno.tinyspider.middleware;

import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import io.github.eno.tinyspider.spider.Spider;

public interface DownloaderMiddleware {

    void processRequest(Request request, Spider spider);

    Response processResponse(Response response, Spider spider);

    Request processException(Request request, Exception exception, Spider spider);

}
