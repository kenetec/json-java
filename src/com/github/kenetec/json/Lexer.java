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
    private static String whitespace = " \n\t";
    private static String quoteChar = "\"";
    private static String symbols = "{}[]:,";
    private static String trueStr = "true";
    private static String falseStr = "false";
    private static String nullStr = "null";

    private Scanner scanner;

    Lexer(String input) {
        this.scanner = new Scanner(input);
        this.scanner.useDelimiter("");
    }

    Lexer (File file) {
        try {
            this.scanner = new Scanner(file);
            this.scanner.useDelimiter("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Token next() throws UnexpectedCharacterException {
        if (scanner.hasNext()) {
            String cs = scanner.next();

            if (whitespace.contains(cs)) {
                // WHITESPACE
                return next();
            } else if (quoteChar.contains(cs)) {
                // STRINGS
                cs = "";
                String c2;

                // build string
                while (!(c2 = scanner.next()).equals("\"")) {
                    cs += c2;
                }

                // skip ending quote

                return new Token(cs, TokenType.STR);
            } else if (symbols.contains(cs)) {
                // SYMBOLS
                switch (cs) {
                    case ":":
                        return new Token(cs, TokenType.COLON);
                    case ",":
                        return new Token(cs, TokenType.COMMA);
                    case "{":
                        return new Token(cs, TokenType.L_BRACE);
                    case "}":
                        return new Token(cs, TokenType.R_BRACE);
                    case "[":
                        return new Token(cs, TokenType.L_BRACKET);
                    case "]":
                        return new Token(cs, TokenType.R_BRACKET);
                    default:
                        break;
                }
            } else if (Character.isDigit(cs.charAt(0))) {
                // NUMBERS
                String numStr = "";

                while ((Character.isDigit(cs.charAt(0)) || cs.contains("."))) {
                    numStr += cs;
                    cs = scanner.next();
                }

                // cs is at comma now


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
                    throw new UnexpectedCharacterException("true or false or null", cs);
                }

                String str = cs;

                for (int i = 1; i < targetKeyword.length(); i++) {
                    str += scanner.next();
                }

                if (!str.equals(targetKeyword))
                    throw new UnexpectedCharacterException(targetKeyword, str);
                
                return new Token(str, (str.contains(trueStr) || str.contains(falseStr)) ? TokenType.BOOL : TokenType.NULL);
            }

            return new Token("", TokenType.EOF);
        }

        return new Token("", TokenType.EOF);
    }
}
