<h2 class="person-title">
	工厂认证
</h2>
<div class="pc-left">
	<div class="completion-data">
		<form id="mfForm" method="post">
			<input type="hidden" id="random" name="manufacturerEntity.random" value="${manufacturerEntity.random!}">
			
			<#if manufacturerEntity.status?? && (isAdminPage!)!="yes">
				<div class="cd-top">
					<div class="data-int-box clearfix">
		            	<div class="approval_status">
		            		<#if !(isAdminPage?? && isAdminPage=="yes")>
		            			<#if manufacturerEntity.status==1>
		            				已审核，点击修改可重新提交审核
		            			<#elseif manufacturerEntity.status==2>
		            				已提交，等待审核
		            			<#elseif manufacturerEntity.status==3>
		            				审核不通过，请修改<br/>
			                	 	原因：${remarks!}
		            			</#if>
		            		</#if>
			            </div> 
		            </div>
           		</div>
			</#if>
			
			<#if (isAdminPage!)=="yes">
			<#if manufacturerEntity.status==3>
				<div class="cd-top">
					<div class="data-int-box clearfix">
		            	<div class="approval_status">
		            	审核不通过，请修改<br/>
			            原因：${remarks!}
		            	</div> 
		            </div>
           		</div>
			</#if>
			
			<div class="cd-top">
				<div class="data-int-box">
					<span class="data-name"> 
						单位：
					</span> 
					<input type="text" value="${userinfo!}" style="border:none;" readonly>
					<span class="dnot-pass"></span>
				</div>
			</div>
			</#if>
			
			<div class="cd-top">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						联系人：
					</span> 
					<input type="text" id="lxr" name="manufacturerEntity.lxr" value="${manufacturerEntity.lxr!}" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>readonly</#if> >
					<span class="dnot-pass"></span>
				</div>
			</div>
			<div class="cd-top">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						类型电话：
					</span> 
					<input type="text" id="tel" name="manufacturerEntity.tel" value="${manufacturerEntity.tel!}" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>readonly</#if> >
					<span class="dnot-pass"></span>
				</div>
			</div>
			<div class="cd-top">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						地址：
					</span> 
					<input type="text" id="address" name="manufacturerEntity.address" value="${manufacturerEntity.address!}" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>readonly</#if> >
					<span class="dnot-pass"></span>
				</div>
			</div>
			
			
			<#if (isAdminPage?? && isAdminPage=="yes") || (manufacturerEntity.status?? && manufacturerEntity.status==1)>
			<div class="cd-top admindiv">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						工厂机器设备数量：
					</span> 
					<input type="text" id="jqsbsl" name="manufacturerEntity.jqsbsl" value="${manufacturerEntity.jqsbsl!}" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>readonly</#if> >
					<span class="dnot-pass"></span>
				</div>
			</div>	
			<div class="cd-top admindiv">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						工人数量：
					</span> 
					<input type="text" id="grsl" name="manufacturerEntity.grsl" value="${manufacturerEntity.grsl!}" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>readonly</#if> >
					<span class="dnot-pass"></span>
				</div>
			</div>	
			<div class="cd-top admindiv">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						品控管理等级：
					</span> 
					<select id="pkgldj" name="manufacturerEntity.pkgldj" style="margin-right: 260px;margin-left: 10px;width:70px;" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>disabled</#if> >
						<option value="选择...">选择...</option>
						<option value="A" <#if (manufacturerEntity.pkgldj!)=="A">selected="selected"</#if> >A级</option>
						<option value="B" <#if (manufacturerEntity.pkgldj!)=="B">selected="selected"</#if> >B级</option>
						<option value="C" <#if (manufacturerEntity.pkgldj!)=="C">selected="selected"</#if> >C级</option>
						<option value="D" <#if (manufacturerEntity.pkgldj!)=="D">selected="selected"</#if> >D级</option>
						<option value="E" <#if (manufacturerEntity.pkgldj!)=="E">selected="selected"</#if> >E级</option>
					</select>
				</div>
			</div>	
			<div class="cd-top admindiv">
				<div class="data-int-box">
					<span class="data-name"> 
						<span class="start">*</span>
						最大日产量：
					</span> 
					<input type="text" id="zdrcl" name="manufacturerEntity.zdrcl" value="${manufacturerEntity.zdrcl!}" <#if manufacturerEntity.status?? && manufacturerEntity.status==1>readonly</#if> >
					<span class="dnot-pass"></span>
				</div>
			</div>	
			</#if>
			
			<div class="cd-bottom" style="margin-top: 0px;">
				<div class="file_btns" style="text-align: center;">
				
					<#if isAdminPage?? && isAdminPage=="yes">
                		<#if (manufacturerEntity.status!)==2>
	                		<button class="d-btn" type="button" style="margin-top: 0px;" id="mfSh">审核通过</button>
	                		<button class="d-btn" type="button" style="margin-top: 0px;" id="mfNsh">审核不通过</button>
	                	</#if>
                	<#else>
                		<#if manufacturerEntity.status?? && (manufacturerEntity.status!)==1>
                		<button class="d-btn" type="button" style="margin-top: 0px;" id="changeTj">修改</button>
                		<#else>
                		<button class="d-btn" type="button" style="margin-top: 0px;" id="mfTj">提交</button>
                		</#if>
                	</#if>
            		
            	</div>
        	</div>
		</form>
	</div>
</div>