$(function(){
	
	//删除图片
	$(".file-upload").on("click",".fa-close",function(e){
		var userType = $(this).attr("data-userType");
		var tishi;
		if(userType==1){
			tishi = "当前用户已审核，删除后需要再次审核，确定删除吗？！";
		}else{
			tishi = "确定删除，重新上传吗？！";
		}
		layer.confirm(
			tishi, 
			{btn: ['确定','取消']	}, 
			function(index1){	
				layer.close(index1);
				window.location.href = "/user/grzx/removeFile.action";
			}
		);
	});
	
	//修改
	$("#changeTj").click(function(){
		$("#wszlForm").find("input").removeAttr("readonly");
		$("#wszlForm").find("input").removeAttr("disabled");
		$(this).remove();
		var htmlBut = '<button class="d-btn" type="button" id="wszlTj" data-op="update" >提交</button>';
		$(".file_btns").append(htmlBut);
		
		for(var i=1;i<=3;i++){
			var htmlLa = '<label for="file'+i+'" style="display:none">点击修改</label>';
			var imgSrc = $(".sfi"+i).find("img").attr("src");
			if(imgSrc){
				$(".sfi"+i).next().show();
			}
			$(".sfi"+i).append(htmlLa);
		}
		
	});
	
	
	$(".file_btns").on("click","#wszlTj",function(){
		var flag = true;
		var obj = $("#companyName");
		var result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		obj = $("#address");
		var result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		obj = $("#nsrsbh");
		var result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		
		//我能提供字段 
		var $checks = $(".data-type .data-type-cont .data-check");
		var checkFlag = true;
		$checks.each(function(){
			if($(this).is(':checked')){
				checkFlag = false;
				return false;
			}
		})
		if(checkFlag){//没有一个被选中
			flag = false;
			$(".data-type .data-type-cont").parent().next().text("该字段不能为空");
		}else{
			$(".data-type .data-type-cont").parent().next().text("");
		}
		
		obj = $("#lxr");
		var result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		obj = $("#lxPhone");
		var result = wszlInputBlur(obj);
		if(!result){
			flag = false;
		}
		if(!flag){
			return;
		}
		
		var fileImgSrc = $(".sfi1").find("img").attr("src");
		if(!fileImgSrc){
			layer.alert("营业执照需要上传!");
			return;
		}
		
		var dataOp = $("#wszlTj").attr("data-op");
		
		wszlTj(dataOp);
	});
	
	
	

	$("#companyName,#address,#nsrsbh,#lxr,#lxPhone").blur(function(){
		wszlInputBlur(this);
	});
	
});

function wszlTj(dataOp){
	if(dataOp=="update"){
		$("#dataOp").val("update");
	}else{
		$("#dataOp").val("save");
	}
	var form = new FormData(document.getElementById("wszlForm"));
	$.ajax({
		url:"/user/grzx/saveWszl.action",
		type:"post",
		data:form,
		processData: false,
		contentType: false,
		success:function(data){
			if(data.result==0){
				window.location.href = "/user/grzx/wszl.action"
			}else{
				layer.alert(data.msg);
			}
		}
	})
}


