<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>单位市场产品编辑</title>
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
	<input type="hidden" name="areaId" value="${info.areaId}"/>
	<input type="hidden" name="noticeDealer" value="${info.noticeDealer}"/>
	<input type="hidden" name="noticeDealerId" value="${info.noticeDealerId}"/>
	<input type="hidden" name="giveDealer" value="${info.giveDealer}"/>
	<input type="hidden" name="giveDealerId" value="${info.giveDealerId}"/>
	<input type="hidden" name="authorDealer" id="authorDealer" value="${info.authorDealer}"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>产品：</label>
		<div class="formControls col-xs-8 col-sm-9">


			<c:choose >
				<c:when test="${not empty info.id}">
					${product.productNum}-${product.productName}
				</c:when>
				<c:otherwise>
					<input type="text" class="input-text" autocomplete="off" placeholder="产品" id="product" name="product" />
				</c:otherwise>
			</c:choose>

			<input type="hidden" name="productId" id="productId" value="${info.productId}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">授权经销商：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<select class="input-text" id="authorDealerId" name="authorDealerId" size="1" onchange="setAuthorDealer(this);">
				<option value="">--请选择经销商--</option>
				<c:forEach items="${dealers}" var="dealer">
					<option value="${dealer.id}" ${info.authorDealerId==dealer.id?'selected':''}>${dealer.dealerNum}</option>
				</c:forEach>
			</select>
			<%--<input type="text" class="input-text" autocomplete="off" placeholder="授权经销商" id="authorDealer" name="authorDealer" value="${info.authorDealer}"/>
			<input type="hidden" id="authorDealerId" name="authorDealerId" value="${info.authorDealerId}"/>--%>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">市场评价：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" placeholder="市场评价" id="comment" name="comment" value="${info.comment}"/>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">有效时间：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text Wdate" style="height:31px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" autocomplete="off" placeholder="有效时间" id="effectTime" name="effectTime" value="<fmt:formatDate value="${info.effectTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
                        console.log(data);
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
                "product":{
                    required:function(){
                        return $("#productId").val()=="";
					}
                }
//                , "comment":{
//                    required:true
//                },
//                "effectTime":{
//                    required:true
//                }
            },
            messages:{
                "product":{
                    required:"请选择产品"
                }
//                , "comment":{
//                    required:"请输入市场评价"
//                },
//                "effectTime":{
//                    required:"请选择有效时间"
//                }
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
                    <%--url:"<%=basePath%>areaProduct/queryProductIsAdd.htm",--%>
                    <%--dataType:"json",--%>
                    <%--async:false,--%>
                    <%--data:{"areaId":"${info.areaId}","productId":productId},--%>
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
					url:"<%=basePath%>areaProduct/editSave.htm",
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
    /**
	 * 设置授权经销商编号
     * @param obj
     */
	function setAuthorDealer(obj){
	    var authorDealerId = $(obj).val();
	    if(authorDealerId){
	        $("#authorDealer").val($(obj).find("option:selected").text());
		}else{
            $("#authorDealer").val("");
		}
	}
	//保存方法
	function save(){
		$("#myForm").submit();
	}
</script> 
</body>
</html>