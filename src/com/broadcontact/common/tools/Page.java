package com.broadcontact.common.tools;

import java.io.Serializable;

public class Page implements Serializable {
	/** 当前是第几页 */
	private long curpage = 1;

	private long pagecount = 1; // 一共有多少页

	private long rowcount = 0; // 一共有多少行

	private int rowPerPage = 20; // 每页有多少行

	/** 页面的URL */
	private String pagename = "";

	/** 页面传递的参数 */
	private String param = "";

	private long start_num = 0;

	private long end_num = 0;

	public Page() {
	}

	/**
	 * 得到翻页的HTML代码
	 * 
	 * @return
	 */
	public String getPages() {
		StringBuffer sb = new StringBuffer();
		sb
				.append("\n"
						+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		sb.append("\n" + "<tr align=\"center\">");
		sb.append("\n" + "<td width=\"25%\" height=\"24\" class=\"dh\">共有");
		sb.append("\n" + "<font color=red>" + rowcount + "</font>条记录</td>");
		sb.append("\n" + "<td width=\"15%\" height=\"24\" class=\"dh\">共");
		sb.append("\n" + "<font color=red>" + pagecount + "</font>页</td>");
		sb.append("\n" + "<td width=\"15%\" height=\"24\" class=\"dh\">第");
		sb.append("\n" + "<font color=red>" + curpage + "</font>页</td>");
		sb.append("\n" + "<td width=\"1%\" height=\"24\" class=\"dh\">&nbsp;</td>");
		sb.append("\n" + "<td width=\"11%\" height=\"24\" class=\"dh\">");
		if (curpage > 1) {
			sb.append("\n" + "<a href=\"" + pagename + "?page=1" + param
					+ "\">首页</a>");
		} else {
			sb.append("\n" + "<font color=\"#999999\">首页</font>");
		}
		sb.append("\n" + "</td>");
		sb.append("\n" + "<td width=\"11%\" height=\"24\" class=\"dh\">");
		if (curpage > 1) {
			sb.append("\n" + "<a href=\"" + pagename + "?page=" + (curpage - 1)
					+ param + "\">前页</a>");
		} else {
			sb.append("\n" + "<font color=\"#999999\">前页</font>");
		}
		sb.append("\n" + "</td>");
		sb.append("\n" + "<td width=\"11%\" height=\"24\" class=\"dh\">");
		if (curpage < pagecount) {
			sb.append("\n" + "<a href=\"" + pagename + "?page=" + (curpage + 1)
					+ param + "\">后页</a>");
		} else {
			sb.append("\n" + "<font color=\"#999999\">后页</font>");
		}
		sb.append("\n" + "</td>");
		sb.append("\n" + "<td width=\"11%\" height=\"24\" class=\"dh\">");
		if (curpage < pagecount) {
			sb.append("\n" + "<a href=\"" + pagename + "?page=" + pagecount + param
					+ "\">尾页</a>");
		} else {
			sb.append("\n" + "<font color=\"#999999\">尾页</font>");
		}
		sb.append("\n" + "</td>");
		sb.append("\n" + "</tr>");
		sb.append("\n" + "</table>" + "\n");

		return sb.toString();
	}

	/**
	 * 得到以数字显示的翻页代码
	 * 
	 * @return
	 */
	public String getNumPages() {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		sb.append("<tr align=\"right\">");
		sb.append("<td >共有");
		sb.append("<font color=red>" + rowcount + "</font>条记录&nbsp;</td>");
		sb.append("<td >每页");
		sb.append("<font color=red>" + rowPerPage + "</font>条记录&nbsp;</td>");
		sb.append("<td >共");
		sb.append("<font color=red>" + pagecount + "</font>页&nbsp;</td>");
		sb.append("<td align=\"left\">");
		for (long i = curpage - 5; i < curpage + 5 && i < pagecount + 1; i++) {
			if (i > 0) {
				sb.append("\n"
						+ "<a href=\""
						+ pagename
						+ "?page="
						+ i
						+ param
						+ "\">["
						+ (i == curpage ? "<strong><font color=\"#FF0000\">" + curpage
								+ "</font></strong>" : "" + i + "") + "]</a>");
			}
		}
		sb.append("\n" + "</td>");
		sb.append("\n" + "</tr>");
		sb.append("\n" + "</table>" + "\n");
		return sb.toString();
	}

	public long getRowcount() {
		return rowcount;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public String getPagename() {
		return pagename;
	}

	public long getPagecount() {
		return pagecount;
	}

	public long getCurpage() {
		return curpage;
	}

	public void setCurpage(String curpage) {
		long page = 1;
		try {
			if (curpage == null) {
				page = 1;
			} else {
				page = Long.parseLong(curpage);
				if (page < 1)
					page = 1;
			}
		} catch (Exception e) {
		}
		setCurpage(page);
	}

	public void setCurpage(long curpage) {
		if (curpage < 1)
			curpage = 1;
		this.curpage = curpage;
	}

	public void setPagecount(long pagecount) {
		this.pagecount = pagecount;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public void setRowPerPage(int rowperpage) {
		this.rowPerPage = rowperpage;
	}

	public void setRowcount(long rowcount) {
		this.rowcount = rowcount;
		if (rowcount != 0 && rowPerPage > 0) {
			this.pagecount = (long) ((rowcount - 1) / rowPerPage) + 1;
		}
		if (pagecount < curpage)
			curpage = pagecount;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public void setStart_num(long start_num) {
		this.start_num = start_num;
	}

	public long getStart_num() {
		return (long) (rowPerPage * (curpage - 1) + 1);
	}

	public void setEnd_num(long end_num) {
		this.end_num = end_num;
	}

	public long getEnd_num() {
		return (long) (rowPerPage * curpage);
	}
}