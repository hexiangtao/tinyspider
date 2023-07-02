package io.github.eno.tinyspider.demo.processor;

import io.github.eno.tinyspider.Scheduler;
import io.github.eno.tinyspider.annotation.Site;
import io.github.eno.tinyspider.page.AbstractPageProcessor;
import io.github.eno.tinyspider.page.ElementParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Site(host = "https://www.aigei.com/", hostContain = "aigei")
public class AiGeiPageProcessor extends AbstractPageProcessor implements ElementParser {
    @Override
    public String select(Element ele) {
        return null;
    }

    @Override
    public void process(Document doc) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        new Scheduler(new AiGeiPageProcessor()).start();
    }
}
