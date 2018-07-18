<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<html>
<head>
    <title>海韦力系统</title>
    <%@include file="/common/htmlhead.jsp" %>
    <link rel="shortcut icon" type="image/x-icon" href="images/ico.ico" media="screen"/>
    <link rel="stylesheet" type="text/css" href="css/public.css">
    <link rel="stylesheet" type="text/css" href="css/detail.css">
    <link rel="stylesheet" type="text/css" href="css/animate.min.css">
    <script src="js/pubilc.js"></script>
    <script src="js/wow.min.js"></script>
    <script src="js/layer/2.1/layer.js"></script>
</head>

<body>
<div class="wrap wrap-index">
    <!-- slider-->
    <div class="slider">
        <div class="jquery-reslider">
            <div class="slider-block" data-url="images/bj.jpg"></div>
            <div class="slider-block" data-url="images/bj1.jpg"></div>
            <div class="slider-block" data-url="images/bj2.jpg"></div>
            <!--btn-->
            <%--<div class="slider-direction slider-direction-next"></div>--%>
            <%--<div class="slider-direction slider-direction-prev"></div>--%>
        </div>
    </div>
    <!-- end build-->
</div>
<!-- jQuery -->

<script src="js/jquery.reslider.js"></script>
<script>
    $(function () {
        $('.jquery-reslider').reSlider({
            speed: 1000,//设置轮播的高度
            delay: 8000,//设置轮播的延迟时间
            imgCount: 3,//设置轮播的图片数
            dots: true,//设置轮播的序号点
            autoPlay: true//设置轮播是否自动播放
        });
    });
</script>
<!--内容-->


<!--内容-->
<script>
    if (!(/msie [6|7|8|9]/i.test(navigator.userAgent))) {
        new WOW().init();
    }
</script>

<div class="copyright ">版权所有：海韦力系统</div>

<!--输入框-->
<div class="lo">
    <div class="enter">
        <div class="log"><img src="images/logo_big.png" alt=""></div>
        <h1>海韦力系统登录</h1>
        <div class="box">
            <p><input type="text" id="userName" class="ico" placeholder="账户"></p>
            <p><input type="password" id="passWord" class="ico1" placeholder="密码"></p>
            <p><input type="button" class="btn" value="登录" onclick="login()"></p>
        </div>
    </div>
    <div class="l"><img src="images/l.png" alt=""></div>
    <div class="r"><img src="images/r.png" alt=""></div>
</div>
</body>
<script>
    $(function(){
        //判断当前如果是登录状态直接跳转到主页
        var userName = "${userInfo.userName}";
        if(userName){
            location.href = "<%=basePath%>toIndex.html"
        }
        //限制login不能再iframe中打开
        if(window!=top){
            window.top.location.href = "<%=basePath%>toLogin.html";
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
    function login() {
        var userName = $("#userName").val();
        var passWord = $("#passWord").val();
        var msg = $("#msg");
        if (!userName) {
            layer.msg("提示：请输入用户名！", {time: 2000, icon: 2})
            return;
        } else if (!passWord) {
            layer.msg("提示：请输入密码！", {time: 2000, icon: 2})
            return;
        }
        var layerIndex = layer.load();
        $.ajax({
            type: "post",
            url: "<%=basePath%>doLogin.html",
            async: true,
            data: {"userName": userName, "passWord": passWord},
            success: function (data) {
                layer.close(layerIndex);
                data = eval("(" + data + ")");
                if (data.result) {
                    location.href = "<%=basePath%>toIndex.html";
                } else {
                    var baseMsg = "提示：";
                    var type = data.type;
                    if (type == "userName") {
                        layer.msg(baseMsg + data.msg, {time: 2000, icon: 2})
                    } else if (type == "passWord") {
                        layer.msg(baseMsg + data.msg, {time: 2000, icon: 2})
                    }
                }
            },
            error: function () {
                layer.close(layerIndex);
                msg.text("提示：系统内部出现问题！");
            }
        });
    }
</script>
</html>
