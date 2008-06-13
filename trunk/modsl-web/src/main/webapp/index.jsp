<html>
<head>
<title>ModSL WebService Home</title>
<link type="text/css" rel="stylesheet" href="modsl.css" />
</head>
<body>
<div class="hdr">ModSL WebService Home</div>
<p/>
<div class="bdy">
This form allows you to test ModSL text-to-diagram capabilities online. 
Enter diagram script into the text area below and click &lt;Render Diagram&gt;. 
It will post the content of the script to <b>http://ws.modsl.org/uml</b> web service and return the resulting 
diagram graphics as PNG file. 
<p/>
<form action="uml" method="get">
<b>UML text-to-diagram</b><br/>
<table cellspacing=0 cellpadding=0 border=0>
<tr><td class="tbld">
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
</td><td class="tbld">
<div class="hlp">
<a href="http://code.google.com/p/modsl">ModSL project page</a>
</div>
<div class="hlp">
<a href="http://code.google.com/p/modsl/wiki/LanguageSyntax">Diagram script syntax help</a>
</div>
</td></tr></table>
(this public webservice limits script size to 2 Kbytes)
<p/>
<input type="submit" value="Render Diagram">
</form>
</div>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-4140734-3");
pageTracker._initData();
pageTracker._trackPageview();
</script>
</body>
</html>
