var manufacturerFlags = {
	flag1 : false,
	flag2 : false,
	flag3 : false,
}
$(function() {
	
	$("#changeTj").click(function(){
		$("#mfForm").find("input").removeAttr("readonly");
		$("#mfForm").find("select").removeAttr("disabled");
		$(this).remove();
		var htmlBut = '<button class="d-btn" type="button" style="margin-top: 0px;" id="mfTj">提交</button>';
		$(".file_btns").append(htmlBut);
		
		$(".admindiv").remove();
		
	})
	
	$(".file_btns").on("click","#mfTj",function(){
		
		var flag = true;
		var obj = $("#lxr");
		var result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		obj = $("#tel");
		result = phoneBlur(obj);
		if(!result){
			flag = false;
		}
		obj = $("#address");
		result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		if(!flag){
			return;
		}
		
		var form = new FormData(document.getElementById("mfForm"));
		$.ajax({
			url:"/user/grzx/saveManufacturer.action",
			type:"post",
			data:form,
			cache: false,  
            processData: false,  
            contentType: false,
			success:function(data){
				if(data.result!=-1){
					window.location.href = "/user/grzx/manufacturer.action";
				}else{
					layer.alert(data.msg);
				}
			}
		})
		
	})
	
	$("#lxr").blur(function() {
		manufacturerFlags.flag1 = false;
		var result = wszlInputBlur(this);//与完善资料中的验证共用
		if(result)
			manufacturerFlags.flag1 = true;
	});
	
	$("#tel").blur(function() {
		manufacturerFlags.flag2 = false;
		var result = phoneBlur(this);
		if(result)
			manufacturerFlags.flag2 = true;
	});

	$("#address").blur(function() {
		manufacturerFlags.flag3 = false;
		var result = wszlInputBlur(this);
		if(result)
			manufacturerFlags.flag3 = true;
	});

});
