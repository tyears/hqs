<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>客户（经销商或大厂部）编辑</title>
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
		<input type="hidden" id="provinceId" name="provinceId" value="${info.provinceId}"/>
		<input type="hidden" id="cityId" name="cityId" value="${info.cityId}"/>
		<input type="hidden" id="districtId" name="districtId" value="${info.districtId}"/>
		<input type="hidden" id="districtName" name="districtName" value="${info.districtName}"/>
		<input type="hidden" id="dealerNumInt" name="dealerNumInt" value="${info.dealerNumInt}"/>
		<input type="hidden" id="dealerNum" name="dealerNum" value="${info.dealerNum}"/>
		<input type="hidden" id="uuid" name="uuid" value="${info.uuid}"/>
		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${info.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>



		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:if test="${empty info.id}">
					<select class="input-text" id="dealerType" name="dealerType" onchange="isshow(this)">
						<option value="1" ${info.dealerType=='1'?'selected':''} >经销商</option>
						<option value="2" ${info.dealerType=='2'?'selected':''} >面粉厂</option>
						<option value="3" ${info.dealerType=='3'?'selected':''} >食品厂</option>
					</select>
				</c:if>
				<c:if test="${not empty info.id}">
					<input type="hidden" name="dealerType" value="${info.dealerType}">
					<input disabled class="input-text" value="${info.dealerType=='1'?'经销商':info.dealerType=='2'?'面粉厂':'食品厂'}">
				</c:if>
			</div>
		</div>
		<c:if test="${not empty info.dealerNum}">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">经销商编号：</label>
				<div class="formControls col-xs-8 col-sm-9">
						${info.dealerNum}
				</div>
			</div>
		</c:if>


		<div class="row cl" id="danxuan" style="display: ${empty info.dealerType?'block':info.dealerType=='1'?'block':'none'}">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span></label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="skin-minimal">
					<div class="radio-box" ${empty info.id||info.dealerType=='1'?'':'style="display:none"'}>
						<input type="radio" id="radio-1" name="ishezuo" value="n" ${empty info.ishezuo?'checked':info.ishezuo=='n'?'checked':''}>
						<label for="radio-1">未合作</label>
					</div>
					<div class="radio-box" ${empty info.id||info.dealerType=='1'?'':'style="display:none"'}>
						<input type="radio" id="radio-2" value="y" name="ishezuo" ${info.ishezuo=='y'?'checked':''}>
						<label for="radio-2">已合作</label>
					</div>
				</div>
			</div>
		</div>

	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>注册手机号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="注册手机号" id="registerTel" name="registerTel" value="${info.registerTel}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="姓名" id="name" name="name" value="${info.name}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">收货人：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="收货人" id="consignee" name="consignee" value="${info.consignee}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">邮编：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="邮编" id="postcode" name="postcode" value="${info.postcode}"/>
		</div>
	</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">短信地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="短信地址" id="smsAddress" name="smsAddress" value="${info.smsAddress}"/>
			</div>
		</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">合作日期：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input readonly="readonly" type="text" class="input-text Wdate"  style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" placeholder="合作日期" id="cooperationTime" name="cooperationTime" value="<fmt:formatDate value="${info.cooperationTime}" pattern="yyyy-MM-dd"/>"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">总评价：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="总评价" id="overallMerit" name="overallMerit" value="${info.overallMerit}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">短信接收号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="短信接收号" id="smsTel" name="smsTel" value="${info.smsTel}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">单位市场：</label>
		<div class="formControls col-xs-8 col-sm-9">
			${info.districtName}
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">经销区域：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="经销区域" id="distributionArea" name="distributionArea" value="${info.distributionArea}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>单位名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="单位名称" id="companyName" name="companyName" value="${info.companyName}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">收货地址 ：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="收货地址 " id="deliveryAddress" name="deliveryAddress" value="${info.deliveryAddress}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">收货电话：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="收货电话" id="deliveryTel" name="deliveryTel" value="${info.deliveryTel}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">传真：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="传真" id="fax" name="fax" value="${info.fax}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">合作情况：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="合作情况" id="cooperationState" name="cooperationState" value="${info.cooperationState}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">信用评价：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="信用评价" id="creditEvaluation" name="creditEvaluation" value="${info.creditEvaluation}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="备注" id="remark" name="remark" value="${info.remark}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>通讯地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="通讯地址" id="postalAddress" name="postalAddress" value="${info.postalAddress}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>其他号码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off"  placeholder="其他号码" name="otherTel" value="${fstotherphone}"/>
			<%--<a class="btn btn-primary radius" href="javascript:void(0)" onclick="delphone(this)">删除</a>--%>
		</div>
	</div>
		<c:if test="${otherphone.size()>0}">
			<c:forEach items="${otherphone}" var="phone">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>其他号码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" style="width: 87%" autocomplete="off"  placeholder="其他号码" name="otherTel" value="${phone}"/>
						<a class="btn btn-primary radius" href="javascript:void(0)" onclick="delphone(this)">删除</a>
				</div>
			</div>
			</c:forEach>
		</c:if>
	</form>
	<br/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span></label>
		<div class="formControls col-xs-8 col-sm-9">
			<a class="btn btn-primary radius" href="javascript:addphone()">增加其他号码</a>
		</div>
	</div>

</article>
<script type="text/javascript">
	function isshow(obj) {
		var d = $(obj).val();
		if(d=='1'){
            $("#danxuan").show();
		}else{
            $("#danxuan").hide();
		}
    }

	function addphone() {
	    var html="";
	    html += '<div class="row cl"><label class="form-label col-xs-4 col-sm-3"></span>其他号码：</label><div class="formControls col-xs-8 col-sm-9">'
				+'<input type="text" style="width: 87%" class="input-text" autocomplete="off" placeholder="其他号码" name="otherTel"/>'
				+' <a class="btn btn-primary radius" href="javascript:void(0)" onclick="delphone(this)">删除</a>'
				+'</div></div>'
		$("#myForm").append(html);
    }
    
    function delphone(obj) {
	    $(obj).parent().parent().remove();
    }

	$(function(){
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
		
		$("#myForm").validate({
			rules:{
                "dealerType":{
                    required:true
                },
				"registerTel":{
					required:true,
                    isMobile:true
				},
				"name":{
					required:true
				},
//				"smsTel":{
//					required:true,
//                    isMobile:true
//				},
				"companyName":{
					required:true
				},
//				"deliveryTel":{
//					required:true,
//                    isMobile:true
//				},
//				"fax":{
//					required:true
//				},
				"postalAddress":{
					required:true
				},
//                "otherTel":{
//                    digits:true
//                    rangelength:[11,11]
//                }
			},
			messages:{
                "dealerType":{
                    required:"请选择客户类型"
                },
				"registerTel":{
					required:"请输入注册手机号"
				},
				"name":{
					required:"请输入姓名"
				},
//				"smsTel":{
//					required:"请输入短信接收号"
//				},
				"companyName":{
					required:"请输入单位名称"
				},
//				"deliveryTel":{
//					required:"请输入收货电话"
//				},
//				"fax":{
//					required:"请输入传真"
//				},
				"postalAddress":{
					required:"请输入通讯地址"
				},
//                "otherTel":{
//                    digits:"请输入正确号码"
//                    rangelength:"请输入正确手机号"
//                }
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				var lo = layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>dealer/editSave.htm",
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
							},function () {
							    layer.close(lo);
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