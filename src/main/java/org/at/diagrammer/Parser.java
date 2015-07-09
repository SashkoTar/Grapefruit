package org.at.diagrammer;

public class Parser {
    Lexer lexer;
    Token lookahead;
    EmbeddedTranslator translator;
    String currentNodeInfo;

    public Parser(Lexer lexer) {
        this(lexer, new EmbeddedTranslator());
    }

    public Parser(Lexer lexer, EmbeddedTranslator translator) {
        this.translator = translator;
        this.lexer = lexer;
        consume();
    }

    public void flow() {
        while (lookahead.getType() == Token.ID || lookahead.getType() == Token.IF) {
            if (lookahead.getType() == Token.ID) {
                matchAssignStatement();
                translator.addAssign(currentNodeInfo);
            } else {
                matchIfStatement();
            }
        }
        translator.closeFlow();
    }

    private void matchIfStatement() {
        match(Token.IF);
        condition();
        translator.addIfBlockCondition(currentNodeInfo);
        codeTrueBlock();
        if (lookahead.getType() == Token.ELSE) {
            match(Token.ELSE);
            codeFalseBlock();
        }
        translator.closeIf();
    }

    private void codeTrueBlock() {
        match(Token.BEGIN_BLOCK);
        while (lookahead.getType() == Token.ID || lookahead.getType() == Token.IF) {
            if (lookahead.getType() == Token.ID) {
                matchAssignStatement();
                translator.addTrueBlock(currentNodeInfo);
            } else {
                translator.addTrueBlock();
                matchIfStatement();
            }
        }
        match(Token.END_BLOCK);
    }

    private void codeFalseBlock() {
        match(Token.BEGIN_BLOCK);
        while (lookahead.getType() == Token.ID || lookahead.getType() == Token.IF) {
            if (lookahead.getType() == Token.ID) {
                matchAssignStatement();
                translator.addFalseBlock(currentNodeInfo);
            } else {
                translator.addFalseBlock();
                matchIfStatement();
            }
        }
        match(Token.END_BLOCK);
    }

    private void condition() {
        match(Token.LPAR);
        Token id1 = match(Token.ID);
        Token relOp = match(Token.RELOP);
        Token id2 = match(Token.ID);
        match(Token.RPAR);
        currentNodeInfo = id1.getLexeme() + " " + relOp.getLexeme() + " " + id2.getLexeme();
    }

    private void matchAssignStatement() {
        Token id1 = match(Token.ID);
        match(Token.ASSIGN);
        Token id2 = match(Token.ID);
        match(Token.SEMICOLON);
        currentNodeInfo = id1.getLexeme() + " = " + id2.getLexeme() + ";";
    }

    private void consume() {
        lookahead = lexer.getNextToken();
    }

    private Token match(int x) {
        if (lookahead.getType() == x) {
            Token consumed = lookahead;
            consume();
            return consumed;
        } else {
            throw new IllegalStateException("Expecting token " + Token.getTokenString(x) + " but found " + Token.getTokenString(lookahead.getType()));
        }
    }

    private void matchNotification(String ruleName) {
        System.out.println("Matched " + ruleName);
    }

}
