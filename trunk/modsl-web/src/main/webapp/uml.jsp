<%@ page contentType="image/svg+xml"%><%@ page import="org.modsl.core.lang.uml.*"%><%!
	UMLProcessor processor;
	public void jspInit() {
		processor = new UMLProcessor();
		processor.init();   
	}
%><%
	String input = request.getParameter("script");
	if (input.length() < 2048) {
		String svg = processor.process(input);
		out.write(svg);
	}
%>