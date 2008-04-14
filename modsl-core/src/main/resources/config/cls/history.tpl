	<path d="M<%  
		
	step = 1;
		
	element.history.each { xy -> 
		if (step == 2) {
			out.write("L");
			step = 3;
		}
		step++; 
		%>${(int)(xy.x + element.size.x / 2d)},${(int)(xy.y + element.size.y / 2d)} <%
	}
		 
	%>" class="history"/>
