<% 
	sp = connector.getAdjustedStartPosition();
	ep = connector.getAdjustedEndPosition();
	
	a1 = ep.minus(connector.getArrow1());
	a2 = ep.minus(connector.getArrow2());
	
	m1 = sp.minus(connector.getMultiStart());
	m2 = ep.minus(connector.getMultiEnd());
	
%>

<line x1="${(int)a1.x}" y1="${(int)a1.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>
<line x1="${(int)a2.x}" y1="${(int)a2.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>

<%	if (connector.isHollowArrow()) { 
		emp = ep.minus(connector.getArrowMiddle()); %>
		<polygon points="${(int)a1.x},${(int)a1.y} ${(int)a2.x},${(int)a2.y} ${(int)ep.x},${(int)ep.y}" 
			class="connector"/>
<%	} else {
		emp = ep;
	} %>	

<line x1="${(int)sp.x}" y1="${(int)sp.y}" x2="${(int)emp.x}" y2="${(int)emp.y}" class="connector"/>

<%	if (connector.startMultiplicity != null) {
		startMText = (int)(config.connectorFST.getStringWidth(connector.startMultiplicity)); %>	
		<rect x="${(int)m1.x}" y="${(int)(m1.y - config.connectorFST.baseline)}" 
			width="${(int)startMText}" height="${(int)config.connectorFST.fontSize}" class="connector_text_background"/>
		<text x="${(int)m1.x}" y="${(int)m1.y}" class="connector_text" 
			font-size="$config.connectorFST.fontSize">$connector.startMultiplicity</text>
<%	} 
	if (connector.endMultiplicity != null) {
		endMText = (int)(config.connectorFST.getStringWidth(connector.endMultiplicity)); %>	
		<rect x="${(int)m2.x}" y="${(int)(m2.y - config.connectorFST.baseline)}" 
			width="${(int)endMText}" height="${(int)config.connectorFST.fontSize}" class="connector_text_background"/>
		<text x="${(int)m2.x}" y="${(int)m2.y}" class="connector_text" 
			font-size="$config.connectorFST.fontSize">$connector.endMultiplicity</text>
<%	} %>
