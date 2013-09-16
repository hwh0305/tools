package org.hao.util;

import java.util.regex.Pattern;

/**
 * 中华人民共和国居民身份证验证类
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-5-20 下午02:47:30
 */
public class PRCIdCard {

	/**
	 * 位权
	 */
	private final static int[] WI = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
			5, 8, 4, 2, 1 };

	/**
	 * 较验码
	 */
	private final static char[] AI = { '1', '0', 'X', '9', '8', '7', '6', '5',
			'4', '3', '2' };

	/**
	 * 匹配身份证号的正则表达式
	 */
	private final static Pattern pattern = Pattern.compile(
			"^(\\d{15,15})|(\\d{17,17}[xX])|(\\d{18,18})$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * 验证身份证号
	 * 
	 * @param idCardNumber
	 *            身份证号
	 * @return 如果验证成功返回true
	 */
	public static boolean verify(String idCardNumber) {
		if (idCardNumber != null) {
			idCardNumber = idCardNumber.trim();
			idCardNumber = idCardNumber.toUpperCase();
		} else
			return false;
		if (pattern.matcher(idCardNumber).matches()) {
			char flag = '\0';
			if (idCardNumber.length() == 15) {
				return true;
			} else
				flag = getVerifyCode(idCardNumber);
			return flag == idCardNumber.charAt(17);
		} else
			return false;
	}

	/**
	 * 返回验证码
	 * 
	 * @param idCardNumber
	 *            至少有前17位的身份证号码
	 * @return 返回此身份证的较验码
	 */
	private static char getVerifyCode(String idCardNumber) {
		int sum = 0;
		for (int i = 0; i < 17; ++i) {
			int tmp = idCardNumber.charAt(i) - '0';
			sum += tmp * WI[i];
		}
		return AI[sum % 11];
	}

	/**
	 * 将15位的身份证号码转换为18位的身份证号码
	 * 
	 * @param oldNumber
	 *            旧的身份证号码
	 * @return 返回18位的身份证号码
	 */
	public static String transition15To18(String oldNumber) {
		if (pattern.matcher(oldNumber).matches()) {
			if (oldNumber.length() == 15) {
				StringBuffer buf = new StringBuffer(oldNumber);
				buf.insert(6, "19");
				buf.append(getVerifyCode(buf.toString()));
				return buf.toString();
			} else
				return oldNumber;
		}
		return null;
	}
}
