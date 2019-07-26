package com.github.kenetec.json;

import java.util.*;

/**
 * <h1>JSON Map Generator</h1>
 * Used by JsonReader to generate a HashMap.
 */
class JsonObjectGenerator {
    private LinkedList<Scope> scopes = new LinkedList<>();

    JsonObjectGenerator() {
    }

    /**
     * Creates an key-value entry in scope.
     * @param key
     * @param val
     */
    void putInScope(String key, Object val) {
        scopes.getLast().getData().put(key, val);
    }

    /**
     * Creates a new scope.
     * @param key Used to create an entry in the previous scope.
     */
    void createScope(String key) {
        Scope newScope = new Scope(key);

        scopes.add(newScope);
    }

    /**
     * Leaves current scope and commits all data from current
     * scope to scope one level up.
     */
    void exitScope() {
        // commit current scope
        Scope toCommit = scopes.removeLast();

        scopes.getLast().getData().put(toCommit.getKey(), toCommit.getData());
    }

    /**
     * Generates a HashMap with parsed values.
     * @return the map.
     */
    JsonObject generate() {
        LinkedList<Scope> scopesCopy = (LinkedList<Scope>) scopes.clone();

        while (scopesCopy.size() > 1) {
            Scope toCommit = scopesCopy.removeLast();

            scopesCopy.getLast().getData().put(toCommit.getKey(), toCommit.getData());
        }

        HashMap<String, Object> map = scopesCopy.get(0).getData();

        return new JsonObject(map);
    }
}
