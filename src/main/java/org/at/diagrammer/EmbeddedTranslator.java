package org.at.diagrammer;

import org.at.diagrammer.sm.Node;

import java.util.Stack;


public class EmbeddedTranslator {
    private Node startNode;
    private Node tailNode;
    private ConditionalBlockBuilder conditionalBlockBuilder;
    private boolean isFirstEntrySet = false;
    private Stack<ConditionalBlockBuilder> stack = new Stack<ConditionalBlockBuilder>();

    private boolean addTrueBlock = false;
    private boolean addFalseBlock = false;


    public EmbeddedTranslator() {
        startNode = new Node("Start", Node.START);
        tailNode = startNode;
    }

    public Node getStartNode() {
        return startNode;
    }


    public void addIfBlockCondition(String currentNodeInfo) {
        if (isFirstEntrySet) {
            stack.push(conditionalBlockBuilder);
            conditionalBlockBuilder = new ConditionalBlockBuilder();
        } else {
            isFirstEntrySet = true;
            conditionalBlockBuilder = new ConditionalBlockBuilder(tailNode);
        }
        conditionalBlockBuilder.startIf(currentNodeInfo);

        if (addTrueBlock) {
            stack.peek().addTrueBlock(conditionalBlockBuilder.ifStart);
            addTrueBlock = false;
        }
        if (addFalseBlock) {
            stack.peek().addFalseBlock(conditionalBlockBuilder.ifStart);
            addFalseBlock = false;
        }
    }

    public void addTrueBlock(String currentNodeInfo) {
        Node node = new Node(currentNodeInfo, Node.CODE);
        conditionalBlockBuilder.addTrueBlock(node);
    }

    public void addTrueBlock() {
        addTrueBlock = true;
    }

    public void addFalseBlock() {
        addFalseBlock = true;
    }

    public void addFalseBlock(String currentNodeInfo) {
        Node node = new Node(currentNodeInfo, Node.CODE);
        conditionalBlockBuilder.addFalseBlock(node);
    }

    public void addAssign(String currentNodeInfo) {
        Node current = new Node(currentNodeInfo, Node.CODE);
        tailNode.next(current);
        tailNode = current;
    }

    public void closeIf() {
        tailNode = conditionalBlockBuilder.closeIf();
        if (!stack.empty()) {
            conditionalBlockBuilder = stack.pop();
            conditionalBlockBuilder.refresh();
        } else {
            isFirstEntrySet = false;
        }
    }


    public void closeFlow() {
        tailNode.next(new Node("End", Node.END));
    }

    public class ConditionalBlockBuilder {
        Node trueBlockTail;
        Node falseBlockTail;
        Node ifStart;
        Node tail;

        public ConditionalBlockBuilder() {
            this.tail = null;
        }

        public ConditionalBlockBuilder(Node tail) {
            this.tail = tail;
        }

        public void startIf(String condition) {
            ifStart = new Node(condition, Node.IF);
        }

        public void addTrueBlock(Node node) {
            if (ifStart.getNext() == null) {
                ifStart.next(node);
            } else {
                trueBlockTail.next(node);
            }
            trueBlockTail = node;
        }

        public void addFalseBlock(Node node) {
            if (ifStart.getNextFalse() == null) {
                ifStart.nextFalse(node);
            } else {
                falseBlockTail.next(node);
            }
            falseBlockTail = node;
        }

        public void updateTrueTail() {
            Node newTail = ifStart.getNext();
            trueBlockTail = findLastAtChain(newTail);
        }

        public void updateFalseTail() {
            Node newTail = ifStart.getNextFalse();
            falseBlockTail = findLastAtChain(newTail);
        }

        private Node findLastAtChain(Node node) {
            Node next = node;
            while (next != null && next.getNodeType() != Node.JOIN) {
                if (next.getNext() == null) {
                    break;
                }
                next = next.getNext();
            }
            return next;
        }

        public Node closeIf() {
            Node join = new Node("Join", Node.JOIN);
            trueBlockTail.next(join);
            if (falseBlockTail != null) {
                falseBlockTail.next(join);
            }
            if (tail != null) {
                tail.next(ifStart);
            }
            tail = join;
            return tail;
        }

        public void refresh() {
            updateTrueTail();
            updateFalseTail();
        }
    }
}
