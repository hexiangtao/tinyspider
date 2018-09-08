package io.github.eno.tinyspider.core.base;

import java.util.List;

import io.github.eno.tinyspider.core.impl.MemoryURLCollector;

public interface URLCollector {

	public static URLCollector create(int capacity, String host) {
		return new MemoryURLCollector(capacity, host);

	}

	public void put(List<String> urls);

	public void put(String url);

	public String take();

	public int size();

	public boolean isEmpty();

	public boolean contains(String url);

	public boolean remove(String url);
	
	public  int  getLimit();

}
