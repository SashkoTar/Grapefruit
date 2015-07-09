package org.at.diagrammer;


public class Token {
    public static final int EOF_TYPE = 0;
    public static final int IF = 1;
    public static final int ELSE = 2;
    public static final int LPAR = 3;
    public static final int RPAR = 4;
    public static final int BEGIN_BLOCK = 5;
    public static final int END_BLOCK = 6;
    public static final int RELOP = 7;
    public static final int ASSIGN = 8;
    public static final int ID = 9;
    public static final int INT = 10;
    public static final int SEMICOLON = 11;

    private static final String [] tokens = {"EOF", "IF", "ELSE", "(", ")", "{", "}", "RELOP", "=", "ID", "INT", ";"};

    private int type;
    private String lexeme;

    public Token(int type) {
        this.type = type;
    }

    public Token() {
    }

    public Token(int type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public int getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public static String getTokenString(int i) {
        return  tokens[i];
    }
}
