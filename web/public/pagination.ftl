<div class="pagination pag-two">
	<#if pageBean.currentPage==1>
		<span style="width:100px;">上一页</span>
	<#else>
		<a	href="javascript:;" onclick="toPage(${pageBean.currentPage-1})">上一页</a> 
	</#if>
	<#list pageBean.beginPageIndex .. pageBean.endPageIndex as num>
		<a <#if num==pageBean.currentPage>class="page_check" style="cursor:default;"</#if> href="javascript:;" <#if num!=pageBean.currentPage>onclick="toPage(${num})"</#if> >${num}</a> 
	</#list>
	
	<#if pageBean.currentPage==pageBean.pageCount>
		<span style="width:100px;">下一页</span>
	<#else>
		<a	href="javascript:;"	onclick="toPage(${pageBean.currentPage+1})">下一页</a>
	</#if>
</div>