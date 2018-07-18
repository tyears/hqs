<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>文章编辑</title>
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
<script type="text/javascript" src='js/dist/autosize.js'></script>
<style type="text/css">
	.form-horizontal .form-label {text-align: right;}
	.row{width: 90%;}
</style>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="myForm">
	<input type="hidden" name="id" value="${info.id}"/>
	<input type="hidden" id="modifyTime" name="modifyTime" value="<fmt:formatDate value="${info.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">所属类别：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<select class="input-text" id="articleTypeId" name="articleTypeId">
				<c:forEach items="${articleTypeList}" var="articleType">
					<option value="${articleType.id}" ${articleType.id==info.articleTypeId?'selected':''}>${articleType.typeName}</option>
				</c:forEach>
			</select>
		</div>
	</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="排序" id="sort" name="sort" value="${info.sort}"/>
			</div>
		</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>标题：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="标题" id="title" name="title" value="${info.title}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>技术平台网址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="技术平台网址" id="technologyUrl" name="technologyUrl" value="${info.technologyUrl}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>内容：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea id="content" name="content" class="textarea" style="height:500px; " placeholder="内容...输入3000个字符以内" onKeyUp="$.Huitextarealength(this,3000)">${info.content}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/3000</p>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">答复短信：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea id="replySms" name="replySms"  class="textarea"  placeholder="答复短信...输入200个字符以内" onKeyUp="$.Huitextarealength(this,200)">${info.replySms}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">提示短信：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea id="promptSms" name="promptSms"  class="textarea"  placeholder="提示短信...输入200个字符以内" onKeyUp="$.Huitextarealength(this,200)">${info.promptSms}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea id="remark" name="remark"  class="textarea"  placeholder="备注...输入200个字符以内" onKeyUp="$.Huitextarealength(this,200)">${info.remark}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
		</div>
	</div>
	</form>
</article>

<style>
	.textarea{ height: 156px;}
	.page-container{ overflow: hidden;}
	.page-container .row{ width: 48%; float: left; margin-right: 2%;}
</style>

<script type="text/javascript">
    autosize($("#content"));
	$(function(){
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
		
		$("#myForm").validate({
			rules:{
                "articleTypeId":{
                    required:true
                },
                "sort":{
                    required:true,
                    digits:true,
                    min:0
                },
				"title":{
					required:true
				},
				"technologyUrl":{
					required:true
				},
				"content":{
					required:true
				}
			},
			messages:{
                "articleTypeId":{
                    required:"请选择所属类别"
                },
                "sort":{
                    required:"请输入排序"
                },
				"title":{
					required:"请输入标题"
				},
				"technologyUrl":{
					required:"请输入技术平台网址"
				},
				"content":{
					required:"请输入内容"
				},
				"replySms":{
					required:"请输入答复短信"
				},
				"promptSms":{
					required:"请输入提示短信"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>article/editSave.htm",
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