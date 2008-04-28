package org.modsl.core.cfg;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.agt.visitor.AbstractVisitor;
import org.modsl.core.agt.visitor.LayoutVisitor;
import org.modsl.core.agt.visitor.StringTemplateVisitor;

public abstract class AbstractProcessor<T extends MetaType> {

    protected StringTemplateVisitor<T> stringTemplateVisitor;
    protected LayoutVisitor<T> layoutVisitor;

    protected Node<?> parse(String s) throws RecognitionException {
        ANTLRStringStream input = new ANTLRStringStream(s);
        Lexer lexer = getLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Parser parser = getParser(tokens);
        runParser(parser);
        return getRoot(parser);
    }

    protected abstract Node<T> getRoot(Parser parser);

    protected abstract void runParser(Parser parser) throws RecognitionException;

    protected abstract Parser getParser(CommonTokenStream tokens);

    protected abstract Lexer getLexer(ANTLRStringStream input);

    public String process(String s, Pt reqSize) {
        Node<?> root = parse(s);
        root.setReqSize(reqSize);
        root.accept(getLayoutVisitor());
        root.rescale(root.getReqSize());
        root.accept(getStringTemplateVisitor());
    }

    private AbstractVisitor<T> getStringTemplateVisitor() {
        return stringTemplateVisitor;
    }

    private AbstractVisitor<T> getLayoutVisitor() {
        return layoutVisitor;
    }

    public void init() {
        stringTemplateVisitor = new StringTemplateVisitor<T>(getPath(), getName(), 0);
        layoutVisitor = new LayoutVisitor<T>();
    }

    protected abstract String getPath();

    protected abstract String getName();

}
