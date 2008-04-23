package org.modsl.core.agt;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;

public class DotFactory {

    public List<Edge> createEdges(Node parent, List<Token> tokens) {

        List<Edge> es = new LinkedList<Edge>();

        Node n1 = null, n2 = null;
        Token t1, t2;

        for (int i = 0; i < tokens.size() - 1; i++) {

            t1 = tokens.get(i);
            t2 = tokens.get(i + 1);

            if (i == 0) {
                n1 = createNodeIfDoesntExist(parent, t1);
            }
            n2 = createNodeIfDoesntExist(parent, t2);

            Edge e = new Edge(n1, n2);
            parent.add(e);
            es.add(e);

            n1 = n2; // << shift

        }
        
        return es;
        
    }

    private Node createNodeIfDoesntExist(Node parent, Token token) {
        Node n = parent.getNode(token.getText());
        if (n == null) {
            return createNode(token);
        } else {
            return n;
        }
    }

    public Node createNode(Token token) {
        return new Node(token.getText());
    }

    public Node createRootNode() {
        return new Node();
    }

}
