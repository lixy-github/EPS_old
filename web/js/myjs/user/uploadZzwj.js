/**
 * 资质文件 上传
 */

var imgURL = "";
function getImg(node) {
	try {
		var file = null;
		if (node.files && node.files[0]) {
			file = node.files[0];
		} else if (node.files && node.files.item(0)) {
			file = node.files.item(0);
		}
		// Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径
		try {
			imgURL = file.getAsDataURL();
		} catch (e) {
			imgRUL = window.URL.createObjectURL(file);
		}
	} catch (e) {
		if (node.files && node.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				imgURL = e.target.result;
			};
			reader.readAsDataURL(node.files[0]);
		}
	}
}

// 点击小图显示大图
function showBigimg(bigImg) {
	$(".show_file_bigimg").css("display", "block").html(bigImg);
	var L = "-" + $(".show_file_bigimg img").width() / 2 + "px";
	var T = "-" + $(".show_file_bigimg img").height() / 2 + "px";
	$(".show_file_bigimg img").css({
		"marginTop" : T,
		"marginLeft" : L
	})
}
$(".show_file_bigimg").click(function() {
	$(this).css("display", "none");
})
$(".show_file_img").click(function() {
	if ($(this).find("img")) {
		showBigimg($(this).html());
	}
})

// 营业执照
function creatImg1(imgRUL) {
	var textHtml = "<img src='" + imgRUL + "'/>";
	textHtml += '<label for="file1" style="display:none">点击修改</label>';
	$(".sfi1").html(textHtml);
	$(".sfi1").click(function() {
		showBigimg(textHtml);
	})
}
/*
 * $(".sfi1").click(function () { if($(".sfi1 img")){
 * showBigimg($(".sfi1").html()) } })
 */

$(".if1").change(function() {
	$(".adf1").css("display", "none");
	$(".sf1").css("display", "block");
});
$(".sf1").mouseover(function() {
	$(this).find("label").css("display", "block")
})
$(".sf1").mouseout(function() {
	$(this).find("label").css("display", "none")
})




//个人中心 头像
function creatHeadImg(imgRUL) {
    var textHtml = "<img src='" + imgRUL + "'/>";
    $(".show_head_img").html(textHtml);
}

$(".headImg").change(function () {
    $(".show_head_img_box").show();
    $(".add_per_head").hide();
});
$(".show_head_img_box").mouseover(function () {
    $(this).find("label").show();
}).mouseout(function () {
    $(this).find("label").hide();
})



var imgFile = $("input[type='file']");
imgFile.on('change', function(e) {
	imgURL = "";
	getImg(this);
	uploadFile(e, this);
});

function uploadFile(e, obj) {
	var temp = $(obj).attr("name");
	var fName = "";
	var imgIndex;
	
	if(temp=="file"){
		imgIndex = 1;
	}
	
	var len = e.currentTarget.files.length;
	for (var i = 0; i < len; i++) {
		var file = e.target.files[i];
		var form = new FormData();
		form.append('file', file);

		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					if (temp == "file") {
						creatImg1(imgRUL);
					} else if (temp == "headImg") {
						//creatHeadImg(imgRUL);
						window.location.href = "/user/grzx/grzx.action"; //导航上头像也变 所以刷页面
					}
				} else {
					if (xhr.status == 601) {
						layer.alert('页面过期，请重新登录!');
						window.location.href = "/index.action";
					} else {
						layer.alert("上传失败！");
					}
				}
			}
		}
		xhr.open("POST", 'uploadFile.action?fileParam=' + temp, true);
		xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
		xhr.send(form);
	}
}
