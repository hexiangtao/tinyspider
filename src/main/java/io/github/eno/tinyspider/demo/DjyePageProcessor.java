package io.github.eno.tinyspider.demo;

import io.github.eno.tinyspider.Scheduler;
import io.github.eno.tinyspider.annotation.Selector;
import io.github.eno.tinyspider.annotation.Site;
import io.github.eno.tinyspider.page.AbstractPageProcessor;
import io.github.eno.tinyspider.util.Logger;
import org.jsoup.nodes.Document;

import java.util.Set;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
@Selector(value = "audio[src]", localDir = "D:/data/music/www.djye.com", download = true)
@Site(host = "http://www.djye.com", hostContain = "djye.com")
public class DjyePageProcessor extends AbstractPageProcessor {
    private final Logger logger = Logger.getLogger(this.getClass());

    public static void main(String[] args) throws Exception {
        new Scheduler(new DjyePageProcessor()).start();
    }

    @Override
    public void process(Document doc) throws Exception {
        logger.info("process url:{}", doc.location());
        Set<String> musicUrls = select(doc);

    }


}
