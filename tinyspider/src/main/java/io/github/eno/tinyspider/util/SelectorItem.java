package io.github.eno.tinyspider.util;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:32
 */
public class SelectorItem {
    private String source;
    private String selector;
    private String attr;
    private boolean download;

    public SelectorItem(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }
}
