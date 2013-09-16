package org.hao.web.encoding;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 编码过滤器
 * 
 * @author 胡文昊 2007-11-23 02:33:21
 */
public class EncodeFilter implements Filter {

	/**
	 * 要设定的编码
	 */
	private String encode;

	/**
	 * 原始的编码
	 */
	private String oldEncode;

	/**
	 * 标识是否关闭缓存
	 */
	private boolean noCache;

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		response.setCharacterEncoding(encode);
		request.setCharacterEncoding(encode);
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			String method = httpServletRequest.getMethod();
			if (method.equalsIgnoreCase("GET")) {
				Enumeration e = request.getParameterNames();
				while (e.hasMoreElements()) {
					String name = (String) e.nextElement();
					String[] str = request.getParameterValues(name);
					for (int i = 0; i < str.length; ++i) {
						str[i] = new String(str[i].getBytes(oldEncode), encode);
					}
				}
			}
			if (noCache) {
				httpServletResponse.setHeader("Pragma", "No-Cache");
				httpServletResponse.setHeader("Cache-Control", "no-cache");
				httpServletResponse.setDateHeader("Expires", 0);
			}
		}
		filter.doFilter(request, response);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		encode = config.getInitParameter("encode");
		oldEncode = config.getInitParameter("oldEncode");
		if (encode == null || encode.trim().length() == 0)
			encode = "UTF-8";
		if (oldEncode == null || oldEncode.trim().length() == 0)
			oldEncode = "ISO-8859-1";
		String cacheCtr = config.getInitParameter("noCache");
		if (cacheCtr != null && cacheCtr.trim().equalsIgnoreCase("false"))
			noCache = false;
		else
			noCache = true;
	}
}
