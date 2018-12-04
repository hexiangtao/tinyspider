package io.github.eno.tinyspider.page;

import java.util.Set;

import org.jsoup.nodes.Document;

public interface Page {

	public byte[] get();

	public Set<String> links();

	public String getUrl();

	public String title();

	public static Page of(Document doc) {
		return new PageImpl(doc);
	}

}
