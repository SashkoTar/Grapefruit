package org.at.diagrammer;

import org.junit.Test;


public class ParserTest {

    @Test
    public void shouldStartParsing() {
       Lexer lexer = new Lexer("a = b;");
        Parser parser = new Parser(lexer);
        parser.flow();
    }

    @Test
    public void shouldStartParsingTwoAssign() {
        Lexer lexer = new Lexer("a = b; c=d;");
        Parser parser = new Parser(lexer);
        parser.flow();
    }

    @Test
    public void shouldStartParsingIf() {
        Lexer lexer = new Lexer("if (x > w) { x = d;}");
        Parser parser = new Parser(lexer);
        parser.flow();
    }

    @Test
    public void shouldStartParsingIfElse() {
        Lexer lexer = new Lexer("if (x > w) { x = d;} else { x = name;}");
        Parser parser = new Parser(lexer);
        parser.flow();
    }
}
