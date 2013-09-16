package org.hao.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ImageOperate旨在图片文件进行处理<br>
 * 此类是一个工具类，故所有的方法均为static方法
 * 
 * @author 胡文昊 <br>
 * 创建时间 2007-6-19 10:48
 */
public class ImageOperate {

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大 false 缩小
	 * @throws IOException
	 */
	public static void scale(String srcImageFile, String result, int scale,
			boolean flag) throws IOException {
		scale(new File(srcImageFile), new File(result), scale, flag);
	}

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param width
	 *            缩放宽度
	 * @param height
	 *            缩放高度
	 * @throws IOException
	 */
	public static void scale(String srcImageFile, String result, int width,
			int height) throws IOException {
		scale(new File(srcImageFile), new File(result), width, height);
	}

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件
	 * @param result
	 *            缩放后的图像
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大 false 缩小
	 * @throws IOException
	 */
	public static synchronized void scale(File srcImageFile, File result,
			int scale, boolean flag) throws IOException {
		BufferedImage src = ImageIO.read(srcImageFile);
		int width = src.getWidth();
		int height = src.getHeight();

		if (flag) {
			width = width * scale;
			height = height * scale;
		} else {
			width = width / scale;
			height = height / scale;
		}

		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		ImageIO.write(tag, "JPEG", result);
	}

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件
	 * @param result
	 *            缩放后的图像
	 * @param width
	 *            缩放宽度
	 * @param height
	 *            缩放高度
	 * @throws IOException
	 */
	public static synchronized void scale(File srcImageFile, File result,
			int width, int height) throws IOException {
		BufferedImage src = ImageIO.read(srcImageFile);
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();

		if (srcWidth <= width)
			width = srcWidth;
		if (srcHeight <= height)
			height = srcHeight;
		if (srcWidth == srcHeight) {
			int min = Math.min(width, height);
			width = min;
			height = min;
		} else {
			int scale = (int) (srcHeight / height + 0.5);
			if (srcWidth / scale > width)
				scale = (int) (srcWidth / width + 0.5);
			height = srcHeight / scale > height ? height : srcHeight / scale;
			width = srcWidth / scale > width ? width : srcWidth / scale;
		}

		// if (srcHeight > height || srcWidth > width) {
		// if (srcHeight < srcWidth)
		// height *= (srcHeight / srcWidth + 0.5); // 0.5是为四舍五入之用
		// else if (srcHeight > srcWidth)
		// width *= (srcWidth / srcHeight + 0.5);
		// else {
		// if (width > height)
		// width = height;
		// else if (width < height)
		// height = width;
		// }
		// }
		Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		ImageIO.write(tag, "JPEG", result);
	}

	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 * 
	 * @param source
	 *            源图像位置
	 * @param result
	 *            目标图像位置
	 * @throws IOException
	 */
	public static void convert(String source, String result) throws IOException {
		File f = new File(source);
		f.canRead();
		f.canWrite();

		synchronized (f) {
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param source
	 *            源图像文件地址
	 * @param result
	 *            转换后图像文件地址
	 * @throws IOException
	 */
	public static synchronized void gray(String source, String result)
			throws IOException {
		BufferedImage src = ImageIO.read(new File(source));
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		src = op.filter(src, null);
		ImageIO.write(src, "JPEG", new File(result));
	}

}
