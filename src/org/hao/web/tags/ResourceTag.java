/**
 * 
 */
package org.hao.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.hao.util.Resource;

/**
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-4-21 下午02:03:11
 */
public class ResourceTag extends TagSupport {

	/**
	 * 串行化版本序列号
	 */
	private static final long serialVersionUID = -869132892109567L;

	/**
	 * 指定的key
	 */
	private Object key;

	/**
	 * @param key
	 *            要设置的 key
	 * @throws JspException
	 */
	public void setKey(Object key) throws JspException {
		this.key = ExpressionEvaluatorManager.evaluate("key", key.toString(),
				Object.class, this, pageContext);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		Resource resource = Resource.getInstance();
		JspWriter out = pageContext.getOut();
		try {
			String value = resource.getResource(key.toString());
			value = value == null ? "" : value;
			out.print(value);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
}
