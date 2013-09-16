/**
 * 
 */
package org.hao.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 使用此标签，设置html的base属性
 * 
 * @author 胡文昊
 * 
 */
public class BasePathTag extends TagSupport {

	/**
	 * 串行化版本序列号
	 */
	private static final long serialVersionUID = -4666293788488718526L;

	/**
	 * 目标打开方式
	 */
	private String target;

	/**
	 * 设置目标打开方式
	 * 
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		try {
			if (target != null && target.trim().length() != 0)
				out.println("<base href=\"" + basePath + "\" target=\""
						+ target + "\" />");
			else
				out.println("<base href=\"" + basePath + "\" />");
			// out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

}
