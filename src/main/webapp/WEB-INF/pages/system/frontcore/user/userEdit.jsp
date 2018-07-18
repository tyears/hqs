<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
	<title>用户编辑</title>
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
	<form class="form form-horizontal" method="post" action="user/editSave.htm" id="myForm">
		<input type="hidden" name="id" value="${info.id}"/>
		<input type="hidden" name="passWord" value="${info.passWord}"/>
		<input type="hidden" name="createTime" value="<fmt:formatDate value="${info.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" name="lastLoginTime" value="<fmt:formatDate value="${info.lastLoginTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<c:if test="${empty info.id}">
			<div class="Huialert Huialert-danger"><i class="icon-remove"></i>信息提示:新增加用户默认登录密码为（${defaultPassWord}）</div>
		</c:if>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="用户名" id="userName" name="userName" <c:if test="${not empty info.id}">readonly="readonly"</c:if> value="${info.userName}"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="姓名" id="name" name="name" value="${info.name}"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">分机号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" autocomplete="off" placeholder="分机号" id="extensionNum" name="extensionNum" value="${info.extensionNum}"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>所属部门：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="input-text" name="departId" id="departId" size="1">
					<option value="">--请选择部门--</option>
					<c:forEach items="${departList}" var="depart">
						<option value="${depart.id}" ${depart.id==info.departId?'selected':''} >${depart.departName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户类型：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input name="userType" type="radio" value="0" id="userType-1" ${info.userType!='1'?'checked':''}/>
					<label for="userType-1">员工</label>
				</div>
				<div class="radio-box">
					<input name="userType" type="radio" value="1" id="userType-2" ${info.userType=='1'?'checked':''}/>
					<label for="userType-2">经理</label>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>询单部门：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<c:forEach items="${xdDepartList}" var="depart" varStatus="status">
					<div class="check-box">
						<input name="xdDeparts" type="checkbox" value="${depart.id}" id="xdDepart-${status.index}" ${depart.checked=='true'?'checked':''}/>
						<label for="xdDepart-${status.index}">${depart.departName}</label>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">打印部门：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<c:forEach items="${dyDepartList}" var="depart" varStatus="status">
					<div class="check-box">
						<input name="dyDeparts" type="checkbox" value="${depart.id}" id="dyDepart-${status.index}" ${depart.checked=='true'?'checked':''}/>
						<label for="dyDepart-${status.index}">${depart.departName}</label>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>状态：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input name="state" type="radio" value="1" id="state-1" ${info.state!='0'?'checked':''}/>
					<label for="sex-1">启用</label>
				</div>
				<div class="radio-box">
					<input name="state" type="radio" value="0" id="sex-2" ${info.state=='0'?'checked':''}/>
					<label for="sex-2">禁用</label>
				</div>
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
                <c:if test="${empty info.id}">
                "userName":{
                    required:true,
                    remote:{
                        url:"<%=basePath%>user/checkUserName.htm",
                        type:"post",
                        dataType: "json",
                        data:{//要传递的数据
                            userName:function(){
                                return $("#userName").val();
                            }
                        }
                    }
                },
                </c:if>
                "extensionNum":{
                    remote:{
                        url:"<%=basePath%>user/checkExtensionNum.htm",
                        type:"post",
                        dataType: "json",
                        data:{//要传递的数据
                            extensionNum:function(){
                                return $("#extensionNum").val();
                            },
                            userId:"${info.id}"
                        }
                    }
                },
                "name":{
                    required:true
                },
                "xdDeparts":{
                    required:true
                }
            },
            messages:{
                <c:if test="${empty info.id}">
                "userName":{
                    required:"请输入用户名",
                    remote:"用户名已经存在"
                },
                </c:if>
                "extensionNum":{
                    remote:"分机号已经存在"
                },
                "name":{
                    required:"请输入姓名"
                },
                "xdDeparts":{
                    required:"请选择询单部门"
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                var layerLoadIndex = layer.load(1,{shade:0.2});
                $(form).ajaxSubmit(function(data){
                    layer.close(layerLoadIndex);
                    var resultObject = eval("("+data+")");
                    if(resultObject.result){
                        layer.msg("保存成功",{
                            icon: 1,
                            time: 1000 //1秒关闭（如果不配置，默认是3秒）
                        },function(){
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.query();
                            parent.layer.close(index);
                        });
                    }else{
                        layer.msg(resultObject.msg,{time:2000,icon:2});
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