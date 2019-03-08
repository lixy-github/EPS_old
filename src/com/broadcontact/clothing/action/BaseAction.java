package com.broadcontact.clothing.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.clothing.entity.UserNewsEntity;
import com.broadcontact.common.hibernate.CommonDAO;
import com.broadcontact.common.hibernate.PaginationSupport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletContextAware {

	private static final long serialVersionUID = 7812153610553218253L;

	protected transient final Log log = LogFactory.getLog(getClass());

	/**
	 * 用于返回错误值
	 */
	protected int retVal;

	/**
	 * 用于Action->View的错误或提示信息的传递
	 */
	protected String msg;

	/**
	 * 用于View的分页支持
	 */
	protected PaginationSupport pageSupport;

	/**
	 * 用于View的分页支持,传递起始记录序号
	 */
	protected int start = 0;

	/**
	 * 用于View的分页支持,传递每页显示值
	 */
	protected int pageSize = 20;

	/**
	 * 用于View的分页支持,传递查询参数
	 */
	protected String keyword;

	/**
	 * 用于View的列表
	 */
	protected Collection list;

	protected ServletContext context;
	
	//分页
	protected int rows;
	protected int page;
	protected String _search;//false:不是搜索   true:搜索
	protected String searchField;//table查询字段
	protected String searchString;//输入的关键字
	protected String searchOper;//操作符 等于 包含...
	protected String sidx;//字段 排序
	protected String sord;//asc desc

	public ServletContext getContext() {
		return context;
	}
	protected String createRandom(){
		return RandomStringUtils.randomAlphanumeric(15);
	}
	public void setContext(ServletContext context) {
		this.context = context;
	}

	protected ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	protected PageContext getPageContext() {
		return ServletActionContext.getPageContext();
	}

	protected ServletConfig getServletConfig() {
		return ServletActionContext.getPageContext().getServletConfig();
	}

	protected HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Collection getList() {
		return list;
	}

	public void setList(Collection list) {
		this.list = list;
	}

	public PaginationSupport getPageSupport() {
		return pageSupport;
	}

	public void setPageSupport(PaginationSupport pageSupport) {
		this.pageSupport = pageSupport;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	protected boolean checkUserAuthority(Long userId,CommonDAO commonDAO) throws Exception {
		boolean flag = true;

		UserEntity userEntity = (UserEntity) commonDAO.selectById(UserEntity.class, userId);
		if (!"1".equals(userEntity.getUserType())) { 
			
			retVal = -1;
			msg = "单位信息未审核！";
			
			flag = false;
		}

		return flag;

	}
	protected UserEntity getCurrUser(){
		return (UserEntity) getSession().getAttribute("_USER_");
	}

	public Map<String, Object> getJsonResult() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("aaData", list);
		result.put("msg", msg);
		result.put("result", retVal);
		return result;
	}

	public String getPageHtml() {
		String queryStr = getRequest().getQueryString();
		if (queryStr == null)
			queryStr = "";
		else
			queryStr = queryStr + "&";
		int pos = queryStr.indexOf("start=");
		if (pos >= 0)
			queryStr = queryStr.substring(0, pos) + queryStr.substring(queryStr.indexOf("&", pos) + 1);
		StringBuffer sb = new StringBuffer();
		sb.append("第" + pageSupport.getCurrPage() + "页 / 总" + pageSupport.getTotalPage() + "页");
		sb.append("| 共" + pageSupport.getTotalCount() + "条记录");
		sb.append(" | <a href=?" + queryStr + "start=0>首页</a>");
		if (!pageSupport.isFirstPage())
			sb.append(" | <a href=?" + queryStr + "start=" + pageSupport.getPreviousIndex() + ">上页</a>");
		else
			sb.append(" | <font color=#999999>上页</font>");
		if (!pageSupport.isEndPage()) {
			sb.append(" | <a href=?" + queryStr + "start=" + pageSupport.getNextIndex() + ">下页</a>");
			sb.append(" | <a href=?" + queryStr + "start=" + pageSupport.getRealEndIndex() + ">尾页</a>");
		} else {
			sb.append(" | <font color=#999999>下页</font>");
			sb.append(" | <font color=#999999>尾页</font>");
		}
		return sb.toString();
	}

	/**
	 * 获取该用户审核不通过时的 最后一条type="1"消息
	 */
	public String getNewsStr(UserEntity user,String type) throws Exception{
		String result = "";
		
		Set<UserNewsEntity> newsSet = user.getUserNewsSet();
		if(newsSet.size()>0){
			Iterator<UserNewsEntity> it = newsSet.iterator();
			while(it.hasNext()){
				UserNewsEntity news = it.next();
				if(type.equals(news.getType())){
					//System.out.println(news.getContent());
					result =  news.getContent();
					break;
				}
			}
		}
		return result;
	}
	
	public void setServletContext(ServletContext arg0) {
		context = arg0;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String get_search() {
		return _search;
	}

	public void set_search(String _search) {
		this._search = _search;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
}
