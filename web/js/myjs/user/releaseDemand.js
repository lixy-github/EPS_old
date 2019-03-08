/**
 * 发布需求页面js
 */

$(function(){
	
	$("#demandType").change(function(){
		var val = $(this).val();
		if(val=="工序加工"){
			$("#demandTypeChildrenDiv").show();
		}else{
			$("#demandTypeChildrenDiv").hide();
		}
	})
		
	//#ggyq,#slyq,#sjyq,#moneyStart,#moneyEnd, 其它暂时可以为空
	$("#memo").blur(function(){
		releaseDemandInputBlur(this);
	});
	
	
	
	$("#rdTj").click(function(){
		var currentPage = $(this).attr("data-currentPage");
		var flag = true;
//		var obj = $("#ggyq");
//		var result = releaseDemandInputBlur(obj);
//		if(!result){
//			flag = false;
//		}
//		obj = $("#slyq");
//		var result = releaseDemandInputBlur(obj);
//		if(!result){
//			flag = false;
//		}
//		obj = $("#sjyq");
//		var result = releaseDemandInputBlur(obj);
//		if(!result){
//			flag = false;
//		}
//		obj = $("#moneyStart");
//		var result = releaseDemandInputBlur(obj);
//		if(!result){
//			flag = false;
//		}
//		obj = $("#moneyEnd");
//		var result = releaseDemandInputBlur(obj);
//		if(!result){
//			flag = false;
//		}
		
		var xqlx = $("#demandType").val();
		var checkboxL = $(".select_type input:checked").length;
		if(xqlx=="工序加工" && checkboxL==0){
			layer.alert("请选择工序加工类别！");
			return;
		}
		
		obj = $("#memo");
		var result = releaseDemandInputBlur(obj);
		if(!result){
			flag = false;
		}
		
		if(!flag){
			return;
		}
		
		rdTj(currentPage);
		
	});
	
});


function rdTj(currentPage){
	var form = new FormData(document.getElementById("rdForm"));
	$.ajax({
		url:"saveDemand.action",
		type:"post",
		data:form,
		processData: false,
		contentType: false,
		success:function(data){
			//layer.alert(data.msg);
			if(data.result==-1){
				layer.alert(data.msg);
			}else{
				$.cookie('data-twoLevelMenu', "我发布的需求" ,{path: '/' });
				window.location.href = "/user/grzx/listDemandForUser.action?currentPage="+currentPage;
			}
		}
	})
}








