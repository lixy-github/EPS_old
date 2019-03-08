$(function () {
    // 显示  收货地址页面  窗口
    $(".show-add li").hover(function () {
        $(this).find(".edit-addr").show();
    }, function () {
        $(this).find(".edit-addr").hide();
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
        // 新增/编辑
    $(".add-address").click(function(){
        $(".madct-name").html("新增");
        $(".manage-address").show();
    })
    $(".change-addr").click(function(){
        $(".madct-name").html("编辑");
        $(".manage-address").show();
    })
        // 设为默认地址
    $(".default-addr").click(function(){
        $(this).parent().parent().find(".addr-change-js").addClass("item-selected").removeClass("addr-no");
        $(this).parent().parent().siblings().find(".addr-change-js").removeClass("item-selected").addClass("addr-no");
        // $(this).parent().parent().split()
    })
        // 删除地址
    $(".del-addr").click(function(){
        $(this).parent().parent().hide();
    })
    $(".madc-close").click(function(){
        $(".manage-address").hide();
    })

})