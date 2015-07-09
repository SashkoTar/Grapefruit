package org.at.diagrammer.generator;

import org.at.diagrammer.sm.Node;
import org.junit.Test;


public class VisitorTest {

    @Test
    public void shouldTraverse() {
        Node start = new Node("Start", Node.START);
        Node block = new Node("x:=1", Node.CODE);
        Node block2 = new Node("x:=2", Node.CODE);
        Node end = new Node("End", Node.END);
        start.next(block);
        block.next(block2);
        block2.next(end);
        Visitor visitor = new Visitor();
        visitor.run(start);
    }

    @Test
    public void shouldGenerateIfElseStatement() {
        Node start = new Node("Start", Node.START);
        Node ifblock = new Node("x > 3", Node.IF);
        Node block = new Node("x:=1", Node.CODE);
        Node block2 = new Node("x:=2", Node.CODE);
        Node end = new Node("End", Node.END);
        start.next(ifblock);
        ifblock.next(block);
        ifblock.nextFalse(block2);
        block.next(end);
        block2.next(end);
        Visitor visitor = new Visitor();
        visitor.run(start);
    }

    @Test
    public void shouldGenerateIfElseStatement2() {
        Node start = new Node("Start", Node.START);
        Node ifblock = new Node("x > 3", Node.IF);
        Node ifblock2 = new Node("x > 2", Node.IF);
        Node block = new Node("x:=1", Node.CODE);
        Node block2 = new Node("x:=2", Node.CODE);
        Node block3 = new Node("x:=3", Node.CODE);
        Node end = new Node("End", Node.END);
        start.next(ifblock);
        ifblock.next(block);
        ifblock.nextFalse(block2);
        block.next(end);
        block2.next(ifblock2);
        ifblock2.next(end);
        ifblock2.nextFalse(block3);
        block3.next(end);
        Visitor visitor = new Visitor();
        visitor.run(start);
    }
}
