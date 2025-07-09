package io.github.eno.tinyspider.engine;

import io.github.eno.tinyspider.spider.Spider;

public class Crawl {

    private final Spider spider;

    public Crawl(Spider spider) {
        this.spider = spider;
    }

    public void run() {
        // TODO: Read settings from annotations

        try (Engine engine = Engine.create(spider)) {
            // TODO: Configure engine with settings
            engine.start();
        }
    }

    public static void main(String[] args) {
        // Example usage:
        // new Crawl(new MySpider()).run();
    }
}
