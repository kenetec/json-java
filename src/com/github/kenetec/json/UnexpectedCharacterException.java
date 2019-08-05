package com.github.kenetec.json;

class UnexpectedCharacterException extends Exception {
    UnexpectedCharacterException(String expected, String received, int line) {
        super(String.format("[Line %d] Expected \"%s\" got \"%s\" instead!", line, expected, received));
    }
}