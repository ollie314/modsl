<% 
	sp = connector.getAdjustedStartPosition();
	ep = connector.getAdjustedEndPosition();
	
	a1 = ep.minus(connector.getArrow1());
	a2 = ep.minus(connector.getArrow2());
	
	labelw = (int)(config.connectorFT.getStringWidth(connector.name));
	
	lx = (int)(connector.midPoint.x - labelw/2d); 
	ly = (int)(connector.midPoint.y); 

%>

<line x1="${(int)a1.x}" y1="${(int)a1.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>
<line x1="${(int)a2.x}" y1="${(int)a2.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>
<line x1="${(int)sp.x}" y1="${(int)sp.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>

<rect x="$lx" y="${ly - config.connectorFT.baseline}" width="$labelw" height="${(int)config.connectorFT.size+1}" class="connector_text_background"/>
<text x="$lx" y="$ly" class="connector_text" font-size="$config.connectorFT.size">$connector.name</text>
