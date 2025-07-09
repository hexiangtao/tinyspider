package io.github.eno.tinyspider.downloader;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Realm;
import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class DefaultDownloader implements Downloader {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDownloader.class);

    private final AsyncHttpClient asyncHttpClient;

    public DefaultDownloader(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    public DefaultDownloader() {
        this(new AsyncHttpClient(new AsyncHttpClientConfig.Builder().build()));
    }

    @Override
    public Future<Response> download(Request request) {
        logger.info("Downloading page: {}", request.getUrl());

        AsyncHttpClient.BoundRequestBuilder requestBuilder = createRequestBuilder(request);

        ListenableFuture<com.ning.http.client.Response> future = requestBuilder.execute();

        return new ResponseFuture(future, request);
    }

    private AsyncHttpClient.BoundRequestBuilder createRequestBuilder(Request request) {
        AsyncHttpClient.BoundRequestBuilder requestBuilder;

        if (request.getMethod() == null || "GET".equalsIgnoreCase(request.getMethod())) {
            requestBuilder = asyncHttpClient.prepareGet(request.getUrl());
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            requestBuilder = asyncHttpClient.preparePost(request.getUrl());
            if (request.getBody() != null) {
                requestBuilder.setBody(request.getBody());
            }
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + request.getMethod());
        }

        // Set headers
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            FluentCaseInsensitiveStringsMap headers = new FluentCaseInsensitiveStringsMap();
            request.getHeaders().forEach(headers::add);
            requestBuilder.setHeaders(headers);
        }

        return requestBuilder;
    }


    @Override
    public void close() {
        asyncHttpClient.close();
    }

    private static class ResponseFuture implements Future<Response> {

        private final ListenableFuture<com.ning.http.client.Response> future;
        private final Request request;

        public ResponseFuture(ListenableFuture<com.ning.http.client.Response> future, Request request) {
            this.future = future;
            this.request = request;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return future.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return future.isCancelled();
        }

        @Override
        public boolean isDone() {
            return future.isDone();
        }

        @Override
        public Response get() throws InterruptedException, java.util.concurrent.ExecutionException {
            com.ning.http.client.Response ningResponse = future.get();
            Map<String, String> headers = new HashMap<>();
            ningResponse.getHeaders().forEach(entry -> headers.put(entry.getKey(), entry.getValue().get(0)));
            try {
                return new Response(ningResponse.getUri().toString(), ningResponse.getStatusCode(), headers, ningResponse.getResponseBodyAsBytes(), request);
            } catch (java.io.IOException e) {
                throw new java.util.concurrent.ExecutionException(e);
            }
        }

        @Override
        public Response get(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
            com.ning.http.client.Response ningResponse = future.get(timeout, unit);
            Map<String, String> headers = new HashMap<>();
            ningResponse.getHeaders().forEach(entry -> headers.put(entry.getKey(), entry.getValue().get(0)));
            try {
                return new Response(ningResponse.getUri().toString(), ningResponse.getStatusCode(), headers, ningResponse.getResponseBodyAsBytes(), request);
            } catch (java.io.IOException e) {
                throw new java.util.concurrent.ExecutionException(e);
            }
        }
    }
}
