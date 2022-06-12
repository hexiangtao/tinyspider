package io.github.eno.tinyspider.page;

import org.jsoup.nodes.Element;

/**
 * @author hexiangtao
 * @date 2022/6/11 15:07
 */
public interface ElementParser {

    String select(Element ele);
}
