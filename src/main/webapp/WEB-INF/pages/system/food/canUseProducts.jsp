<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
	<title>可适用产品列表</title>
	<%@include file="/common/htmlhead.jsp" %>
	<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
	<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
	<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
	<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
	<script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
	<script type="text/javascript" src="js/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
</head>
<body>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-10">
		<%--<span class="l">
			<a class="btn btn-primary radius" onclick="edit();">
				<i class="Hui-iconfont">&#xe600;</i>添加
			</a>
		</span>--%>
		<span class="r">共查询出数据：<strong id="totalCount">${products.size()}</strong>条</span>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
			<tr class="text-c">
				<th width="20%">产品信息</th>
				<th width="10%">零售价/袋</th>
				<th width="10%">零售价/箱</th>
				<th width="10%">经销价/袋</th>
				<th width="10%">经销价/箱</th>
				<th width="22%">备注</th>
				<th width="18%">排序号<p class="f-12 c-red">（升序）</p></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${products}" var="data">
				<tr class="text-c">
					<td class="text-l">
						<ul>
							<li><strong>产品名称：</strong><span class="c-999">${data.product_name}</span></li>
							<li><strong>货号：</strong><span class="c-999">${data.product_num}</span></li>
							<li><strong>规格：</strong><span class="c-999">${data.spec}</span></li>
						</ul>
					</td>
					<td>￥${empty data.retail_price_bag?0:data.retail_price_bag}</td>
					<td>￥${empty data.retail_price_box?0:data.retail_price_box}</td>
					<td>￥${empty data.sell_price_bag?0:data.sell_price_bag}</td>
					<td>￥${empty data.sell_price_box?0:data.sell_price_box}</td>
					<td>${data.remark}</td>
					<td>
						<input type="text" class="input-text" style="width:70px;" value="${data.productFoodSort}">
						<a style="text-decoration:none" class="ml-5 btn btn-success-outline radius size-S" onClick="saveSort(this,'${data.productFoodId}');" title="编辑">
							<i class="Hui-iconfont">&#xe6df;</i>保存
						</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
    //保存排序
    function saveSort(obj,productFoodId){
        var sort = $(obj).prev().val();
        var reg = /^[1-9]*[1-9][0-9]*$/;
        if(!reg.test(sort)){
            layer.msg("请输入正整数",{icon:2,time:1000});
            $(obj).prev().focus();
            return;
		}
        var layerIndex = layer.load();
        $.ajax({
            type:"post",
            url:"<%=basePath%>food/saveSort.htm",
            dataType:"json",
            async:false,
            data:{"productFoodId":productFoodId,"sort":sort},
            success:function(data){
                layer.close(layerIndex);
                if(data.result){
                    layer.msg(data.msg,{icon:1,time:1000},function(){
                        window.location.reload();
                    });
                }else{
                    layer.msg(data.msg, {icon:2,time:1000});
                    return;
                }
            },
            error:function(){
                layer.alert("提示：系统内部出现问题！");
            }
        });
    }
</script>
</body>
</html>