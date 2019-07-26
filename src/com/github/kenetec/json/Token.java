package com.github.kenetec.json;

enum TokenType {
    STR,
    NUM,
    BOOL,
    NULL,
    L_BRACE,
    R_BRACE,
    L_BRACKET,
    R_BRACKET,
    COMMA,
    COLON,
    EOF
};

class Token {
    private TokenType type;
    private String data;

    Token(String data, TokenType type) {
        this.data = data;
        this.type = type;
    }

    TokenType getType() { return type; }
    String getData() { return data; };
}