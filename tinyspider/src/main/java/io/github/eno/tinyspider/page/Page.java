package io.github.eno.tinyspider.page;

import org.jsoup.nodes.Document;

import java.util.Set;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public interface Page {

    byte[] get();

    Document html();

    Set<String> links();

    String getUrl();

    String title();

    static Page of(Document doc) {
        return new PageImpl(doc);
    }

}
