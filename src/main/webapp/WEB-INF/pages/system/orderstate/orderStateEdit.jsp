<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>订单状态排序编辑</title>
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
		<c:forEach items="${infoList}" var="info">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>
					<c:if test="${info.id==1}">市场部</c:if>
					<c:if test="${info.id==2}">市场部经理</c:if>
					<c:if test="${info.id==3}">大厂部</c:if>
					<c:if test="${info.id==4}">技术部</c:if>
					<c:if test="${info.id==5}">货运部</c:if>
					<c:if test="${info.id==6}">财务部</c:if>
					<c:if test="${info.id==11}">订货部</c:if>
					<c:if test="${info.id==12}">调货部</c:if>
					<c:if test="${info.id==13}">办公室</c:if>
					<c:if test="${info.id==14}">其他1</c:if>
					<c:if test="${info.id==15}">其他2</c:if>
					<c:if test="${info.id==16}">网络部</c:if>
                    <c:if test="${info.id==17}">信息部</c:if>
					<c:if test="${info.id==7}">完成</c:if>
					<c:if test="${info.id==8}">无效询单</c:if>
					<c:if test="${info.id==9}">恶意试用</c:if>
					<c:if test="${info.id==10}">无人接听</c:if>
					：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  type="text" class="input-text stateSort" autocomplete="off" placeholder="排序" id="sort${info.id}" name="sort${info.id}"

						   <c:if test="${info.id==1}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==2}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==3}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==4}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==5}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==6}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==11}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==12}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==13}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==14}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==15}">value="${info.sort}"</c:if>
							<c:if test="${info.id==16}">value="${info.sort}"</c:if>
							<c:if test="${info.id==17}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==7}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==8}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==9}">value="${info.sort}"</c:if>
						   <c:if test="${info.id==10}">value="${info.sort}"</c:if>
					/>
					<input type="hidden" value="${info.id}" name="stateId" class="stateId"/>
				</div>
			</div>
		</c:forEach>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i>保存</button>
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
				"sort1":{
					required:true,
                    digits:true,
                    min:0
				}
                ,"sort2":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort3":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort4":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort5":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort6":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort7":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort8":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort9":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort10":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort11":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort12":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort13":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort14":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort15":{
                    required:true,
                    digits:true,
                    min:0
                }
                ,"sort16":{
                    required:true,
                    digits:true,
                    min:0
                }
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				layer.load(1);
                var stateSort="";
                var stateId="";
				$(".stateSort").each(function () {
					stateSort += $(this).val()+",";
                });
                $(".stateId").each(function () {
                    stateId += $(this).val()+",";
                });
				$.ajax({
					type:"post",
					url:"<%=basePath%>orderState/editSave.htm",
					dataType:"json",
					async:true,
					data:{stateSort:stateSort,stateId:stateId},
					success:function(data){
						if(data.result){
							layer.msg(data.msg, {
								  icon: 1,
								  time: 1000 //1秒关闭（如果不配置，默认是3秒）
								}, function(){
							    location.reload();
									layer.close(index);
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