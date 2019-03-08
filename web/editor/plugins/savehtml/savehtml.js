KindEditor.plugin('savehtml', function(K) {
	var editor = this, name = 'savehtml';
	editor.clickToolbar(name, function(k) {
		var random = $("#random").val();
		if(random==""){
			alert("请先填写基本信息");
			return;
		}
		$.mask.show("loading");
		$.ajax({    
            type:'post',        
            url:'/user/grzx/saveGoodsHtml.action',    
            data:{"goods.random":random,"goods.html":editor.html()},    
            dataType:'json',    
            success:function(data){
            	if(data.status==-1){
            		//失败
            	}else{
            		//成功
            	}
            	$.mask.show("success");
            },
            error:function(data){
            	$.mask.show("error");
            }
        }); 
	});
});
