<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/jsphead.jsp" %>
<html>
<head>
<title>500页面</title>
<%@ include file="/common/htmlhead.jsp" %>
<link href="js/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
</head>
<% 
	Exception ex = (Exception)request.getAttribute("Exception");
	ex.printStackTrace();
%>
<body>
	<section class="container-fluid page-404 minWP text-c">
		<p class="error-title"><i class="Hui-iconfont va-m" style="font-size:80px">&#xe688;</i>
			<span class="va-m"> error</span>
		</p>
		<p class="error-description">不好意思，您访问的页面出现错误~</p>
		<p class="error-info">您可以：
			<a href="javascript:;" onclick="history.go(-1)" class="c-primary">返回上一页</a>
			<%--<span class="ml-20">|</span>--%>
			<%--<a href="javascript:goIndex();" class="c-primary ml-20">去首页 &gt;</a>--%>
		</p>
	</section>
</body>
<script type="text/javascript">
	function goIndex(){
		window.top.location.href = "<%=basePath%>toIndex.htm";
	}
</script>
</html>

