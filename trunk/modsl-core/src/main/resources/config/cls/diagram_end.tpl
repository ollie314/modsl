<rect x="0" 
	y="${(int)(diagram.size.y - config.diagramFooterFST.height)}" 
	width="${(int)diagram.size.x}" 
	height="$config.diagramFooterFST.height" 
	class="footer"/>
	
<text x="$config.diagramFooterFST.leftPadding" 
	y="${(int)(diagram.size.y - config.diagramFooterFST.getExtHeight(1) + config.diagramFooterFST.getExtBaseline(0))}" 
	font-size="$config.diagramFooterFST.fontSize" 
	class="footer_text">Created by ModSL in ${diagram.timeline.toString()} milliseconds. Layout $diagram.lastKnownSize -> Final $diagram.requestedSize.</text>

</svg>

