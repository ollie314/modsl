package org.modsl.agt;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;

public class AGTFactory {

    public static List<Edge> createEdges(Node parent, List<Token> tokens) {
        List<Edge> es = new LinkedList<Edge>();
        for (int i = 0; i < tokens.size() - 1; i++) {
            Edge e = new Edge(tokens.get(i).getText(), tokens.get(i + 1).getText());
            parent.add(e);
            es.add(e);
        }
        return es;
    }

    public static Node createNode(Token token) {
        return new Node(token.getText());
    }

    public static Node createRootNode() {
        return new Node();
    }

}
