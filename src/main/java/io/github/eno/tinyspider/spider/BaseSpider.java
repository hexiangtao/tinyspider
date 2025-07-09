package io.github.eno.tinyspider.spider;

import io.github.eno.tinyspider.core.Item;
import io.github.eno.tinyspider.http.Request;
import io.github.eno.tinyspider.http.Response;

import java.util.Collections;

public abstract class BaseSpider implements Spider {

    private final String name;

    protected BaseSpider(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Iterable<?> parse(Response response) {
        // Default implementation does nothing
        return Collections.emptyList();
    }
}
