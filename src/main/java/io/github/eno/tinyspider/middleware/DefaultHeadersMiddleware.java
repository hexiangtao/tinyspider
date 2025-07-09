package io.github.eno.tinyspider.middleware;

import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import io.github.eno.tinyspider.spider.Spider;

import java.util.Map;

public class DefaultHeadersMiddleware implements DownloaderMiddleware {

    private final Map<String, String> defaultHeaders;

    public DefaultHeadersMiddleware(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    @Override
    public void processRequest(Request request, Spider spider) {
        for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
            if (!request.getHeaders().containsKey(entry.getKey())) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Response processResponse(Response response, Spider spider) {
        return response;
    }

    @Override
    public Request processException(Request request, Exception exception, Spider spider) {
        return null;
    }
}
