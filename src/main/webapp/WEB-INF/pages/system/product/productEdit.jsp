<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>产品编辑</title>
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
	<input type="hidden" name="foodIds" id="foodIds" value="${foodIds}"/>
	<input type="hidden" name="createTime" value="<fmt:formatDate value="${info.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>产品分类：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<select class="input-text" id="typeId" name="typeId" size="1">
				<option value="">--请选择类别--</option>
				<c:forEach items="${types}" var="type">
					<option value="${type.id}" ${type.id==info.typeId?'selected':''}>${type.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>产品名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="产品名称" id="productName" name="productName" value="${info.productName}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">产品简拼：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="产品简拼" id="jianpin" name="jianpin" value="${info.jianpin}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>货号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="货号" id="productNum" name="productNum" value="${info.productNum}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>规格：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="规格" id="spec" name="spec" value="${info.spec}"/>
		</div>
	</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>每箱袋数：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="每箱袋数" id="numBox" name="numBox" value="${info.numBox}"/>
			</div>
		</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">零售价/袋：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="零售价/袋" id="retailPriceBag" name="retailPriceBag" value="${info.retailPriceBag}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">零售价/箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="零售价/箱" id="retailPriceBox" name="retailPriceBox" value="${info.retailPriceBox}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">经销价/袋：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="经销价/袋" id="sellPriceBag" name="sellPriceBag" value="${info.sellPriceBag}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">经销价/箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="经销价/箱" id="sellPriceBox" name="sellPriceBox" value="${info.sellPriceBox}"/>
		</div>
	</div>
	<div class="row cl" onclick="chooseFood();">
		<label class="form-label col-xs-4 col-sm-3">适用食品：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal" >
			<%--<c:forEach items="${foods}" var="food" varStatus="status">--%>
				<%--<div class="check-box">--%>
					<%--<input name="foodId" type="checkbox" value="${food.id}" id="food-${status.index}" ${food.checked?'checked':''}/>--%>
					<%--<label for="food-${status.index}">${food.food_name}</label>--%>
				<%--</div>--%>
			<%--</c:forEach>--%>
				<textarea id="foodNames"   class="textarea"  placeholder="适用食品" disabled>${foods}</textarea>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea id="remark" name="remark"  class="textarea"  placeholder="备注...输入200个字符以内" onKeyUp="$.Huitextarealength(this,200)">${info.remark}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
		</div>
	</div>
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
                "typeId":{
                    required:true
                },
				"productName":{
					required:true
				},
//                "jianpin":{
//                    required:true
//                },
				"productNum":{
					required:true
				},
				"spec":{
					required:true
				},
				"numBox":{
            		required:true,
                	digits:true,
					min:1
        		},
				"retailPriceBag":{
					number:true,
					min:0
				},
                "retailPriceBox":{
                    number:true,
                    min:0
                },
                "sellPriceBag":{
                    number:true,
                    min:0
                },
                "sellPriceBox":{
                    number:true,
                    min:0
                },
                "sort":{
                    required:true,
                    digits:true,
                    min:1
                }
			},
			messages:{
                "typeId":{
                    required:"请选择产品分类"
                },
				"productName":{
					required:"请输入产品名称"
				},
//                "jianpin":{
//                    required:"请输入食品简拼"
//                },
				"productNum":{
					required:"请输入货号"
				},
				"spec":{
					required:"请输入规格"
				},
                "sort":{
                    required:"请输入排序号"
                }
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
			    //获取适配食物id
//				var foods = $("input[name='foodId']:checked");
//				var foodIds = "";
//				if(foods.length>0){
//				    for(var i=0;i<foods.length;i++){
//				        if(i==foods.length-1){
//                            foodIds += $(foods[i]).val();
//						}else{
//                            foodIds += $(foods[i]).val()+",";
//						}
//					}
//				}
//				$("#foodIds").val(foodIds);
				var lo = layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>product/editSave.htm",
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
						    layer.close(lo);
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

	/*选择食品*/
    function chooseFood(){
        var title = "<i class='Hui-iconfont'>&#xe60c;</i>选择食品";
        var index = layer.open({
            type: 2,
            title: title,
            area: ['1030px', '600px'],
            btn: ["<i class='Hui-iconfont'>&#xe632;</i>保存", "取消"],
            yes: function(index, layero){
                var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                iframeWin.saveFood();
            },
            //按钮【按钮二】的回调
            cancel: function(index, layero){
                layer.close(index);
            },
            content: "<%=basePath%>product/chooseFood.htm?ids="+$("#foodIds").val()
        });
        //layer.full(index);
    }
</script> 
</body>
</html>