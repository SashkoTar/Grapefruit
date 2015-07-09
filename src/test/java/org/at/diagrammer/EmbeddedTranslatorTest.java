package org.at.diagrammer;

import org.at.diagrammer.sm.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.at.diagrammer.NodeNavigator.assertNode;

public class EmbeddedTranslatorTest {

    public static final String MULTIPLE_IF_BRANCHING = "if (x > w) { if (x > condition2) { x = firstVar; } else { x = secondVar; } }  else {x = thirdVar;} x = common;";
    public static final String MULTIPLE_ELSE_BRANCHING = "if (x > w) { x = firstVar; } else {if (x > condition2) { x = secondVar;} else {x = thirdVar; y = fourthVar;}} x = common;";

    private EmbeddedTranslator translator;

    @Test
    public void shouldParseYesNotPath() {
        buildSemanticModel(MULTIPLE_IF_BRANCHING);
        Node node = translator.getStartNode();

        assertNode(node).isSTART()
                .next().isIF()
                .next().isIF()
                .nextFalse().isCODE("x = secondVar;")
                .next().isJOIN()
                .next().isJOIN()
                .next().isCODE("x = common;")
                .next().isEND();
    }


    @Test
    public void shouldParseYesYesPath() {
        buildSemanticModel(MULTIPLE_IF_BRANCHING);
        Node node = translator.getStartNode();

        assertNode(node).isSTART()
                .next().isIF()
                .next().isIF()
                .next().isCODE("x = firstVar;")
                .next().isJOIN()
                .next().isJOIN()
                .next().isCODE("x = common;")
                .next().isEND();
    }

    @Test
    public void shouldParseNotPath() {
        buildSemanticModel(MULTIPLE_IF_BRANCHING);
        Node node = translator.getStartNode();

        assertNode(node).isSTART()
                .next().isIF()
                .nextFalse().isCODE("x = thirdVar;")
                .next().isJOIN()
                .next().isCODE("x = common;")
                .next().isEND();
    }

    @Test
    public void shouldParseNotNotPath() {
        buildSemanticModel(MULTIPLE_ELSE_BRANCHING);
        Node node = translator.getStartNode();

        assertNode(node).isSTART()
                .next().isIF("x > w")
                .nextFalse().isIF()
                .nextFalse().isCODE("x = thirdVar;")
                .next().isCODE("y = fourthVar;")
                .next().isJOIN()
                .next().isJOIN()
                .next().isCODE("x = common;")
                .next().isEND();
    }


    @Test
    public void shouldParseNotYesPath() {
        buildSemanticModel(MULTIPLE_ELSE_BRANCHING);
        Node node = translator.getStartNode();

        assertNode(node).isSTART()
                .next().isIF("x > w")
                .nextFalse().isIF()
                .next().isCODE("x = secondVar;")
                .next().isJOIN()
                .next().isJOIN()
                .next().isCODE("x = common;")
                .next().isEND();
    }


    private EmbeddedTranslator buildSemanticModel(String inputCode) {
        Lexer lexer = new Lexer(inputCode);
        translator = new EmbeddedTranslator();
        Parser parser = new Parser(lexer, translator);
        parser.flow();
        return translator;
    }


    private void traversNodes(Node node) {
        if (node.getNext() != null) {
            System.out.println("[" + node.getInfo() + "] --> [" + node.getNext().getInfo() + "]");
            traversNodes(node.getNext());
        }
        if (node.getNodeType() == Node.IF && node.getNextFalse() != null) {
            System.out.println("[" + node.getInfo() + "] - false -> [" + node.getNextFalse().getInfo() + "]");
            traversNodes(node.getNextFalse());
        }
    }

    private void mm(int x, int w, int condition2, int firstVar, int secondVar, int thirdVar, int common) {

        if (x > w) {
            x = firstVar;
        } else {
            if (x > condition2) {
                x = secondVar;
            } else {
                x = thirdVar;
            }
        }
        x = common;
    }

}
