<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>客户（经销商或大厂部）查询页面</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
<link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
<script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="js/common/queryPage.js"></script> 
</head>
<body>
<nav class="breadcrumb"></nav>
<div class="page-container">
	<form action="dealer/listPageData.htm" method="post" id="queryForm">
	<div class="text-c">
		<input type="hidden" id="pageNum" name="pageNum" value="1"/>
		<input type="hidden" id="pageSize" name="pageSize" value="10"/>
		<input type="hidden" id="provinceId" name="provinceId" value="${params.provinceId}"/>
		<input type="hidden" id="cityId" name="cityId" value="${params.cityId}"/>
		<input type="hidden" id="districtId" name="districtId" value="${params.districtId}"/>
		关键字：<input type="text" id="keyword" name="keyword" value="${params.keyword}" placeholder="关键字" style="width:277px" class="input-text"/>
		<span class="select-box" style="width:150px">
			<select class="select" name="dealerType" id="dealerType" size="1">
				<option value="">--请选择客户类型--</option>
				<option value="1"  ${params.dealerType==1?'selected':''}>经销商</option>
				<option value="2" ${params.dealerType==2?'selected':''}>面粉厂</option>
				<option value="3" ${params.dealerType==3?'selected':''}>食品厂</option>
			</select>
		</span>
		<button class="btn btn-success" onclick="query();" type="button"><i class="Hui-iconfont">&#xe665;</i> 搜列表</button>
	</div>
	</form>
	<form enctype="multipart/form-data" id="myForm" action="dealer/excelProduct.htm" method="post">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<c:if test="${params.level=='3'}">

			<a class="btn btn-primary radius" onclick="edit();">
				<i class="Hui-iconfont">&#xe600;</i>添加
			</a>
		</c:if>
			<a class="btn btn-danger radius" onclick="path.click();">
			<i class="Hui-iconfont">&#xe645;</i>Excel导入
		</a>

		<input type="file" id="path" name="upfile" style="display:none" >
		<a class="btn btn-success radius" href="javascript:window.open('template/dealer.xlsx');">
				<i class="Hui-iconfont">&#xe640;</i>下载模板
			</a>


		</span>
		<span class="r">共查询出数据：<strong id="totalCount"></strong>条</span>
	</div>
	</form>
	<div class="mt-20" id="mainContent"></div>
</div>
<script type="text/javascript">
//查询数据(注意：方法名统一为query)
function query(){
	queryData("#queryForm","#mainContent");
}

/*客户（经销商或大厂部）编辑*/
function edit(id){
	id = id ? id : "";
	var title = id ? "<i class='Hui-iconfont'>&#xe60c;</i>编辑" : "<i class='Hui-iconfont'>&#xe60c;</i>添加";
	var index = parent.layer.open({
		type: 2,
		title: title,
        area: ['800px', '600px'],
		btn: ["<i class='Hui-iconfont'>&#xe632;</i>保存", "取消"],
		yes: function(index, layero){
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
			iframeWin.save();
	    },
    	//按钮【按钮二】的回调
	    cancel: function(index, layero){
	    	layer.close(index);
	  	},
		content: "<%=basePath%>dealer/toEdit.htm?id="+id+"&fkId=${params.fkId}"
	});
//    parent.layer.full(index);
}

/*客户（经销商或大厂部）删除*/
function deleteObjs(id){
	var layerIndex = layer.confirm('确认要删除吗？',{icon:3},function(index){
		$.ajax({
			type:"post",
			url:"<%=basePath%>dealer/deleteData.htm",
			dataType:"json",
			async:false,
			data:{"id":id},
			success:function(data){
				layer.close(layerIndex);
				if(data.result){
					layer.msg(data.msg,{icon:1,time:1000},function(){
						query();
					});
				}else{
					layer.msg(data.msg, {icon:2,time:1000});
					return;
				}
			},
			error:function(){
				layer.alert("提示：系统内部出现问题！");
			}
		});
	});
}

//查看关联产品
function showProduct(id){
    var title = "<i class='Hui-iconfont'>&#xe627;</i>关联产品";
    var index = parent.layer.open({
        type: 2,
        title: title,
        area: ['1000px', '500px'],
        content: "<%=basePath%>dealerProduct/toQuery.htm?dealerId="+id
    });
}

$(function () {
    $('#path').change(function(){
        if(checkData()){
            layer.load(1);
            $("#myForm").ajaxSubmit({
                url:"<%=basePath%>dealer/excelProduct.htm",
                dataType:"json",
                success:function(data){
                    if(data.result){
                        layer.msg(data.msg, {
                            time: 1000 //1秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            query();
                            layer.closeAll();
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


//JS校验form表单信息
function checkData(){
    var fileDir = $("#path").val();
    var suffix = fileDir.substr(fileDir.lastIndexOf("."));
    if("" == fileDir){
        layer.msg("请选择需要导入的Excel文件！", {
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        $("#path").val("");
        return false;
    }
    if(".xls" != suffix && ".xlsx" != suffix ){
        layer.msg("请选择Excel格式的文件导入！", {
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        $("#path").val("");
        return false;
    }
    $("#upfile").val(fileDir);
    return true;
}
</script>
<%@include file="/common/keydownQuery.jsp" %>
</body>
</html>