package io.github.eno.tinyspider.page;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public class PageImpl implements Page {

    private Document doc;

    public PageImpl(Document doc) {
        this.doc = doc;
    }

    @Override
    public byte[] get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Document html() {
        return doc;
    }

    @Override
    public String getUrl() {
        return doc.baseUri();
    }

    @Override
    public String title() {
        return doc.title();
    }

    @Override
    public Set<String> links() {

        Set<String> links = new HashSet<String>();
        Elements eles = doc.select("a[href]");
        for (Element element : eles) {
            links.add(extractLink(element));
        }
        return links;
    }

    private String extractLink(Element element) {
        String link = element.attr("abs:href").trim();
        if (link == null || link.trim().length() == 0) {
            return null;
        }
        link = link.contains("#") ? link.substring(0, link.indexOf("#")) : link;
        return link;

    }

    @Override
    public String toString() {
        return doc.html();
    }

}
