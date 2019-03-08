$(function(){
	//滚动加载
	$("#goodsDiv img").scrollLoading();
	//
	$("[name^='price']").bind("keyup onafterpaste",function(){
		this.value=this.value.replace(/[^\d.]/g,'').replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3').replace(/[^\d]{2,}/g,'');
		if(this.name == 'goods.danjia'){
			if(this.value > 99999999){
				this.value = 99999999;
			}
		}
	});
})

function search(){
	var type = $(".typeActive").data("type");
	if(type){
		$("#listGoodsForm").append($("<input type='hidden' name='type' value='"+type+"'>"));
	}
	$("#listGoodsForm").submit();
}
function typeActive(_this){
	var type = $(_this).data("type");
	$(".typeActive").each(function(){
		if($(this).data("type") != type){
			$(this).removeClass("typeActive");
		}
	})
	$(_this).toggleClass('typeActive');
	search();
}

//加入收藏
function flyCollection(event,random) {
	var $login = $(".login:visible");
	if ($login.length > 0) {
		$login.click();
		return;
	}
	var event = event || window.event;
	var target = event.target || event.srcElement;
	$.ajax({
		url:"/user/grzx/addCollection.action",
		data:{"random":random},
		dataType:'json',
		success:function(data){
			if(data.status==0){
				$(target).parents(".product_show").find("#ysc").show().end().find("#tjsc").hide();
				layer.msg("收藏成功",{icon:1,time:1000});
			}
			
		}
	})
	
}

