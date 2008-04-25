<rect x="0" 
	y="${(int)(diagram.size.y - config.diagramFooterFT.height)}" 
	width="${(int)diagram.size.x}" 
	height="$config.diagramFooterFT.height" 
	class="footer"/>
	
<text x="$config.diagramFooterFT.leftPadding" 
	y="${(int)(diagram.size.y - config.diagramFooterFT.getExtHeight(1) + config.diagramFooterFT.getExtBaseline(0))}" 
	font-size="$config.diagramFooterFT.size" 
	class="footer_text">Created by ModSL in ${diagram.timeline.toString()} milliseconds. Layout $diagram.lastKnownSize.rounded -> Final $diagram.requestedSize.rounded.</text>

</svg>

