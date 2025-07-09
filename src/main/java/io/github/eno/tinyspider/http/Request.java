package io.github.eno.tinyspider.http;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private final String url;
    private final String method;
    private Map<String, String> headers;
    private Map<String, Object> cookies;
    private String body;
    private Map<String, Object> meta;

    public Request(String url) {
        this(url, "GET");
    }

    public Request(String url, String method) {
        this.url = url;
        this.method = method;
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
        this.meta = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Request addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public Map<String, Object> getCookies() {
        return cookies;
    }

    public Request setCookies(Map<String, Object> cookies) {
        this.cookies = cookies;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Request setBody(String body) {
        this.body = body;
        return this;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public Request setMeta(Map<String, Object> meta) {
        this.meta = meta;
        return this;
    }

    public Request putMeta(String key, Object value) {
        this.meta.put(key, value);
        return this;
    }

    public Object getMeta(String key) {
        return this.meta.get(key);
    }
}
