package io.github.eno.tinyspider.persistence;

import io.github.eno.tinyspider.util.Logger;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public class UrlCollector {

    private final Logger logger = Logger.getLogger(UrlCollector.class);

    private final Set<String> history;
    private final Queue<String> unFetched;

    private String host;

    private static final int maxFetchSize = Integer.MAX_VALUE;

    public boolean isEnableFetch() {
        return getHistory().size() < maxFetchSize;
    }

    private static final class Holder {
        static final UrlCollector INSTANCE = new UrlCollector();
    }

    public static UrlCollector instance() {

        return Holder.INSTANCE;
    }

    public UrlCollector host(String host) {
        this.host = host;
        this.unFetched.add(host);
        return this;
    }

    private UrlCollector() {
        this.history = new HashSet<String>();
        this.unFetched = new ConcurrentLinkedQueue<String>();
    }

    public boolean isFetched(String link) {
        synchronized (UrlCollector.class) {
            return history.contains(link);
        }
    }

    public void addToHistory(String url) {
        synchronized (UrlCollector.class) {
            history.add(url);
        }
    }

    public void removeHistory(String url) {
        synchronized (UrlCollector.class) {
            history.remove(url);
        }
    }

    public String poll() {
        String url = unFetched.poll();
        addToHistory(url);
        return url;
    }

    public void offer(Set<String> links) {
        for (String link : links) {
            offer(link);
        }
    }

    public void offer(String url) {
        if (url == null || url.trim().length() == 0) {
            return;
        }
        if (history.contains(url)) {
            return;
        }
        unFetched.offer(url);

    }

    public boolean isEmpty() {
        return unFetched.isEmpty();
    }

    public Set<String> getHistory() {
        return history;
    }

    public Queue<String> getUnFetched() {
        return unFetched;
    }

}
