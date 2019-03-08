//$(function () {
//    // 产品分类
//    var I = 0;
//    $(".show_list_text").click(function () {
//        $(".show_list_option").css("display", "block");
//        I++;
//        if (I == 2) {
//            $(".show_list_option").css("display", "none");
//            I = 0;
//        }
//    });
//    $(".show_list_option p").click(function () {
//        $(".show_list_text span").html($(this).html());
//        $(".show_list_option").css("display", "none");
//        I = 0;
//    });
//})
//
//// 分页
//var obj, j;
//var page = 0;
//var currentPage = 0; //当前页
//var listNum = 6; //每页显示个数
//var PagesLen; //总页数
//var PageNum = 3; //分页链接个数
//
//window.onload = function () {
//    obj = $(".sc_l_bottom .product_show");
//    j = obj.length; //要分页数据的个数
//    // console.log(j);
//    PagesLen = Math.ceil(j / listNum); //总页数
//    upPage(0);
//}
//
//function upPage(p) {
//    currentPage = p
//    //内容变换
//    for (var i = 0; i < j; i++) {
//        obj[i].style.display = "none"
//    }
//    for (var i = p * listNum; i < (p + 1) * listNum; i++) {
//        if (obj[i]) obj[i].style.display = "block";
//        // console.log(i);
//    }
//    //分页链接变换
//    var strPrev = '<a class="pagination_prev" href="#" onclick="upPage(' + 'currentPage==0?0:(currentPage-1)' + ')"><</a> '; //上一页
//    var PageNum_2 = PageNum % 2 == 0 ? Math.ceil(PageNum / 2) + 1 : Math.ceil(PageNum / 2);
//    var PageNum_3 = PageNum % 2 == 0 ? Math.ceil(PageNum / 2) : Math.ceil(PageNum / 2) + 1;
//    var strC = "",
//        startPage, endPage;
//    if (PageNum >= PagesLen) {
//        startPage = 0;
//        endPage = PagesLen - 1;
//    } else if (currentPage < PageNum_2) {
//        startPage = 0;
//        endPage = PagesLen - 1 > PageNum ? PageNum : PagesLen - 1;
//    } else {
//        startPage = (currentPage + PageNum_3 >= PagesLen) ? PagesLen - PageNum - 1 : currentPage - PageNum_2 +
//            1;
//        var t = startPage + PageNum;
//        endPage = (t > PagesLen) ? PagesLen - 1 : t;
//    }
//    for (var i = startPage; i <= endPage; i++) {
//        if (i == currentPage) {
//            strC += '<a class="page_check" href="#" onclick="upPage(' + i + ')">' + (i + 1) + '</a> ';
//        } else {
//            strC += '<a class="page_page" href="#" onclick="upPage(' + i + ')">' + (i + 1) + '</a> ';
//        }
//    }
//    var strNext = '<a class="pagination_next" href="#" onclick="upPage(' + 'currentPage==(PagesLen - 1)?(PagesLen - 1):(currentPage+1)' + ')">></a>'; //下一页
//    $(".pagination").html(strPrev + strC + strNext);
//    
//    // 上一页   下一页是否显示
//    if(currentPage==0){
//        $(".pagination_prev").hide();
//    }
//    if(currentPage==PagesLen-1){
//        $(".pagination_next").hide();
//    }
//}