/**
 * 
 */
package org.hao.web.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 当产生一个request请求的时候，将BasePath写入Request中
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-8-28 20:20
 */
public class BasePathListener implements ServletRequestListener {

	private final static String DEFAULT_ATTRIBUTE_NAME = "basePath";
	
	private final static String PARAM_NAME = "attributeName";
	
	private static String attributeName = null;
	
	public void requestDestroyed(ServletRequestEvent event) {
		ServletRequest servletRequest = event.getServletRequest();
		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			request.removeAttribute(attributeName);
		}
	}

	public void requestInitialized(ServletRequestEvent event) {
		if (attributeName == null) {
			String name = event.getServletContext().getInitParameter(PARAM_NAME);
			if (name == null || name.trim().length() == 0)
				attributeName = DEFAULT_ATTRIBUTE_NAME;
			else
				attributeName = name;
		}
		ServletRequest servletRequest = event.getServletRequest();
		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			request.setAttribute(attributeName, basePath);
		}
	}

}
