package io.github.eno.tinyspider.core.base;

import io.github.eno.tinyspider.core.impl.PageCollectorImpl;

public interface PageCollector<T> {

	public static PageCollector<Page> create(int nPageCapacity) {
		return new PageCollectorImpl(nPageCapacity);
	}

	public void put(T e);

	public T take();

	public int size();

	public int limit();

}
