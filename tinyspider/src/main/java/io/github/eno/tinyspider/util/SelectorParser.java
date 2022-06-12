package io.github.eno.tinyspider.util;

public class SelectorParser {

    public static SelectorItem parse(String source) {
        if (source == null || source.trim().length() == 0) {
            return null;
        }
        SelectorItem item = new SelectorItem(source);
        if (source.indexOf('[') <= 0 || source.indexOf(']') <= 0) {
            item.setSelector(source);
            return item;
        }
        int begin = source.indexOf('['), end = source.indexOf(']');
        String selector = source.substring(0, begin);
        String attr = source.substring(begin + 1, end);

        item.setSelector(selector);
        item.setAttr(attr);
        boolean download = source.endsWith(".download") ? true : false;
        item.setDownload(download);
        return item;

    }


}
