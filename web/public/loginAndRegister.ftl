<!-- 登录 -->
<div class="loginbox" style="display:none;">
    <div class="login-account">
        <p class="close"></p>
        <p class="logintitle">用户登录</p>
        <form action="">
            <div class="dnot-pass-box">
                <input class="phone" type="text" placeholder="手机号码/邮箱">
                <span class="dnot-pass"></span>
            </div>
            <div class="dnot-pass-box">
                <div class="inputCode">
                    <input type="password" placeholder="密码" class="phonePassword" data-temp="login">
                    <span class="dnot-pass"></span>
                </div>
            </div>
            <div>
                <button type="button" class="entry" id="login">登录</button>
            </div>
            <div class="forgetbox">
                <a href="/toLostPwdPage.action" target="_black">忘记密码？</a>
                <a class="registerbox-change">新用户注册</a>
            </div>
        </form>
    </div>
</div>


<!-- 注册页面 -->
<div class="registerbox" style="display:none">
    <div class="register-account">
        <p class="close"></p>
        <div class="changeRegisterBtns">
            <span class="phoneRegisterChange" style="display:none">手机注册</span>
            <span class="foxRegisterChange">邮箱注册</span>
        </div>
        <!-- 手机注册 -->
        <div class="phoneRegister" style="display:block">
            <p class="logintitle">手机注册</p>
            <form>
                <div class="dnot-pass-box">
                    <input class="phone" type="text" placeholder="手机号码">
                    <span class="dnot-pass"></span>
                </div>
                <div class="dnot-pass-box">
                	<div class="reformcode">
                   		<input type="password" placeholder="密码（6-16个字符；字符/数字）" class="phonePassword">
                   		<span class="dnot-pass"></span>
                     </div>
                </div>
                <div class="dnot-pass-box">
                    <div class="reformcode">
                        <input type="password" placeholder="请确认密码" class="reformintcode">
                        <span class="dnot-pass"></span>
                    </div>
                </div>
                
                <div class="code-js">
                    <div class="codes">
                        <input type="text" placeholder="验证码" class="code">
                        <span class="dnot-pass"></span>
                        <span class="codeimg">
                        	<img border="0" src=""> 
                        </span>
                        <a class="changeImg">看不清？</a>
                    </div>
                    <div class="inputcodebox">
                        <input type="text" placeholder="请输入验证码" class="inputcode">
                        <span class="dnot-pass"></span>
                        <a class="getcode" data-flag="phoneRegister">获取手机验证码</a>
                    </div>
            	</div>
                <div class="readbox">
                    <label>
                        <input type="checkbox" checked>
                        <span>阅读并同意xxxxx网的服务协议</span>
                    </label>
                </div>
                <div>
                    <button type="button" class="entry" id="register">注册</button>
                </div>
                <div class="haved-account">
                    已有账号：
                    <a class="loginbox-change">直接登录</a>
                </div>
            </form>
        </div>
        <!-- 邮箱注册 -->
        <div class="foxRegister" style="display:none">
            <p class="logintitle">邮箱注册</p>
            <form action="">
                <div class="dnot-pass-box">
                    <input class="email" type="text" placeholder="邮箱">
                    <span class="dnot-pass"></span>
                </div>
                <div class="dnot-pass-box">
                	<div class="reformcode">
                        <input type="password" placeholder="密码（6-16个字符；字符/数字）" class="phonePassword">
                        <span class="dnot-pass"></span>
                    </div>
                </div>
                <div class="dnot-pass-box">
                    <div class="reformcode">
                        <input type="password" placeholder="请确认密码" class="reformintcode">
                        <span class="dnot-pass"></span>
                    </div>
                </div>
                <div class="code-js">
                    <div class="codes">
                        <input type="text" placeholder="验证码" class="code">
                        <span class="dnot-pass"></span>
                        <span class="codeimg">
                        	<img border="0" src="">
                        </span>
                        <a class="changeImg">看不清？</a>
                    </div>
                    <div class="inputcodebox">
                        <input type="text" placeholder="请输入验证码" class="inputcode">
                        <span class="dnot-pass"></span>
                        <a class="getEmailcode" data-flag="foxRegister">获取邮箱验证码</a>
                    </div>
            	</div>
                
                <div class="readbox">
                    <label>
                        <input type="checkbox" checked>
                        <span>阅读并同意xxxxx网的服务协议</span>
                    </label>
                </div>
                <div>
                    <button type="button" class="entry" id="emailRegister">注册</button>
                </div>
                <div class="haved-account">
                    已有账号：
                    <a class="loginbox-change">直接登录</a>
                </div>
            </form>
        </div>
    </div>
</div>
<link href="/js/layer/css/layui.css" rel="stylesheet">
<script type="text/javascript" src="/js/layer/layer.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/myjs/publicFun.js"></script>
<script type="text/javascript" src="/js/myjs/indexForBack.js"></script>
<script type="text/javascript" src="/js/head-foot.js"></script>
<script type="text/javascript" src="/js/utils.js"></script>


