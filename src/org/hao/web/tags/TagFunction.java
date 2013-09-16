/**
 * 
 */
package org.hao.web.tags;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * EL表达式用到的位逻辑运算方法
 * 
 * @author 胡文昊
 * 
 */
public final class TagFunction {

	/**
	 * 与运算
	 * 
	 * @param left
	 *            左值
	 * @param right
	 *            右值
	 * @return left & ringht
	 */
	public static int AND(int left, int right) {
		return left & right;
	}

	/**
	 * 或运算
	 * 
	 * @param left
	 *            左值
	 * @param right
	 *            右值
	 * @return left | ringht
	 */
	public static int OR(int left, int right) {
		return left | right;
	}

	/**
	 * 异或运算
	 * 
	 * @param left
	 *            左值
	 * @param right
	 *            右值
	 * @return left ^ ringht
	 */
	public static int XOR(int left, int right) {
		return left ^ right;
	}

	/**
	 * 非运算
	 * 
	 * @param number
	 *            值
	 * @return !number
	 */
	public static int NOT(int number) {
		return ~number;
	}

	/**
	 * 编码
	 * 
	 * @param words
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeUTF8(String words)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(words, "UTF-8");
	}

	/**
	 * 解码
	 * 
	 * @param words
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeUTF8(String words)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(words, "UTF=8");
	}
	
	/**
	 * 编码
	 * 
	 * @param words
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String words, String code)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(words, code);
	}

	/**
	 * 解码
	 * 
	 * @param words
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String words, String code)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(words, code);
	}
}
