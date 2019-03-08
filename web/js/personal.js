$(function () {
//    $(".data-btn").click(function () {
//        var Dates = document.getElementsByName("data");
//        var Dl = Dates.length;
//        var DateHtml = "";
//        for (var i = 0; i < Dl; i++) {
//            if (Dates[i].checked == true) {
//                DateHtml += Dates[i].nextSibling.nodeValue.trim() + ",";
//            }
//        }
//        if(DateHtml==""){
//        	$("#advantage").parent().next().text("该字段不能为空");
//        }else{
//        	$("#advantage").parent().next().text("");
//        	$("#advantageHiddenInput").val(DateHtml);
//        	$(".data-container").html(DateHtml);
//        }
//    });
    
 // 发布商品页面  开始
    $(".upload_step span").click(function () {
        var Index = $(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".publish_goods_box .pgb-js").eq(Index).css("display", "block").siblings().css("display", "none");
    })

    // 工序加工
    $(".processing,.process_int").click(function(){
        $(".select_pros").slideDown();
    })
    $(".slect_procedure").mouseleave(function(){
        $(".select_pros").slideUp();
    })
    // 发布商品页面 结束
    
    // 修改密码 页面
    $(".change_account").click(function(){
        $(this).find(".ch_icon").addClass("ch_icon_js");
        $(this).find(".change_pass_line").show();
        $(this).siblings().find(".ch_icon").removeClass("ch_icon_js");
        $(this).siblings().find(".change_pass_line").hide();
        $(".select_form").find("form").eq($(this).index()).show().siblings().hide();
    })
    // 修改密码 页面结束
    
    // 绑定邮箱/手机
//    $(".mail_btn").click(function(){
//    	var obj = $(".bmm_js .code_img");
//    	createCode(obj, "bindsjAndyx");
//        $(".bmm_js").show();
//    })
//    $(".phone_btn").click(function(){
//    	var obj = $(".bpm_js .code_img");
//    	createCode(obj, "bindsjAndyx");
//        $(".bpm_js").show();
//    })
//    $(".bind_close").click(function(){
//        $(".bmm_js,.bpm_js").hide();
//    })
    
    // 绑定邮箱/手机  结束
})