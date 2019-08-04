package com.github.kenetec.json;

import java.util.*;
import java.io.File;

public class Parser {
    private Lexer lexer;
    private Token currentToken;

    // Create a controllable nested hashmap class
    private JsonObjectGenerator jsonObjectGenerator = new JsonObjectGenerator();

    public Parser(String input) {
        this.lexer = new Lexer(input);
    }

    public Parser(File file) {
        this.lexer = new Lexer(file);
    }

    public JsonObject generate() throws UnexpectedTokenException, UnexpectedCharacterException {
        this.currentToken = lexer.next();

        startBlock();

        return jsonObjectGenerator.generate();
    }

    /**
     * Consume given token type
     * @param tokenType from TokenType Enum
     * @return Token
     * @throws UnexpectedTokenException
     */
    private Token consume(TokenType tokenType) throws UnexpectedTokenException {

        if (currentToken.getType() == tokenType) {
            Token ret = currentToken;

            //System.out.println(ret.getType());

            try {
                currentToken = lexer.next();
            } catch (UnexpectedCharacterException e) {
                e.printStackTrace();

                // exit
                System.exit(-1);
            }

            return ret;
        }

        throw new UnexpectedTokenException(tokenType, currentToken.getType());
    }

    private boolean found(TokenType tokenType) {
        return currentToken.getType() == tokenType;
    }

    /**
     * void startBlock()
     *
     * Called at the start of the file
     */
    private void startBlock() throws UnexpectedTokenException {
        // Look for pair or '}'
        consume(TokenType.L_BRACE);

        jsonObjectGenerator.createScope("");

        while (found(TokenType.STR))
            pair();

        consume(TokenType.R_BRACE);
        consume(TokenType.EOF);
    }

    /**
     * void block(String key)
     *
     * Called at the start of '{'
     */
    private void block(String key) throws UnexpectedTokenException {
        // Look for pair or '}'
        consume(TokenType.L_BRACE);

        jsonObjectGenerator.createScope(key);

        while (found(TokenType.STR))
            pair();

        consume(TokenType.R_BRACE);

        jsonObjectGenerator.exitScope();
    }

    /**
     * void pair()
     *
     * Searches for the "key": "value" || '{' || '[' pair
     */
    private void pair() throws UnexpectedTokenException {
        // consume key
        String key = consume(TokenType.STR).getData();

        // consume colon
        consume(TokenType.COLON);

        // consume value
        if (found(TokenType.STR)) {
            Token value = consume(TokenType.STR);

            jsonObjectGenerator.putInScope(key, value.getData());
        } else if (found(TokenType.NUM)) {
            Token token = consume(TokenType.NUM);
            double val = Double.parseDouble(token.getData());

            jsonObjectGenerator.putInScope(key, val);
        } else if (found(TokenType.BOOL)) {
            Token token = consume(TokenType.BOOL);
            Boolean val = Boolean.parseBoolean(token.getData());

            jsonObjectGenerator.putInScope(key, val);
        } else if (found(TokenType.NULL)) {
            Token token = consume(TokenType.NULL);

            jsonObjectGenerator.putInScope(key, null);
        } else if (found(TokenType.L_BRACE)) {
            block(key);
        } else if (found(TokenType.L_BRACKET)) {
            array(key);
        }

        if (found(TokenType.COMMA)) {
            consume(TokenType.COMMA);

            pair();
        }
    }

    /**
     * void array()
     * @throws UnexpectedTokenException
     *
     * Gets array strings
     */
    private void array(String key) throws UnexpectedTokenException {
        // consume left bracket
        consume(TokenType.L_BRACKET);

        // Array entry
        ArrayList<String> entries = new ArrayList<>();

        while (!found(TokenType.R_BRACKET)) {
            Token entry = null;

            if (found(TokenType.STR)) {
                entry = consume(TokenType.STR);
            } else if (found(TokenType.NUM)) {
                entry = consume(TokenType.NUM);
            } else if (found(TokenType.BOOL)) {
                entry = consume(TokenType.BOOL);
            } else if (found(TokenType.L_BRACE)) {
                // object
                // to be continued.
            } else if (found(TokenType.L_BRACKET)) {
                // list
                // to be continued.
            }

            // add entry
            if (entry != null) {
                entries.add(entry.getData().toString());
            } else {
                //throw new UnexpectedTokenException();
            }

            if (found(TokenType.COMMA)) {
                consume(TokenType.COMMA);
            } else {
                break;
            }
        }

        jsonObjectGenerator.putInScope(key, entries);

        // consume right bracket
        consume(TokenType.R_BRACKET);
    }

    /**
     * void arrayEntry()
     * @throws UnexpectedTokenException
     *
     * Get array entry " ",
     */
    private Token arrayStrEntry() throws UnexpectedTokenException {
        Token entry = consume(TokenType.STR);



        return entry;
    }
}