package io.github.eno.tinyspider.core.base;

import java.util.List;

import org.jsoup.nodes.Document;

import io.github.eno.tinyspider.core.impl.PageImpl;

public interface Page {

	public byte[] get();

	public List<String> links();

	public String getUrl();

	public String title();

	public static Page of(Document doc) {
		return new PageImpl(doc);
	}

}
