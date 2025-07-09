package io.github.eno.tinyspider.core;

import java.util.HashMap;
import java.util.Map;

public class Item {

    private final Map<String, Object> fields = new HashMap<>();

    public void put(String key, Object value) {
        this.fields.put(key, value);
    }

    public Object get(String key) {
        return this.fields.get(key);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Item{" +
                "fields=" + fields +
                '}';
    }
}
