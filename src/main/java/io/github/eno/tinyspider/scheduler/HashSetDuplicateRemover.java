package io.github.eno.tinyspider.scheduler;

import io.github.eno.tinyspider.http.Request;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HashSetDuplicateRemover implements DuplicateRemover {

    private final Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public boolean isDuplicate(Request request) {
        return !urls.add(request.getUrl());
    }

    @Override
    public void reset() {
        urls.clear();
    }

    @Override
    public int getTotalRequestsCount() {
        return urls.size();
    }
}
