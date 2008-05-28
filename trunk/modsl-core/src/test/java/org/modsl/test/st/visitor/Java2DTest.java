package org.modsl.test.st.visitor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class Java2DTest {

	static {
		System.setProperty("java.awt.headless", "true");
	}

	@Test
	public void bufferedImage() throws IOException {
		BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		g.setColor(Color.blue);
		g.fillOval(0, 0, 199, 199);
		g.dispose();
		ImageIO.write(bufferedImage, "png", new File("etc/png-out/bufimg.png"));
	}

}
