package org.modsl.core.lang.uml.decorator;

import org.modsl.core.agt.decor.SimpleNodeDecorator;
import org.modsl.core.agt.model.Pt;

public class CollabNodeDecorator extends SimpleNodeDecorator {

	public Pt getSep1() {
		return new Pt(parent.getPos().x, parent.getPos().y + getFt().getExtHeight(1));
	}

	public Pt getSep2() {
		return new Pt(parent.getPos().x + parent.getSize().x, parent.getPos().y + getFt().getExtHeight(1));
	}

}
