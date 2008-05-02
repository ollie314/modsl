package org.modsl.core.agt.common;

import org.modsl.core.agt.model.AbstractGraphElement;

public abstract class AbstractDecorator<E extends AbstractGraphElement<?>> {
	
	protected E parent;

	public void decorate(E element) {
		this.parent = element;
	}

}
