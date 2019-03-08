$(function () {
    // 切换手机/邮箱
    $(".phone-pw").click(function () {
        $(".changeBox").css("display", "block").animate({
            height: "70px"
        }, 200)
    });
    $(".fox-pw").click(function () {
        $(".changeBox").css("display", "block").animate({
            height: "70px"
        }, 200)
    });
    $(".phone-js").click(function () {
        $(".phone-pw-box").css("display", "block");
        $(".fox-pw-box").css("display", "none");
        $(".changeBox").css("display", "none");
        $(".fox-pw").css("display", "none");
        $(".phone-pw").css("display", "block");
    });
    $(".fox-js").click(function () {
        $(".phone-pw-box").css("display", "none");
        $(".fox-pw-box").css("display", "block");
        $(".changeBox").css("display", "none");
        $(".fox-pw").css("display", "block");
        $(".phone-pw").css("display", "none");
    });

    // if($(".verify-step2").css("display")=="block"){
    //     $(".step2").addClass("z-old").addClass("z-old2");
    // }else{
    //     $(".step2").removeClass("z-old").removeClass("z-old2");
    // }
    // if($(".verify-step3").css("display")=="block"){
    //     $(".step3").addClass("z-old").addClass("z-old3");
    // }else{
    //     $(".step3").removeClass("z-old").removeClass("z-old3");
    // }

//    $(".submit-btn1").click(function () {
//        $(".verify-step1").css("display", "none");
//        $(".verify-step2").css("display", "block");
//        $(".verify-step3").css("display", "none");
//        $(".step2").addClass("z-old").addClass("z-old2");
//    });
//    $(".submit-btn2").click(function () {
//        $(".verify-step1").css("display", "none");
//        $(".verify-step2").css("display", "none");
//        $(".verify-step3").css("display", "block");
//        $(".step3").addClass("z-old").addClass("z-old3");
//    });
})