<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<html>
<head>
    <title>海韦力系统</title>
    <%@include file="/common/htmlhead.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <link href="css/base.css" rel="stylesheet" type="text/css">
    <link href="css/index.css" rel="stylesheet" type="text/css">

</head>

<body>
<div class="navbox">
    <div class="nav">
        <ul class="n_dai">
            <li><h2 class="red">${mapNum.dclNum}</h2>
                <h3>待处理</h3></li>
            <li><h2 class="red black">${mapNum.clNum}</h2>
                <h3>调单</h3></li>
            <li><h2 class="red">${mapNum.allNum}</h2>
                <h3>总单</h3></li>
        </ul>
        <div class="n_yi">
            <div><a target="left" href="javascript:window.parent.yjdd()">一键调单</a></div>
            <c:if test="${not empty info.tel}">
                <p onclick="window.parent.callPhone('${info.tel}')">拨号</p>
            </c:if>
            <c:if test="${empty info.tel}">
                <p onclick="window.parent.callPhone($('#phone').val())">拨号</p>
            </c:if>
        </div>
        <div class="n_bo">
                <input placeholder="号码" id="phone">
                <button onclick="window.parent.callPhone($('#phone').val())">拨号</button>
        </div>
        <div class="n_bo n_bw">
            <input id="chadan" placeholder="询单编号/手机号" onclick="window.parent.tabTox();">
            <button type="button" onclick="chadan()">查单</button>
        </div>


        <div class="n_bo n_bw">
            <select id="orderState">
                <c:forEach items="${orderStateList}" var="info">
                    <c:if test="${info.id!=7 and info.id!=8 and info.id!=9 and info.id!=10}">
                    <option value="${info.id}">
                        <c:if test="${info.id==1}">市场部</c:if>
                        <c:if test="${info.id==2}">市场部经理</c:if>
                        <c:if test="${info.id==3}">大厂部</c:if>
                        <c:if test="${info.id==4}">技术部</c:if>
                        <c:if test="${info.id==5}">货运部</c:if>
                        <c:if test="${info.id==6}">财务部</c:if>
                        <c:if test="${info.id==11}">订货部</c:if>
                        <c:if test="${info.id==12}">调货部</c:if>
                        <c:if test="${info.id==13}">办公室</c:if>
                        <c:if test="${info.id==16}">网络部</c:if>
                        <c:if test="${info.id==17}">信息部</c:if>
                        <c:if test="${info.id==14}">其他1</c:if>
                        <c:if test="${info.id==15}">其他2</c:if>
                        <%--<c:if test="${info.id==8}">无效询单</c:if>--%>
                        <%--<c:if test="${info.id==9}">恶意试用</c:if>--%>
                        <%--<c:if test="${info.id==10}">无人接听</c:if>--%>
                    </option>
                    </c:if>
                </c:forEach>
            </select>
            <c:if test="${empty info}">
                <button onclick="window.parent.layer.msg('请先调单')">转单</button>
            </c:if>
            <c:if test="${not empty info}">
                <button onclick="window.parent.zhuandan($('#orderState').val());">转单</button>
            </c:if>
        </div>
        <div class="n_bo n_bw">
            <select id="orderState2">
                <c:forEach items="${orderStateList}" var="info">
                    <c:if test="${info.id==7 || info.id==8 || info.id==9 || info.id==10}">
                    <option value="${info.id}">
                        <c:if test="${info.id==7}">完成</c:if>
                        <c:if test="${info.id==8}">无效询单</c:if>
                        <c:if test="${info.id==9}">恶意试用</c:if>
                        <c:if test="${info.id==10}">无人接听</c:if>
                    </option>
                    </c:if>
                </c:forEach>
            </select>
            <c:if test="${empty info}">
                <button onclick="window.parent.layer.msg('请先调单')">结单</button>
            </c:if>
            <c:if test="${not empty info}">
                <button onclick="window.parent.zhuandan($('#orderState2').val());">结单</button>
            </c:if>
        </div>
        <div class="n_nav">
            <ul>
                <user:rights funCode="Fun_JSFW">
                    <li><a target="left" href="toJsfw.html">技术服务</a></li>
                </user:rights>
                <user:rights funCode="Fun_ZSZB">
                    <li><a target="left" href="givePc/toGiveRecord.html">甄别</a></li>
                </user:rights>
                <user:rights funCode="Fun_DWSC">
                    <li><a target="left" href="dealerPc/toAreaMarket.html">单位市场</a></li>
                </user:rights>
                <user:rights funCode="Fun_JGZX">
                    <li><a target="left" href="product/productPage.html">价格咨询</a></li>
                </user:rights>
                <user:rights funCode="Fun_JXSXZ">
                    <li><a target="left" href="dealerPc/toDealerChoose.html">经销商选择</a></li>
                </user:rights>
                <user:rights funCode="Fun_KHCX">
                    <li><a target="left" href="dealerPc/toDealerQuery.html">客户查询</a></li>
                </user:rights>
                <user:rights funCode="Fun_JSJY">
                    <li><a target="left" href="javascript:" onclick="window.parent.jishujianyi();">建议</a></li>
                </user:rights>
            </ul>
            <ul>
                <user:rights funCode="Fun_DCL">
                    <li><a target="left" href="order/toMarket.html">待处理</a></li>
                </user:rights>
                <user:rights funCode="Fun_DCLDY">
                    <li><a target="left" href="order/toMarket.html?isPrint=1">待处理打印</a></li>
                </user:rights>
                <user:rights funCode="Fun_LDDR">
                    <li onclick="window.parent.aa();"><a href="javascript:">来电录入</a></li>
                </user:rights>
                <user:rights funCode="Fun_LYDR">
                    <li><a target="left" href="order/toLiuYan.html">留言导入</a></li>
                </user:rights>
            </ul>
            <ul>
                <user:rights funCode="Fun_KDXXGZ">
                    <li><a target="left" href="givePc/toExpressInfo.html">快递信息告知</a></li>
                </user:rights>
                <user:rights funCode="Fun_ZHCX">
                    <li><a target="left" href="givePc/toZhonghe.html">综合查询</a></li>
                </user:rights>
                <user:rights funCode="Fun_ZXXXGZ">
                    <li><a target="left" href="givePc/toConsultInfo.html">咨询信息下载</a></li>
                </user:rights>
                <%--<user:rights funCode="Fun_ZSSH">--%>
                    <%--<li><a target="left" href="givePc/toGiveExamine.html">宣传审核</a></li>--%>
                <%--</user:rights>--%>
                <%--<user:rights funCode="Fun_DYFHD">--%>
                    <%--<li><a target="left" href="givePc/toPrintInvoice.html">打印发货单</a></li>--%>
                <%--</user:rights>--%>
                <user:rights funCode="Fun_JSBJY">
                    <li><a target="left" href="technicalProposal/toQuery.html">建议下载</a></li>
                </user:rights>
                <user:rights funCode="Fun_JSSDR">
                    <li><a target="left" href="dealerPc/toDealerImport.html">经销商导入</a></li>
                </user:rights>
                <user:rights funCode="Fun_BTDY">
                    <li class="dayin" onClick="window.parent.dayin();"><a href="javascript:">便条打印</a></li>
                </user:rights>
                <user:rights funCode="Fun_DXFS">
                    <li><a target="left" href="javascript:" onclick="window.parent.showSms();">短信发送</a></li>
                </user:rights>
                <%--<li><a target="left" href="wushuju.html">无数据</a></li>--%>
                <!--
                <li><a href="javascript:" onClick="window.parent.al();">统计一</a></li>
                <li><a href="javascript:" onClick="window.parent.al();">统计二</a></li>-->
            </ul>
        </div>
    </div>
</div>
<script src="js/base.js"></script>
<script>
    $(function () {
        $(".n_nav ul li").click(function () {
            $(".n_nav ul li").removeClass("on");
            $(this).addClass("on");


        })
    })

    function chadan() {
        var num = $("#chadan").val();
        if (!num) {
            parent.layer.msg("请输入询单编号", {time: 1000});
            return;
        }
        $.post("order/chadan.html", {num: num}, function (data) {
            if (data.result) {
                window.top.location.reload();
            } else {
                parent.layer.msg(data.msg, {time: 2000});
                return;
            }
        }, "json")
    }
</script>

</body>
</html>
