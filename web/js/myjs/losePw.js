var lostPwFlags = {
	flag1 : false, 
	flag2 : false, 
	flag3 : false, //step1手机的三个验证 1 2 3
	flag4 : false, //step2的验证
	flag5 : false, //step1邮箱的三个验证
}

$(function(){
	
	$(".forgotpw-container .verify-step1 #tel").blur(function(){
		lostPwFlags.flag1 = false;
		var result = phoneBlur(this);
		if(result)
			lostPwFlags.flag1 = true;
	});
	
	$(".forgotpw-container .verify-step1 #imgCode").blur(function(){
		lostPwFlags.flag2 = false;
		var result = codeBlur(this);
		if(result)
			lostPwFlags.flag2 = true;
	});
	
	$(".forgotpw-container .verify-step1 #telCode").blur(function(){
		lostPwFlags.flag3 = false;
		var result = inputcodeBlur(this);
		if(result)
			lostPwFlags.flag3 = true;
	});
	
	$(".forgotpw-container .verify-step1 #email").blur(function(){
		lostPwFlags.flag5 = false;
		var result = emailBlur(this);
		if(result)
			lostPwFlags.flag5 = true;
	})
	
	$(".forgotpw-container .verify-step2 #pwd,.forgotpw-container .verify-step2 #rePwd").blur(function(){
		lostPwFlags.flag4 = false;
		var result = passwordBlur(this);
		if(result)
			lostPwFlags.flag4 = true;
	});
	
	
	//获取验证码
	$(".forgotpw-container .verify-step1 .got-code").click(function(){
		getCodeFun(this);
	});
	
	$("#codeImgImg").click(function(){
		createCode(this,"lostWdPage");
	})
	
	$(".forgotpw-container .verify-step2 #tjStep2").click(function(){
		var f = checkBeforeStep2Tj();
		if(f==true){
			var userId = $("#userId").val();
			
			if(userId==""){
				layer.alert("身份没有验证！");
			}else{
				var password = $(".forgotpw-container .verify-step2 #pwd").val();
				$.ajax({
					url:"/updatePassword.action",
					type:"post",
					data:{
						"losePwdType":0,
						"userEntity.id":userId,
						"userEntity.password":password
					},
					success:function(data){
						if(data.result==0){ //修改成功
							$(".verify-step1").css("display", "none");
					        $(".verify-step2").css("display", "none");
					        $(".verify-step3").css("display", "block");
					        $(".step3").addClass("z-old").addClass("z-old3");
						}else if(data.result==-1){
							$(".verify-step2 #rePwd").next().text(data.msg);
						}
						
					}
				})
			}
		}
	});
	
	$(".forgotpw-container .verify-step1 #tjStep1forTel").click(function(){
		var f = checkBeforeStep1Tj();
		if(f==true){
			var tel =  $(".forgotpw-container .verify-step1 #tel").val().trim();
			var imgCodeByUser = $(".forgotpw-container .verify-step1 #imgCode").val().trim();
			var telCodeByUser = $(".forgotpw-container .verify-step1 #telCode").val().trim();
			$.ajax({
				url:"/losePwdCheckTelOrEmail.action",
				type:"post",
				data:{
					"losePwdType":0,
					"telCodeByUser":telCodeByUser,
					"imgCodeByUser":imgCodeByUser,
					"userEntity.tel":tel,
				},
				success:function(data){
					if(data.result==-1){
						$(".verify-step1 #telCode").next().text(data.msg);
					}
					
					//需要切换一次图形验证码
					$("#codeImgImg").trigger("click");
					
					if(data.msg=="验证成功"){
						$(".verify-step1").css("display", "none");
				        $(".verify-step2").css("display", "block");
				        $(".verify-step3").css("display", "none");
				        $(".step2").addClass("z-old").addClass("z-old2");
					}
					
					var userId = data.aaData[0].split("_")[0];
					$("#userId").val(userId);
				}
			})
		}
	});
	
	
	$(".forgotpw-container .verify-step1 #tjStep1forEmail").click(function(){
		var f = checkBeforeStep1TjForEmail();
		if(f==true){
			var email = $(".forgotpw-container .verify-step1 #email").val().trim();
			$.ajax({
				url:"/losePwdCheckTelOrEmail.action",
				type:"post",
				data:{
					"losePwdType":1,
					"userEntity.email":email,
				},
				success:function(data){
					if(data.result==-1){
						$(".forgotpw-container .verify-step1 #email").next().text(data.msg);
					}else if(data.result==0){
						var param = data.aaData[0];
						//发送邮件
						$.ajax({
							url:"/public/sendEmail.action",
							type:"post",
							data:{
								"userEntity.email":email,
								"sendEmailType":0,
								"param":param
							},
							success:function(data){
								if(data.result==-1){
									$(".verify-step1 #email").next().text(data.msg);
								}else{
									layer.alert(data.msg+",可前往邮件修改密码");
									//然后可以回到主页
								}
							}
						})
						
					}
				}
			})
		}
	});
	
	
});

function checkBeforeStep2Tj(){
	var f = true;
	if(!lostPwFlags.flag4){
		f = false;
		var obj = $(".forgotpw-container .verify-step2 #pwd");
		passwordBlur(obj);	
		var obj = $(".forgotpw-container .verify-step2 #rePwd");
		passwordBlur(obj);	
	}
	return f;
}

function checkBeforeStep1Tj(){
	var f = true;
	if(!lostPwFlags.flag1){
		f = false;
		var obj = $(".forgotpw-container .verify-step1 #tel");
		phoneBlur(obj);
	}
	
	if(!lostPwFlags.flag2){
		f = false;
		var obj = $(".forgotpw-container .verify-step1 #imgCode");
		codeBlur(obj);	
	}
	
	if(!lostPwFlags.flag3){
		f = false;
		var obj = $(".forgotpw-container .verify-step1 #telCode");
		inputcodeBlur(obj);
	}
	return f;
}

function checkBeforeStep1TjForEmail(){
	var f = true;
	if(!lostPwFlags.flag5){
		f = false;
		var obj = $(".forgotpw-container .verify-step1 #email");
		emailBlur(obj);
	}
	return f;
}


