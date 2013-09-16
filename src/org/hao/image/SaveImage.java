package org.hao.image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * SaveImage类是保存图片的便利操作类<br>
 * 文件上传时得到byte一维数组，首先将此一维数据存储到目的地，并返回存储路径。再产生缩略图并保存到指定位置，并返回此路径
 * 
 * @author 胡文昊<br>
 *         创建时间 2007-6-19 11:29
 */
public class SaveImage implements java.io.Serializable {

	/**
	 * 生成的串行化版本序列号
	 */
	private static final long serialVersionUID = 7811355023115029054L;

	/**
	 * 图像宽度
	 */
	private int width;

	/**
	 * 图像高度
	 */
	private int height;

	/**
	 * 放置路径
	 */
	private String path;

	/**
	 * 上传得到的byte一维数组
	 */
	private byte[] source;

	/**
	 * 原始图像文件名
	 */
	private String baseFileName;

	/**
	 * 保存的原始图像的路径
	 */
	private String srcFileName;

	/**
	 * 保存的缩略图的路径
	 */
	private String scaleFileName;

	/**
	 * 构造默认的SaveImage
	 */
	public SaveImage() {
	}

	/**
	 * 返回缩略图的高度
	 * 
	 * @return 缩略图的高度
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 设置缩略图的高度
	 * 
	 * @param height
	 *            要设置的缩略图的高度
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 返回在服务器上存储路径
	 * 
	 * @return path 在服务器上存储路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置在服务器上存储路径
	 * 
	 * @param path
	 *            要设置的在服务器上存储路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 返回缩略图的宽度
	 * 
	 * @return width 缩略图的宽度
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 设置缩略图的宽度
	 * 
	 * @param width
	 *            要设置的缩略图的宽度
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 设置上传得到的byte数组
	 * 
	 * @return source 上传得到的byte数组
	 */
	public byte[] getSource() {
		return source;
	}

	/**
	 * 设置上传的参数
	 * 
	 * @param source
	 *            要设置的上传得到的byte数组
	 * @param baseFileName
	 *            要设置的上传得到的文件名
	 */
	public void setSource(byte[] source, String baseFileName) {
		this.source = source;
		this.baseFileName = baseFileName;
	}

	/**
	 * 返回文件的原始名
	 * 
	 * @return baseFileName 返回文件的原始名
	 */
	public String getBaseFileName() {
		return baseFileName;
	}

	/**
	 * 设置文件的原始名
	 * 
	 * @param baseFileName
	 *            要设置的文件的原始名
	 */
	public void setBaseFileName(String baseFileName) {
		this.baseFileName = baseFileName;
	}

	/**
	 * 返回缩略图的路径
	 * 
	 * @return scaleFileName 返回缩略图的路径
	 */
	public String getScaleFileName() {
		return scaleFileName;
	}

	/**
	 * 返回储存图像的路径
	 * 
	 * @return srcFileName 返回储存图像的路径
	 */
	public String getSrcFileName() {
		return srcFileName;
	}

	/**
	 * 保存上传的图像，如果没有设置byte数组，返回null，如果保存成功，返回文件保存的路径
	 * 
	 * @return 如果没有设置byte数组，返回null，如果保存成功，返回文件保存的路径
	 * @throws IOException
	 */
	public String upload() throws IOException {
		long begin = new Date().getTime();

		if (source == null || source.length == 0) {
			srcFileName = null;
			return srcFileName;
		}

		String fileName = getFileName(begin);
		srcFileName = path + "/" + fileName;

		if (baseFileName != null && baseFileName.length() != 0) {
			String tmp = baseFileName.substring(baseFileName.lastIndexOf('.'));
			srcFileName += tmp;
		} else
			srcFileName += ".jpg";

		FileOutputStream byteOut = new FileOutputStream(srcFileName, true);
		byteOut.write(source);
		byteOut.close();

		return srcFileName;
	}

	/**
	 * 保存上传的图像的缩略图，如果没有设置byte数组，返回null，如果保存成功，返回缩略图文件保存的路径
	 * 
	 * @return 如果没有设置byte数组，返回null，如果保存成功，返回缩略图文件保存的路径
	 * @throws IOException
	 */
	public String scale() throws IOException {
		if (srcFileName == null || srcFileName.length() == 0) {
			scaleFileName = null;
			return scaleFileName;
		}

		scaleFileName = srcFileName;

		int point = scaleFileName.lastIndexOf('.');
		String tmp = (point == -1 ? scaleFileName : scaleFileName.substring(0,
				point));
		scaleFileName = tmp + "_s.jpg";

		ImageOperate.scale(srcFileName, scaleFileName, width, height);

		return scaleFileName;
	}

	/**
	 * 生成上传图片的名
	 * 
	 * @return 返回生成的上传的图片的名
	 */
	private String getFileName(long begin) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(Long.toHexString(Math.abs(begin)));
		long end = new Date().getTime();
		fileName.append(Long.toHexString(Math.abs(end)));
		return fileName.toString();
	}
}