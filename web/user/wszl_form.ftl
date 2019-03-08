<div class="pc-left">
	<h2 class="person-title">
         单位信息
     </h2>
    <#-- 完善资料 -->
    <div class="completion-data">
        <form id="wszlForm" enctype="multipart/form-data" method="post">
        	<input type='hidden' id='dataOp' name="dataOp"/>
            <#-- 上 -->
            <div class="cd-top">
            	<div class="data-int-box clearfix">
            		<#if userEntity.userType?? && (isAdminPage!)!="yes">
                	<div class="approval_status">
		            	<#if !(isAdminPage?? && isAdminPage=="yes")>
			            	<#if (userEntity.userType!) == "1">
			                 已审核，点击修改可重新提交审核
			                <#elseif (userEntity.userType!) == "2">
			                 已提交，等待审核
			                <#elseif (userEntity.userType!) == "3">
			                 审核不通过，请修改<br/>
			                 原因：${remarks!}
			                </#if>
			            </#if>
		            </div> 
		            </#if> 
		            
		            <#if (isAdminPage!)=="yes" && (userEntity.userType!) == "3">
		            <div class="approval_status">
		            	 审核不通过，请修改<br/>
			             原因：${remarks!}
		            </div> 
		            </#if>        
                </div>
                <div class="data-int-box">
                    <span class="data-name">
                        <span class="start">*</span>
                        单位名称：
                    </span>
                    <input type="text" id="companyName" name="userEntity.companyName" value="${userEntity.companyName!}" <#if (userEntity.userType!)=="1">readonly</#if> >
                    <span class="dnot-pass"></span>
                </div>
                <div class="data-int-box">
                    <span class="data-name">
                        <span class="start">*</span>
                        地址：
                    </span>
                    <input type="text" id="address" name="userEntity.address" value="${userEntity.address!}" <#if (userEntity.userType!)=="1">readonly</#if> >
                    <span class="dnot-pass"></span>
                </div>
                <div class="data-int-box">
                    <span class="data-name">
                        <span class="start">*</span>
                        纳税人识别号：
                    </span>
                    <input type="text" id="nsrsbh" name="userEntity.nsrsbh" value="${userEntity.nsrsbh!}" <#if (userEntity.userType!)=="1">readonly</#if> >
                    <span class="dnot-pass"></span>
                </div>
                <div class="data-int-box" style="margin-bottom:90px">
                    <span class="data-name">
                        <span class="start">*</span>
                        我能提供：
                    </span>
                    <div class="data-type">
                        <div class="data-type-cont" style="display:block">
                            <label for="data1">
                                <input id="data1" type="checkbox" name="data" value="生产订单" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("生产订单")!=-1>checked</#if> > 生产订单
                            </label>
                            <label for="data2">
                                <input id="data2" type="checkbox" name="data" value="工序加工" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("工序加工")!=-1>checked</#if> > 工序加工
                            </label>
                            <label for="data3">
                                <input id="data3" type="checkbox" name="data" value="毛衫设计" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("毛衫设计")!=-1>checked</#if> > 毛衫设计
                            </label>
                            <label for="data4">
                                <input id="data4" type="checkbox" name="data" value="产品质检" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("产品质检")!=-1>checked</#if> > 产品质检
                            </label>
                            <label for="data5">
                                <input id="data5" type="checkbox" name="data" value="原材料" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("原材料")!=-1>checked</#if> > 原材料
                            </label>
                            <label for="data6">
                                <input id="data6" type="checkbox" name="data" value="成衣销售" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("成衣销售")!=-1>checked</#if> > 成衣销售
                            </label>
                            <label for="data7">
                                <input id="data7" type="checkbox" name="data" value="仓储服务" class="data-check" <#if (userEntity.userType!)=="1">disabled</#if> <#if (userEntity.advantage!)?index_of("仓储服务")!=-1>checked</#if> > 仓储服务
                            </label>
                        </div>
                    </div>
                    <span class="dnot-pass dp_2"></span>
                </div>
                <div class="data-int-box">
                    <span class="data-name">
                        <span class="start">*</span>
                        联系人：
                    </span>
                    <input type="text" id="lxr" name="userEntity.lxr" value="${userEntity.lxr!}" <#if (userEntity.userType!)=="1">readonly</#if> >
                    <span class="dnot-pass"></span>
                </div>
                <div class="data-int-box">
                    <span class="data-name">
                        <span class="start">*</span>
                        联系电话：
                    </span>
                    <input type="text" id="lxPhone" name="userEntity.lxPhone" value="${userEntity.lxPhone!}" <#if (userEntity.userType!)=="1">readonly</#if> >
                    <span class="dnot-pass"></span>
                </div>
               <div class="data-int-box clearfix">
                    <span class="data-name dn">
                        <span class="start">*</span>
                        营业执照：
                    </span>
                    <div class="file-upload-box">
                        <div class="file-upload">
                            <#-- 添加文件 -->
                            <div class="add-file adf1" <#if userEntity.yyzzImg??>style="display:none;"</#if> >
                                <div class="file-bg"></div>
                                <input id="file1" type="file" class="in-file if1" name="file" accept="image/*">
                                <label for="file1"></label>
                            </div>
                            <#-- 显示/修改文件 -->
                            <div class="show-file <#if !((userEntity.userType!) == "1" || (isAdminPage!)=="yes") >sf1</#if>" <#if !(userEntity.yyzzImg??)>style="display:none"</#if>>
                                <div class="show_file_img sfi1">
                                	<#if userEntity.yyzzImg??>
                                		<img alt="" src="/upfile/zzwj/${userEntity.yyzzImg!}">
                                	</#if>
                                </div>
                                
                                <label for="file1" <#if (userEntity.userType!) == "1" || (isAdminPage!)=="yes" >style="display:none"</#if> >点击修改</label>
                                <p class="del_file_img fa fa-close" data-userType="${userEntity.userType!}" <#if (userEntity.userType!) == "1" || (isAdminPage!)=="yes">style="display:none;"</#if>></p>
                            </div>
                        </div>
                        <div class="show_file_bigimg" style="display:none"></div>
                    </div>
                    <#-- 提示 -->
                    <div class="file-upload-tips">
                        （注：扫描件上请上传JPG或PNG格式的文件，最大不超过3M）
                    </div>
                </div>
                <div>
                	<#if isAdminPage?? && isAdminPage=="yes">
                		<#if (userEntity.userType!)=="2">
		                	<div class="file_btns">
		                		<button class="d-btn" type="button" id="wszlSh">审核通过</button>
		                		<button class="d-btn" type="button" id="wszlNsh">审核不通过</button>
		                	<div>
	                	</#if>
                	<#else>
                	<div class="file_btns">
                		 <#if (userEntity.userType!) != "1">
                		<button class="d-btn" type="button" id="wszlTj" data-op="save">提交</button>
                		<#else>
                		<button class="d-btn" type="button" id="changeTj">修改</button>
                		</#if>
                	</div>
                	</#if>
                </div>
            </div>
        </form>
    </div>
</div>






