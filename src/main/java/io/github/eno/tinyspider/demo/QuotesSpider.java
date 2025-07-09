package io.github.eno.tinyspider.demo;

import io.github.eno.tinyspider.core.Item;
import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import io.github.eno.tinyspider.spider.BaseSpider;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuotesSpider extends BaseSpider {

    public QuotesSpider() {
        super("quotes");
    }

    @Override
    public Iterable<Request> startRequests() {
        return Collections.singletonList(new Request("http://quotes.toscrape.com/"));
    }

    @Override
    public Iterable<?> parse(Response response) {
        List<Object> results = new ArrayList<>();

        // Extract quotes
        for (Element quoteElement : response.getDocument().select("div.quote")) {
            Item item = new Item();
            item.put("text", quoteElement.select("span.text").text());
            item.put("author", quoteElement.select("small.author").text());
            results.add(item);
        }

        // Find next page
        Element nextLink = response.getDocument().select("li.next > a").first();
        if (nextLink != null) {
            String nextPageUrl = nextLink.absUrl("href");
            results.add(new Request(nextPageUrl));
        }

        return results;
    }
}
