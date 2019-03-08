function addAddress(){
	if(!checkAddress()){
		return false;
	}
	$.ajax({           
        url:'/user/grzx/addAddress.action',    
        data:$("#addressForm").serialize(),    
        dataType:'json',    
        success:function(data){
        	var address = data.address;
        	if($("input[name='address.id']").val().trim() == ""){
        		$(".item-selected").addClass("addr-no").removeClass("item-selected");
            	var li = '<li class="clearfix" data-id="'+address.id+
            				'" data-province="'+address.province+'" data-city="'+address.city+'" data-county="'+address.county+
            				'" data-detaladdress="'+address.detalAddress+'" data-linkman="'+address.linkMan+'" data-phonesms="'+address.phoneSms+'">'+
            				'<div class="item-selected addr-change-js">'+
            					'<span>'+address.linkMan+'</span>'+
            					'<b></b>'+
            				'</div>'+
    				        '<div class="addr-detail">'+
    				            '<span class="addr-name">'+address.linkMan+'</span>'+
    				            '<span class="addr-info">'+address.province+' '+address.city+' '+address.county+' '+address.detalAddress+'</span>'+
    				            '<span class="addr-tel">'+address.phoneSms+'</span>'+
    				        '</div>'+
    				        '<div class="edit-addr" style="display:none">'+
    				            '<span class="default-addr change-color" data-edit="default">设为默认地址</span>'+
    				            '<span class="change-addr change-color" data-edit="edit">编辑</span>'+
    				            '<span class="del-addr change-color" data-edit="remove">删除</span>'+
    				        '</div>'+
    				     '</li>';
            	$(".item-buyer ul").prepend(li);
            	$(".show-add").show();
            	toAddress(address); //页脚  寄送到哪 html
        	}else{
        		var $target = $("input[name='address.id']").data("target");
        		$target.find(".addr-change-js span").html(address.linkMan);
            	$target.find(".addr-detail span")
            		.eq(0).html(address.linkMan).end()
            		.eq(1).html(address.province+' '+address.city+' '+address.county+' '+address.detalAddress).end()
            		.eq(2).html(address.phoneSms);
            	if($target.find(".item-selected").length){		//寄送至
            		toAddress(address);
            	}
        	}
        	$(".manage-address").hide();	//关闭窗口
        }
    });
}

function checkAddress(){
	$(".dnot-pass").hide();
	var isOk = true;
	$("#addressForm input:visible").each(function(){
		if(this.id=="phoneSms" && !this.value.match(/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/)){
			isOk = false;
			$(this).siblings(".dnot-pass").show();
		}else if(this.value.trim() == ""){
			isOk = false;
			$(this).siblings(".dnot-pass").show();
		};
	});
	if($("#province option:selected").val().trim()=="" || $("#city option:selected").val().trim()==""){
		$("#address-msg").show();
		isOk = false;
	}
	return isOk;
	
}
function toAddress(address){  //寄送至
	$("#toAddress").html(address.province+' '+address.city+' '+address.county+' '+address.detalAddress); 	
	$(".cia-addr span").eq(0).html("收货人").end().eq(1).html(address.linkMan).end().eq(2).html(address.phoneSms);
}
//收货地址事件
function addressClick(event) {
	var event = event || window.event;
	var target = event.target || event.srcElement;
	$target = $(target);
	if($target.hasClass("show-add"))
		return;
	var edit = $target.data("edit");
	if(edit == "default"){			//编辑按钮
		defaultAddress($target.parents(".clearfix"));
	}else if(edit == "edit"){
		editAddress($target.parents(".clearfix"));
	}else if(edit == "remove"){
		removeAddress($target.parents(".clearfix"));
	}else{	//点击选中
		$(".show-add .addr-change-js").addClass("addr-no").removeClass("item-selected");
		$target.parents(".clearfix").andSelf().find(".addr-change-js").addClass("item-selected").removeClass("addr-no");
		$selectedLi = $(".item-selected").parent();
		var addressArr = $selectedLi.find(".addr-info").html().split(" ");
		toAddress({
			province:addressArr[0]||"",
			city:addressArr[1]||"",
			county:addressArr[2]||"",
			detalAddress:addressArr[3]||"",
			linkMan:$selectedLi.find(".addr-name").html(),
			phoneSms:$selectedLi.find(".addr-tel").html()
			
		}); //页脚  寄送到哪 html
	}
}
function defaultAddress($target){
	$.ajax({           
        url:'/user/grzx/updateAddressDefault.action',    
        data:{id:$target.data("id")},    
        dataType:'json',    
        success:function(data){
        	$(".addr-default").hide();
        	$target.find(".addr-default").show();
        }
    });
}
function editAddress($target){
	$(".madct-name").html("编辑");
	$(".manage-address").show();
	$("input[name='address.id']").val($target.data("id")).data("target",$target);
	$("#province").val($target.data("province"));
	$("#province").trigger("change");
	$("#city").val($target.data("city"));
	$("#city").trigger("change");
	$("#county").val($target.data("county"));
	$("#detalAddress").val($target.data("detaladdress"));
	$("#linkMan").val($target.data("linkman"));
	$("#phoneSms").val($target.data("phonesms"));
	
}

function removeAddress($target){
	layer.confirm('确定要删除改地址吗？', {
		  btn: ['确定','取消'] //按钮
	}, function(index){
		$.ajax({           
	        url:'/user/grzx/removeAddress.action',    
	        data:{id:$target.data("id")},    
	        dataType:'json',    
	        success:function(data){
	        	$target.remove();
	        	if($target.find(".item-selected").length){
	        		$("#toAddress").html("");
		        	$(".cia-addr span").eq(0).html("").end().eq(1).html("").end().eq(2).html("请填写收货地址");
	        	}
	        	layer.close(index); 
	        }
	    });
		
	});
	
}

//提交订单
function addOrder(){
	var str = "";
	$(".goods-info").each(function(){
		if(!$(this).data("isenough")){
			str = str+" "+$(this).data("goodsname")
		}
	});
	if(str != ""){
		layer.alert('商品：'+str+' <span style="color:red">缺货</span>，请退回购物车重新选择', function(index){
			location.href = "/user/grzx/listCart.action"
			layer.close(index);
		});       
		return false;
	}
	$addressSelected = $(".item-selected");
	if(!$addressSelected.length){
		layer.alert('请填写收货地址', function(index){
			location.href = "#pt-sett";
			layer.close(index);
		});
		return false;
	}
	location.href = "/user/grzx/addOrder.action?id="+$addressSelected.parent().data("id")
	
}

$(function () {
	 // 新增
    $(".add-address").click(function(){
    	$("input[name='address.id']").val("");
    	$("#detalAddress").val("");
    	$("#linkMan").val("");
    	$("#phoneSms").val("");
        $(".madct-name").html("新增");
        $(".manage-address").show();
    })

    // 折叠  已有的收货地址
    $(".open-addr").click(function () {
        $(this).hide();
        $(".show-add li,.shrink-addr").show();
    })
    $(".shrink-addr").click(function () {
        $(this).hide();
        $(".show-add li").eq(0).show().siblings().hide();
        $(".open-addr").show();
    })

    // 支付方式
    $(".select-pay>span").click(function () {
        $(this).addClass("pay-confirm").siblings().removeClass("pay-confirm");
        $(this).find("b").show();
        $(this).siblings().find("b").hide();
    })

    // 开发票
    $(".judge-invoice").change(function () {
        if ($(this).is(":checked")) {
            $(".invoice-type").removeClass("disabled");
        } else {
            $(".invoice-type").addClass("disabled");
        }
    })
    // 增值税发票
    $(".VAT-ordinary").click(function(){
        $(".open-invoice").show();
        $(".vat-o").addClass("select-VAT-invoice").siblings().removeClass("select-VAT-invoice");
        $(".int-invoice .inti-box:gt(0)").find(".must").hide();
    })
    $(".VAT-special").click(function(){
        $(".open-invoice").show();
        $(".vat-s").addClass("select-VAT-invoice").siblings().removeClass("select-VAT-invoice");
        $(".int-invoice .inti-box").find(".must").show();
    })
    $(".opib-close").click(function(){
        $(".open-invoice").hide();
    })
    $(".VAT-invoice").click(function(){
        $(this).addClass("select-VAT-invoice").siblings().removeClass("select-VAT-invoice");
        if($(this).text().indexOf("专用")!=-1){
            $(".int-invoice .inti-box").find(".must").show();
        }else{
            $(".int-invoice .inti-box:gt(0)").find(".must").hide();
        }
    })

    // 选择地址
    $("#distpicker5").distpicker({
        autoSelect: false
    });
    $(".madc-close").click(function(){
        $(".manage-address").hide();
    })

})