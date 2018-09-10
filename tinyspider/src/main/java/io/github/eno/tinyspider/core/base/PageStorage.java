package io.github.eno.tinyspider.core.base;

import io.github.eno.tinyspider.core.impl.PageStorageImpl;

public interface PageStorage<T> {

	public static PageStorage<Page> create(int nPageCapacity) {
		return new PageStorageImpl(nPageCapacity);
	}

	public void put(T e);

	public T take();

	public int size();

	public int limit();

}
