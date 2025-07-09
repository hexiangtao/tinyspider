package io.github.eno.tinyspider.spider;

import io.github.eno.tinyspider.core.Item;
import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;

public interface Spider {

    String getName();

    Iterable<Request> startRequests();

    Iterable<?> parse(Response response);

}
