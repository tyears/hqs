<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>区域编辑</title>
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
	<input type="hidden" name="id" value="${info.id}"/>
		<input type="hidden" name="fkId" value="${info.fkId}"/>
		<input type="hidden" name="isParent" value="${info.isParent=='true'?'true':'false'}"/>
		<input type="hidden" name="level" value="${info.level}"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>区域名字：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="区域名字" id="areaName" name="areaName" value="${info.areaName}"/>
		</div>
	</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">拼音：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="拼音" id="spell" name="spell" value="${info.spell}"/>
			</div>
		</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="排序" id="sort" name="sort" value="${info.sort}"/>
		</div>
	</div>
		<c:if test="${info.level==2}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">评价：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="评价" id="comment" name="comment" value="${info.comment}"/>
			</div>
		</div>
		</c:if>
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
				"areaName":{
					required:true
				},
//                "spell":{
//                    required:true
//                },
				"sort":{
					required:true,
                    digits:true,
                    min:0
				}
			},
			messages:{
				"areaName":{
					required:"请输入区域名字"
				},
//                "spell":{
//                    required:"请输入拼音"
//                },
				"sort":{
					required:"请输入排序"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>area/editSave.htm",
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
                                parent.onloadNode();
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