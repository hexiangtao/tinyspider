package com.iyuexian.spider;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Links {
	private Set<String> fetched;
	private Queue<String> unFetched;

	private static final class Holder {
		static final Links INSTANCE = new Links();
	}

	public static Links instance() {

		return Holder.INSTANCE;
	}

	private Links() {
		this.fetched = new HashSet<String>();
		this.unFetched = new ConcurrentLinkedQueue<String>();
	}

	public boolean isFetched(String link) {
		synchronized (Links.class) {
			return fetched.contains(link);
		}
	}

	public void setFetched(String url) {
		synchronized (Links.class) {
			fetched.add(url);
		}
	}

	public String poll() {
		return unFetched.poll();
	}

	public void offer(Set<String> links) {
		for (String link : links) {
			offer(link);
		}
	}

	public void offer(String url) {
		unFetched.offer(url);
	}

	public boolean isQueueEmpty() {
		return unFetched.isEmpty();
	}

	public Set<String> getFetched() {
		return fetched;
	}

	public Queue<String> getUnFetched() {
		return unFetched;
	}

}
