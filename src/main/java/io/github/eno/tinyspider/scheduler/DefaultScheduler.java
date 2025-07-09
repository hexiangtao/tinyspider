package io.github.eno.tinyspider.scheduler;

import io.github.eno.tinyspider.http.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultScheduler implements Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultScheduler.class);

    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final DuplicateRemover duplicateRemover = new HashSetDuplicateRemover();

    @Override
    public void push(Request request) {
        if (!duplicateRemover.isDuplicate(request)) {
            logger.debug("Pushing request to queue: {}", request.getUrl());
            queue.add(request);
        }
    }

    @Override
    public Request poll() {
        return queue.poll();
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
