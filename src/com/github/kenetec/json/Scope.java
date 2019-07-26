package com.github.kenetec.json;

import java.util.HashMap;

/**
 * <h1>Scope</h1>
 * Used to wrap a HashMap<String, Object> and a String key together.
 */
class Scope {
    private String key;
    private HashMap<String, Object> data = new HashMap<>();

    Scope(String key) {
        this.key = key;
    }

    String getKey() {
        return key;
    }

    HashMap<String, Object> getData() {
        return data;
    }
}