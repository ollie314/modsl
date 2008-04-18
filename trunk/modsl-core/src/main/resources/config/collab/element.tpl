<%	header_baseline = (int)(element.position.y + config.elementHeaderFT.getExtBaseline(0));
	header_underline = (int)(element.position.y + config.elementHeaderFT.getExtUnderline(0));
	header_left = (int)(element.position.x + config.elementHeaderFT.leftPadding);
	header_right = (int)(header_left + config.elementHeaderFT.getStringWidth(element.name));
%>

<rect name="$element.name" x="${(int)element.position.x}" y="${(int)element.position.y}" 
    width="${(int)element.size.x}" height="${(int)element.size.y}" rx="$config.elementRx" class="element"/>

<text x="$header_left" 
    y="$header_baseline" font-size="$config.elementHeaderFT.fontSize" class="element_header">$element.name</text>

<line x1="$header_left" y1="$header_underline" x2="$header_right" y2="$header_underline" class="element_underline"/>

 