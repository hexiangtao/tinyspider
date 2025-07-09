package io.github.eno.tinyspider.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

public class Response {

    private final String url;
    private final int statusCode;
    private final Map<String, String> headers;
    private final byte[] body;
    private final Request request;
    private Document document;

    public Response(String url, int statusCode, Map<String, String> headers, byte[] body, Request request) {
        this.url = url;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public String getBodyAsString() {
        return new String(body);
    }

    public Request getRequest() {
        return request;
    }

    public Document getDocument() {
        if (document == null) {
            document = Jsoup.parse(getBodyAsString(), url);
        }
        return document;
    }
}
