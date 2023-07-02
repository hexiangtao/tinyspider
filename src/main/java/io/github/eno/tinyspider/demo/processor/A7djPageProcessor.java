package io.github.eno.tinyspider.demo.processor;

import io.github.eno.tinyspider.Scheduler;
import io.github.eno.tinyspider.annotation.Selector;
import io.github.eno.tinyspider.annotation.Site;
import io.github.eno.tinyspider.demo.parser.A7djElementParser;
import io.github.eno.tinyspider.page.AbstractPageProcessor;
import io.github.eno.tinyspider.util.Logger;
import org.jsoup.nodes.Document;

import java.util.Set;

@Site(host = "http://www.a7dj.com/", hostContain = "a7dj.com")
@Selector(value = "audio[src]", localDir = "D:/data/music/20230628/a7dj", download = true, parser = A7djElementParser.class)
public class A7djPageProcessor extends AbstractPageProcessor {
    private final Logger logger = Logger.getLogger(this.getClass());


    public static void main(String[] args) throws Exception {
        new Scheduler(new A7djPageProcessor()).start();
    }

    @Override
    public void process(Document doc) throws Exception {
        logger.info("process url:{}", doc.location());
        Set<String> musicUrls = select(doc);
        logger.info("musicUrls:{}", musicUrls);
    }
}
