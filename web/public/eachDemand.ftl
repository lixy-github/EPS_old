<div class="sc_left">
    <div class="chain-list">
        <#if pageBean.recordList??>
		<#list pageBean.recordList as demandEntity>
		<div class="cl-item clearfix">
			<div class="cli-name">
				<p class="clin-color"></p>
				<p class="clin-text">${demandEntity.demandType!}</p>
			</div>
			<div class="cli-top">
				<div class="clit-company">
					<i class="fa fa-user-o"></i> <span class="clitc-name">${demandEntity.userEntity.companyName!}</span>
				</div>
				<div class="clit-date">
					<p>
						<i class="fa fa-tag fa-rotate-90"></i> <span>${demandEntity.time}</span>
					</p>
					<p>
						<i class="fa fa-commenting-o"></i> <span> 留言( <span>2</span> ) </span>
					</p>
				</div>
			</div>
			<div class="cli-middle">
				<#if isChain?? && isChain=="yes">
				${demandEntity.ggyq!}
				<#else>
				<a href="/user/grzx/releaseDemand.action?demandEntity.random=${demandEntity.random!}&currentPage=${currentPage!}" title="查看">${demandEntity.ggyq!}</a>
				</#if>
			</div>
			<div class="cli-bottom">${demandEntity.memo!}
			</div>
		</div>
		</#list>
		</#if>
	</div>
    <!-- 分页 -->
    <#include "/public/pagination.ftl">
</div>