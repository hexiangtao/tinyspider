package io.github.eno.tinyspider.page;

import org.jsoup.nodes.Element;

/**
 * @author hexiangtao
 * @date 2022/6/11 15:11
 */
public class DefaultElementParser implements ElementParser {
    @Override
    public String select(Element ele) {
        return ele.text();
    }
}
