package io.github.eno.tinyspider.page;

import org.jsoup.nodes.Document;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public class PageProcessorImpl extends AbstractPageProcessor implements PageProcessor {

    public PageProcessorImpl() {
    }

    @Override
    public void process(Document doc) throws Exception {
        select(doc);
    }
}
