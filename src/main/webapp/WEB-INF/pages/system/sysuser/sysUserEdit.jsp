<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="dictionary" uri="/dictionary-tags"%>
<html>
<head>
<title>后台用户编辑</title>
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
	<form class="form form-horizontal" method="post" action="sysUser/editSave.htm" id="myForm">
	<input type="hidden" name="id" value="${info.id}"/>
	<input type="hidden" name="passWord" value="${info.passWord}"/>
	<input type="hidden" name="createTime" value="<fmt:formatDate value="${info.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	<input type="hidden" name="lastLoginTime" value="<fmt:formatDate value="${info.lastLoginTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	<c:if test="${empty info.id}">
	<div class="Huialert Huialert-danger"><i class="icon-remove"></i>信息提示:新增加用户默认登录密码为（${defaultPassWord}）</div>
	</c:if>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="用户名" id="userName" name="userName" <c:if test="${not empty info.id}">readonly="readonly"</c:if> value="${info.userName}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="姓名" id="name" name="name" value="${info.name}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>状态：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="state" type="radio" value="1" id="state-1" ${info.state!='0'?'checked':''}/>
				<label for="sex-1">启用</label>
			</div>
			<div class="radio-box">
				<input name="state" type="radio" value="0" id="sex-2" ${info.state=='0'?'checked':''}/>
				<label for="sex-2">禁用</label>
			</div>
		</div>
	</div>
		<!--
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>科目：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<dictionary:select dictionaryCode="kemu" name="type" id="type" clazz="input-text" value="${kemu}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<dictionary:radio dictionaryCode="sex" name="sex" value="${sex}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>兴趣爱好：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<dictionary:checkBox dictionaryCode="favorite" name="favorite" value="${favorite}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>兴趣爱好：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<dictionary:write dictionaryCode="favorite" property="favorite"/>
		</div>
	</div>
	-->
	</form>
</article>
<script type="text/javascript">
	$(function(){
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
		$("#myForm").validate({
			rules:{
				<c:if test="${empty info.id}">
				"userName":{
					required:true,
					remote:{
						url:"<%=basePath%>sysUser/checkUserName.htm",
						type:"post",
						dataType: "json",
						data:{//要传递的数据
							userName:function(){
								return $("#userName").val();
							}
						}                 
					}
				},
				</c:if>
				"name":{
					required:true
				}
			},
			messages:{
				<c:if test="${empty info.id}">
				"userName":{
					required:"请输入用户名",
					remote:"用户名已经存在"
				},
				</c:if>
				"name":{
					required:"请输入姓名"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				var layerLoadIndex = layer.load(1,{shade:0.2});
				 $(form).ajaxSubmit(function(data){
					 layer.close(layerLoadIndex);
					 var resultObject = eval("("+data+")");
					 if(resultObject.result){
						 layer.msg("保存成功",{
							 icon: 1,
							 time: 1000 //1秒关闭（如果不配置，默认是3秒）
							 },function(){
							 	var index = parent.layer.getFrameIndex(window.name);
								parent.query();
								parent.layer.close(index);
						 }); 
					 }else{
						 layer.msg(resultObject.msg,{time:2000,icon:2});
					 }
				 });
			}
		});
	});
	//保存方法
	function save(){
		$("#myForm").submit();
	}
</script> 
</body>
</html>