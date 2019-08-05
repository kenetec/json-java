package com.github.kenetec.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * class Lexer
 *
 * Tokenizes the com.github.kenetec.json input.
 * Keywords: true, false, null
 * Numbers are integers or floating point.
 */
class Lexer {
    private static String whitespace = " \n\t\r";
    private static String newSpaces = "\n\r";
    private static String quoteChar = "\"";
    private static String symbols = "{}[]:,";
    private static String trueStr = "true";
    private static String falseStr = "false";
    private static String nullStr = "null";

    private Scanner scanner;

    private String cs = "";
    private int currentLine = 0;

    private Boolean lastToken = false;

    Lexer(String input) {
        this.scanner = new Scanner(input);
        this.scanner.useDelimiter("");
        cs = scanner.next();
    }

    Lexer (File file) {
        try {
            this.scanner = new Scanner(file);
            this.scanner.useDelimiter("");
            cs = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    int getCurrentLine() { return currentLine; }

    Token next() throws UnexpectedCharacterException {
        if (scanner.hasNext()) {
            Token next = process();
            TokenType type = next.getType();

            if (type != TokenType.NUM && scanner.hasNext())
                cs = scanner.next();

            return next;
        } else {
            lastToken = true;
//            this.scanner.close();

//            // process last token
//            return process();
            return new Token("", TokenType.EOF);
        }
    }



    private Token process() throws UnexpectedCharacterException {
        if (whitespace.contains(cs)) {
            // WHITESPACE
            if (newSpaces.contains(cs)) {
                currentLine++;
            }

            // next char
            cs = scanner.next();

            return process();
        } else if (quoteChar.contains(cs)) {
            // STRINGS
            String build = "";
            String c;

            // build string
            while (!(c = scanner.next()).equals("\"")) {
                build += c;
            }

            return new Token(build, TokenType.STR);
        } else if (symbols.contains(cs)) {
            // SYMBOLS
            String current = cs;

            // find symbol
            switch (current) {
                case ":":
                    return new Token(current, TokenType.COLON);
                case ",":
                    return new Token(current, TokenType.COMMA);
                case "{":
                    return new Token(current, TokenType.L_BRACE);
                case "}":
                    return new Token(current, TokenType.R_BRACE);
                case "[":
                    return new Token(current, TokenType.L_BRACKET);
                case "]":
                    return new Token(current, TokenType.R_BRACKET);
                default:
                    break;
            }
        } else if (Character.isDigit(cs.charAt(0)) || cs.contains("-")) {
            // NUMBERS
            String numStr = "";

            do {
                numStr += cs;
                cs = scanner.next();
            } while ((Character.isDigit(cs.charAt(0)) || cs.contains(".")));

            // cs is at comma now
            // don't get next char

            return new Token(numStr, TokenType.NUM);
        } else if (Character.isLetter(cs.charAt(0))) {
            String targetKeyword = "";
            // KEYWORDS
            if (cs.contains("t")) {
                // true
                targetKeyword = trueStr;
            } else if (cs.contains("f")) {
                targetKeyword = falseStr;
            } else if (cs.contains("n")) {
                targetKeyword = nullStr;
            } else {
                throw new UnexpectedCharacterException("true or false or null", cs, currentLine);
            }

            String str = cs;

            for (int i = 1; i < targetKeyword.length(); i++) {
                str += scanner.next();
            }

            if (!str.equals(targetKeyword))
                throw new UnexpectedCharacterException(targetKeyword, str, currentLine);

            return new Token(str, (str.contains(trueStr) || str.contains(falseStr)) ? TokenType.BOOL : TokenType.NULL);
        }

        throw new UnexpectedCharacterException("A valid character", cs, currentLine);
    }
}
