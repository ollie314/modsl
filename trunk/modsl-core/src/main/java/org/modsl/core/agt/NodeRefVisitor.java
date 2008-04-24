package org.modsl.core.agt;

public class NodeRefVisitor<T extends AGTType> implements AGTVisitor<T> {

    @Override
    public void visit(Edge<T> edge) {
        edge.setNode1(resolveNode(edge.getParent(), edge.getNode1(), edge.getNode1Name()));
        edge.setNode2(resolveNode(edge.getParent(), edge.getNode2(), edge.getNode2Name()));
    }

    @Override
    public void visit(Node<T> node) {
        // NO OP
    }

    /**
     * If node reference is null will resolve nodeName to node reference,
     * otherwise just returns existing node reference
     * @param node
     * @param nodeName
     * @return node
     */
    private Node<T> resolveNode(Node<T> parent, Node<T> node, String nodeName) {
        if (node == null) {
            Node<T> n = parent.getNode(nodeName);
            if (n == null) {
                throw new InvalidNodeNameException(nodeName);
            }
            return n;
        }
        return node;
    }

}
