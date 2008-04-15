<text x="${(int)element_detail.absolutePosition.x}" y="${(int)(element_detail.absolutePosition.y + config.elementDetailFST.yBaseLine)}" class="element_detail" font-size="$config.elementDetailFST.fontSize">$element_detail.name</text>
<%	if (org.modsl.cls.model.ClassElementDetailScope.STATIC.equals(element_detail.scope)) {
		sx1 = (int)element_detail.absolutePosition.x;
		sx2 = sx1 + config.elementDetailFST.stringWidth(element_detail.name);
		sy = (int)(element_detail.absolutePosition.y + config.elementDetailFST.yUnderLine); %>
		<line x1="$sx1" y1="$sy" x2="$sx2" y2="$sy" class="element_detail_underline"/>
<%	} %>
