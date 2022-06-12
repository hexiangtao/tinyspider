package io.github.eno.tinyspider.page;

import io.github.eno.tinyspider.annotation.Selector;
import io.github.eno.tinyspider.annotation.Site;
import io.github.eno.tinyspider.downloader.MediaDownloader;
import io.github.eno.tinyspider.persistence.UrlCollector;
import io.github.eno.tinyspider.util.Logger;
import io.github.eno.tinyspider.util.SelectorItem;
import io.github.eno.tinyspider.util.SelectorParser;
import io.github.eno.tinyspider.util.StringUtil;
import org.assertj.core.util.Lists;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public abstract class AbstractPageProcessor implements PageProcessor {

    private static final Logger logger = Logger.getLogger(AbstractPageProcessor.class);


    @Override
    public void process(Page page) throws Exception {
        Set<String> urls = page.links().stream().filter(this::matched).collect(Collectors.toSet());
        UrlCollector.instance().offer(urls);
        process(page.html());

    }

    private boolean matched(String url) {
        if (StringUtil.isBlank(url)) {
            return false;
        }
        Site annotation = this.getClass().getAnnotation(Site.class);
        Set<String> filterUrl = new HashSet<>(Arrays.asList(annotation.hostContain()));
        return filterUrl.stream().anyMatch(url::contains);
    }

    public Set<String> select(Document doc) throws Exception {
        List<SelectorItem> selectors = getSelectors();
        Set<String> contents = new HashSet<>();
        for (SelectorItem selectorItem : selectors) {
            contents.addAll(extractedElementsValue(doc, selectorItem));
        }
        Selector selector = this.getClass().getAnnotation(Selector.class);
        if (!selector.download()) {
            return contents;
        }

        Class<? extends MediaDownloader> downloaderClazz = selector.downloader();
        for (String url : contents) {
            MediaDownloader downloader = downloaderClazz.getDeclaredConstructor(String.class, String.class).newInstance(url, selector.localDir());
            downloader.download(url);
        }
        return contents;
    }

    private List<String> extractedElementsValue(Document doc, SelectorItem selectorItem) throws Exception {
        List<String> contents = Lists.newArrayList();
        Elements elements = doc.select(selectorItem.getSelector());
        for (Element element : elements) {
            String val = extractElementValue(element, selectorItem);
            contents.add(val);
        }
        Set<String> collect = contents.stream().filter(o -> !StringUtil.isBlank(o)).collect(Collectors.toSet());
        logger.info("提取:{}", collect);
        return contents;
    }

    /**
     * 提取元素值
     *
     * @param ele
     * @param selectorItem
     * @return
     */
    private String extractElementValue(Element ele, SelectorItem selectorItem) throws Exception {
        if (StringUtil.isBlank(selectorItem.getAttr())) {
            return ele.text();
        }
        String attr = selectorItem.getAttr();

        Selector selector = this.getClass().getAnnotation(Selector.class);
        String attrValue = selector.parser() != null ? selector.parser().getDeclaredConstructor().newInstance().select(ele) : ele.attr(attr);
        if (StringUtil.isBlank(attrValue)) {
            return "";
        }


        return attrValue;
    }

    /**
     * 获取选择器
     *
     * @return
     */
    private List<SelectorItem> getSelectors() {
        List<SelectorItem> list = new ArrayList<>();
        Selector selector = this.getClass().getAnnotation(Selector.class);
        if (selector == null || selector.value() == null || selector.value().length == 0) {
            return list;
        }
        for (String source : selector.value()) {
            list.add(SelectorParser.parse(source));
        }
        return list;
    }

    /**
     * 子类处理具体业务逻辑
     *
     * @param doc
     * @throws Exception
     */
    public abstract void process(Document doc) throws Exception;


}
