var xgmmFlags = {
		flag1 : false, //原密码
		flag2 : false, //新密码 确认新密码
		
		flag3 : false, //原密码
		flag4: false, //图形验证码
		flag5: false, //验证码
}

$(function(){
	$("#byZhForm .oldPwd").blur(function(){
		xgmmFlags.flag1 = false;
		var result = passwordBlur(this);
		if(result)
			xgmmFlags.flag1 = true;
	});
	$("#newPwd,#reNewPwd").blur(function(){
		xgmmFlags.flag2 = false;
		var result = passwordBlur(this);
		if(result)
			xgmmFlags.flag2 = true;
	});
	
	
	$("#byTelForm .oldPwd").blur(function(){
		xgmmFlags.flag3 = false;
		var result = passwordBlur(this);
		if(result)
			xgmmFlags.flag3 = true;
	});
	$(".code").unbind("blur");
	$("#byTelForm .code").blur(function(){
		xgmmFlags.flag4 = false;
		var result = codeBlur(this);
		if(result)
			xgmmFlags.flag4 = true;
	});
	$(".inputcode").unbind("blur");
	$("#byTelForm .inputcode").blur(function(){
		xgmmFlags.flag5 = false;
		var result = inputcodeBlur(this);
		if(result)
			xgmmFlags.flag5 = true;
	});
	
	
	$("#updateBtn,#saveBtn").click(function(){
		
		var idName = $(this).attr("id");
		var result;
		var form;
		if(idName=="saveBtn"){
			result = checkBeforeUpdatePws_tel();
		}else{
			result = checkBeforeUpdatePwd();
		}
		if(result){
			if(idName=="saveBtn"){
				form = new FormData(document.getElementById("byTelForm"));
			}else{
				form = new FormData(document.getElementById("byZhForm"));
			}
			
			$.ajax({
				url:"/user/grzx/updateNewPassword.action",
				type:"post",
				data:form,
				processData: false,
				contentType: false,
				success:function(data){
					layer.alert(data.msg);
					if(data.result==0){
						window.location.href = "/user/grzx/grzx.action";
					}
				}
			})
			
		}
		
	});
	
	
});

function checkBeforeUpdatePws_tel(){
	var f = true;
	
	if(!xgmmFlags.flag3){
		f = false;
		var obj = $("#byTelForm .oldPwd");
		passwordBlur(obj)
	}
	
	//图像验证码
	if(!xgmmFlags.flag4){
		f = false;
		var obj = $("#byTelForm .code");
		codeBlur(obj);
	}
	
	if(!xgmmFlags.flag5){
		f = false;
		var obj = $("#byTelForm .inputcode");
		inputcodeBlur(obj);
	}
	
	return f;
}

function checkBeforeUpdatePwd(){
	var f = true;
	if(!xgmmFlags.flag1){
		f = false;
		var obj = $("#byZhForm .oldPwd");
		passwordBlur(obj)
	}
	
	if(!xgmmFlags.flag2){
		f = false;
		var obj = $("#newPwd");
		passwordBlur(obj);	
		var obj = $("#reNewPwd");
		passwordBlur(obj);	
	}
	return f;
}
