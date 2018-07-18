<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/jsphead.jsp" %>
<html>
<head>
<title>默认页面</title>
<%@ include file="/common/htmlhead.jsp" %>
</head>
<body onload="location.href='<%=basePath%>toIndex.htm';">
<body>
</body>
<script type="text/javascript">
    <%-- window.onload= function () {
        if(isPC()){//pc访问
            location.href='<%=basePath%>pc/toIndex.ht';
        }else{//手机访问
            location.href='<%=basePath%>toIndex.htm';
        }
    }
    //判断是否pc访问
    function isPC() {
        var userAgentInfo = navigator.userAgent;
        var Agents = ["Android", "iPhone",
            "SymbianOS", "Windows Phone",
            "iPad", "iPod"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    } --%>
</script>
</html>

