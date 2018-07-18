<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>前台用户角色赋予</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<script type="text/javascript" src="js/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="js/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
<script type="text/javascript" src="js/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="js/jquery.validation/1.14.0/messages_zh.min.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
<style type="text/css">
	.form-horizontal .form-label {text-align: right;}
	.row{width: 90%;}
</style>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" method="post" id="myForm">
	<input type="hidden" name="userId" value="${userId}"/>
	<div class="row cl" style="margin-top: 0px;">
		<div class="formControls col-xs-12 col-sm-12 skin-minimal">
			<c:forEach items="${roleList}" var="role" varStatus="status">
			<div class="check-box">
				<input name="role" type="checkbox" value="${role.id}" id="role-${status.index}" ${role.checked=='true'?'checked':''}/>
				<label for="role-${status.index}">${role.name}</label>
			</div>
			</c:forEach>
		</div>
	</div>
	</form>
</article>
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
});
//保存
function save(){
	var roleIds = "";
	var checkedBoxs = $("input[type='checkbox']:checked");
	for(var i=0;i<checkedBoxs.length;i++){
		var roleId = $(checkedBoxs[i]).val();
		if(i == checkedBoxs.length-1){
			roleIds += roleId;
		}else{
			roleIds += roleId+",";
		}
	}
	var layerLoadIndex = parent.layer.load();
	$.ajax({
		type:"post",
		url:"<%=basePath%>user/saveUserRole.htm",
		dataType:"json",
		async:false,
		data:{"userId":"${userId}","roleIds":roleIds},
		success:function(data){
			parent.layer.close(layerLoadIndex);
			if(data.result){
				layer.msg("保存成功",{icon:1,time:1000},function(){
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
			}else{
				layer.msg(data.msg, {icon:2,time:2000});
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