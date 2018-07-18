<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
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
	<form class="form form-horizontal" method="post" id="myForm">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">当前密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" placeholder="现在密码" id="oldPassword" name="oldPassword"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">新密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" value="" placeholder="密码" id="newPassword1" name="newPassword1"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">确认密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off"  placeholder="确认新密码" id="newPassword2" name="newPassword2"/>
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
				oldPassword:{
					required:true,
					minlength:4,
					maxlength:16
				},
				newPassword1:{
					required:true,
					minlength:4,
					maxlength:16
				},
				newPassword2:{
					required:true,
					minlength:4,
					maxlength:16,
					equalTo: "#newPassword1"
				}
			},
			messages:{
				oldPassword:{
					required:"请输入原密码",
					minlength:"最少4位字符",
					maxlength:"最多16为字符"
				},
				newPassword1:{
					required:"请输入新密码",
					minlength:"最少4位字符",
					maxlength:"最多16为字符"
				},
				newPassword2:{
					required:"请输入确认密码",
					minlength:"最少4位字符",
					maxlength:"最多16为字符",
					equalTo: "两次输入密码不相同"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				var oldPassWord = $("#oldPassword").val();
				var newPassWord = $("#newPassword1").val();
				$.ajax({
					type:"post",
					url:"<%=basePath%>updatePsw.htm",
					async:false,
					data:{"oldPassWord":oldPassWord,"newPassWord":newPassWord},
					success:function(data){
						data = eval("("+data+")");
						if(data.result){
							layer.msg('修改成功！', {
								  icon: 1,
								  time: 1000 //2秒关闭（如果不配置，默认是3秒）
								}, function(){
									var index = parent.layer.getFrameIndex(window.name);
									parent.layer.close(index);
							});
						}else{
							layer.msg(data.msg, {
								  icon: 2,
								  time: 2000 //2秒关闭（如果不配置，默认是3秒）
								}, function(){
									$("#oldPassword").focus();
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