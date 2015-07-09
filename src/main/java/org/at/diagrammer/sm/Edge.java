package org.at.diagrammer.sm;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 6/28/15
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge {

    private final String label;

    public Edge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
