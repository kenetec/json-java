package com.github.kenetec.json;

import java.io.File;


public class JsonReader {
    public static JsonObject read(String text) {
        try {
            Parser parser = new Parser(text);

            return parser.generate();
        } catch (UnexpectedCharacterException | UnexpectedTokenException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonObject read(File file) {
        try {
            Parser parser = new Parser(file);

            return parser.generate();
        } catch (UnexpectedCharacterException | UnexpectedTokenException e) {
            e.printStackTrace();
        }

        return null;
    }
}
