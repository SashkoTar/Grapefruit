package org.at.diagrammer.generator;

import org.at.diagrammer.sm.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Visitor {

    private StringBuilder declarationBlock = new StringBuilder();
    private StringBuilder transitionBlock = new StringBuilder();
    private int codeBlockCounter = 1;
    private int ifBlockCounter = 1;
    private int joinBlockCounter = 1;
    private Map<Node, String> nameTable = new HashMap<Node, String>();

    private Set<String> transitions = new HashSet<String>();
    private Set<Node> nodes = new HashSet<Node>();

    public void run(Node node) {
        visit(node);
        declarationBlock.append("start[label=\"Start\", shape=circle]; \n");
        declarationBlock.append("end[label = \"End\", shape=doublecircle]; \n");
        StringBuilder graph = new StringBuilder();
        graph.append("digraph g{ \n \n")
                .append(declarationBlock)
                .append("\n")
                .append(transitionBlock)
                .append("\n")
                .append("}");
        System.out.println(graph);
    }

    public void visit(Node node) {
        if(wasVisited(node)) {
            return;
        }
        handleNode(node);
        if (node.getNext() != null) {
            visit(node.getNext());
            if (node.getNodeType() == Node.IF && node.getNextFalse() != null) {
                visit(node.getNextFalse());
            }
        }
    }

    private boolean wasVisited(Node node) {
        if(nodes.contains(node)) {
            return true;
        } else {
            nodes.add(node);
            return false;
        }
    }

    private void handleNode(Node node) {
        switch (node.getNodeType()) {
            case Node.START: case Node.END:
                break;
            case Node.IF:
                declarationBlock
                        .append(obtainNodeName(node))
                        .append("[label = \"")
                        .append(node.getInfo())
                        .append("\", shape=diamond]; \n");
                break;
            case Node.CODE: case Node.JOIN:
                declarationBlock
                        .append(obtainNodeName(node))
                        .append("[label = \"")
                        .append(node.getInfo())
                        .append("\", shape=box]; \n");
                break;
            default:
                throw new IllegalArgumentException("Node type is incorrect");
        }
        if (node.getNodeType() != Node.END) {
            if (node.getNodeType() == Node.IF) {
                appendTransition(node, node.getNext(), "Yes");
                appendTransition(node, node.getNextFalse(), "No");
            } else {
                appendTransition(node, node.getNext(), "");
            }
        }
    }

    //TODO Reduce amount of code, remove transitionBlock
    private void appendTransition(Node from, Node to, String label){
        StringBuilder singleTransition = new StringBuilder();
        singleTransition.append(obtainNodeName(from)).append(" -> ").append(obtainNodeName(to));
        singleTransition.append("[label=\"").append(label).append("\"];\n");
        if(!transitions.contains(singleTransition.toString())) {
            transitionBlock.append(singleTransition);
            transitions.add(singleTransition.toString());
        }

    }

    private String obtainNodeName(Node node) {
        String nodeName;
        try {
        if (!nameTable.containsKey(node)) {
            switch (node.getNodeType()) {
                case Node.START:
                    nameTable.put(node, "start");
                    break;
                case Node.IF:
                    nodeName = "ifblock" + ifBlockCounter++;
                    nameTable.put(node, nodeName);
                    break;
                case Node.CODE:
                    nodeName = "codeblock" + codeBlockCounter++;
                    nameTable.put(node, nodeName);
                    break;
                case Node.JOIN:
                    nodeName = "joinblock" + joinBlockCounter++;
                    nameTable.put(node, nodeName);
                    break;
                case Node.END:
                    nameTable.put(node, "end");
                    break;
                default:
                    throw new IllegalArgumentException("Node type is incorrect " + node.getNodeType());
            }
        } }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return nameTable.get(node);
    }
}
