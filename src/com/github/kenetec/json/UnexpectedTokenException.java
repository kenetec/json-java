package com.github.kenetec.json;

/**
 * Exception thrown when consume(TokenType type) is called but
 * current token type does not match.
 */
public class UnexpectedTokenException extends Exception {
    public UnexpectedTokenException(TokenType expecting, TokenType actual) {
        super(String.format("Expecting \"%s\" but instead found \"%s\"", expecting, actual));
    }
}
