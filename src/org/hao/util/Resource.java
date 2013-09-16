/**
 * 
 */
package org.hao.util;

import java.util.ResourceBundle;

/**
 * 资源簇
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-4-21 下午01:55:46
 */
public class Resource {

	/**
	 * 资源簇对象
	 */
	private ResourceBundle bundle = null;

	/**
	 * 资源对象
	 */
	private static Resource instance = null;

	/**
	 * 资源名称
	 */
	public static String resource = null;

	/**
	 * 构造一个资源簇
	 */
	private Resource() {
		if (resource != null)
			bundle = ResourceBundle.getBundle(resource);
	}

	/**
	 * 得到一个资源实例
	 * 
	 * @return 返回全局唯一的资源实例
	 */
	public static Resource getInstance() {
		if (instance == null)
			instance = new Resource();
		return instance;
	}

	/**
	 * 根据资源名称返回对应的资源字符串
	 * 
	 * @param key
	 *            资源名称
	 * @return 返回资源字符串
	 */
	public String getResource(String key) {
		if (bundle == null)
			return null;
		String value = bundle.getString(key);
		return value == null ? null : value.trim();
	}
}
