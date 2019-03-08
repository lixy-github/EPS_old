$(function () {
	$(window).scroll(function () {
        var Ling = $(document).scrollTop();
        if (Ling > 500) {
            $(".nav_header_box").addClass("nhb_fixed");
            $(".navFixed_line").show();
            $(".logo a").html('<img src="images/logo.png">');
            $(".line").addClass("line-js");
        } else {
            $(".nav_header_box").removeClass("nhb_fixed");
            $(".navFixed_line").hide();
            $(".logo a").html('<img src="images/logo-light.png">');
            $(".line").removeClass("line-js");
        }
    })
    // 顶部轮播
    function slide1() {
        var LI = $(".bd-js-1 ul li");
        var HTML = "";
        for (var i = 0; i < LI.length; i++) {
            HTML += "<li></li>";
        }
        $(".hd-js-1 ul").html(HTML);

        jQuery(".slideBox1").slide({
            mainCell: ".bd ul",
            autoPlay: true,
            effect: "leftLoop",
            trigger: "click",
            interTime:3000,
            delayTime: 700
        });

        var Swidth="-"+$(".hd-js-1").width()/2+"px";
        $(".slideBox1 .hd").css("marginLeft",Swidth);

    };
    slide1();

    function slide2() {
        var LI = $(".bd-js-2 ul li");
        var HTML = "";
        for (var i = 0; i < LI.length; i++) {
            HTML += "<li></li>";
        }
        $(".hd-js-2 ul").html("");

        jQuery(".slideBox2").slide({
            mainCell: ".bd ul",
            autoPlay: true,
            effect: "fade",
            trigger: "click",
            interTime: 3000,
            delayTime: 1500
        });
    };
    slide2();

    function slide3() {
        var LI = $(".bd-js-3 ul li");
        var HTML = "";
        for (var i = 0; i < LI.length; i++) {
            HTML += "<li></li>";
        }
        $(".hd-js-3 ul").html(HTML);

        jQuery(".slideBox3").slide({
            mainCell: ".bd ul",
            autoPlay: true,
            effect: "fade",
            trigger: "click",
            interTime: 3000,
            delayTime: 500
        });
        $(".slideBox3 .prev,.slideBox3 .next").css("display","none");
    };
    slide3();

    jQuery(".slideTxtBox").slide({
        effect: "fade",
        trigger:"click"
    });
})