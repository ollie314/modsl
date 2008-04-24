package org.modsl.core.agt;

public class ToStringVisitor<T extends AGTType> implements AGTVisitor<T> {

    protected StringBuffer sb = new StringBuffer();

    @Override
    public void visit(Node<T> node) {
        if (node.getNodes().size() > 0) {
            sb.append("{");
        }
        sb.append(node.toString()).append(" ");
        if (node.getNodes().size() > 0) {
            sb.append("}");
        }
    }

    @Override
    public void visit(Edge<T> edge) {
        sb.append(edge.toString()).append(" ");
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}
