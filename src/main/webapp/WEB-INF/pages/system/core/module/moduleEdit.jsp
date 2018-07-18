<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>聚商后台系统</title>
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
	.radio-box label{font-size: 25px;}
</style>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="myForm">
	<input type="hidden" name="id" value="${info.id}"/>
	<input type="hidden" name="fkId" value="${info.fkId}"/>
	<input type="hidden" name="isParent" value="${info.isParent=='true'?'true':'false'}"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>资源名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="资源名称" id="name" name="name" value="${info.name}"/>
		</div>
	</div>
	<c:if test="${level>1}">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>资源地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="资源地址" id="path" name="path" value="${info.path}"/>
		</div>
	</div>
	</c:if>
	<c:if test="${level<=1}">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>图标：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe625" id="icon-0" ${info.iconValue=='xe625'?'checked':''}/>
				<label for="icon-0"><i class="icon Hui-iconfont">&#xe625;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe616" id="icon-1" ${info.iconValue=='xe616'?'checked':''}/>
				<label for="icon-1"><i class="Hui-iconfont">&#xe616;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe613" id="icon-2" ${info.iconValue=='xe613'?'checked':''}/>
				<label for="icon-2"><i class="Hui-iconfont">&#xe613;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe60f" id="icon-3" ${info.iconValue=='xe60f'?'checked':''}/>
				<label for="icon-3"><i class="Hui-iconfont">&#xe60f;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe64b" id="icon-4" ${info.iconValue=='xe64b'?'checked':''}/>
				<label for="icon-4"><i class="Hui-iconfont">&#xe64b;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe62e" id="icon-5" ${info.iconValue=='xe62e'?'checked':''}/>
				<label for="icon-5"><i class="Hui-iconfont">&#xe62e;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe633" id="icon-6" ${info.iconValue=='xe633'?'checked':''}/>
				<label for="icon-6"><i class="Hui-iconfont">&#xe633;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe634" id="icon-7" ${info.iconValue=='xe634'?'checked':''}/>
				<label for="icon-7"><i class="Hui-iconfont">&#xe634;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe646" id="icon-8" ${info.iconValue=='xe646'?'checked':''}/>
				<label for="icon-8"><i class="Hui-iconfont">&#xe646;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe681" id="icon-9" ${info.iconValue=='xe681'?'checked':''}/>
				<label for="icon-9"><i class="Hui-iconfont">&#xe681;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe636" id="icon-10" ${info.iconValue=='xe636'?'checked':''}/>
				<label for="icon-10"><i class="Hui-iconfont">&#xe636;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe687" id="icon-11" ${info.iconValue=='xe687'?'checked':''}/>
				<label for="icon-11"><i class="Hui-iconfont">&#xe687;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe637" id="icon-12" ${info.iconValue=='xe637'?'checked':''}/>
				<label for="icon-12"><i class="Hui-iconfont">&#xe637;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe692" id="icon-13" ${info.iconValue=='xe692'?'checked':''}/>
				<label for="icon-13"><i class="Hui-iconfont">&#xe692;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe639" id="icon-14" ${info.iconValue=='xe639'?'checked':''}/>
				<label for="icon-14"><i class="Hui-iconfont">&#xe639;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe623" id="icon-15" ${info.iconValue=='xe623'?'checked':''}/>
				<label for="icon-15"><i class="Hui-iconfont">&#xe623;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe626" id="icon-16" ${info.iconValue=='xe626'?'checked':''}/>
				<label for="icon-16"><i class="Hui-iconfont">&#xe626;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe63e" id="icon-17" ${info.iconValue=='xe63e'?'checked':''}/>
				<label for="icon-17"><i class="Hui-iconfont">&#xe63e;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe63c" id="icon-18" ${info.iconValue=='xe63c'?'checked':''}/>
				<label for="icon-18"><i class="Hui-iconfont">&#xe63c;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe627" id="icon-19" ${info.iconValue=='xe627'?'checked':''}/>
				<label for="icon-19"><i class="Hui-iconfont">&#xe627;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe72d" id="icon-20" ${info.iconValue=='xe72d'?'checked':''}/>
				<label for="icon-20"><i class="Hui-iconfont">&#xe72d;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe61d" id="icon-21" ${info.iconValue=='xe61d'?'checked':''}/>
				<label for="icon-21"><i class="Hui-iconfont">&#xe61d;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe642" id="icon-22" ${info.iconValue=='xe642'?'checked':''}/>
				<label for="icon-22"><i class="Hui-iconfont">&#xe642;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe641" id="icon-23" ${info.iconValue=='xe641'?'checked':''}/>
				<label for="icon-23"><i class="Hui-iconfont">&#xe641;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe728" id="icon-24" ${info.iconValue=='&#xe728'?'checked':''}/>
				<label for="icon-24"><i class="Hui-iconfont">&#xe728;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe609" id="icon-25" ${info.iconValue=='xe609'?'checked':''}/>
				<label for="icon-25"><i class="Hui-iconfont">&#xe609;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe62d" id="icon-26" ${info.iconValue=='xe62d'?'checked':''}/>
				<label for="icon-26"><i class="Hui-iconfont">&#xe62d;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe60a" id="icon-27" ${info.iconValue=='xe60a'?'checked':''}/>
				<label for="icon-27"><i class="Hui-iconfont">&#xe60a;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe602" id="icon-28" ${info.iconValue=='xe602'?'checked':''}/>
				<label for="icon-28"><i class="Hui-iconfont">&#xe602;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe638" id="icon-29" ${info.iconValue=='xe638'?'checked':''}/>
				<label for="icon-29"><i class="Hui-iconfont">&#xe638;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe70d" id="icon-30" ${info.iconValue=='xe70d'?'checked':''}/>
				<label for="icon-30"><i class="Hui-iconfont">&#xe70d;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe62b" id="icon-31" ${info.iconValue=='xe62b'?'checked':''}/>
				<label for="icon-31"><i class="Hui-iconfont">&#xe62b;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe653" id="icon-32" ${info.iconValue=='xe653'?'checked':''}/>
				<label for="icon-32"><i class="Hui-iconfont">&#xe653;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe643" id="icon-33" ${info.iconValue=='xe643'?'checked':''}/>
				<label for="icon-33"><i class="Hui-iconfont">&#xe643;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6b4" id="icon-34" ${info.iconValue=='xe6b4'?'checked':''}/>
				<label for="icon-34"><i class="Hui-iconfont">&#xe6b4;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6d0" id="icon-35" ${info.iconValue=='xe6d0'?'checked':''}/>
				<label for="icon-35"><i class="Hui-iconfont">&#xe6d0;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe666" id="icon-36" ${info.iconValue=='xe666'?'checked':''}/>
				<label for="icon-36"><i class="Hui-iconfont">&#xe666;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe686" id="icon-37" ${info.iconValue=='xe686'?'checked':''}/>
				<label for="icon-37"><i class="Hui-iconfont">&#xe686;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe62f" id="icon-38" ${info.iconValue=='xe62f'?'checked':''}/>
				<label for="icon-38"><i class="Hui-iconfont">&#xe62f;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe63b" id="icon-39" ${info.iconValue=='xe63b'?'checked':''}/>
				<label for="icon-39"><i class="Hui-iconfont">&#xe63b;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe70c" id="icon-40" ${info.iconValue=='xe70c'?'checked':''}/>
				<label for="icon-40"><i class="Hui-iconfont">&#xe70c;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe635" id="icon-41" ${info.iconValue=='xe635'?'checked':''}/>
				<label for="icon-41"><i class="Hui-iconfont">&#xe635;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe61e" id="icon-42" ${info.iconValue=='xe61e'?'checked':''}/>
				<label for="icon-42"><i class="Hui-iconfont">&#xe61e;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe61a" id="icon-43" ${info.iconValue=='xe61a'?'checked':''}/>
				<label for="icon-43"><i class="Hui-iconfont">&#xe61a;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe669" id="icon-44" ${info.iconValue=='xe669'?'checked':''}/>
				<label for="icon-44"><i class="Hui-iconfont">&#xe669;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe66a" id="icon-45" ${info.iconValue=='xe66a'?'checked':''}/>
				<label for="icon-45"><i class="Hui-iconfont">&#xe66a;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe670" id="icon-46" ${info.iconValue=='xe670'?'checked':''}/>
				<label for="icon-46"><i class="Hui-iconfont">&#xe670;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6b7" id="icon-47" ${info.iconValue=='xe6b7'?'checked':''}/>
				<label for="icon-47"><i class="Hui-iconfont">&#xe6b7;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe620" id="icon-48" ${info.iconValue=='xe620'?'checked':''}/>
				<label for="icon-48"><i class="Hui-iconfont">&#xe620;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe694" id="icon-49" ${info.iconValue=='xe694'?'checked':''}/>
				<label for="icon-49"><i class="Hui-iconfont">&#xe694;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe67b" id="icon-50" ${info.iconValue=='xe67b'?'checked':''}/>
				<label for="icon-50"><i class="Hui-iconfont">&#xe67b;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6da" id="icon-51" ${info.iconValue=='xe6da'?'checked':''}/>
				<label for="icon-51"><i class="Hui-iconfont">&#xe6da;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe71f" id="icon-52" ${info.iconValue=='xe71f'?'checked':''}/>
				<label for="icon-52"><i class="Hui-iconfont">&#xe71f;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe722" id="icon-53" ${info.iconValue=='xe722'?'checked':''}/>
				<label for="icon-53"><i class="Hui-iconfont">&#xe722;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6a3" id="icon-54" ${info.iconValue=='xe6a3'?'checked':''}/>
				<label for="icon-54"><i class="Hui-iconfont">&#xe6a3;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe696" id="icon-55" ${info.iconValue=='xe696'?'checked':''}/>
				<label for="icon-55"><i class="Hui-iconfont">&#xe696;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe64f" id="icon-56" ${info.iconValue=='xe64f'?'checked':''}/>
				<label for="icon-56"><i class="Hui-iconfont">&#xe64f;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe654" id="icon-57" ${info.iconValue=='xe654'?'checked':''}/>
				<label for="icon-57"><i class="Hui-iconfont">&#xe654;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6cb" id="icon-58" ${info.iconValue=='xe6cb'?'checked':''}/>
				<label for="icon-58"><i class="Hui-iconfont">&#xe6cb;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe64a" id="icon-59" ${info.iconValue=='xe64a'?'checked':''}/>
				<label for="icon-59"><i class="Hui-iconfont">&#xe64a;</i></label>
			</div>
			<div class="radio-box">
				<input name="iconValue" type="radio" value="xe6a2" id="icon-60" ${info.iconValue=='xe6a2'?'checked':''}/>
				<label for="icon-60"><i class="Hui-iconfont">&#xe6a2;</i></label>
			</div>
		</div>
	</div>
	</c:if>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="排序" id="sort" name="sort" value="${info.sort}"/>
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
				<c:if test="${level>1}">
				"path":{
					required:true
				},
				</c:if>
				<c:if test="${level<=1}">
				"iconValue":{
					required:true
				},
				</c:if>
				"sort":{
					required:true,
					digits:true,
					min:0
				}
			},
			messages:{
				"name":{
					required:"请输入资源名称"
				},
				<c:if test="${level>1}">
				"path":{
					required:"请输入资源地址"
				},
				</c:if>
				<c:if test="${level<=1}">
				"iconValue":{
					required:"请选择图标"
				},
				</c:if>
				"sort":{
					required:"请输入序号"
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				var layerIndex = layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>sysModule/editSave.htm",
					dataType:"json",
					async:false,
					data:$(form).serialize(),
					success:function(data){
						layer.close(layerIndex);
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
						layer.close(layerIndex);
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