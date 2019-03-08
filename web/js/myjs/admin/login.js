var adminLogin = {
		flag1:false,
		flag2:false,
}

$(function(){
	$("#tel").blur(function(){
		adminLogin.flag1 = false;
		var result = phoneBlur(this);
		if(result)
			adminLogin.flag1 = true;
	})
	
	$("#password").blur(function(){
		adminLogin.flag2 = false;
		var result = passwordBlur(this);
		if(result)
			adminLogin.flag2 = true;
	})
	
	
	$("#login").click(function(){
		var result = checkBeforeAdminLogin();
		
		if(result){
			var form = new FormData(document.getElementById("adminloginForm"));
		
		
			$.ajax({
				url:"/admin/loginForAdmin.action",
				type:"post",
				data:form,
				processData: false,
				contentType: false,
				success:function(data){
					if(data.result==-1){
						$("#password").next().text(data.msg);
					}else{
						$("#password").next().text("");
						window.location.href = "/admin/back/getAllUserPage.action";
					}
				}
			})
		
		}
	});
})

function checkBeforeAdminLogin(){
	var f = true;
	if(!adminLogin.flag1){
		f = false;
		var obj = $("#tel");
		phoneBlur(obj);
	}
	
	if(!adminLogin.flag2){
		f = false;
		var obj = $("#password");
		passwordBlur(obj);
	}
	return f;
}