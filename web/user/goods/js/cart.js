function checkedAll1(event) {
	var event = event || window.event;
	var target = event.srcElement||event.target;
	var isCheck = $(target).is(":checked");
	$(".seller-name input[name='shop-cart']").each(function(i,n){
		if(isCheck != $(this).is(":checked")){
			$(this).click();
		}
	});
}
function checkedSome(event) {
	var event = event || window.event;
	var target = event.srcElement||event.target;
	var isCheck = $(target).is(":checked");
	$(target).parents(".cart-info").find("input[name^='shop-cart']").each(function(i,n){
		if(isCheck != $(this).is(":checked")){
			$(this).click();
		}
	});
}
//选中商品
function cartCheckedClick(event){
	var event = event || window.event;
	var target = event.srcElement||event.target;
	event.preventDefault();
	$.ajax({           
        url:'/user/grzx/updateCartChecked.action',    
        data:{"id":$(target).data("id")},    
        dataType:'json',    
        success:function(data){
        	$(target).prop("checked",data.isCheck);
        	var checkedSome = !$(target).parents(".cart-info").find(".seller-shop input[name='shop-cart']:not(:checked)").length;
        	$(target).parents(".cart-info").find(".seller-name [name='shop-cart']").prop("checked",checkedSome);
        	var checkedAll = !$(".seller-name input[name='shop-cart']:not(:checked)").length;
        	$(".selectAll").prop("checked",checkedAll);
        	countCart();
        }
    });
}
//删除商品
function removeCart(_this,id){
	layer.confirm('确定删除吗?', function(index){
		$.ajax({           
	        url:'/user/grzx/removeCart.action',    
	        data:{"id":id},    
	        dataType:'json',    
	        success:function(data){
	        	$.updateCartNum(-$(_this).parent().find(".num-show").val());
        		var $cartInfo = $(_this).parents(".cart-info");
            	if($cartInfo.find(".seller-shop").length<=1){
            		$cartInfo.remove();
            	}else{
            		$(_this).parents(".seller-shop").remove();
            	}
            	if($(".seller-shop").length ==0){
            		$(".shop-cart-show").remove();
            		$(".cart-none").show();
            	}
            	countCart();
            	layer.close(index);
            }       
	    });
	});
}
function removeCartAll(){
	layer.confirm('确定删除选中商品吗?', function(index){
		$(".ss-checkbox input[name='shop-cart']:checked").each(function(i,_this){
			!function(_this){
				$.ajax({           
			        url:'/user/grzx/removeCart.action',    
			        data:{"id":$(_this).data("id")},    
			        dataType:'json',    
			        success:function(data){
			        	$.updateCartNum(-$(_this).parent().parent().find(".num-show").val());
		        		var $cartInfo = $(_this).parents(".cart-info");
		            	if($cartInfo.find(".seller-shop").length<=1){
		            		$cartInfo.remove();
		            	}else{
		            		$(_this).parents(".seller-shop").remove();
		            	}
		            	if($(".seller-shop").length ==0){
		            		$(".shop-cart-show").remove();
		            		$(".cart-none").show();
		            	}
		            	countCart();
		            	layer.close(index);
		            }       
			    });
			}(_this);
		});
	});
}
function toOrder(_this){
	if($(_this).hasClass("settle-btn-ok")){
		location.href = "/user/grzx/getOrderInfo.action?t="+ (+new Date());
	}
}
//统计
function countCart(){
	var totalNum = 0,
		totalMoney = 0;
	$(".ss-checkbox input[name='shop-cart']:checked").each(function(){
		var $ul = $(this).parent().parent();
		totalNum += Number($ul.find(".num-show").val());
		totalMoney += Number($ul.find(".totalMoney").html());
	});
	$(".goods-num").html(totalNum);
	$(".title-price").html(totalMoney);
	if(totalNum > 0){
		$("#toOrderButton").removeClass().addClass("settle-btn-ok");
	}else{
		$("#toOrderButton").removeClass().addClass("settle-btn-no");
	}
	
}

//检查库存
function checkCartNum(_this){
	$this = $(_this);
	var kucun = $this.data("kucun");
	var num = Number($this.val());
	var $goods = $this.parents(".seller-shop").find("input[name='shop-cart']");
	if(num > kucun){
		layer.msg('库存不足',{icon:5,time: 1000},function(){});
		$this.val(kucun);
		num = kucun;
	}
	if(isNaN(num) || num==0){
		num = 1;
		$this.val(num);
	}
	
	$.ajax({           
        url:'/user/grzx/updateCartNum1.action',    
        data:{id:$goods.data("id"),num:num},    
        dataType:'json',    
        success:function(data){        	
        }       
    });
	var prevNum = Number($this.data("prevnum"));
	var car_num = Number($(".car_num").html());
	console.log(prevNum)
	console.log(car_num)
	$(".car_num").html(car_num-prevNum+num);
	$this.data("prevnum",num);
	var totalMoney = (num*$this.data("danjia")).toFixed(2);
	$this.parents(".seller-shop").find(".totalMoney").html(totalMoney);
	//若选中 计算总价
	if($goods.is(":checked")){
		countCart();
	}
}

//加减
function addCartNum(_this,num){
	var $num = $(_this).siblings(".num-show");
	num += Number($num.val());	
	if(num <= 0)
		return;
	$num.val(num);
	checkCartNum($num[0]);	
}
$(function () {
	countCart();
});