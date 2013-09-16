/**
 * 
 */
package org.hao.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * @author hao
 * 
 */
public class ValidateCodeImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5457644224921216332L;

	private String validateCode;

	private BufferedImage image;

	public static final int WIDTH = 60;

	public static final int HEIGHT = 20;

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	protected void paintImage(Graphics g) {
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 0xFF; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		validateCode = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			validateCode += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}

		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

		// 图象生效
		g.dispose();
	}

	public ValidateCodeImage() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		paintImage(image.getGraphics());
	}

	public void repaint() {
		paintImage(image.getGraphics());
	}

	public Image getImage() {
		return image;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void writeImage(OutputStream output) throws IOException {
		ImageIO.write(image, "JPEG", output);
	}

	public void writeImage(File output) throws IOException {
		ImageIO.write(image, "JPEG", output);
	}

	public void writeImage(ImageOutputStream output) throws IOException {
		ImageIO.write(image, "JPEG", output);
	}
}
