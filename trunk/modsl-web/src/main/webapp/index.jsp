<html>
<head>
<title>ModSL WebService Home</title>
</head>
<body>
<h3>ModSL WebService Test Page</h3>
<p/>
This form allows you to test ModSL text-to-diagram capabilities online. 
Please enter the diagram script in the text area below and click Submit. 
It will post the content of the script to http://ws.modsl.org/uml and return the resulting 
diagram graphics as SVG.
<form action="uml.jsp" method="post">
<b>UML text-to-diagram</b>
<p>
<textarea name="script" rows="16" cols="64">
enter your UML script here
</textarea>
<br/>
<input type="submit" value="Show Diagram">
</p> 
</form>
</body>
</html>
