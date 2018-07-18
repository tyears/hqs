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
    <link href="css/style01.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/common/queryPage.js"></script>
    <script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
</head>
<body>
<div class="left_top">
    <iframe id="left_top" src="order/toLeftTop.html" frameborder="0" width="100%" scrolling="no" ></iframe>
</div>

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>甄别</b></div>
        <h6>
            <select onchange="queryPage(this.value)" >
                <option  disabled>显示条数</option>
                <option  >10</option>
                <option  >20</option>
                <option >50</option>
                <option >100</option>
            </select>
        </h6>
    </div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left dbox_none">
                <dl>
                    <dd>
                        <form action="givePc/giveRecordData.html" method="post" id="queryForm">
                            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
                            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
                            <input type="hidden" id="areaIdZS" name="areaIdZS" value="${params.areaIdZS}"/>
                            <button type="button" onclick="byphone()" class="">按手机号查</button>
                            <button type="button" onclick="byname()" class="">按姓名查</button>
                            <button type="button" onclick="byarea()" class="">按单位市场查</button>
                            <input type="hidden" id="dealerTel" name="dealerTel" value="${params.dealerTel}">
                            <input type="hidden" id="dealerName" name="dealerName" value="${params.dealerName}">
                            <input placeholder="请输入地址" id="dealerAddress" name="dealerAddress" value="${params.dealerAddress}">
                            <input placeholder="请输入产品名称" id="giveContent" name="giveContent" value="${params.giveContent}">
                            <button class="chaxun" onclick="query();">查询</button>
                            <button class="chongzhi" type="button" onclick="resetKey();">重置</button>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge zs_biao">
                    <table class="hov_mou">
                        <tr>
                            <th class="ww1">序号</th>
                            <th class="ww2">手机</th>
                            <th class="ww3">联系人</th>
                            <th class="ww4">地址</th>
                            <th class="ww5">产品名称</th>
                            <th class="ww6">客户类型</th>
                            <th class="ww6">宣传类型</th>
                            <th class="ww7" style="width: 6%">宣传时间</th>
                            <th style="width: 6%;cursor: pointer;">货单号</th>
                            <th class="ww9">操作员</th>
                            <th class="ww10">经销商</th>
                            <th class="ww11">备注</th>
                        </tr>
                        <c:if test="${not empty info}">
                            <tr class="gaoliang">
                                <td class="ww1">0</td>
                                <td class="ww2">${info.tel}</td>
                                <td class="ww3">${info.name}</td>
                                <td class="ww4">${info.address}</td>
                                <td class="ww5"></td>
                                <td class="ww6"></td>
                                <td class="ww6"></td>
                                <td class="ww7"></td>
                                <td style="width: 6%;cursor: pointer;"></td>
                                <td class="ww9"></td>
                                <td class="ww10"></td>
                                <td class="ww11"></td>
                            </tr>
                        </c:if>
                    </table>
                    <div style="max-height:400px; overflow-y:auto" id="mainContent"></div>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.zengsongzhenbie}</div>
    </div>
</div>

<style>
    .dongdaibox .dongtai .dbox .bigbiaoge table tr.gaoliang td{ padding: 12px 5px !important;}
</style>
<!--弹窗-->
<div class="heibg"></div>

<div class="popup popup02 po02 poo02">
    <h2><span>快递单号录入</span><i>×</i></h2>
    <form>
        <dl>
            <dd><input type="hidden" id="zengsong_tel" value=""></dd>
            <dd><textarea rows="3" id="tongzhi" placeholder=""></textarea></dd>
            <dd class="submit"><input type="button"  value="发送通知"></dd>
        </dl>
    </form>
</div>

<script>
    <%--function duanxin() {--%>
        <%--var text = $("#tongzhi").text();--%>
        <%--var phone = $("#zengsong_tel").val();--%>
        <%--var lo = layer.load(1, {--%>
            <%--shade: [0.1,'#fff'] //0.1透明度的白色背景--%>
        <%--});--%>
        <%--$.post("<%=basePath%>sendOutSms.html",{phone:phone,text:text},function (data) {--%>
            <%--layer.close(lo);--%>
            <%--layer.msg(data.msg,{time:1500});--%>
        <%--},"json");--%>
    <%--}--%>

    function query(){
        queryData("#queryForm","#mainContent");
    }
    function queryPage(pn) {
        $("#pageSize").val(pn);
        query();
    }

    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            query();
        }
    });
    function resetKey() {
        $("#dealerTel").val('');
        $("#dealerName").val('');
        $("#dealerAddress").val('');
        $("#giveContent").val('');
        query();
    }

    function byphone() {
        if(${not empty info}) {
            $("#dealerTel").val('${info.tel}');
        }
        $("#dealerName").val('');
        $("#areaIdZS").val('');
        query();

    }
    function byname() {
        if(${not empty info}) {
            $("#dealerName").val('${info.name}');
        }
        $("#dealerTel").val('');
        $("#areaIdZS").val('');
        query();
    }
    function byarea() {
        if(${not empty info.areaId}) {
            $("#areaIdZS").val('${info.areaId}');
            $("#dealerTel").val('');
            $("#dealerName").val('');
            query();
        }else {
            layer.msg("请先完善询单单位市场");
            return false;
        }
    }
</script>
</body>
</html>
