/**
 * 
 */
package org.hao.web.tags;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.hao.util.PageBean;
import org.hao.util.PageBeanUtil;

/**
 * 分页标签
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-4-11 上午11:03:03
 */
public class PageTag extends TagSupport {

	/**
	 * 生成的串行化版本序列号
	 */
	private static final long serialVersionUID = -4556769650360303944L;

	/**
	 * 维护的form名称
	 */
	private final static String FORM_NAME = "__form__pagebean__pagetag__hao__";

	/**
	 * 分页使用的div
	 */
	private final static String TAG_DIV = "__hao__page__tag__div__";

	/**
	 * 当前页对象ID
	 */
	private final static String PAGE_ID = "__hao__page__id__";

	/**
	 * 第一页层ID
	 */
	private final static String FIRST_PAGE_ID = "__hao__first__page__id__";

	/**
	 * 上一页层ID
	 */
	private final static String PREV_PAGE_ID = "__hao__prev__page__id__";

	/**
	 * 下一页层ID
	 */
	private final static String NEXT_PAGE_ID = "__hao__next__page__id__";

	/**
	 * 最后一页层ID
	 */
	private final static String LAST_PAGE_ID = "__hao__last__page__id__";

	/**
	 * 选择框层ID
	 */
	private static final String SELECT_PAGE_ID = "__hao__select__page__id__";

	/**
	 * 分析URL的参数
	 */
	private final static Pattern pattern = Pattern.compile("[\\?&]\\w+=");

	/**
	 * 要分页的url地址
	 */
	private Object url = null;

	/**
	 * 页面标签
	 */
	private String field = null;

	/**
	 * 使用到的分页对象
	 */
	private PageBean pageBean = null;

	/**
	 * 当前页
	 */
	private int currentPage = 0;

	/**
	 * 总页数
	 */
	private int totalPage = 0;

	/**
	 * 总记录数
	 */
	private long totalSize = 0;

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		pageBean = getPageBean();
		if (pageBean != null) {
			totalSize = pageBean.getTotalSize();
			try {
				if (totalSize > 0) {
					out.print("<div class='" + TAG_DIV + "'>");
					out.print(getForm());
					out.print(getDescription());
					out.print(getPrevious());
					out.print(getNext());
					out.print(getSelectPage());
					out.print("</div>");
				}
				pageBean = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return TagSupport.SKIP_BODY;
	}

	/**
	 * 设置url
	 * 
	 * @param url
	 *            要设置的 url
	 * @throws JspException
	 */
	public void setUrl(Object url) throws JspException {
		this.url = ExpressionEvaluatorManager.evaluate("url", url.toString(),
				Object.class, this, pageContext);
	}

	/**
	 * 设置PageBean对象
	 * 
	 * @param pageBean
	 *            pageBean对象
	 * @throws JspException
	 */
	public void setPageBean(PageBean pageBean) throws JspException {
		this.pageBean = pageBean;
	}

	/**
	 * 设置标签
	 * 
	 * @param field
	 *            要设置的标签
	 * @throws JspException
	 */
	public void setField(String field) throws JspException {
		this.field = (String) ExpressionEvaluatorManager.evaluate("field",
				field.toString(), String.class, this, pageContext);
	}

	/**
	 * 返回分页控制对象
	 * 
	 * @return 返回分布控制对象
	 */
	private PageBean getPageBean() {
		if (pageBean == null)
			pageBean = (PageBean) pageContext
					.getAttribute(PageBeanUtil.ATTRIBUTE_NAME);
		if (pageBean == null)
			pageBean = (PageBean) pageContext.getRequest().getAttribute(
					PageBeanUtil.ATTRIBUTE_NAME);
		if (pageBean == null)
			pageBean = (PageBean) pageContext.getSession().getAttribute(
					PageBeanUtil.ATTRIBUTE_NAME);
		if (pageBean == null)
			pageBean = (PageBean) pageContext.getServletContext().getAttribute(
					PageBeanUtil.ATTRIBUTE_NAME);
		return pageBean;
	}

	/**
	 * 是否还有下一页
	 * 
	 * @return 如果有返回true
	 */
	private boolean haveNext() {
		return currentPage < totalPage;
	}

	/**
	 * 是否有上一页
	 * 
	 * @return 如果有返回true
	 */
	private boolean havePrevious() {
		return currentPage > 1;
	}

	/**
	 * 返回当前的标签
	 * 
	 * @return 返回当前的标签
	 */
	private String getField() {
		return field == null || field.trim().length() == 0 ? PageBeanUtil.FIELD : field;
	}

	/**
	 * 返回设置的url
	 * 
	 * @return 返回设置的url
	 */
	private Object getUrl() {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		String urlAddr = url.toString();
		if (urlAddr.charAt(0) != '/')
			basePath += "/";
		return basePath + urlAddr;
	}

	/**
	 * 初始化Form
	 * 
	 * @return 返回form的HTML代码
	 */
	private String getForm() {
		currentPage = pageBean.getCurrentPage();
		totalPage = pageBean.getTotalPage();
		url = getUrl();
		field = getField();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		StringBuilder buf = new StringBuilder();
		buf.append("<form name='"
				+ FORM_NAME
				+ "' "
				+ (request.getContentType() == null ? "" : "enctype='"
						+ request.getContentType() + "'") + " action='" + url
				+ "' method='" + request.getMethod()
				+ "' style='display: none;'>");
		buf.append("<input type='hidden' id='" + PAGE_ID + "' name='" + field
				+ "' value='" + currentPage + "' />");
		Set<String> set = new HashSet<String>();
		if (request.getMethod().equalsIgnoreCase("POST")) {
			Matcher matcher = pattern.matcher(url.toString());
			while (matcher.find()) {
				String result = matcher.group();
				set.add(result.substring(1, result.length() - 1));
			}
		}
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (set.contains(key) || key.equals(field))
				continue;
			String[] values = request.getParameterValues(key);
			for (int i = 0; i < values.length; ++i) {
				buf.append("<input type='hidden' name='" + key + "' value='"
						+ values[i] + "' />");
			}
		}
		buf.append("</form>");
		buf.append("<script type='text/javascript'>");
		buf.append("function __hao__forward__(page) {");
		buf.append("var formObj = document.getElementsByName('");
		buf.append(FORM_NAME);
		buf
				.append("');if (formObj.length) formObj = formObj[0]; document.getElementById('");
		buf.append(PAGE_ID);
		buf.append("').value = page;formObj.submit(); }</script>");
		return buf.toString();
	}

	/**
	 * 分页统计信息
	 */
	private String getDescription() {
		StringBuilder buf = new StringBuilder();
		buf.append("当前第&nbsp;<font color='red'>");
		buf.append(currentPage);
		buf.append("</font>&nbsp;页&nbsp;&nbsp;共计&nbsp;<font color='red'>");
		buf.append(totalPage);
		buf.append("</font>&nbsp;页&nbsp;&nbsp;共&nbsp;<font color='red'>");
		buf.append(totalSize);
		buf.append("</font>&nbsp;条记录&nbsp;&nbsp;");
		return buf.toString();
	}

	/**
	 * 首页和上一页按钮内容
	 */
	private String getPrevious() {
		StringBuilder buf = new StringBuilder();
		if (havePrevious()) {
			buf.append("<input class='" + FIRST_PAGE_ID
					+ "' type='button' title='第一页' value='第一页'");
			buf.append(" onclick=\"javascript:__hao__forward__(1)\" />");
			buf.append("<input class='" + PREV_PAGE_ID
					+ "' type='button' title='上一页' value='上一页'");
			buf.append(" onclick=\"javascript:__hao__forward__(");
			buf.append(currentPage - 1);
			buf.append(")\" />");
		} else {
			buf
					.append("<input class='"
							+ FIRST_PAGE_ID
							+ "' type='button' title='第一页' value='第一页' disabled='disabled' />");
			buf
					.append("<input class='"
							+ PREV_PAGE_ID
							+ "' type='button' title='上一页' value='上一页' disabled='disabled' />");
		}
		return buf.toString();
	}

	/**
	 * 下一页和末页内容
	 */
	private String getNext() {
		StringBuilder buf = new StringBuilder();
		if (haveNext()) {
			buf.append("<input class='" + NEXT_PAGE_ID
					+ "' type='button' title='下一页' value='下一页'");
			buf.append(" onclick=\"javascript:__hao__forward__(");
			buf.append(currentPage + 1);
			buf.append(") \" />");
			buf.append("<input class='" + LAST_PAGE_ID
					+ "' type='button' title='最后一页' value='最后一页'");
			buf.append(" onclick=\"javascript:__hao__forward__(");
			buf.append(totalPage);
			buf.append(") \" />");
		} else {
			buf
					.append("<input class='"
							+ NEXT_PAGE_ID
							+ "' type='button' title='下一页' value='下一页' disabled='disabled' />");
			buf
					.append("<input class='"
							+ LAST_PAGE_ID
							+ "' type='button' title='最后一页' value='最后一页' disabled='disabled' />");
		}
		return buf.toString();
	}

	/**
	 * 选择框内容
	 */
	private String getSelectPage() {
		StringBuilder buf = new StringBuilder();
		buf
				.append("&nbsp;&nbsp;跳转到&nbsp;<select class='"
						+ SELECT_PAGE_ID
						+ "' title='选择你要跳转的页面' onchange='__hao__forward__(this.options[this.selectedIndex].value)'>");
		for (int i = 1; i <= totalPage; ++i) {
			buf.append("<option value='");
			buf.append(i);
			buf.append("'");
			if (i == currentPage)
				buf.append(" selected='selected'");
			buf.append(">");
			buf.append(i);
			buf.append("</option>");
		}
		buf.append("</select>");
		return buf.toString();
	}
}
