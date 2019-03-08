<div class="pc-right" onclick="twoLevelMenu(event)" id="twoLevelMenu">
    <ul>
        <li>
            <p>会员管理</p>
            <div class="drop-down" style="display:none">
                <a href="/user/grzx/grzx.action" data-twoLevelMenu="用户中心">用户中心</a>
                <a href="/user/grzx/xgmm.action" data-twoLevelMenu="修改密码">修改密码</a>
                <a href="/user/grzx/bindsjAndyx.action" data-twoLevelMenu="绑定邮箱/手机">绑定邮箱/手机</a>
                <a href="/user/grzx/wszl.action" data-twoLevelMenu="单位信息">单位信息</a>
                <a href="/user/grzx/manufacturer.action" data-twoLevelMenu="工厂认证">工厂认证</a>
            </div>
        </li>
        <li>
            <p>需求管理</p>
            <div class="drop-down" style="display:none">
                <a href="/user/grzx/releaseDemand.action" data-twoLevelMenu="发布新需求">发布新需求</a>
                <a href="/user/grzx/listDemandForUser.action" data-twoLevelMenu="我发布的需求">我发布的需求</a>
                <a href="/user/grzx/listDemandNeedHandle.action" data-twoLevelMenu="待我响应的需求">待我响应的需求</a>
            </div>
        </li>
        <li>
            <p>订单管理</p>
            <div class="drop-down" style="display:none">
                <a href="">在线下单</a>
                <a href="">我下的订单</a>
                <a href="">我接的订单</a>
            </div>
        </li>
        <li>
            <p>财务管理</p>
            <div class="drop-down" style="display:none">
                <a href="">我的账户</a>
                <a href="">支付记录</a>
                <a href="">开票管理</a>
            </div>
        </li>
        <li>
            <p>商品超市</p>
            <div class="drop-down" style="display:none">
           		<a href="/user/grzx/publishGoods.action" data-twoLevelMenu="发布商品">发布商品</a>
                <a href="/user/grzx/listMyGoods.action?status=100" data-twoLevelMenu="我发布的商品">我发布的商品</a>
                <a href="/user/grzx/listCart.action" target="_black">我的购物车</a>
                <a href="/user/grzx/listPurchaseOrder.action"  data-twoLevelMenu="我购买的商品">我购买的商品</a>
                <a href="/user/grzx/listSalesOrder.action?t=all" data-twoLevelMenu="我的销售交易">我的销售交易</a>
            </div>
        </li>
        <li>
            <p>收藏夹</p>
            <div class="drop-down" style="display:none">
                <a href="">我关注的单位</a>
                <a  href="/user/grzx/myCollection.action" data-twoLevelMenu="我关注的商品">我关注的商品</a>
                <a href="">我关注的需求</a>
            </div>
        </li>
    </ul>
</div>

<script>
	function twoLevelMenu(event){
		var event = event || window.event;
    	var target = event.srcElement||event.target;
    	if(target.tagName.toUpperCase() == "A"){		//a标签
    		var val = $(target).attr("data-twoLevelMenu");
			val && $.cookie('data-twoLevelMenu', val,{path: '/' });
    	}else if(target.tagName.toUpperCase() == "P"){		//下拉菜单
    		var $drop = $(target).siblings(".drop-down");
    		$(".drop-down").each(function(){
    			if($(this).text() != $drop.text()){
    				$(this).hide();
    			}
    		})
    		$drop.toggle();	
    	}
    	
	}
	(function(){	
		$("#twoLevelMenu").find(".drop-down").hide().end().find(".fontcolor").removeClass("fontcolor");
		$(".fontcolor").removeClass("fontcolor");
		var val = $.cookie('data-twoLevelMenu');	
		if(val){
			$("[data-twoLevelMenu='"+val+"']").addClass("fontcolor").parent().show();
		}
	})()
</script>

