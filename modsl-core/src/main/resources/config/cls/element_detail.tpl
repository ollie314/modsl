<text x="${(int)element_detail.position.x}" y="${(int)(element_detail.position.y + config.elementDetailFT.baseline)}" 
	class="element_detail" font-size="$config.elementDetailFT.fontSize">$element_detail.name</text>
	
<%	
	if (org.modsl.cls.model.ClassElementDetailScope.STATIC.equals(element_detail.scope)) {
		sx1 = (int)element_detail.position.x;
		sx2 = sx1 + config.elementDetailFT.getStringWidth(element_detail.name);
		sy = (int)(element_detail.position.y + config.elementDetailFT.underline); 
%>
		<line x1="$sx1" y1="$sy" x2="$sx2" y2="$sy" class="element_detail_underline"/>
		
<%	} %>
