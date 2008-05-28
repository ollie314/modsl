<%@ page contentType="image/png"%><%@ page import="java.awt.*, java.awt.image.*, javax.imageio.*"%><%
	response.setContentType("image/png");
	BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
	Graphics g = bufferedImage.getGraphics();
	g.setColor(Color.blue);
	g.fillOval(0, 0, 199, 199);
	g.dispose();
	ImageIO.write(bufferedImage, "png", response.getOutputStream());
%>
