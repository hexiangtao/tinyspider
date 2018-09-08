package io.github.eno.tinyspider.core.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.iyuexian.spider.util.Logger;

import io.github.eno.tinyspider.core.base.Page;
import io.github.eno.tinyspider.core.base.PageCollector;

public class PageCollectorImpl implements PageCollector<Page> {

	private BlockingQueue<Page> queue;

	private int capacity;

	public PageCollectorImpl(int nPageCapacity) {
		this.capacity = nPageCapacity;
		this.queue = new ArrayBlockingQueue<>(nPageCapacity);
	}

	public void put(Page page) {
		try {

			Logger.debug("put  page to PageCollector:{}", page.getUrl());
			queue.offer(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page take() {

		try {
			return queue.poll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public int limit() {
		return this.capacity;
	}

}
