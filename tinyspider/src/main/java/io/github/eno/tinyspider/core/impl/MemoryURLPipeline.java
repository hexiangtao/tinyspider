package io.github.eno.tinyspider.core.impl;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.iyuexian.spider.util.Logger;

import io.github.eno.tinyspider.core.base.URLPipeline;

public class MemoryURLPipeline implements URLPipeline {

	private BlockingQueue<String> queue;

	private int capacity;

	public MemoryURLPipeline(int capacity, String host) {
		this.capacity = capacity;
		this.queue = new ArrayBlockingQueue<>(capacity);
		this.put(host);
	}

	@Override
	public void put(List<String> urls) {

		if (urls == null) {
			return;
		}
		for (String url : urls) {
			put(url);
		}

	}

	@Override
	public void put(String url) {
		if (url == null || url.equalsIgnoreCase("null")) {
			return;
		}
		try {
			if (contains(url)) {
				return;
			}
			Logger.debug("put {} to urlCollector", url);

			queue.offer(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String take() {

		try {
			return queue.poll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public boolean contains(String url) {
		return queue.contains(url);
	}

	@Override
	public boolean remove(String url) {
		return queue.remove(url);
	}

	@Override
	public int getLimit() {
		return capacity;
	}

}
