package org.at.diagrammer;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class LexerTest {

    Lexer lexer;
    String inputText;
    Token token;

    @Before
    public void init() {
        lexer = new Lexer(inputText);
    }

    @Test
    public void shouldReturnIfToken() {
        lexer = new Lexer(" IF ");
        token = lexer.getNextToken();
        assertEquals(Token.IF, token.getType());
    }

    @Test
    public void shouldReturnTwoTokens() {
        inputText = " IF  ELSE ";
        lexer = new Lexer(inputText);
        token = lexer.getNextToken();
        assertEquals(Token.IF, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.ELSE, token.getType());
    }

    @Test
    public void shouldReturnElseIfTokens() {
        inputText = "   ELSE        iF  ";
        lexer = new Lexer(inputText);
        token = lexer.getNextToken();
        assertEquals(Token.ELSE, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.IF, token.getType());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException() {
        inputText = " ~ ";
        lexer = new Lexer(inputText);
        lexer.getNextToken();
    }

    @Test
    public void shouldReturnIfAndLParTokens() {
        inputText = " iF ( ";
        lexer = new Lexer(inputText);
        token = lexer.getNextToken();
        assertEquals(Token.IF, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.LPAR, token.getType());
    }

    @Test
    public void shouldReturnLParAndRParTokens() {
        inputText = "( )";
        lexer = new Lexer(inputText);
        token = lexer.getNextToken();
        assertEquals(Token.LPAR, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.RPAR, token.getType());
    }

    @Test
    public void shouldReturnBeginBlockAndEndBlock() {
        inputText = "{ }";
        lexer = new Lexer(inputText);
        token = lexer.getNextToken();
        assertEquals(Token.BEGIN_BLOCK, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.END_BLOCK, token.getType());
    }

    @Test
    public void shouldReturnIdAndRelOpAndId() {
        inputText = "a = b;";
        lexer = new Lexer(inputText);
        token = lexer.getNextToken();
        assertEquals(Token.ID, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.ASSIGN, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.ID, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.SEMICOLON, token.getType());
        token = lexer.getNextToken();
        assertEquals(Token.EOF_TYPE, token.getType());
    }

    @Test
    public void shouldReturnRelOp() {
        assertEquals(Token.RELOP, getTokenForString(">").getType());
        assertEquals(">", getTokenForString(">").getLexeme());
    }

    @Test
    public void shouldReturnRelOpForGreaterEqual() {
        assertEquals(Token.RELOP, getTokenForString(">=").getType());
        assertEquals(">=", getTokenForString(">=").getLexeme());
    }

    @Test
    public void shouldReturnRelOpForLess() {
        assertEquals(Token.RELOP, getTokenForString("<").getType());
        assertEquals("<", getTokenForString("<").getLexeme());
    }

    @Test
    public void shouldReturnRelOpForLessEqual() {
        assertEquals(Token.RELOP, getTokenForString("<=").getType());
        assertEquals("<=", getTokenForString("<=").getLexeme());
    }

    @Test
    public void shouldReturnRelOpForEqual() {
        assertEquals(Token.RELOP, getTokenForString("==").getType());
        assertEquals("==", getTokenForString("==").getLexeme());
    }


    @Test
    public void shouldReturnRelOpForNotEqual() {
        assertEquals(Token.RELOP, getTokenForString("!=").getType());
        assertEquals("!=", getTokenForString("!=").getLexeme());
    }

    @Test
    public void shouldReturnAssign() {
        assertEquals(Token.ASSIGN, getTokenForString("=").getType());
    }

    @Test
    public void shouldReturnId() {
        assertEquals(Token.ID, getTokenForString(" r2234").getType());
        assertEquals("r2234", getTokenForString(" r2234").getLexeme());
    }

    @Test
    public void shouldReturnInt() {
        assertEquals(Token.INT, getTokenForString(" 2234").getType());
        assertEquals("2234", getTokenForString(" 2234").getLexeme());
    }

    @Test
    public void shouldAnalyzeString() {
        lexer = new Lexer("if (x>5) {i = vv}");
        assertEquals(Token.IF, lexer.getNextToken().getType());
        assertEquals(Token.LPAR, lexer.getNextToken().getType());
        assertEquals(Token.ID, lexer.getNextToken().getType());
        assertEquals(Token.RELOP, lexer.getNextToken().getType());
        assertEquals(Token.INT, lexer.getNextToken().getType());
        assertEquals(Token.RPAR, lexer.getNextToken().getType());
        assertEquals(Token.BEGIN_BLOCK, lexer.getNextToken().getType());
    }

    private Token getTokenForString(String inputText) {
        lexer = new Lexer(inputText);
        return lexer.getNextToken();
    }
}
