/**
 * 公共的一些js
 */

var indexFlags = {
	flag1 : false, //手机
	flag2 : false, //密码
	flag3 : false, //图形验证码
	flag4 : false, //发送验证码(手机)
	flag5 : false, //邮箱
}

$(function(){	
	//登录 
	$("#login").click(function(){
		var f = checkBeforeLogin();
		if(f==true){
			var account = $(".loginbox .phone").val().trim();
			var password = $(".loginbox .phonePassword").val().trim();			
			$.ajax({
				url:"/login.action",
				type:"post",
				data:{
					"userEntity.tel":account,
					"userEntity.password":password
				},
				success:function(data){
					if(data.result==0){
						var url = $("#login_back_url").attr("data-url");
						if(url){
							window.location.href = url;
						}else{
							window.location.href = "/user/grzx/grzx.action";
						}					
					}else{
						$(".loginbox .phonePassword").next().text(data.msg);
					}
				}
			})
		}
	});
	
	//邮箱注册
	$("#emailRegister").click(function(){
		var f = checkBeforeClickEmailRegister();
		
		if(f==true){
			var email = $(".foxRegister .email").val().trim();
			var password = $(".foxRegister .phonePassword").val().trim();
			var emailCodeByUser = $(".foxRegister .inputcode").val().trim();
			var imgCodeByUser = $(".foxRegister .code").val().trim();
			
			$.ajax({
				url:"/registerUser.action",
				type:"post",
				data:{
					"userEntity.email":email,
					"userEntity.password":password,
					"emailCodeByUser":emailCodeByUser,
					"imgCodeByUser":imgCodeByUser,
					"registerType":1
				},
				success:function(data){
					if(data.result==-1){
						$(".foxRegister .inputcode").next().text(data.msg);
					}else{
						$(".foxRegister .email").val("");
						$(".foxRegister .phonePassword").val("");
						$(".foxRegister .reformintcode").val("");
						$(".foxRegister .code").val("");
						$(".foxRegister .inputcode").val("");
						layer.confirm(
							'注册成功,前往登录？！', 
							{btn: ['确定','取消']	}, 
							function(index){	
								layer.close(index);
								$(".registerbox").hide();
								$(".loginbox").show();
							}
						);
					}
				}
			})
		}
		
	})
	
	
	//手机注册
	$("#register").click(function(){
		var f = checkBeforeClickRegister();//点击前的验证
		if(f==true){
			
			var tel = $(".phoneRegister .phone").val().trim();
			var password = $(".phoneRegister .phonePassword").val().trim();
			var telCodeByUser = $(".phoneRegister .inputcode").val().trim();
			var imgCodeByUser = $(".phoneRegister .code").val().trim();
			
			$.ajax({
				url:"/registerUser.action",
				type:"post",
				data:{
					"userEntity.tel":tel,
					"userEntity.password":password,
					"telCodeByUser":telCodeByUser,
					"imgCodeByUser":imgCodeByUser,
					"registerType":0
				},
				success:function(data){
					if(data.result==-1){
						$(".phoneRegister .inputcode").next().text(data.msg);
					}else{
						$(".phoneRegister .phone").val("");
						$(".phoneRegister .phonePassword").val("");
						$(".phoneRegister .reformintcode").val("");
						$(".phoneRegister .code").val("");
						$(".phoneRegister .inputcode").val("");
						layer.confirm(
							'注册成功,前往登录？！', 
							{btn: ['确定','取消']	}, 
							function(index){	
								layer.close(index);
								$(".registerbox").hide();
								$(".loginbox").show();
							}
						);
					}
				}
			});
			
		}
	});
	
	//phone 失焦
	$(".phone").blur(function(){
		indexFlags.flag1 = false;
		var result = phoneBlur(this);
		if(result)
			indexFlags.flag1 = true;
	});
	
	//密码 确认密码失焦
	$(".phonePassword,.reformintcode").blur(function(){
		indexFlags.flag2 = false;
		var result = passwordBlur(this);
		if(result)
			indexFlags.flag2 = true;
	});
	
	//验证码
	$(".code").blur(function(){
		indexFlags.flag3 = false;
		var result = codeBlur(this);
		if(result)
			indexFlags.flag3 = true;
	});
	
	$(".inputcode").blur(function(){
		indexFlags.flag4 = false;
		var result = inputcodeBlur(this);
		if(result)
			indexFlags.flag4 = true;
	});
	
	$(".email").blur(function(){
		indexFlags.flag5 = false;
		var result = emailBlur(this)
		if(result)
			indexFlags.flag5 = true;
	});
	
	//获取手机验证码
	$(".getcode").click(function(){
		getCodeFun(this);
	});
	
	//注册时获取邮箱验证码
	$(".getEmailcode").click(function(){
		getEmailCodeFun(this);
	})
	
	//图片验证码切换
	$(".changeImg").click(function(){
		createCode(this);
	});
	
});

/**
 * 手机注册时的验证
 * @returns {Boolean}
 */
function checkBeforeClickRegister(){
	var f = true;
	if(!indexFlags.flag1){
		f = false;
		var obj = $(".phoneRegister .phone");
		phoneBlur(obj);
	}
	if(!indexFlags.flag2){
		f = false;
		var obj = $(".phoneRegister .phonePassword");
		passwordBlur(obj);	
		var obj = $(".phoneRegister .reformintcode");
		passwordBlur(obj);	
	}
	if(!indexFlags.flag3){
		f = false;
		var obj = $(".phoneRegister .code");
		codeBlur(obj);
	}
	if(!indexFlags.flag4){
		f = false;
		var obj = $(".phoneRegister .inputcode");
		inputcodeBlur(obj);
	}
	
	return f;
}

/**
 * 邮箱注册时的验证
 */
function checkBeforeClickEmailRegister(){
	var f = true;
	if(!indexFlags.flag5){
		f = false;
		var obj = $(".foxRegister .email");
		emailBlur(obj);
	}
	if(!indexFlags.flag2){
		f = false;
		var obj = $(".foxRegister .phonePassword");
		passwordBlur(obj);	
		var obj = $(".foxRegister .reformintcode");
		passwordBlur(obj);	
	}
	if(!indexFlags.flag3){
		f = false;
		var obj = $(".foxRegister .code");
		codeBlur(obj);
	}
	if(!indexFlags.flag4){
		f = false;
		var obj = $(".foxRegister .inputcode");
		inputcodeBlur(obj);
	}
	return f;
}

/**
 * 点击登录前验证
 */
function checkBeforeLogin(){
	var f = true;
	if(!indexFlags.flag1){
		f = false;
		var obj = $(".loginbox .phone");
		phoneBlur(obj);
	}
	
	if(!indexFlags.flag2){
		f = false;
		var obj = $(".loginbox .phonePassword");
		passwordBlur(obj);	
	}
	return f;
}



