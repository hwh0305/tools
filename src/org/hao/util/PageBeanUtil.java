/**
 * 
 */
package org.hao.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户PageBean和PageTag的便利类，提供了从Servlet中得到当前页码和PageBean对象的方法
 * 
 * @author 胡文昊 <br />
 *         创建时间：2008-08-26 21:46
 * 
 */
public final class PageBeanUtil {

	/**
	 * 属性名称
	 */
	public final static String ATTRIBUTE_NAME = "_pagebean_";

	/**
	 * 默认的页面标签
	 */
	public final static String FIELD = "p";

	/**
	 * 一个工具方法，得到正确的当前页码数
	 * 
	 * @param request
	 *            使用的请求对象
	 * @param field
	 *            要验证的字符名称
	 * @param attributeName
	 *            pageBean 对象的属性名称
	 * @return 返回正确的页码
	 */
	public static int getCurrentPage(HttpServletRequest request, String field,
			String attributeName) {
		int page = 1;
		try {
			String fieldValue = request.getParameter(field);
			if (fieldValue == null || fieldValue.length() == 0) {
				PageBean pageBean = getPageBean(request, attributeName);
				if (pageBean != null)
					page = pageBean.getCurrentPage();
				else
					page = 1;
			} else
				page = Integer.parseInt(fieldValue);
		} catch (Exception e) {
			page = 1;
		}
		return page < 1 ? 1 : page;
	}

	/**
	 * 一个工具方法，得到正确的当前页码数
	 * 
	 * @param request
	 *            使用的请求对象
	 * @param field
	 *            要验证的字符名称
	 * @return 返回正确的页码
	 */
	public static int getCurrentPage(HttpServletRequest request, String field) {
		return getCurrentPage(request, field, ATTRIBUTE_NAME);
	}

	/**
	 * 一个工具方法，得到正确的当前页码数
	 * 
	 * @param request
	 *            使用的请求对象
	 * @return 返回正确的页码
	 */
	public static int getCurrentPage(HttpServletRequest request) {
		return getCurrentPage(request, FIELD, ATTRIBUTE_NAME);
	}

	/**
	 * 得到一个PageBean对象
	 * 
	 * @param request
	 *            请求对象
	 * @return 返回一个PageBean对象
	 */
	public static PageBean getPageBean(HttpServletRequest request) {
		return getPageBean(request, ATTRIBUTE_NAME);
	}

	/**
	 * 得到一个PageBean对象
	 * 
	 * @param request
	 *            请求对象
	 * @param attributeName
	 *            存储对象的名称
	 * @return 返回一个PageBean对象
	 */
	public static PageBean getPageBean(HttpServletRequest request,
			String attributeName) {
		PageBean pageBean = (PageBean) request.getAttribute(attributeName);
		if (pageBean == null)
			pageBean = (PageBean) request.getSession().getAttribute(
					attributeName);
		if (pageBean == null)
			pageBean = (PageBean) request.getSession().getServletContext()
					.getAttribute(attributeName);
		if (pageBean == null)
			pageBean = new PageBean();
		request.setAttribute(attributeName, pageBean);
		return pageBean;
	}
}
