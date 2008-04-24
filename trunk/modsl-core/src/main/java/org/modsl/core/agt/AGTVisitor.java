package org.modsl.core.agt;

public interface AGTVisitor<T extends AGTType> {

    public void visit(Node<T> node);

    public void visit(Edge<T> edge);

}
