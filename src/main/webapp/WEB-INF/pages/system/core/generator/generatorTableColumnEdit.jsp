<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>构件表字段编辑</title>
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
	<input type="hidden" name="id" value="${info.id}"/>
	<input type="hidden" name="fkId" value="${info.fkId}"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>字段名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="字段名" id="name" name="name" value="${info.name}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>类型：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<select class="input-text" id="type" name="type">
				<option value="1" ${info.type=='1'?'selected':''}>varchar</option>
				<option value="2" ${info.type=='2'?'selected':''}>int</option>
				<option value="3" ${info.type=='3'?'selected':''}>double</option>
				<option value="4" ${info.type=='4'?'selected':''}>datetime</option>
				<option value="5" ${info.type=='5'?'selected':''}>decimal</option>
			</select>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>长度：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="长度" id="length" name="length" value="${empty info.length?'255':info.length}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否主键：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="isPrimaryKey" type="radio" value="y" id="isPrimaryKey-0" ${info.isPrimaryKey=='y'?'checked':''}/>
				<label for="isPrimaryKey-0">是</label>
			</div>
			<div class="radio-box">
				<input name="isPrimaryKey" type="radio" value="n" id="isPrimaryKey-0" ${info.isPrimaryKey!='y'?'checked':''}/>
				<label for="isPrimaryKey-1">否</label>
			</div>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">注释：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="注释" id="remark" name="remark" value="${info.remark}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否查询条件：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="isQuery" type="radio" value="y" id="isQueryKey-0" ${info.isQuery=='y'?'checked':''}/>
				<label for="isQueryKey-0">是</label>
			</div>
			<div class="radio-box">
				<input name="isQuery" type="radio" value="n" id="isQueryKey-1" ${info.isQuery!='y'?'checked':''}/>
				<label for="isQueryKey-1">否</label>
			</div>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否列表展示：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="isList" type="radio" value="y" id="isListKey-0" ${info.isList=='y'?'checked':''}/>
				<label for="isListKey-0">是</label>
			</div>
			<div class="radio-box">
				<input name="isList" type="radio" value="n" id="isListKey-1" ${info.isList!='y'?'checked':''}/>
				<label for="isListKey-1">否</label>
			</div>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否必填：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="isRequired" type="radio" value="y" id="isRequiredKey-0" ${info.isRequired=='y'?'checked':''}/>
				<label for="isRequiredKey-0">是</label>
			</div>
			<div class="radio-box">
				<input name="isRequired" type="radio" value="n" id="isRequiredKey-1" ${info.isRequired!='y'?'checked':''}/>
				<label for="isRequiredKey-1">否</label>
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
				"name":{
					required:true
				},
				"type":{
					required:true
				},
				"length":{
					required:true,
					digits:true,
					min:0
				}
			},
			messages:{
				"name":{
					required:"请输入字段名"
				},
				"type":{
					required:"请输入类型"
				},
				"length":{
					required:"请输入长度"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>generatorTableColumn/editSave.htm",
					dataType:"json",
					async:false,
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