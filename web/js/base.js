//=====================全局函数========================
//Tab控制函数
function tabs(tabId, tabNum){
	//设置点击后的切换样式
	$(tabId + " .tab li").removeClass("curr");
	$(tabId + " .tab li").eq(tabNum).addClass("curr");
	//根据参数决定显示内容
	$(tabId + " .tabcon").hide();
	$(tabId + " .tabcon").eq(tabNum).show();
}
//=====================全局函数========================

//==================图片详细页函数=====================
//鼠标经过预览图片函数
function preview(img){
	$("#preview .jqzoom img").attr("src",$(img).attr("src"));
	$("#preview .jqzoom img").attr("jqimg",$(img).attr("bimg"));


	// var test=$(img).attr("src");
	// console.log(test.replace("-s","-m"));//三种大小的图片
}

//图片放大镜效果
$(function(){
	$(".jqzoom").jqueryzoom({xzoom:350,yzoom:350});
});

//图片预览小图移动效果,页面加载时触发
$(function(){
	var tempLength = 0; //临时变量,当前移动的长度
	var viewNum = 3; //设置每次显示图片的个数量
	var moveNum = 1; //每次移动的数量
	var moveTime = 300; //移动速度,毫秒
	var scrollDiv = $(".spec-scroll .items ul"); //进行移动动画的容器
	var scrollItems = $(".spec-scroll .items ul li"); //移动容器里的集合
	var moveLength = scrollItems.eq(0).width() * moveNum; //计算每次移动的长度
	var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width(); //计算总长度,总个数*单个长度
	  
	//下一张
	$(".spec-scroll .next").bind("click",function(){
		if(tempLength < countLength){
			if((countLength - tempLength) > moveLength){
				scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
				tempLength += moveLength;
			}else{
				scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
				tempLength += (countLength - tempLength);
			}
		}
	});
	//上一张
	$(".spec-scroll .prev").bind("click",function(){
		if(tempLength > 0){
			if(tempLength > moveLength){
				scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
				tempLength -= moveLength;
			}else{
				scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
				tempLength = 0;
			}
		}
	});
});
//==================图片详细页函数=====================
$(function(){
	$(function () {
		// 产品数量加减
		$(".quantity_plus").click(function () {
			var Num1 = parseFloat($(".qty").val())
			if(isNaN(Num1)){
				Num1=1;
			}else{
				Num1 += 1;
			}
			$(".qty").val(Num1);
		});
		$(".quantity_minus").click(function () {
			var Num2 = parseFloat($(".qty").val())
			if (Num2 > 1) {
				Num2 -= 1;
				$(".qty").val(Num2);
			}
		});
		$(".qty").bind("keyup onafterpaste",function () {
			this.value = this.value.replace(/^0|\D/g,"");
		});
	});
});