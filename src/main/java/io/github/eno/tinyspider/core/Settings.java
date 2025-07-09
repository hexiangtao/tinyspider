package io.github.eno.tinyspider.core;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private final Map<String, Object> values = new HashMap<>();

    public void set(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {
        return values.get(name);
    }

    public String getString(String name) {
        return (String) get(name);
    }

    public int getInt(String name, int defaultValue) {
        Object value = get(name);
        if (value == null) {
            return defaultValue;
        }
        return (int) value;
    }
}
