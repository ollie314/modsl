package org.modsl.core.agt.common;

import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

public class SimpleNodeDecorator extends AbstractDecorator<Node<?>> {

	public Pt getTextPos() {
		FontTransform ft = parent.getType().getConfig().getFontTransform();
		return new Pt(parent.getPos().x + ft.getLeftPadding(), parent.getPos().y + ft.getExtBaseline(0));
	}

}
