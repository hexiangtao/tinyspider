# tinyspider

A lightweight, extensible web scraping framework for Java.

## Features

-   Modular architecture (Engine, Scheduler, Downloader, Spider, Pipeline, Middleware)
-   Asynchronous I/O using `async-http-client`
-   Extensible with Middlewares and Pipelines
-   Easy to use

## Quick Start

### 1. Add dependency

```xml
<dependency>
    <groupId>com.iyuexian.tinyspider</groupId>
    <artifactId>tinyspider</artifactId>
    <version>0.1.0</version>
</dependency>
```

### 2. Create a Spider

Create a class that extends `io.github.eno.tinyspider.spider.BaseSpider`.

```java
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
    public Iterable<Item> parse(Response response) {
        List<Item> items = new ArrayList<>();

        // Extract quotes
        for (Element quoteElement : response.getDocument().select("div.quote")) {
            Item item = new Item();
            item.put("text", quoteElement.select("span.text").text());
            item.put("author", quoteElement.select("small.author").text());
            items.add(item);
        }

        // Find next page
        Element nextLink = response.getDocument().select("li.next > a").first();
        if (nextLink != null) {
            String nextPageUrl = nextLink.absUrl("href");
            items.add((Item) new Request(nextPageUrl));
        }

        return items;
    }
}
```

### 3. Run the Spider

```java
package io.github.eno.tinyspider.demo;

import io.github.eno.tinyspider.engine.Engine;
import io.github.eno.tinyspider.pipeline.ConsolePipeline;

public class Main {

    public static void main(String[] args) {
        QuotesSpider spider = new QuotesSpider();
        try (Engine engine = Engine.create(spider)) {
            engine.addPipeline(new ConsolePipeline());
            engine.start();
        }
    }
}
```



