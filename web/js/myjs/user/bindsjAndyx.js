var bindsjAndyxFlags  = {
		flag1 : false, 
		flag2 : false, 
		flag3 : false, 
		flag4 : false, //绑定邮箱的四个input
		
		flag5 : false, 
		flag6 : false, 
		flag7 : false, //绑定手机的四个input
		
	}

$(function(){
	
	$(".email").unbind("blur");
	$("#email").blur(function(){
		bindsjAndyxFlags.flag1 = false;
		var result = emailBlur(this);
		if(result)
			bindsjAndyxFlags.flag1 = true;
	});
	
	$("#password").blur(function(){
		bindsjAndyxFlags.flag2 = false;
		var result = passwordBlur(this);
		if(result)
			bindsjAndyxFlags.flag2 = true;
	});
	
	$(".code").unbind("blur");
	$("#emailImgCode").blur(function(){
		bindsjAndyxFlags.flag3 = false;
		var result = inputcodeBlur(this)
		if(result)
			bindsjAndyxFlags.flag3 = true;
	})
	
	$(".inputcode").unbind("blur");
	$("#emailCode").blur(function(){
		bindsjAndyxFlags.flag4 = false;
		var result = codeBlur(this);
		if(result)
			bindsjAndyxFlags.flag4 = true;
	});
	
	
	$("#tel").blur(function(){
		bindsjAndyxFlags.flag5 = false;
		var result = phoneBlur(this);
		if(result)
			bindsjAndyxFlags.flag5 = true;
		
	});
	
	$("#telImgCode").blur(function(){
		bindsjAndyxFlags.flag6 = false;
		var result = inputcodeBlur(this);
		if(result)
			bindsjAndyxFlags.flag6 = true;
	});
	
	$("#telCode").blur(function(){
		bindsjAndyxFlags.flag7 = false;
		var result = codeBlur(this);
		if(result)
			bindsjAndyxFlags.flag7 = true;
	});
	
	//图片验证码切换
	$(".code_img").click(function(){
		createCode(this,"bindsjAndyx");
	});
	
	
	
	$(".dobtn").click(function(){
		var result;
		var idName = $(this).attr("id");
		if(idName=="updateEmailBtn"){
			result = checkBeforeBindEmail();
		}else if(idName=="updateTelBtn"){
			result = checkBeforeBindTel();
		}
		if(result){
			var form;
			if(idName=="updateEmailBtn"){
				form = new FormData(document.getElementById("emailForm"));
			}else if(idName=="updateTelBtn"){
				form = new FormData(document.getElementById("telForm"));
			}
			
			$.ajax({
				url:"/user/grzx/updateEmailOrTel.action",
				type:"post",
				data:form,
				processData: false,
				contentType: false,
				success:function(data){
					layer.alert(data.msg);
					if(data.result==0){
						window.location.href = "/user/grzx/bindsjAndyx.action";
					}
				}
			});
			
		}
		
	});
	
	
	
	
});


function checkBeforeBindEmail(){
	var f = true;
	if(!bindsjAndyxFlags.flag1){
		f = false;
		var obj = $("#email");
		emailBlur(obj);
	}
	
	if(!bindsjAndyxFlags.flag2){
		f = false;
		var obj = $("#password");
		passwordBlur(obj);
	}
	
	if(!bindsjAndyxFlags.flag3){
		f = false;
		var obj = $("#emailImgCode");
		inputcodeBlur(obj)
	}
	
	if(!bindsjAndyxFlags.flag4){
		f = false;
		var obj = $("#emailCode");
		codeBlur(obj);
	}
	
	return f;
}


function checkBeforeBindTel(){
	var f = true;
	if(!bindsjAndyxFlags.flag5){
		f = false;
		var obj = $("#tel");
		phoneBlur(obj);
	}
	
	if(!bindsjAndyxFlags.flag6){
		f = false;
		var obj = $("#telImgCode");
		inputcodeBlur(obj);
	}
	
	if(!bindsjAndyxFlags.flag7){
		f = false;
		var obj = $("#telCode");
		codeBlur(obj);
	}
	
	return f;
}











