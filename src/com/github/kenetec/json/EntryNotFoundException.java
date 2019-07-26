package com.github.kenetec.json;

/**
 * <h1>Entry Not Found Exception</h1>
 * Thrown when JsonObject cannot find a given path (::getFromPath).
 */
public class EntryNotFoundException extends Exception {
    public EntryNotFoundException(String path) {
        super("\"" + path + "\" could not be located in com.github.kenetec.json object.");
    }
}
