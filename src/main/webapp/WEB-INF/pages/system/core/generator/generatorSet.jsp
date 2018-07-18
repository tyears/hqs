<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>构件代码设置</title>
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
	<form class="form form-horizontal" id="myForm">
	<input type="hidden" name="id" value="${id}"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>功能名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="功能名" id="funName" name="funName"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>包名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="包名" id="packageName" name="packageName"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>类名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="类名" id="className" name="className"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>页面前缀名 ：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="页面前缀名" id="pagePreName" name="pagePreName"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>图标：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="model" id="generatorType-0" checked="checked"/>
				<label for="generatorType-0">Model</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="mapper" id="generatorType-1" checked="checked"/>
				<label for="generatorType-1">Mapper</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="service" id="generatorType-2" checked="checked"/>
				<label for="generatorType-2">Service</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="serviceImpl" id="generatorType-3" checked="checked"/>
				<label for="generatorType-3">ServiceImpl</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="controller" id="generatorType-4" checked="checked"/>
				<label for="generatorType-4">Controller</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="queryPage" id="generatorType-5" checked="checked"/>
				<label for="generatorType-5">QueryPage</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="listPage" id="generatorType-6" checked="checked"/>
				<label for="generatorType-6">ListPage</label>
			</div>
			<div class="check-box">
				<input name="generatorType" type="checkbox" value="editPage" id="generatorType-7" checked="checked"/>
				<label for="generatorType-7">EditPage</label>
			</div>
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
		
		$("#myForm").validate({
			rules:{
				"funName":{
					required:true
				},
				"packageName":{
					required:true
				},
				"className":{
					required:true
				},
				"pagePreName":{
					required:true
				}
			},
			messages:{
				"funName":{
					required:"请输入功能名"
				},
				"packageName":{
					required:"请输入包名"
				},
				"className":{
					required:"请输入类名"
				},
				"pagePreName":{
					required:"页面前缀名"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				var loadIndex = layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>generatorTable/createCode.htm",
					dataType:"json",
					async:true,
					data:$(form).serialize(),
					success:function(data){
						layer.close(loadIndex);
						if(data.result){
							layer.msg(data.msg, {
								  icon: 1,
								  time: 1000 //1秒关闭（如果不配置，默认是3秒）
								}, function(){
									var index = parent.layer.getFrameIndex(window.name);
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
						layer.close(loadIndex);
						layer.alert("提示：系统内部出现问题！");
					}
				});
			}
		});
	});
	//保存方法
	function execute(){
		$("#myForm").submit();
	}
</script> 
</body>
</html>