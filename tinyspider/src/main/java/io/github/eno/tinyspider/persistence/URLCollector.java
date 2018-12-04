package io.github.eno.tinyspider.persistence;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.github.eno.tinyspider.util.Logger;

public class URLCollector {

	private Logger logger = Logger.getLogger(URLCollector.class);

	private Set<String> history;
	private Queue<String> unFetched;

	private String host;

	private static final int maxFetchSize = Integer.MAX_VALUE;

	public boolean isEnableFetch() {
		return getHistory().size() < maxFetchSize;
	}

	private static final class Holder {
		static final URLCollector INSTANCE = new URLCollector();
	}

	public static URLCollector instance() {

		return Holder.INSTANCE;
	}

	public URLCollector host(String host) {
		this.host = host;
		this.unFetched.add(host);
		return this;
	}

	private URLCollector() {
		this.history = new HashSet<String>();
		this.unFetched = new ConcurrentLinkedQueue<String>();
	}

	public boolean isFetched(String link) {
		synchronized (URLCollector.class) {
			return history.contains(link);
		}
	}

	public void addToHistory(String url) {
		synchronized (URLCollector.class) {
			history.add(url);
		}
	}

	public void removeHistory(String url) {
		synchronized (URLCollector.class) {
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
		// if (!url.contains(host)) {
		// return;
		// }
		boolean susccess = unFetched.offer(url);
		logger.debug("offered: {} {}",  url,susccess);

	}

	public boolean isQueueEmpty() {
		return unFetched.isEmpty();
	}

	public Set<String> getHistory() {
		return history;
	}

	public Queue<String> getUnFetched() {
		return unFetched;
	}

}
