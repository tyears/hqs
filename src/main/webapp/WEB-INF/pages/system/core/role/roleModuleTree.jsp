<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
<title>角色资源菜单树</title>
<%@include file="/common/htmlhead.jsp" %>
<link rel="stylesheet" type="text/css" href="js/zTree/v3/css/metroStyle/metroStyle.css"/>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="js/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
</head>
<body>
	<ul id="moduleTree" class="ztree"></ul>
	<form action="sysRole/saveRoleModule.htm" id="myForm" method="post" >
		<input type="hidden" name="id" value="${roleId}"/>
	</form>
<script type="text/javascript">
var zTree;
var treeNodes = [];
var setting = {
	check: {
		enable: true,
        chkboxType: { "Y": "ps", "N": "s" }
	},
	data: {
		key: {
			name: "name",
			title: "name"
		},
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "fkId",
			rootPId: 0
		}
	}
};
$(document).ready(function(){
	var loadIndex = layer.load();
	$.ajax({
		type:"post",
		url:"<%=basePath%>sysRole/getModuleNodes.htm",
		dataType:"json",
		async:true,
		data:{roleId:'${roleId}'},
		success:function(data){
			layer.close(loadIndex);
			treeNodes = data;
			//初始化树
			zTree = $.fn.zTree.init($("#moduleTree"), setting ,treeNodes);
		},
		error:function(){
			layer.close(loadIndex);
			layer.alert("提示：系统内部出现问题！");
		}
	}); 
});
//保存权限
function save(){
	var loadIndex = layer.load(1);
	//获取选中的权限
	var checkedModule = zTree.getCheckedNodes(true);
	var myForm = $("#myForm");
	var roleId = "${roleId}";
	for(var i=0;i<checkedModule.length;i++){
		var inputStr = "<input type='hidden' name='sysRoleModuleList["+i+"].roleId' value='"+roleId+"'/>";
		inputStr += "<input type='hidden' name='sysRoleModuleList["+i+"].moduleId' value='"+checkedModule[i].id+"'/>";
		myForm.append(inputStr);
	}
	$.ajax({
		type:"post",
		url:"<%=basePath%>sysRole/saveRoleModule.htm",
		dataType:"json",
		async:true,
		data:$("#myForm").serialize(),
		success:function(data){
			layer.close(loadIndex);
			if(data.result){
				layer.msg(data.msg, {
					  icon: 1,
					  time: 1000 //1秒关闭（如果不配置，默认是3秒）
					}, function(){
						var index = parent.layer.getFrameIndex(window.name);
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
			layer.close(loadIndex);
			layer.alert("提示：系统内部出现问题！");
		}
	});
}
</script>
</body>
</html>