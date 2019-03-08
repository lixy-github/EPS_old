$(function () {
    var test = $(".pur-info");
    for (var i = 0; i < test.length; i++) {
        if ($(test[i]).find("li:first-child").height() != $(test[i]).find("li:gt(0)").height()) {
            $(test[i]).find("li:gt(0)").height($(test[i]).find("li:first-child").height())
        }
    }

    // 删除弹窗
    $(".pur-del").click(function () {
        $(".pur-del-box").show();
        var _this = this;
        $(".pdbt-confirm").click(function () {
            $(_this).parents(".pur-goods").remove();
            $(".pur-del-box").hide();

            obj = $(".pur-show-goods .pur-goods");
            j = obj.length; //要分页数据的个数
            PagesLen = Math.ceil(j / listNum); //总页数
            upPage(currentPage);

            var purGoods = $(".pur-goods");
            var array=[];
            for (var i = 0; i < purGoods.length; i++) {
                array.push($(purGoods[i]).css("display"))
            }
            
            if(array.indexOf("block")==-1){
                currentPage-=1;
                upPage(currentPage);
            }
        })
    })

    $(".pdb-close,.pdbt-cancel").click(function () {
        $(".pur-del-box").hide();
    })
})