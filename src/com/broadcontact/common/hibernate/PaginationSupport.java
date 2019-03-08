package com.broadcontact.common.hibernate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.broadcontact.common.tools.Tools;

public class PaginationSupport {

	   // 分页支持类
    public PaginationSupport() {
        this(DEFAULT_COUNT_ON_EACH_PAGE);
    }

    public PaginationSupport(int countOnEachPage) {
        startIndex = 0;
        if (countOnEachPage < 1) {
            throw new IllegalArgumentException(
                    "Count should be greater than zero!");
        } else {
            this.countOnEachPage = countOnEachPage;
        }
    }

    public PaginationSupport(List items, int totalCount, int countOnEachPage,
            int startIndex) {
        this.items = items;
        this.startIndex = startIndex;
        this.totalCount = totalCount;
        this.countOnEachPage = countOnEachPage;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setCountOnEachPage(int countOnEachPage) {
        this.countOnEachPage = countOnEachPage;
    }

    public List getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getFirstPage() {
        return 0;
    }

    public int getEndPage() {
        int[] pages = getNextStartIndexes();
        return pages[pages.length];
    }

    public int getEndIndex() {
        int endIndex = getStartIndex() + countOnEachPage;
        if (endIndex > totalCount)
            return totalCount;
        else
            return endIndex;
    }

    public int getRealEndIndex() {
        int size = getTotalPage();
        return (size - 1) * countOnEachPage;
    }

    public int getStartIndex() {
        if (startIndex > totalCount)
            return totalCount;
        if (startIndex < 0)
            return 0;
        else
            return startIndex;
    }

    public int getNextIndex() {
        return getNextStartIndexes()[0];
    }

    public int getPreviousIndex() {
        int previousIndexes[] = getPreviousStartIndexes();
        return previousIndexes[previousIndexes.length - 1];
    }

    public int[] getNextStartIndexes() {
        int index = getEndIndex();
        if (index == totalCount)
            return null;
        int count = (totalCount - index) / countOnEachPage;
        if ((totalCount - index) % countOnEachPage > 0)
            count++;
        int result[] = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = index;
            index += countOnEachPage;
        }
        return result;
    }

    public int[] getPreviousStartIndexes() {
        int index = getStartIndex();
        if (index == 0)
            return null;
        int count = index / countOnEachPage;
        if (index % countOnEachPage > 0)
            count++;
        int result[] = new int[count];
        for (int i = count - 1; i > 0; i--) {
            index -= countOnEachPage;
            result[i] = index;
        }

        return result;
    }

    public int getCountOnEachPage() {
        return countOnEachPage;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isFirstPage() {
        return startIndex == 0;
    }

    public boolean isEndPage() {
        return startIndex + countOnEachPage >= totalCount;
    }

    private static int DEFAULT_COUNT_ON_EACH_PAGE = 10;

    private List items;

    private int totalCount;

    private int startIndex;

    private int countOnEachPage;

    private String keyword;

    private String value;
    
    private String anchor;

    /**
     * @return Returns the keyword.
     */
    public String getKeyword() {
        return keyword;
    }
    
    public void appendKeyword(String queryStr){
    	this.keyword = this.keyword += queryStr;
    }

    /**
     * @param keyword
     *            The keyword to set.
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    public int getTotalPage() {
        return totalCount / countOnEachPage
                + (totalCount % countOnEachPage == 0 ? 0 : 1);
    }

    public int getCurrPage() {
        return startIndex / countOnEachPage + 1;
    }

    public String getHtml() {
    	if(getTotalPage() <= 1){
    		return "";
    	}
        StringBuffer sb = new StringBuffer();
        sb.append("<div class='pagination'>");
        sb.append("<a href=?" + Tools.encode4URL(getKeyword())+ "&start=0#"+getAnchor()+"><<</a> ");
        int index=1;
        if(getCurrPage()-2>0 && getTotalPage()>5){
        	if(getTotalPage() - getCurrPage() < 2){
        		index = getCurrPage()-4;
        	}else{
        		index = getCurrPage()-2;
        	}
        	
        }
        int i = getTotalPage()>5?5:getTotalPage();
        while(i-- > 0){
        	if(getCurrPage() == index){
        		sb.append("<a class='page_check' href=?" + Tools.encode4URL(getKeyword())	
                        + "&start="+getCountOnEachPage()*(index-1)+"#"+getAnchor()+">"+index+"</a> ");
        	}else{
        		sb.append("<a href=?" + Tools.encode4URL(getKeyword())	
                        + "&start="+getCountOnEachPage()*(index-1)+"#"+getAnchor()+">"+index+"</a> ");
        	}
        	index++;
        }
	    sb.append("<a href=?" + Tools.encode4URL(getKeyword()) + "&start="+ getRealEndIndex() + "#"+getAnchor()+">>></a> ");
	    sb.append(" &nbsp;&nbsp;共" + getTotalPage() + "页&nbsp;&nbsp;");
        sb.append("到第<input type=text id=PAGINATION_INPUT style=\"\" size=3>页&nbsp;<a href=\"#\" onclick=\"PAGINATION_JUMPTO(\'?" + Tools.encode4URL(getKeyword()) + "&start=\',"+(countOnEachPage)+")\">确定</a>");
        sb.append("<script>function PAGINATION_JUMPTO(href,co){window.location.assign(href+co*(parseInt(document.getElementById('PAGINATION_INPUT').value)-1))}</script>");
        sb.append("</div>");
        return sb.toString();
    }

    public String toHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("分页显示:");
        sb.append("第" + getCurrPage() + "页 / 总" + getTotalPage() + "页");
        sb.append("| 共" + getTotalCount() + "条记录");      
        sb.append(" | <a href=?keyword=" + keyword + "&value=" + value
                + "&start=0>首页</a>");
        if (!isFirstPage())
            sb.append(" | <a href=?keyword=" + keyword + "&value=" + value
                    + "&start=" + getPreviousIndex() + ">上页</a>");
        else
            sb.append(" | <font color=#999999>上页</font>");
        if (!isEndPage()) {
            sb.append(" | <a href=?keyword=" + keyword + "&value=" + value
                    + "&start=" + getNextIndex() + ">下页</a>");
            sb.append(" | <a href=?keyword=" + keyword + "&value=" + value
                    + "&start=" + getRealEndIndex() + ">尾页</a>");
        } else {
            sb.append(" | <font color=#999999>下页</font>");
            sb.append(" | <font color=#999999>尾页</font>");
        }
        return sb.toString();
    }

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
}
