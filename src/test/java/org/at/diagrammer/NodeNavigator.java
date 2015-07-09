package org.at.diagrammer;

import org.at.diagrammer.sm.Node;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 7/8/15
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeNavigator {

    private Node currentNode;

    private NodeNavigator(Node node) {
        this.currentNode = node;
    }

    public static NodeNavigator assertNode(Node startNode) {
         return new NodeNavigator(startNode);
    }

    public NodeNavigator next() {
        currentNode = currentNode.getNext();
        return this;
    }

    public NodeNavigator nextFalse() {
        currentNode = currentNode.getNextFalse();
        return this;
    }

    public NodeNavigator isSTART() {
        assertEquals("Should Be START Node", Node.START, currentNode.getNodeType());
        return this;
    }

    public NodeNavigator isIF(String expectedLabel) {
        isIF();
        assertEquals(expectedLabel, currentNode.getInfo());
        return this;
    }

    public NodeNavigator isIF() {
        assertEquals("Should Be IF Node", Node.IF, currentNode.getNodeType());
        return this;
    }


    public NodeNavigator isCODE(String expectedLabel) {
        isCODE();
        assertEquals(expectedLabel, currentNode.getInfo());
        return this;
    }

    public NodeNavigator isCODE() {
        assertEquals("Should Be CODE Node", Node.CODE, currentNode.getNodeType());
        return this;
    }

    public NodeNavigator isJOIN() {
        assertEquals("Should Be JOIN Node", Node.JOIN, currentNode.getNodeType());
        return this;
    }

    public NodeNavigator isEND() {
        assertEquals("Should Be END Node", Node.END, currentNode.getNodeType());
        return this;
    }

}
