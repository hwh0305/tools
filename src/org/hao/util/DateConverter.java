/**
 * 
 */
package org.hao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.Converter;

/**
 * 用于BeanUtils的java.util.Date类型的转换器。必须使用BeanUtil包。
 * <p>
 * 使用方法: <br />
 * 使用 ConvertUtils.register(new org.hao.util.DateConverter(),
 * java.util.Date.class); 注册转换器，然后即可使用。转换器全局仅注册一次即可。
 * </p>
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-6-11 上午09:05:18
 */
public class DateConverter implements Converter {

	/**
	 * 日期格式化类
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat();

	/**
	 * 存储日期格式
	 */
	private static Collection<String> patterns = new ArrayList<String>(13);

	// 初始化日期格式暂时为13个
	static {
		DateConverter.patterns.add("yyyy-MM-dd HH:mm:ss,SSS");
		DateConverter.patterns.add("yyyy/MM/dd HH:mm:ss,SSS");
		DateConverter.patterns.add("yyyy-MM-dd HH:mm:ss");
		DateConverter.patterns.add("yyyy/MM/dd HH:mm:ss");
		DateConverter.patterns.add("yyyy-MM-dd HH:mm");
		DateConverter.patterns.add("yyyy/MM/dd HH:mm");
		DateConverter.patterns.add("yyyy-MM-dd HH");
		DateConverter.patterns.add("yyyy/MM/dd HH");
		DateConverter.patterns.add("yyyy-MM-dd");
		DateConverter.patterns.add("yyyy/MM/dd");
		DateConverter.patterns.add("yyyy-MM");
		DateConverter.patterns.add("yyyy/MM");
		DateConverter.patterns.add("yyyy");
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
	 *      java.lang.Object)
	 */
	public Object convert(Class type, Object value) {
		if (value == null
				|| (type != java.util.Date.class && !(value instanceof CharSequence)))
			return null;
		else {
			Object result = null;
			Iterator<String> itr = patterns.iterator();
			while (itr.hasNext()) {
				try {
					String pattern = itr.next();
					dateFormat.applyPattern(pattern);
					result = dateFormat.parse(value.toString());
					break;
				} catch (ParseException e) {
					continue;
				}
			}
			return result;
		}
	}
}
