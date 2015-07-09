package org.at.diagrammer.sm;


public class Node {
    public static final int START = 0;
    public static final int CODE = 1;
    public static final int IF = 2;
    public static final int END = 3;
    public static final int JOIN = 4;

    private String info;
    private int nodeType;

    private Node next;
    private Node nextFalse;

    public Node(String info, int nodeType) {
        this.info = info;
        this.nodeType = nodeType;
    }

    public void next(Node block) {
        next = block;
    }

    public void nextFalse(Node block) {
        if(this.getNodeType() != Node.IF) {
            throw new IllegalStateException("Applied only for IF node type not for " + block.getNodeType());
        }
        this.nextFalse = block;
    }

    public String getInfo() {
        return info;
    }

    public Node getNext() {
        return next;
    }

    public Node getNextFalse() {
        if(this.getNodeType() != Node.IF) {
            throw new IllegalStateException("Applied only for IF node");
        }
        return nextFalse;
    }

    public int getNodeType() {
        return nodeType;
    }
}
