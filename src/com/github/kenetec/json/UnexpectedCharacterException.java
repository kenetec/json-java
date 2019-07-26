package com.github.kenetec.json;

public class UnexpectedCharacterException extends Exception {
    public UnexpectedCharacterException(String expected, String received) {
        super(String.format("Expected \"%s\" got \"%s\" instead!", expected, received));
    }
}