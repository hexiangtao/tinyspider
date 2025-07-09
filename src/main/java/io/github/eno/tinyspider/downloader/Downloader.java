package io.github.eno.tinyspider.downloader;

import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;

import java.util.concurrent.Future;

public interface Downloader {

    Future<Response> download(Request request);

    void close();
}
