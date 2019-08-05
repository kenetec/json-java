package com.github.kenetec.json;

/**
 * Exception thrown when consume(TokenType type) is called but
 * current token type does not match.
 */
class UnexpectedTokenException extends Exception {
    UnexpectedTokenException(TokenType expected, TokenType received, int line) {
        super(String.format("[Line %d] Expected \"%s\" got \"%s\" instead!", line, expected, received));
    }
}
