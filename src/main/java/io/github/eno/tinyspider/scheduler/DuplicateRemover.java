package io.github.eno.tinyspider.scheduler;

import io.github.eno.tinyspider.http.Request;

public interface DuplicateRemover {

    boolean isDuplicate(Request request);

    void reset();

    int getTotalRequestsCount();
}
