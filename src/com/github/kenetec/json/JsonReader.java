package com.github.kenetec.json;

import java.io.File;


public class JsonReader {
    public static JsonObject read(String text) {
        try {
            JsonParser parser = new JsonParser(text);

            return parser.generate();
        } catch (UnexpectedCharacterException | UnexpectedTokenException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonObject read(File file) {
        try {
            JsonParser parser = new JsonParser(file);

            return parser.generate();
        } catch (UnexpectedCharacterException | UnexpectedTokenException e) {
            e.printStackTrace();
        }

        return null;
    }
}
