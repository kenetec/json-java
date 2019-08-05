package com.github.kenetec.json;

/**
 * <h1>Entry Not Found Exception</h1>
 * Thrown when JsonObject cannot find a given path (::getFromPath).
 */
class EntryNotFoundException extends Exception {
    EntryNotFoundException(String path) {
        super("\"" + path + "\" could not be located in com.github.kenetec.json object.");
    }
}
