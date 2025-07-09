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
