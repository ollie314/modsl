<html>
<head>
<title>ModSL WebService Home</title>
</head>
<body>
<h3>ModSL WebService Test Page</h3>
<p/>
This form allows you to test ModSL text-to-diagram capabilities online. 
Please enter diagram script into the text area below and click [Submit]. 
It will post the content of the script to <b>http://ws.modsl.org/uml</b> and return the resulting 
diagram graphics as PNG. 
<p/>
<form action="uml" method="get">
<b>UML text-to-diagram</b>
<p/>
<textarea name="script" rows="16" cols="64">
class diagram MyDiagram { 
   interface MyInterface { 
      method1(); 
   } 
   class MyClass1 implements MyInterface { 
      var1; var2; method1(); method2(); 
   }
   class MyClass2 implements MyInterface { 
      method1();  
      1->0..n(MyClass1);
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
