package io.github.eno.tinyspider.spider;

import io.github.eno.tinyspider.core.Item;
import io.github.eno.tinyspider.demo.QuotesSpider;
import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuotesSpiderTest {

    @Test
    void testParse() {
        String html = "<div class=\"quote\"><span class=\"text\">The world as we have created it is a process of our thinking. It cannot be changed without changing our thinking.</span><span>by <small class=\"author\">Albert Einstein</small></span></div>" +
                "<div class=\"quote\"><span class=\"text\">It is our choices, Harry, that show what we truly are, far more than our abilities.</span><span>by <small class=\"author\">J.K. Rowling</small></span></div>" +
                "<ul class=\"pager\"><li class=\"next\"><a href=\"/page/2/\">Next <span aria-hidden=\"true\">&rarr;</span></a></li></ul>";

        QuotesSpider spider = new QuotesSpider();
        Request request = new Request("http://quotes.toscrape.com/");
        Response response = new Response(request.getUrl(), 200, null, html.getBytes(), request);

        List<Object> results = new ArrayList<>();
        spider.parse(response).forEach(results::add);

        assertEquals(3, results.size());

        // Check first quote
        Item firstQuote = (Item) results.get(0);
        assertEquals("The world as we have created it is a process of our thinking. It cannot be changed without changing our thinking.", firstQuote.get("text"));
        assertEquals("Albert Einstein", firstQuote.get("author"));

        // Check second quote
        Item secondQuote = (Item) results.get(1);
        assertEquals("It is our choices, Harry, that show what we truly are, far more than our abilities.", secondQuote.get("text"));
        assertEquals("J.K. Rowling", secondQuote.get("author"));

        // Check next page request
        assertTrue(results.get(2) instanceof Request);
        Request nextRequest = (Request) results.get(2);
        assertEquals("http://quotes.toscrape.com/page/2/", nextRequest.getUrl());
    }
}
