/**
 * 
 */
package org.hao.util;

import java.io.Serializable;

/**
 * 分页查询信息
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-4-10 下午05:36:36
 */
public class PageBean implements Serializable {

	/**
	 * 串行化版本序列号
	 */
	private static final long serialVersionUID = -718768813927240943L;

	/**
	 * 每次查询的长度
	 */
	private int pageSize;

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 总记录数
	 */
	private long totalSize;

	/**
	 * 默认构造一个查询长度为20的对象
	 */
	public PageBean() {
		this(20, 0, 0);
	}

	/**
	 * 构造一个指定查询长度的对象
	 * 
	 * @param pageSize
	 *            要查询的长度
	 */
	public PageBean(int pageSize) {
		this(pageSize, 0, 0);
	}

	/**
	 * 构造一个指定查询长度和当前页面的对象
	 * 
	 * @param pageSize
	 *            查询长度
	 * @param currentPage
	 *            当前页面
	 */
	public PageBean(int pageSize, int currentPage) {
		this(pageSize, currentPage, 0);
	}

	/**
	 * 构造一个指定查询长度，当前页面和总长度的对象
	 * 
	 * @param pageSize
	 *            查询长度
	 * @param currentPage
	 *            当前页面
	 * @param totalSize
	 *            总记录长度
	 */
	public PageBean(int pageSize, int currentPage, int totalSize) {
		this.pageSize = pageSize;
		this.currentPage = currentPage < 1 ? 1 : currentPage;
		this.totalSize = totalSize;
	}

	/**
	 * 返回当前页面
	 * 
	 * @return 返回当前页面
	 */
	public int getCurrentPage() {
		if (currentPage < 1)
			return 1;
		else {
			int totalPage = getTotalPage();
			if (currentPage > totalPage)
				return totalPage;
			else
				return currentPage;
		}
	}

	/**
	 * 设置当前页面
	 * 
	 * @param currentPage
	 *            当前页面
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage < 1 ? 1 : currentPage;
	}

	/**
	 * 返回每页的长度
	 * 
	 * @return 返回每页的长度
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的长度
	 * 
	 * @param pageSize
	 *            每页的长度
	 */
	public void setPageSize(int pageSize) {
		if (pageSize <= 0)
			throw new RuntimeException("每页长度必须大于0");
		this.pageSize = pageSize;
	}

	/**
	 * 返回总记录数
	 * 
	 * @return 返回总记录数
	 */
	public long getTotalSize() {
		return totalSize;
	}

	/**
	 * 设置总记录数
	 * 
	 * @param totalSize
	 *            要设置的总记录数
	 */
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize < 0 ? 0 : totalSize;
	}

	/**
	 * 返回总页面数
	 * 
	 * @return 返回总页面数
	 */
	public int getTotalPage() {
		return (int) ((totalSize + pageSize - 1) / pageSize);
	}

	/**
	 * 返回开始查询位置
	 * 
	 * @return 返回开始查询位置
	 */
	public int getStart() {
		int totalPage = getTotalPage();
		currentPage = this.currentPage > totalPage ? totalPage
				: this.currentPage;
		return currentPage == 0 ? 0 : (currentPage - 1) * pageSize;
	}
}
