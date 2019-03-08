/**
 * ajax 请求页面过期
 */
$(function(){
	$(document).ajaxError(function(event,request, settings){
		 var sessionStatus = request.getResponseHeader('sessionstatus');
         if(sessionStatus == 'timeout') {
        	layer.alert('页面过期，请重新登录!', function(index){         		
    		   layer.close(index);
    		   window.location.href = "/index.action";  
    		 });       
    	 }else{
    		 layer.msg('出错了', {icon: 2,time:1000});
    	 }
	});
});


/**
 * 验证手机是否合法
 */
function checkMobile(tel) {
	if (!(/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|4|5|6|7|8|9])\d{8}$/.test(tel))) {
		return false;
	}
	return true;
}


/**
 * 获取邮箱验证码
 * @param obj 验证码jq对象
 */
function getEmailCodeFun(obj){
	if($(obj).attr("data-disabled")=="yes"){
		return;
	}
	var dataFlag = $(obj).attr("data-flag");
	var email = $("."+dataFlag+" .email").val().trim();
	if(email==""){
		$("."+dataFlag+" .email").next(".dnot-pass").text("邮箱不能为空");
	}else{
		var result = checkEmailReg(email);
		if(result==false){
			$("."+dataFlag+" .email").next(".dnot-pass").text("邮箱格式不正确");
		}else{
			$("."+dataFlag+" .code").next(".dnot-pass").text("");
			
			var time = 60;
			var imgCode = $("."+dataFlag+" .code").val().trim();
			if(imgCode==""){
				$("."+dataFlag+" .code").next().text("图形验证码不能为空");
				return;
			}
			//获取后台验证码
			$.ajax({
				url:"/public/sendEmail.action",
				data:{
					"userEntity.email":email,
					"sendEmailType":1, //0表示忘记密码邮件   1表示注册验证码
					"imgCodeByUser":imgCode //用户自己输入的图片验证码
				},
				success:function(data){
					if(data.result==-1){
						if(data.msg=="图片验证码不正确"){
							$("."+dataFlag+" .code").next(".dnot-pass").text(data.msg);
						}else{
							$("."+dataFlag+" .inputcode").next(".dnot-pass").text(data.msg);
						}
					}else if(data.result==0){
						layer.alert("验证码已经发送到邮箱，请查收");
						$("."+dataFlag+" .getEmailcode").attr("data-disabled","yes");
						var si = setInterval(function() {
							if(time==0){
								clearInterval(si);
								$("."+dataFlag+" .getEmailcode").text("获取邮箱验证码");
								$("."+dataFlag+" .getEmailcode").removeAttr("data-disabled");
							}else{
								$("."+dataFlag+" .getEmailcode").text(time+"秒后重新获取");
								time--;
							}
						},1000);
					}
				}
			})
		}
	}
}

/**
 * 获取手机验证码
 * @param obj 验证码jq对象
 */
function getCodeFun(obj){
	if($(obj).attr("data-disabled")=="yes"){
		return;
	}
	var dataFlag = $(obj).attr("data-flag");
	var tel;
	if(dataFlag=="byTelForm"){ //xgmm.action 下的通过手机修改密码 
		tel = $("#telSpan").text().trim();
	}else{
		tel = $("."+dataFlag+" .phone").val().trim();
	}
	if(tel==""){ //通过手机修改密码时不需要验证 是否为空
		$("."+dataFlag+" .phone").next(".dnot-pass").text("手机号码不能为空");
	}else{
		var result = checkMobile(tel);
		if(result==false){
			$("."+dataFlag+" .phone").next(".dnot-pass").text("手机号码格式不正确");
		}else{
			
			$("."+dataFlag+" .code").next(".dnot-pass").text("");
			
			var time = 60;
			var imgCode = $("."+dataFlag+" .code").val().trim();
			if(imgCode==""){
				$("."+dataFlag+" .code").next().text("图形验证码不能为空");
				return;
			}
			//获取后台验证码
			$.ajax({
				url:"/public/getTelCode.action",
				data:{
					"userEntity.tel":tel,
					"imgCodeByUser":imgCode
				},
				success:function(data){
					if(data.result==-1){
						if(data.msg=="图片验证码不正确"){
							$("."+dataFlag+" .code").next(".dnot-pass").text(data.msg);
						}else{
							$("."+dataFlag+" .inputcode").next(".dnot-pass").text(data.msg);
						}
					}else if(data.result==0){
						layer.alert("验证码已经发送到手机，请查收");
						$("."+dataFlag+" .getcode").attr("data-disabled","yes");
						if($("."+dataFlag+" .code").next(".dnot-pass").text()!=""){
							$("."+dataFlag+" .code").next(".dnot-pass").text("");
						}
						if($("."+dataFlag+" .inputcode").next(".dnot-pass").text()!=""){
							$("."+dataFlag+" .inputcode").next(".dnot-pass").text("");
						}
						var si = setInterval(function() {
							if(time==0){
								clearInterval(si);
								$("."+dataFlag+" .getcode").text("获取手机验证码");
								$("."+dataFlag+" .getcode").removeAttr("data-disabled");
							}else{
								$("."+dataFlag+" .getcode").text(time+"秒后重新获取");
								time--;
							}
						},1000);
					}
				}
			})
		}
	}
}

/**
 * phone 的失焦 
 */
function phoneBlur(obj){
	var phone = $(obj).val().trim();
	var type = $(obj).attr("placeholder");
	if(type=="手机号码/邮箱"){
		if(phone==""){
			$(obj).next(".dnot-pass").text("手机号码/邮箱不能为空");
			return false;
		}else{
			var flagTel = checkMobile(phone);
			var flagEmail = checkEmailReg(phone);
			if( flagTel==false && flagEmail==false ) {
				$(obj).next(".dnot-pass").text("手机号码/邮箱格式不正确");
				return false;
			}else{
				$(obj).next(".dnot-pass").text("");
				return true;
			}
		}
	}else{
		if(phone==""){
			$(obj).next(".dnot-pass").text("手机号码不能为空");
			return false;
		}else{
			var flag = checkMobile(phone);
			if(!flag){
				$(obj).next(".dnot-pass").text("手机号码格式不正确");
				return false;
			}else{
				$(obj).next(".dnot-pass").text("");
				return true;
			}
		}
	}
	
}

/**
 * 密码 确认密码 失焦
 */
function passwordBlur(obj){
	var className = $(obj).attr("class");
	var password = $(obj).val();
	if(password==""){
		$(obj).next(".dnot-pass").text("密码不能为空");
		return false;
	}else{
		var result = checkPwdReg(password);
		if(result==false){
			$(obj).next(".dnot-pass").text("密码应为6-16个字符（字母/数字）");
			return false;
		}else{
			$(obj).next(".dnot-pass").text("");
		}
	}
	if($(obj).attr("data-temp")=="login" || $(obj).attr("data-temp")=="oldPwd"){ //登录时、修改密码旧密码 不需要检查两次密码是否一致
		return true;
	}
	
	var r;
//	alert(className)
//	alert(className.indexOf("phonePassword"))
	if(className.indexOf("phonePassword")!=-1){ 
		var rePassword = $(obj).parent().parent().next().find(".reformintcode").val().trim();
		r = checkTwoPwdIsSame(password, rePassword,obj);
	}else if(className.indexOf("reformintcode")!=-1){
		var rePassword = $(obj).parent().parent().prev().find(".phonePassword").val().trim(); //这里是密码 那变量password自然是重复密码
		r = checkTwoPwdIsSame(rePassword, password,obj)
	}
	return r;
}
//检查密码正在表达式  字母数字组成 6-16长度
function checkPwdReg(password){
	if (!(/^[a-zA-Z0-9]{6,16}$/.test(password))) {
		return false;
	}
	return true;
}

//检查两次密码是否一致
function checkTwoPwdIsSame(password,rePassword,obj){
	if(rePassword!=""){
		if(password!=rePassword){
			$(obj).next(".dnot-pass").text("两次密码不一致");
			return false;
		}else{
			var className = $(obj).attr("class");
			$(obj).next(".dnot-pass").text("");
			if(className.indexOf("phonePassword")!=-1){
				$(obj).parent().parent().next().find(".dnot-pass").text("");
			}else if(className.indexOf("reformintcode")!=-1){
				$(obj).parent().parent().prev().find(".dnot-pass").text("");
			}
			
			return true;
		}
	}
	return false;
}

/**
 * 检查验证码
 */
function codeBlur(obj){
	var codeByUser = $(obj).val().trim();
	if(codeByUser==""){
		$(obj).next(".dnot-pass").text("验证码不能为空");
		return false;
	}else{
		$(obj).next(".dnot-pass").text("");
		return true;
	}
}

/**
 * 手机验证码
 */
function inputcodeBlur(obj){
	var inputcode = $(obj).val().trim();
	if(inputcode==""){
		$(obj).next(".dnot-pass").text("验证码不能为空");
		return false;
	}else{
		$(obj).next(".dnot-pass").text("");
		return true;
	}
}

/**
 * 邮箱Blur
 */
function emailBlur(obj){
	var email = $(obj).val().trim();
	if(email==""){
		$(obj).next(".dnot-pass").text("邮箱不能为空");
		return false;
	}else{
		var r = checkEmailReg(email);
		if(r==false){
			$(obj).next(".dnot-pass").text("邮箱格式不正确");
			return false;
		}else{
			$(obj).next(".dnot-pass").text("");
			return true;
		}
	}
}
//邮箱正则表达式验证
function checkEmailReg(email){
	if (!(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email))) {
		return false;
	}
	return true;
}


/**
 * 创建验证码
 */
function createCode(obj,type) {
	var $imgObj;
	if(type=="lostWdPage"){
		$imgObj = $(obj);
	}else if(type=="bindsjAndyx"){
		$imgObj = $(obj).find("img")
	}else{
		$imgObj = $(obj).prev().find("img");
	}
	$imgObj.removeAttr("src");
	var t = new Date().getTime();
	$imgObj.attr("src","/public/getCodeImg.action?t="+t);
}

/**
 * 完善资料中的6个不能为空判断
 */
function wszlInputBlur(obj){
	var val = $(obj).val().trim();
	if(val==""){
		$(obj).next(".dnot-pass").text("该字段不能为空");
		return false;
	}
	$(obj).next(".dnot-pass").text("");
	return true;
}

/**
 * 发布需求中的6个不能为空判断
 */
function releaseDemandInputBlur(obj){
	var idName = $(obj).attr("id");
	var val = $(obj).val().trim();
	if(val==""){
		if("moneyStart" == idName){
			$(obj).next().next().next(".dnot-pass").text("该字段不能为空");
		}else{
			$(obj).next(".dnot-pass").text("该字段不能为空");
		}
		return false;
	}
	
	if("moneyStart" == idName){
		$(obj).next().next().next(".dnot-pass").text("");
	}else{
		$(obj).next(".dnot-pass").text("");
	}
	return true;
	
}


