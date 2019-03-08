<!-- 导航条 -->
<div class="nav_header_box nhb_fixed">
    <div class="nav_box">
        <div class="nav clearfix">
            <div class="logo">
                <a href="">
                    <img src="/images/logo.png">
                </a>
            </div>

			 <#include "/public/link.ftl">
			 
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
    <div class="login_register_box lnl_status">
    	<#if !_USER_??>
        <div class="login-register-btns">
            <a class="login">登录</a>
            <a class="register">注册</a>
        </div>
        <#else>
        <!-- 登陆成功后显示购物车 -->
        <div class="buy-car-box">
            <a href="/user/grzx/listCart.action" class="car-icon fa fa-shopping-cart">
                <i class="car_num">${_CART_GOODS_NUM_}</i>
            </a>
            <div class="head-img">
                <div class="user-head-img">
                    <#if _USER_.headImg??>
		                	<img src="/upfile/head/${_USER_.headImg!}">
		                	<#else>
		                	<img src="/images/user-default-photo7.png">
	                	</#if>
                		<i class="head-news">9+</i>
                </div>
                <div class="per_withdraw" style="display:none">
                    <!-- <i class="fa fa-angle-up"></i> -->
                    <a href="/user/grzx/grzx.action">个人中心</a>
                    <a href="" class="news">
                        消息
                        <span class="news-num">(15)</span>
                    </a>
                		<a href="/logout.action">退出</a>
                </div>
            </div>
        </div>
        </#if>
    </div>
    <div class="nav_cont" style="display:none">
        <ul>
            <li>
                <a href="/index.action">首页</a>
            </li>
            <li>
                <a href="/chain.action">供应链</a>
            </li>
            <li>
		        <a href="">金融</a>
		    </li>
            <li>
                <a href="/supermarket/listGoods.action">超市</a>
            </li>
            <li>
                <a href="/design.action">设计</a>
            </li>
            <li>
                <a href="/factory.action">智能工厂</a>
            </li>
        </ul>
    </div>
    <!-- 下线 -->
    <div class="navFixed_line"></div>
</div>


<#include "/public/loginAndRegister.ftl">
