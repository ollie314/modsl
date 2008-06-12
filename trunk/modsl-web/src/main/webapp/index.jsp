<html>
<head>
<title>ModSL WebService Home</title>
</head>
<body>
<h3>ModSL WebService Test Page</h3>
<p/>
This form allows you to test ModSL text-to-diagram capabilities online. 
Enter diagram script into the text area below and click &lt;Render Diagram&gt;. 
It will post the content of the script to <b>http://ws.modsl.org/uml</b> web service and return the resulting 
diagram graphics as PNG file. 
<p/>
<form action="uml" method="get">
<b>UML text-to-diagram</b>
<p/>
<textarea name="script" rows="24" cols="64">
class diagram Sample {
   abstract class AbstractElement {
      name; parent; type;
      abstract accept(AbstractVisitor);
   } 
   abstract class AbstractBox extends AbstractElement {
      pos; disp; size;
   }
   class Edge extends AbstractElement {
      labels; bends; node1; node2;
   }
   class Bend extends AbstractBox {}
   class Graph extends AbstractBox {
      reqSize; labels; 
      1->*(Edge);
      1->*(Node);
   }
   class Node extends AbstractBox {
      connectedEdges; labels;
   }	
}
</textarea>
<br/>
<a href="http://code.google.com/p/modsl/wiki/LanguageSyntax">Diagram Script Syntax Help</a>
&nbsp; &nbsp; (2 Kbytes script size limit)
<p/>
<input type="submit" value="Render Diagram">
</form>
</body>
</html>
