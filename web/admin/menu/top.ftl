<style>
	.admin-li{
		position:relative;
	}
	.admin-dropdown{
		background:#fff;
		z-index:999;
		position:absolute;
		top: 31px;
		left: -25px;
		width:160px;
		padding-top: 20px;
		border:1px solid #ddd;
		border-top:none;
		color:#797979;
	}
	.admin-dropdown:before{
		position:absolute;
		top:0;
		left: -1px;
	    width: 160px;
	    height: 20px;
		content:"";
		display:block;
		background:#fff;
		border-bottom:1px solid #ddd;
	}
	.admin-dropdown .adm-list{
		padding:5px 0;
	}
	.admin-dropdown .adm-list>p{
		font-size: 16px;
		color: #212121;
		margin: 10px 0 10px 14px;
		cursor:pointer;
	}
	.admin-dropdown .adm-list ul li{
		float:none!important;
		margin-left:15px!important;
	}
	.admin-dropdown .adm-list ul li a{
		font-size:14px!important;
	}
	.admin-dropdown .adm-list ul li:hover a{
		color:#159eee!important;
	}
	
	
	.little-admin-li .little-admin-dropdown{
		border-bottom:1px solid #eaeaea;
	}
	.little-admin-li .little-admin-dropdown .adm-list .adml-ul{
		width:100%;
	}
	.little-admin-li .little-admin-dropdown .adm-list .adml-ul li a{
		padding-left:25px;
		font-size: 13px;
	}
</style>
<script>
	$(function(){
		$(".admin-li").mouseover(function(){
			$(this).find(".admin-dropdown").show();
		}).mouseout(function(){
			$(this).find(".admin-dropdown").hide();
		})
		
		//小窗口导航头
		$(".little-admin-li").mouseover(function(){
			$(this).find(".little-admin-dropdown").show();
			$(this).find("a").css("border","none");
		}).mouseout(function(){
			$(this).find(".little-admin-dropdown").hide();
			$(this).find("a").css({
				"borderBottomWidth":"1px",
				"borderBottomStyle":"solid",
				"borderBottomColor":"#eaeaea"
			});
			$(".little-admin-li>a").eq($(".little-admin-li>a").length-1).css("border","none")
		})
		
	})
	
</script>
<!-- 头部 -->
<div class="nav_header_box nhb_fixed">
    <div class="nav_box">
        <div class="nav clearfix">
            <div class="logo">
                <a href="">
                    <img src="/images/logo.png">
                </a>
            </div>
            <div class="login_register_box">
                <div class="login-register-btns" style="display:none">
                    <a class="login">登录</a>
                    <a class="register">注册</a>
                </div>
            </div>
            <div class="nav_list">
                <ul class="clearfix">
                    <li class="admin-li">
                        <a href="/index.action">会员管理</a>
                        <div class="admin-dropdown" style="display:none">
                    		<div class="adm-list">
                    			<ul class="adml-ul">
                    				<li>
                    					<a>注册用户列表</a>
                    				</li>
                    				<li>
                    					<a href="/admin/back/getAllUserPage.action">待审核单位</a>
                    				</li>
                    				<li>
                    					<a href="/admin/back/getAllManufacturerPage.action">工厂认证管理</a>
                    				</li>
                    			</ul>
                    		</div>
                        </div>
                    </li>
                    <li  class="admin-li">
                        <a href="/admin/back/getAllUserPage.action">需求管理</a>
                        <div class="admin-dropdown" style="display:none">
                    		<div class="adm-list">
                    			<ul class="adml-ul">
                    				<li>
                    					<a href="/admin/back/getAllDemandPage.action">带处理的需求</a>
                    				</li>
                    				<li>
                    					<a>需求跟踪</a>
                    				</li>
                    			</ul>
                    		</div>
                        </div>
                    </li>
                    <li  class="admin-li">
                        <a href="">订单管理</a>
                        <div class="admin-dropdown" style="display:none">
                    		<div class="adm-list">
                    			<ul class="adml-ul">
                    				<li>
                    					<a>订单跟踪</a>
                    				</li>
                    			</ul>
                    		</div>
                        </div>
                    </li>
                    <li class="admin-li">
                        <a href="">财务管理</a>
                        <div class="admin-dropdown" style="display:none">
                    		<div class="adm-list">
                    			<ul class="adml-ul">
                    				<li>
                    					<a>会员费</a>
                    				</li>
                    				<li>
                    					<a>服务费</a>
                    				</li>
                    				<li>
                    					<a>发票</a>
                    				</li>
                    			</ul>
                    		</div>
                        </div>
                    </li>
                    <li class="admin-li">
                        <a href="">超市商品</a>
                        <div class="admin-dropdown" style="display:none">
                    		<div class="adm-list">
                    			<ul class="adml-ul">
                    				<li>
                    					<a>交易记录</a>
                    				</li>
                    			</ul>
                    		</div>
                        </div>
                    </li>
                </ul>
            </div>

        </div>
    </div>
    <!-- 下线 -->
    <div class="navFixed_line"></div>
</div>

<#--缩小屏幕 menu-->
<div class="little_nav clearfix" style="display:none">
    <div class="little_nav_logo">
        <a>
            <img src="/images/logo.png">
        </a>
    </div>
    <div class="little_nav_list">
        <p class="little_btn fa fa-bars"></p>
    </div>
        <div class="login-register-btns" style="display:none">
            <a class="login">登录</a>
            <a class="register">注册</a>
        </div>
    <div class="nav_cont" style="display:none">
        <ul>
			<li class="little-admin-li">
			    <a href="/index.action">会员管理</a>
			    <div class="little-admin-dropdown" style="display:none">
					<div class="adm-list">
						<ul class="adml-ul">
							<li>
								<a>注册用户列表</a>
							</li>
							<li>
								<a>待审核单位</a>
							</li>
							<li>
								<a>工厂认证管理</a>
							</li>
						</ul>
					</div>
			    </div>
			</li>
            <li class="little-admin-li">
                <a href="/admin/back/getAllUserPage.action">需求管理</a>
                <div class="little-admin-dropdown" style="display:none">
            		<div class="adm-list">
            			<ul class="adml-ul">
            				<li>
            					<a>待处理的需求</a>
            				</li>
            				<li>
            					<a>需求跟踪</a>
            				</li>
            			</ul>
            		</div>
                </div>
            </li>
            <li  class="little-admin-li">
                <a href="">订单管理</a>
                <div class="little-admin-dropdown" style="display:none">
            		<div class="adm-list">
            			<ul class="adml-ul">
            				<li>
            					<a>订单跟踪</a>
            				</li>
            			</ul>
            		</div>
                </div>
            </li>
            <li class="little-admin-li">
                <a href="">财务管理</a>
                <div class="little-admin-dropdown" style="display:none">
            		<div class="adm-list">
            			<ul class="adml-ul">
            				<li>
            					<a>会员费</a>
            				</li>
            				<li>
            					<a>服务费</a>
            				</li>
            				<li>
            					<a>发票</a>
            				</li>
            			</ul>
            		</div>
                </div>
            </li>
            <li class="little-admin-li">
                <a href="">超市商品</a>
                <div class="little-admin-dropdown" style="display:none">
            		<div class="adm-list">
            			<ul class="adml-ul">
            				<li>
            					<a>交易记录</a>
            				</li>
            			</ul>
            		</div>
                </div>
            </li>
        </ul>
    </div>
    <!-- 下线 -->
    <div class="navFixed_line"></div>
</div>