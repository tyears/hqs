<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>客户产品关联编辑</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-ui-1.12.1/jquery-ui.min.css" />
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="js/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
<script type="text/javascript" src="js/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="js/jquery.validation/1.14.0/messages_zh.min.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<style type="text/css">
	.form-horizontal .form-label {text-align: right;}
	.row{width: 90%;}
</style>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="myForm">
	<input type="hidden" name="id" value="${info.id}"/>
		<input type="hidden" name="dealerId" value="${info.dealerId}"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">产品：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:choose >
					<c:when test="${not empty info.id}">
						${info.product.productNum}-${info.product.productName}
					</c:when>
					<c:otherwise>
						<input type="text" class="input-text" autocomplete="off" placeholder="产品" id="product"  />
					</c:otherwise>
				</c:choose>
				<input type="hidden"  name="productId" id="productId" value="${info.productId}"/>
			</div>
		</div>
	<%--<div class="row cl">--%>
		<%--<label class="form-label col-xs-4 col-sm-3">赠送件数：</label>--%>
		<%--<div class="formControls col-xs-8 col-sm-9">--%>
			<%--<input type="text" class="input-text" autocomplete="off" placeholder="赠送件数" id="giveNum" name="giveNum" value="${info.giveNum}"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<%--<div class="row cl">--%>
		<%--<label class="form-label col-xs-4 col-sm-3">第一次赠送：</label>--%>
		<%--<div class="formControls col-xs-8 col-sm-9">--%>
			<%--<input type="text" class="input-text Wdate" style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" placeholder="第一次赠送" id="firstTime" name="firstTime" value="<fmt:formatDate value="${info.firstTime}" pattern="yyyy-MM-dd"/>"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<%--<div class="row cl">--%>
		<%--<label class="form-label col-xs-4 col-sm-3">最后赠送：</label>--%>
		<%--<div class="formControls col-xs-8 col-sm-9">--%>
			<%--<input type="text" class="input-text Wdate" style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" placeholder="最后赠送" id="lastTime" name="lastTime" value="<fmt:formatDate value="${info.lastTime}" pattern="yyyy-MM-dd"/>"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<%--<div class="row cl">--%>
		<%--<label class="form-label col-xs-4 col-sm-3">经销商赠送：</label>--%>
		<%--<div class="formControls col-xs-8 col-sm-9">--%>
			<%--<input type="text" class="input-text" autocomplete="off" placeholder="经销商赠送" id="dealerGiveNum" name="dealerGiveNum" value="${info.dealerGiveNum}"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<%--<div class="row cl">--%>
		<%--<label class="form-label col-xs-4 col-sm-3">公司赠送：</label>--%>
		<%--<div class="formControls col-xs-8 col-sm-9">--%>
			<%--<input type="text" class="input-text" autocomplete="off" placeholder="公司赠送" id="companyGiveNum" name="companyGiveNum" value="${info.companyGiveNum}"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">评价：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="评价" id="comment" name="comment" value="${info.comment}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">最后进货：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text Wdate" style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" placeholder="最后进货" id="lastPurchaseTime" name="lastPurchaseTime" value="<fmt:formatDate value="${info.lastPurchaseTime}" pattern="yyyy-MM-dd"/>"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">确定月数：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text Wdate" style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" placeholder="确定月数" id="trueMonthTime" name="trueMonthTime" value="<fmt:formatDate value="${info.trueMonthTime}" pattern="yyyy-MM-dd"/>"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="备注" id="remark" name="remark" value="${info.remark}"/>
		</div>
	</div>
	</form>
</article>
<script type="text/javascript">
	$(function(){
        $("#product").autocomplete({
            minLength: 0,
            source: function(request,response){
                $.ajax({
                    url:"<%=basePath%>product/queryList.htm",
                    type:"post",
                    dataType:"json",
                    data:{"keyWord":$("#product").val()},
                    success:function(data){
                        response($.map(data.slice(0, 5),function(item){
                            return{
                                label:item.product_num+"-"+item.product_name,
                                value:item.id
                            }
                        }));
                    }
                });
            },
            focus:function(event,ui){
                $("#product").val(ui.item.label);
                $("#productId").val(ui.item.value);
                return false;
            },
            select:function(event,ui){
                $("#product").val(ui.item.label);
                $("#productId").val(ui.item.value);
                return false;
            }
        });
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
		
		$("#myForm").validate({
			rules:{

			},
			messages:{
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
			    var productId=$("#productId").val();
			    if(!$.trim(productId)){
                    layer.msg("请选择产品", {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
			        return;
				}

				<%--<c:if test="${empty info.id}">--%>
				<%--var isNext=true;--%>
                <%--$.ajax({--%>
                    <%--type:"post",--%>
                    <%--url:"<%=basePath%>dealerProduct/queryProductIsAdd.htm",--%>
                    <%--dataType:"json",--%>
                    <%--async:false,--%>
                    <%--data:{"dealerId":"${info.dealerId}","productId":productId},--%>
                    <%--success:function(data){--%>
                        <%--if(data.result){--%>
                            <%--isNext=data.obj;--%>
                        <%--}else{--%>
                            <%--layer.msg(data.msg, {--%>
                                <%--icon: 2,--%>
                                <%--time: 2000 //2秒关闭（如果不配置，默认是3秒）--%>
                            <%--});--%>
                            <%--return false;--%>
                        <%--}--%>
                    <%--},--%>
                    <%--error:function(){--%>
                        <%--layer.alert("提示：系统内部出现问题！");--%>
                    <%--}--%>
                <%--});--%>

                <%--if(isNext){--%>
                    <%--layer.msg("该商品已经关联", {--%>
                        <%--icon: 2,--%>
                        <%--time: 2000 //2秒关闭（如果不配置，默认是3秒）--%>
                    <%--});--%>
                    <%--return false;--%>
				<%--}--%>
                <%--</c:if>--%>

				var la=layer.load(1);
				$.ajax({
					type:"post",
					url:"<%=basePath%>dealerProduct/editSave.htm",
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
							},function () {
								layer.close(la);
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