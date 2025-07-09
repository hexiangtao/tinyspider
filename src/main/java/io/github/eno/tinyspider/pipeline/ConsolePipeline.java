package io.github.eno.tinyspider.pipeline;

import io.github.eno.tinyspider.core.Item;
import io.github.eno.tinyspider.spider.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsolePipeline implements ItemPipeline {

    private static final Logger logger = LoggerFactory.getLogger(ConsolePipeline.class);

    @Override
    public void processItem(Item item, Spider spider) {
        logger.info("Got item from spider '{}': {}", spider.getName(), item);
    }
}
