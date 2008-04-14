<%
	header_baseline = (int)(element.position.y + config.elementHeaderFST.yBaseLine);
	header_line = header_baseline + config.elementHeaderFST.yTrail;
	vm_line = header_line + config.elementDetailFST.getYStack(element.attributes.size());
	
%>	<rect name="$element.name" x="${(int)element.position.x}" y="${(int)element.position.y}" width="${(int)element.size.x}" height="${(int)element.size.y}" rx="$config.elementRx" class="element"/>
	<line x1="${(int)element.position.x}" y1="$header_line" x2="${(int)(element.position.x + element.size.x)}" y2="$header_line" class="element"/>
	<line x1="${(int)element.position.x}" y1="$vm_line" x2="${(int)(element.position.x + element.size.x)}" y2="$vm_line" class="element"/>
	<text x="${(int)(element.position.x + config.elementHeaderFST.xLead)}" y="$header_baseline" font-size="$config.elementHeaderFST.fontSize" class="element_header">$element.name</text>

