<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<html>
<head>
<title>登录页面</title>
<%@include file="/common/htmlhead.jsp"%>
<link href="static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="js/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
<script src="js/layer/2.1/layer.js"></script>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<!-- <div class="header"><p  class="f-30 ml-30 c-white"><b>后台管理系统</b></p></div> -->
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <div id="msg" style="text-align: center;color: red;"></div>
    <form class="form form-horizontal" method="post">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
          <input id="userName" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input type="password" id="passWord"placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input class="input-text size-L" type="text" id="checkCode" placeholder="验证码" style="width:150px;">
          <img src="code.validatecode" style="width:180px;cursor: pointer;" onclick="this.src ='code.validatecode?a='+Math.random();"></div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input type="button" onclick="login();" class="btn btn-warning radius size-L" style="width:360px;" value="登&nbsp;&nbsp;&nbsp;&nbsp;录">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer"><a style="color: white" href="http://www.cccuu.com" target="_blank">技术支持©河南灵秀网络科技有限公司</a></div>
</body>
<script type="text/javascript">
	$(function(){
		//判断当前如果是登录状态直接跳转到主页
		var userName = "${sysUserInfo.userName}";
		if(userName){
			location.href = "<%=basePath%>toIndex.htm"
		}
		//限制login不能再iframe中打开
		if(window!=top){
			window.top.location.href = "<%=basePath%>toLogin.htm";
		}
		//回车登录
		$("body").keydown(function(e){
			var curKey = e.which;
			if(curKey == 13){
				login();
			}
		});
	});
	//登录操作
	function login(){
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		var checkCode = $("#checkCode").val();
		//var isRember = $("#isRember")[0].checked ? "yes":"no";
		var msg = $("#msg");
		if(!userName){
			msg.text("提示：请输入用户名！");
			return;
		}else if(!passWord){
			msg.text("提示：请输入密码！");
			return;
		}
		else if(!checkCode){
			msg.text("提示：请输入验证码！");
			return;
		}
		var layerIndex = layer.load();
		$.ajax({
			type:"post",
			url:"<%=basePath%>doLogin.htm",
			async:true,
			data:{"userName":userName,"passWord":passWord,"checkCode":checkCode},
			success:function(data){
				layer.close(layerIndex);
				data = eval("("+data+")");
				if(data.result){
					location.href = "<%=basePath%>toIndex.htm";
				}else{
					var baseMsg = "提示：";
					var type = data.type;
					var msgTip = data.msg;
					if(type == "checkCode"){
						msg.text(baseMsg+msgTip);
					}else if(type == "userName"){
						msg.text(baseMsg+msgTip);
					}else{
						msg.text(baseMsg+msgTip);
					}
				}
			},
			error:function(){
				layer.close(layerIndex);
				msg.text("提示：系统内部出现问题！");
			}
		});
	}
</script>
</html>

