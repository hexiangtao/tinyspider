package io.github.eno.tinyspider.demo.parser;

import io.github.eno.tinyspider.page.ElementParser;
import org.jsoup.nodes.Element;

public class A7djElementParser implements ElementParser {
    @Override
    public String select(Element ele) {
        return ele.attr("src");
    }
}
