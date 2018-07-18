<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>操作日志管理</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="js/common/queryPage.js"></script> 
</head>
<body>
<nav class="breadcrumb"></nav>
<div class="page-container">
	<form action="log/listPageData.htm" method="post" id="queryForm">
	<div class="text-c">
		<input type="hidden" id="pageNum" name="pageNum" value="1"/>
		<input type="hidden" id="pageSize" name="pageSize" value="10"/>
		<input type="text" id="method" name="method" value="${params.method}" placeholder="操作人" style="width:100px" class="input-text"/>
		<span class="select-box" style="width: 7rem">
			<select class="select" name="type" onchange="query()">
			<option value="0" ${params.type==0?'selected':''}>后台</option>
			<option value="1" ${params.type==1?'selected':''}>前台</option>
		</select>
		</span>
		<input type="text" id="code" name="operation" value="${params.operation}"  placeholder="操作名称" style="width:100px" class="input-text"/>
		<%--<input type="text" id="code" name="method" value="${params.method}"  placeholder="操作方法" style="width:100px" class="input-text"/>--%>
		<input type="text" id="code" name="requestIp" value="${params.requestIp}"  placeholder="访问IP" style="width:100px" class="input-text"/>
		<input type="text" id="code" name="beginTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${params.beginTime}"  placeholder="开始时间" style="width:100px;height:30px;" class="input-text Wdate"/>~
		<input type="text" id="code" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${params.endTime}"  placeholder="结束时间" style="width:100px;height:30px;" class="input-text Wdate"/>
		<button class="btn btn-success" onclick="query();" type="button"><i class="Hui-iconfont">&#xe665;</i> 搜列表</button>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="r">共查询出数据：<strong id="totalCount"></strong>条</span>
	</div>
	<div class="mt-20" id="mainContent"></div>
</div>
<script type="text/javascript">
//查询数据(注意：方法名统一为query)
function query(){
	queryData("#queryForm","#mainContent");
}
/*角色单个-删除*/
function deleteData(id){
	var layerIndex = layer.confirm('确认要删除吗？',{icon:3},function(index){
		$.ajax({
			type:"post",
			url:"<%=basePath%>sysRole/deleteData.htm",
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