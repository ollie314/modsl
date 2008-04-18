<defs>
	<linearGradient id="header_gradient" x1="10%" y1="50%" x2="100%" y2="100%">
		<stop offset="0%" style="stop-color:white; stop-opacity:1"/>
		<stop offset="100%" style="stop-color:lightsteelblue; stop-opacity:0.5"/>
	</linearGradient>
	<linearGradient id="element_gradient" x1="30%" y1="0%" x2="100%" y2="100%">
		<stop offset="0%" style="stop-color:white;stop-opacity:1"/>
		<stop offset="100%" style="stop-color:lightsteelblue;stop-opacity:1"/>
	</linearGradient>
</defs>
		
<style type="text/css">
	<![CDATA[ 
		
		* {
			font-family: $config.diagramFontFamily;
		} 
			
		.header {
			fill:url(#header_gradient);
		}
			
		.header_text {
			fill: black;
		}
			
		.footer {
			fill: none; 
		}
			
		.footer_text {
			fill: grey; 
		}
			
		.element {
			fill: url(#element_gradient); 
			stroke: midnightblue; 
			stroke-width: 1px;
			opacity: 0.75;
		}
			
		.element_underline {
			stroke: black; 
			stroke-width: 1px;
		}
			
		.element_detail {
			fill: black;
		}
			
		.element_detail_underline {
			stroke: black; 
			stroke-width: 1px;
		}
			
		.element_header {
			fill: black; 
		}
			
		.connector {
			fill: white; 
			stroke: dimgrey; 
			stroke-width: 1px;
		}
		
		.connector_text {
			fill: black; 
		}
		
		.connector_text_background {
			fill: white; 
			stroke: white;
			opacity: 0.9;
		}
		
		.history {
			fill: none;
			stroke: lightgrey;
			stroke-width: 1px;
		}
		 
	]]>
</style>
