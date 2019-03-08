$(function(){
    // 导航背景
    $(".nav_list ul li a").click(function () {
        $(this).addClass("active").parent().siblings().find("a").removeClass("active");
    });
    $(".nav_list ul li").mouseover(function () {
        // if ($(this).index() != $(".nav_list ul li").length - 1) {
            $(this).find("a").addClass("active").parent().siblings().find("a").removeClass("active");
        // }
            
        $(".admin-dropdown .adm-list a").removeClass("active");
    });
    $(".nav_list ul li").mouseout(function(){
        $(this).find("a").removeClass("active").parent().siblings().find("a").removeClass("active");
    });
    
 // 头像下拉菜单
    $(".head-img").mouseover(function(){
        $(".per_withdraw").css("display","block");
    }).mouseout(function(){
        $(".per_withdraw").css("display","none");
    })

    // 导航头切换
    function judgeWow(){
        if($(window).width()<1040){
            $(".nav_header_box").hide();
            $(".little_nav").show();
        }else{
            $(".nav_header_box").show();
            $(".little_nav").hide();
        }
    }
    judgeWow();
    $(window).resize(function(){
        judgeWow()
    })
    $(".little_btn").click(function(){
        if($(".nav_cont").css("display")=="block"){
            $(".nav_cont").slideUp();
        }else{
            $(".nav_cont").slideDown();
        }
        
    })

    // 登录页面
    $(".login").click(function(){
        $(".loginbox").css("display","block");
    });
    $(".loginbox .close").click(function(){
        $(".loginbox").css("display","none");
    });
    $(".code-change").click(function(){
        $(".code-js").css("display","block");
        $(".password-js").css("display","none");
    });
    $(".password-change").click(function(){
        $(".code-js").css("display","none");
        $(".password-js").css("display","block");
    });
    $(".registerbox-change").click(function(){
    	createCode($(".phoneRegister .changeImg"));
        $(".loginbox").css("display","none");
        $(".registerbox").css("display","block");
    })

    // 注册页面
    $(".register").click(function(){
        // $(this).removeClass("active");
    	createCode($(".phoneRegister .changeImg"));
        $(".registerbox").css("display","block");
    });
    $(".registerbox .close").click(function(){
        $(".registerbox").css("display","none");
    });
    $(".loginbox-change").click(function(){
        $(".loginbox").css("display","block");
        $(".registerbox").css("display","none");
    });
    //切换注册
    $(".phoneRegisterChange").click(function(){
    	createCode($(".phoneRegister .changeImg"));
    	
        $(this).css("display","none").siblings().css("display","block");
        $(".phoneRegister").css("display","block");
        $(".foxRegister").css("display","none");
        $(".changeRegisterBtns").addClass("changeTop");
    });
    $(".foxRegisterChange").click(function(){
    	createCode($(".foxRegister .changeImg"));
    	
        $(this).css("display","none").siblings().css("display","block");
        $(".phoneRegister").css("display","none");
        $(".foxRegister").css("display","block");
        $(".changeRegisterBtns").addClass("changeTop");
    });

})