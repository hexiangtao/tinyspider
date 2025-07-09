package io.github.eno.tinyspider.scheduler;

import io.github.eno.tinyspider.http.Request;

public interface Scheduler {

    void push(Request request);

    Request poll();

    boolean hasNext();

}
