<% 
	sp = connector.getAdjustedStartPosition();
	ep = connector.getAdjustedEndPosition();
	
	a1 = ep.minus(connector.getArrow1());
	a2 = ep.minus(connector.getArrow2());
%>

<line x1="${(int)a1.x}" y1="${(int)a1.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>
<line x1="${(int)a2.x}" y1="${(int)a2.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>
<line x1="${(int)sp.x}" y1="${(int)sp.y}" x2="${(int)ep.x}" y2="${(int)ep.y}" class="connector"/>

