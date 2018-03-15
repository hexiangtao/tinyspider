package com.iyuexian.spider.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import com.iyuexian.spider.demo.City;

public class LinkStorage {

	
	
	public static void main(String[] args) {

		LinkStorage.instance().offer("000");
		LinkStorage.instance().offer("000");
		System.out.println(LinkStorage.instance().getUnFetched().size());

	}

	private Set<String> fetched;
	private Queue<String> unFetched;

	private static final int maxFetchSize = 100000;

	public boolean isEnableFetch() {
		return LinkStorage.instance().getFetched().size() < maxFetchSize;
	}

	private static final class Holder {
		static final LinkStorage INSTANCE = new LinkStorage();
	}

	public static LinkStorage instance() {

		return Holder.INSTANCE;
	}

	private LinkStorage() {
		this.fetched = new HashSet<String>();
		this.unFetched = new ArrayBlockingQueue<String>(maxFetchSize / 100);
	}

	public boolean isFetched(String link) {
		synchronized (LinkStorage.class) {
			return fetched.contains(link);
		}
	}

	public void putFetched(String url) {
		synchronized (LinkStorage.class) {
			fetched.add(url);
		}
	}

	public void removeFetched(String url) {
		synchronized (LinkStorage.class) {
			fetched.remove(url);
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
		if (unFetched.contains(url)) {
			return;
		}
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
