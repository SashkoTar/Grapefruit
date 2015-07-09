package org.at.diagrammer;


public class Lexer {
    String input;
    int position = 0;
    char c;
    private static final char EOF = (char) -1;

    public Lexer(String inputText) {
        this.input = inputText;
    }

    public Token getNextToken() {
        String lexeme = "";
        if(c == EOF) {
            return new Token(Token.EOF_TYPE);
        }
        c = input.charAt(position);
        eatWhitespaces();
        while (c != EOF) {
            if (isLetter()) {
                while (isLetter() || isDigit()) {
                    lexeme += c;
                    consume();
                }
                if (lexeme.toUpperCase().equals("IF")) return new Token(Token.IF);
                if (lexeme.toUpperCase().equals("ELSE")) return new Token(Token.ELSE);
                return new Token(Token.ID, lexeme);
            }

            if (isDigit()) {
                while (isDigit()) {
                    lexeme += c;
                    consume();
                }
                return new Token(Token.INT, lexeme);
            }
            if (c == ';') {
                consume();
                return new Token(Token.SEMICOLON);
            }
            if (c == '(') {
                consume();
                return new Token(Token.LPAR);
            }
            if (c == ')') {
                consume();
                return new Token(Token.RPAR);
            }
            if (c == '{') {
                consume();
                return new Token(Token.BEGIN_BLOCK);
            }
            if (c == '}') {
                consume();
                return new Token(Token.END_BLOCK);
            }
            if (c == '>') {
                consume();
                if (c == '=') {
                    consume();
                    return  new Token(Token.RELOP, ">=");
                }  else {
                    return  new Token(Token.RELOP, ">");
                }
            }
            if (c == '<') {
                consume();
                if (c == '=') {
                    consume();
                    return  new Token(Token.RELOP, "<=");
                }  else {
                    return  new Token(Token.RELOP, "<");
                }
            }
            if (c == '=') {
                consume();
                if (c == '=') {
                    consume();
                    return  new Token(Token.RELOP, "==");
                }  else {
                    return  new Token(Token.ASSIGN);
                }
            }
            if (c == '!') {
                consume();
                if (c == '=') {
                    consume();
                    return  new Token(Token.RELOP, "!=");
                }  else {
                    throw new IllegalStateException("No acceptance for char: " + c);
                }
            }

            throw new IllegalStateException("No acceptance for char: " + c);
        }
        return new Token(Token.EOF_TYPE);
    }

    private boolean isDigit() {
        return Character.isDigit(c);
    }

    private boolean isLetter() {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    private void consume() {
        ++position;
        if (position >= input.length()) {
            c = EOF;
        } else {
            c = input.charAt(position);
        }
    }

    private void eatWhitespaces() {
        while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
            consume();
        }
    }
}
