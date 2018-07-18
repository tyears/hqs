<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>单位市场产品查询页面</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
<script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="js/common/queryPage.js"></script> 
</head>
<body>
<div class="page-container">
	<form action="areaProduct/listPageData.htm" method="post" id="queryForm">
	<div class="text-c">
		<input type="hidden" id="pageNum" name="pageNum" value="1"/>
		<input type="hidden" id="pageSize" name="pageSize" value="10"/>
		<input type="hidden" id="areaId" name="areaId" value="${params.areaId}"/>
		<input type="text" id="productName" name="productName" value="${params.productName}" placeholder="产品名称" style="width:150px" class="input-text"/>
		<input type="text" id="productNum" name="productNum" value="${params.productNum}" placeholder="货号" style="width:150px" class="input-text"/>
		<input type="text" id="jianpin" name="jianpin" value="${params.jianpin}" placeholder="产品简拼" style="width:150px" class="input-text"/>
		<button class="btn btn-success" onclick="query();" type="button"><i class="Hui-iconfont">&#xe665;</i> 搜列表</button>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a class="btn btn-primary radius" onclick="edit();">
				<i class="Hui-iconfont">&#xe600;</i>添加
			</a>
		</span>
		<span class="r">共查询出数据：<strong id="totalCount"></strong>条</span>
	</div>
	<div class="mt-20" id="mainContent"></div>
</div>
<script type="text/javascript">
//查询数据(注意：方法名统一为query)
function query(){
	queryData("#queryForm","#mainContent");
}

/*单位市场产品编辑*/
function edit(id){
	id = id ? id : "";
	var title = id ? "<i class='Hui-iconfont'>&#xe60c;</i>编辑" : "<i class='Hui-iconfont'>&#xe60c;</i>添加";
	var index = layer.open({
		type: 2,
		title: title,
		area: ['600px', '400px'],
		btn: ["<i class='Hui-iconfont'>&#xe632;</i>保存", "取消"],
		yes: function(index, layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
			iframeWin.save();
	    },
    	//按钮【按钮二】的回调
	    cancel: function(index, layero){
	    	layer.close(index);
	  	},
		content: "<%=basePath%>areaProduct/toEdit.htm?id="+id+"&areaId=${params.areaId}"
	});
	//layer.full(index);
}

/*单位市场产品删除*/
function deleteObjs(id){
	var layerIndex = layer.confirm('确认要删除吗？',{icon:3},function(index){
		$.ajax({
			type:"post",
			url:"<%=basePath%>areaProduct/deleteData.htm",
			dataType:"json",
			async:false,
			data:{"id":id},
			success:function(data){
				layer.close(layerIndex);
				if(data.result){
					layer.msg(data.msg,{icon:1,time:1000},function(){
						query();
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
	});
}
</script>
<%@include file="/common/keydownQuery.jsp" %>
</body>
</html>