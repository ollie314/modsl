<%@ 
	page contentType="image/png"
%><%@ 
	page import="org.modsl.core.lang.uml.*, org.modsl.core.render.*, java.awt.*, java.awt.image.*, javax.imageio.*"
%><%!
	UMLTranslator translator;
	public void jspInit() {
		StyleLoader stl = new StyleLoader();
        stl.load("cfg/uml:cfg", "uml", UMLMetaType.class);
		translator = new UMLTranslator();
	}
%><%
	String input = request.getParameter("script");
	if (input.length() < 2048) {
		ImageIO.write(translator.translate(input), "png", response.getOutputStream());
	}
%>