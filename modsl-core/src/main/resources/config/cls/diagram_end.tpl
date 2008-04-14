<%
	parsing = diagram.parsingFinishedTime-diagram.startTime;
	layout = diagram.layoutFinishedTime-diagram.parsingFinishedTime;
	rendering = diagram.renderingFinishedTime-diagram.layoutFinishedTime;
	total = diagram.renderingFinishedTime-diagram.startTime;
		
%>	<rect x="0" y="${(int)(diagram.size.y - config.diagramFooterFST.ySize)}" width="${(int)diagram.size.x}" height="$config.diagramFooterFST.ySize" class="footer"/>
	<text x="$config.diagramFooterFST.xLead" y="${(int)(diagram.size.y - config.diagramFooterFST.yTrail)}" font-size="$config.diagramFooterFST.fontSize" class="footer_text">Created by ModSL in $total milliseconds ($parsing, $layout, $rendering). Layout $diagram.lastKnownSize -> Final $diagram.requestedSize.</text>
	
</svg>

