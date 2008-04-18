<rect x="0" 
	y="${(int)(diagram.size.y - config.diagramFooterFT.height)}" 
	width="${(int)diagram.size.x}" 
	height="$config.diagramFooterFT.height" 
	class="footer"/>
	
<text x="$config.diagramFooterFT.leftPadding" 
	y="${(int)(diagram.size.y - config.diagramFooterFT.getExtHeight(1) + config.diagramFooterFT.getExtBaseline(0))}" 
	font-size="$config.diagramFooterFT.fontSize" 
	class="footer_text">Created by ModSL in ${diagram.timeline.toString()} milliseconds. Layout $diagram.lastKnownSize -> Final $diagram.requestedSize.</text>

</svg>

