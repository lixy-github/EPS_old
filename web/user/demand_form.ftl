<h2 class="person-title">
	<#if isAdminPage?? && isAdminPage=="yes">
	需求处理
	<#else>
		<#if demandEntity.random??>
		修改需求
		<#else>
		发布新需求
		</#if>
	</#if>
</h2>
<div class="pc-left">
    <div class="uoload_product_box" style="padding-top: 0px;">
        <div class="publish_goods_box">
            <!-- 基本信息 -->
            <div class="publish_goods pgb-js">
                <form id="rdForm">
                	<input type="hidden" value="${demandEntity.random!}" name="demandEntity.random"/>
                    <div class="plg_int_box">
                        <span class="">
                            <span class="start">*</span>
                            需求类型：
                        </span>
                        <div class="select_type">
                        	<select style="height: 25px;" name="demandEntity.demandType" id="demandType">
                        		<option value="成衣订购" <#if (demandEntity.demandType!)=="成衣订购">selected="selected"</#if> >成衣订购</option>
                        		<option value="工序加工" <#if (demandEntity.demandType!)=="工序加工">selected="selected"</#if> >工序加工</option>
                        		<option value="原料采购" <#if (demandEntity.demandType!)=="原料采购">selected="selected"</#if> >原料采购</option>
                        		<option value="服装设计" <#if (demandEntity.demandType!)=="服装设计">selected="selected"</#if> >服装设计</option>
                        	</select>
                        </div>
                    </div>
                    
                    <div class="plg_int_box"  id="demandTypeChildrenDiv" style="display: none;">
                        <span class="">
                            <span class="start">*</span>
                            加工类型：
                        </span>
                        <div class="select_type">
                        		<input type="checkbox" value="织片" <#if (demandEntity.demandTypeChildren!)=="织片">checked="checked"</#if> name="demandEntity.demandTypeChildren" style="width: 15px;">织片
                        		<input type="checkbox" value="套口缝盘" <#if (demandEntity.demandTypeChildren!)=="套口缝盘">checked="checked"</#if> name="demandEntity.demandTypeChildren" style="width: 15px;">套口缝盘
                        		<input type="checkbox" value="整烫" <#if (demandEntity.demandTypeChildren!)=="整烫">checked="checked"</#if>  name="demandEntity.demandTypeChildren" style="width: 15px;">整烫
                        		<input type="checkbox" value="包装" <#if (demandEntity.demandTypeChildren!)=="包装">checked="checked"</#if>  name="demandEntity.demandTypeChildren" style="width: 15px;">包装
                        		<input type="checkbox" value="印花" <#if (demandEntity.demandTypeChildren!)=="印花">checked="checked"</#if>  name="demandEntity.demandTypeChildren" style="width: 15px;">印花
                        </div>
                    </div>

                    <div class="plg_int_box">
                        <span class="">
                            规格要求：
                        </span>
                        <input type="text" name="demandEntity.ggyq" id="ggyq" value="${demandEntity.ggyq!}">
                        <span class="dnot-pass"></span>
                    </div>
                    <div class="plg_int_box">
                        <span class="">
                            数量要求：
                        </span>
                        <input type="text" placeholder="单位自填：件、套、公斤、吨" name="demandEntity.slyq" id="slyq" value="${demandEntity.slyq!}">
                        <span class="dnot-pass"></span>
                    </div>
                    <div class="plg_int_box">
                        <span class="">
                            时间要求：
                        </span>
                        <input type="text" name="demandEntity.sjyq" id="sjyq" value="${demandEntity.sjyq!}">
                        <span class="dnot-pass"></span>
                    </div>
                    <div class="plg_int_box plgib">
                        <span class="">
                            单价要求：
                        </span>
                        <input type="text" class="price_section" name="demandEntity.moneyStart" id="moneyStart" value="${demandEntity.moneyStart!}">
                        <span>——</span>
                        <input type="text" class="price_section" name="demandEntity.moneyEnd" id="moneyEnd" value="${demandEntity.moneyEnd!}">
                        <span class="dnot-pass"></span>
                    </div>
                    <div class="plg_int_box texttext clearfix">
                        <span class="plg_int_taname">
                            <span class="start">*</span>
                            详情描述：
                        </span>
                        <textarea class="plg_int_textara" name="demandEntity.memo" id="memo">${demandEntity.memo!}</textarea>
                        <span class="dnot-pass plg_dp"></span>
                    </div>
                    <#if isAdminPage?? && isAdminPage=="yes">
                    <div class="plg_int_box">
                        <span class="">
                        	公司匹配：
                        </span>
                        <input type="hidden" value="" id="selectCnHid"/>
                        <input type="text" value="${selectCnHid!}" id="selectCn" onclick="window.open('/admin/back/getSelector.action?demandEntity.random=${demandEntity.random!}','ss','toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,width=608,height=350,left=600,top=150','true')">
                        <span class="dnot-pass"></span>
                    </div>
                    </#if>
                    
               		<div class="plg_int_box" style="text-align:center">
               			<#if isAdminPage?? && isAdminPage=="yes">
               			<button type="button" class="upload_img_btn" style="margin-left:100px" id="ppTj">确定匹配</button>
               			<#else>
               			<button type="button" class="upload_img_btn" style="margin-left:100px" id="rdTj" data-currentPage="${currentPage!}">提交</button>
               			</#if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>