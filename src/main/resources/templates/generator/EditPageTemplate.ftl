<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>${funName}编辑</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
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
	<form class="form form-horizontal" id="myForm">
	<input type="hidden" name="id" value="${r'${info.id}'}"/>
	<#if tableColumns?exists>
	<#list tableColumns as column>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><#if column.isRequired=='y'><span class="c-red">*</span></#if>${column.remark}：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<#if column.type!='4'>
			<input type="text" class="input-text" autocomplete="off" placeholder="${column.remark}" id="${column.filedName}" name="${column.filedName}" value="${r'${info.'+column.filedName+'}'}"/>
			<#else>
			<input type="text" class="input-text Wdate" style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" autocomplete="off" placeholder="${column.remark}" id="${column.filedName}" name="${column.filedName}" value="<fmt:formatDate value="${r'${info.'+column.filedName+'}'}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</#if>
		</div>
	</div>
	</#list>
	</#if>
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
			<#if requiredColumns?exists>
			rules:{
				<#list requiredColumns as column>
				<#if column_has_next>
				"${column.filedName}":{
					required:true
				},
				<#else>
				"${column.filedName}":{
					required:true
				}
				</#if>
				</#list>
			},
			messages:{
				<#list requiredColumns as column>
				<#if column_has_next>
				"${column.filedName}":{
					required:"请输入${column.remark}"
				},
				<#else>
				"${column.filedName}":{
					required:"请输入${column.remark}"
				}
				</#if>
				</#list>
			},
			</#if>
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>${className?uncap_first}/editSave.htm",
					dataType:"json",
					async:true,
					data:$(form).serialize(),
					success:function(data){
						if(data.result){
							layer.msg(data.msg, {
								  icon: 1,
								  time: 1000 //1秒关闭（如果不配置，默认是3秒）
								}, function(){
									var index = parent.layer.getFrameIndex(window.name);
									parent.query();
									parent.layer.close(index);
							});   
						}else{
							layer.msg(data.msg, {
								  icon: 2,
								  time: 2000 //2秒关闭（如果不配置，默认是3秒）
							});
							return;
						}
					},
					error:function(){
						layer.alert("提示：系统内部出现问题！");
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