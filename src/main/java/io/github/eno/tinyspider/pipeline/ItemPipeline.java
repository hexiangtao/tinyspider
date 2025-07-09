package io.github.eno.tinyspider.pipeline;

import io.github.eno.tinyspider.core.Item;
import io.github.eno.tinyspider.spider.Spider;

public interface ItemPipeline {

    void processItem(Item item, Spider spider);

}
