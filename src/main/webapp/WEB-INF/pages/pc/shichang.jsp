<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<html>
<head>
    <title>海韦力系统</title>
    <%@include file="/common/htmlhead.jsp" %>
    <link href="css/base.css" rel="stylesheet" type="text/css">
    <link href="css/index.css" rel="stylesheet" type="text/css">
    <link href="css/style01.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
    <script language="javascript" src="js/lodop/LodopFuncs.js"></script>
</head>
<body>
<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>待处理${not empty params.isPrint?'打印':''}</b></div><h6>
        <form id="queryForm" action="order/toMarket.html" method="post">
            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
            <input type="hidden"  name="isMarket" value="1"/>
            <input type="hidden"  name="isPrint" value="${params.isPrint}"/>
        </form>
        <select onchange="queryPage(this.value)" >
            <option  disabled>显示条数</option>
            <option ${pageInfo.pageSize==10?'selected':''} >10</option>
            <option ${pageInfo.pageSize==20?'selected':''} >20</option>
            <option ${pageInfo.pageSize==50?'selected':''}>50</option>
            <option ${pageInfo.pageSize==100?'selected':''}>100</option>
        </select>
    </h6></div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left dbox_none">
                <dl>
                    <dd style="width:100%;">
                        <form>
                            <p><i>${pageInfo.total}</i>条信息</p>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge">
                    <table class="hov_mou">
                        <tr>
                            <th>序号</th>
                            <th>询单编号</th>
                            <th>部门</th>
                            <th>客户类型</th>
                            <th>留言类别</th>
                            <th>手机号码</th>
                            <th>姓名</th>
                            <th>号码归属地</th>
                            <th>客户留言                                                              </th>
                            <th>转单留言</th>
                            <th>地址</th>
                            <th>操作员</th>
                            <th>导入日期</th>
                            <th>转单日期</th>
                            <c:if test="${not empty params.isPrint}">
                                <th>打印状态</th>
                                <th>操作</th>
                            </c:if>

                        </tr>
                        <c:forEach items="${pageInfo.list}" var="order" varStatus="orderSta">
                            <tr>
                                <td>${orderSta.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                <td>${order.orderNum}</td>
                                <td id="dept${order.id}">
                                    <c:if test="${order.state=='1'}">市场部</c:if>
                                    <c:if test="${order.state=='2'}">市场部</c:if>
                                    <c:if test="${order.state=='3'}">大厂部</c:if>
                                    <c:if test="${order.state=='4'}">技术部</c:if>
                                    <c:if test="${order.state=='5'}">货运部</c:if>
                                    <c:if test="${order.state=='6'}">财务部</c:if>
                                    <c:if test="${order.state=='11'}">订货部</c:if>
                                    <c:if test="${order.state=='12'}">调货部</c:if>
                                    <c:if test="${order.state=='13'}">办公室</c:if>
                                    <c:if test="${order.state=='14'}">其他1</c:if>
                                    <c:if test="${order.state=='15'}">其他2</c:if>
                                    <c:if test="${order.state=='16'}">网络部</c:if>
                                    <c:if test="${order.state=='17'}">信息部</c:if>
                                </td>
                                <td>
                                    <c:if test="${order.dealerType=='0'}">普通客户</c:if>
                                    <c:if test="${order.dealerType=='1'}">经销商</c:if>
                                    <c:if test="${order.dealerType=='2'}">面粉厂</c:if>
                                    <c:if test="${order.dealerType=='3'}">食品厂</c:if>
                                    <c:if test="${order.dealerType=='4'}">其他</c:if>
                                </td>
                                <td>${order.messageType}</td>
                                <td>${order.tel}</td>
                                <td>${order.name}</td>
                                <td>${order.numberAttribution}</td>
                                <td style="text-align:left;">${order.dealerMessage}</td>
                                <td style="text-align:left;">${order.userMessage}</td>
                                <td style="text-align:left;">${order.address}</td>
                                <td>${not empty order.transferManName?order.transferManName:order.userName}</td>
                                <td><fmt:formatDate value="${order.importTime}" pattern="yyyy-MM-dd"/> </td>
                                <td><fmt:formatDate value="${order.transferTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                                <c:if test="${not empty params.isPrint}">
                                <td id="td${order.id}">${order.printState=='1'?'已打印':'未打印'}</td>
                                <td><button class="shenhe sh03"  data-orderid="${order.id}" data-ordernum="${order.orderNum}" data-tel="${order.tel}" data-areaname="${order.areaName}" data-ordernum="${order.orderNum}" data-dealerid="${order.dealerId}" data-name="${order.name}" data-msg="${order.dealerMessage}" data-type="${order.dealerType}" >打印告知单</button></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="caozuo_box">
                    </div>
                    <div class="fenye" id="pageDiv">
                    </div>
                    <c:if test="${pageInfo.list.size()==0}">
                        <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${empty params.isPrint}">
            <div class="beizhu_box">${sessionScope.remarks.daichuli}</div>
        </c:if>
        <c:if test="${not empty params.isPrint}">
            <div class="beizhu_box">${sessionScope.remarks.daichulidayin}</div>
        </c:if>
    </div>
</div>
<!--弹窗---->
<div class="heibg"></div>

<div class="popup popup002">
    <h2><span>打印告知单</span><i>×</i></h2>
    <input type="hidden" id="orderId"/>
    <input type="hidden" id="orderNum"/>
        <dl>
            <dd><input type="text" placeholder="接单人（举例：部门）" id="orderMan"></dd>
            <dd><input type="text" placeholder="手机号码" id="orderTel"></dd>
            <dd><input type="text" placeholder="姓名" id="orderName"></dd>
            <dd><select id="orderType">
                <option disabled>客户类型</option>
                <option value="0">普通客户</option>
                <option value="1">经销商</option>
                <option value="2">面粉厂</option>
                <option value="3">食品厂</option>
                <option value="4">其他</option>
            </select></dd>
            <dd><textarea rows="3" placeholder="客户留言" id="dealerMessage"></textarea></dd>
            <%--<dd><textarea rows="3" placeholder="操作留言" id="operationRecord"></textarea></dd>--%>
            <%--<dd><textarea rows="3" placeholder="经理意见" id="orderJl"></textarea></dd>--%>
            <dd><textarea rows="3" placeholder="内容" id="orderContent"></textarea></dd>
            <%--<dd><label><input type="checkbox" id="isUse"><span>是否加急</span></label></dd>--%>
        </dl>
    <div class="submit" style="width: 92%;margin: 20px auto;"><input type="button" onclick="printOrder()" value="打印" style="width: 100%;"></div>
    <div class="beizhu_box" style="margin: 0 20px; width: 470px;">${sessionScope.remarks.gaozhi}</div>
</div>

<!--弹窗---->
<script src="js/base.js"></script>
<!--全选---->
<script>
    <c:if test="${not empty pageInfo}">
    laypage({
        cont: 'pageDiv',
        pages: '${pageInfo.pages}', //总页数
        curr: '${pageInfo.pageNum}', //当前页
        groups: 5,//连续显示页数
        first:'首页',
        last:'尾页',
        prev: '&lt;',
        next: '&gt;',
        skin: '#41973c',//皮肤，目前支持：molv、yahei、flow 和16进制颜色值
        skip: false,//是否支持跳转页
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#pageNum").val(e.curr);
                queryForm();
            }
        }
    });
    </c:if>
    function queryForm() {
        $("#queryForm").submit();
    }

    function queryPage(pn) {
        $("#pageSize").val(pn);
        queryForm();
    }

    $(".checkbox_all input").click(function(){
        if($(this).attr('checked')=='checked'){
            $(".checkbox_input").attr('checked','checked');
        }else{
            $(".checkbox_input").removeAttr('checked','checked');
        }
    });
    $(function(){
        $(".sh03").click(function(){
            if($(this).attr("data-dealerid")){
                $.post("<%=basePath%>dealerPc/queryDealer.html",{dealerId:$(this).attr("data-dealerid")},
                    function(data){
                    if(data.result){
                        $("#orderName").val($(this).attr("data-name")+"【"+data.obj+$(this).attr("data-dealernum")+"】");
                    }
                    }, "json");
            }else {
                $("#orderName").val($(this).attr("data-name")+"【"+$(this).attr("data-areaname")+"】");
            }
            $("#orderId").val($(this).attr("data-orderid"));
            var oid=$(this).attr("data-orderid");
            $("#orderMan").val($.trim($("#dept"+oid).text()));
            $("#orderNum").val($(this).attr("data-ordernum"));
            $("#orderTel").val($(this).attr("data-tel"));
            $("#orderType").val($(this).attr("data-type"));
            $("#dealerMessage").val($(this).attr("data-msg"));
            $("#operationRecord").val($(this).attr("data-use"));
            $(".heibg").fadeIn();
            $(".popup002").slideDown();
        });
        $(".heibg,.popup >h2 i").click(function(){
            $(".popup002,.heibg").fadeOut();
        })
    })

</script>

<div id="printStyle" style="display: none">
    <div class='print'>
        <div><span>告知单</span><i></i></div>
        <div class="none bot"><em id="orderNumP"></em><i class="none" id="timeP"></i></div>
        <table cellpadding='0' cellspacing='0' style='border-collapse:collapse;'>
            <tr><td>To：<span id="orderManP"></span></td></tr>
            <tr><td id="telP" style="text-align: center;"></td></tr>
            <tr><td id="orderNameP" style="text-align: center;"></td></tr>
            <tr><td id="orderTypeP" style="text-align: center;"></td></tr>
            <tr><td id="dealerMessageP" valign="top"></td></tr>
            <tr><td id="orderContentP"></td></tr>
        </table>
        <div class="none"><span style='text-align:right; width: 70%; font-size: 15px;'>下单：</span><i><b id="">${userInfo.name}</b></i></div>
    </div>

    <style>
        .print{ width:260px;margin-right:-10px }
        .print div{ overflow:hidden; line-height: 40px; padding: 0 10px; position: relative;}
        .print div span{ display:block; font-size:22px; color: #111; text-align: center;}
        .print div em{ display:block; font-size:20px; color: #333; font-style: normal;}
        .print div i{ position: absolute; right: 0; top: 0; width: 30%; font-style: normal; text-align: center; }
        .print div.none{ border-top: none;}
        .print div.bot{ border-bottom: none;}
        .print div i.none { border:none;}
        .print table{ line-height:50px; font-size: 15px; width: 250px; color: #111;}
        .print table td{ padding:5px 5px; line-height: 16px; border: 1px solid #111;width: 230px;height:16px;}
        .print table td span{ width: 80%; display: inline-block; font-weight: bold; text-align: center;}
        #dealerMessageP{height:360px}
        #orderContentP{height:40px}
        #timeP{margin-right:20px}
    </style>
</div>

<script type="text/javascript">
    function printOrder() {
        $("#orderNumP").text($("#orderNum").val());
        $("#timeP").text(showDate());
        $("#orderManP").text($("#orderMan").val());
        $("#telP").text($("#orderTel").val());
        $("#orderNameP").text($("#orderName").val());
        $("#orderTypeP").text($("#orderType").find("option:selected").text());
        $("#dealerMessageP").text($("#dealerMessage").val());
        $("#orderContentP").text($("#orderContent").val());

        var LODOP;
        LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        LODOP.PRINT_INIT("通知单");
        //设置页面 ，10mm 页面底部边距 ，3和CreateCustomPage高度为自适应
        //LODOP.ADD_PRINT_HTM(-1, -411, 1100, 735, document.getElementById("printStyle").innerHTML);
        LODOP.SET_PRINT_MODE("POS_BASEON_PAPER",true);
        LODOP.SET_PRINT_PAGESIZE(1, 800, 2100, "");
        LODOP.ADD_PRINT_HTM(0, 0, "68mm", "200mm", document.getElementById("printStyle").innerHTML);
        LODOP.SET_PRINT_STYLEA(0,"Horient",2);

        //LODOP.SET_PRINT_STYLEA(0,"Vorient",2);
        LODOP.PREVIEW();

        var orderId=$("#orderId").val();
        $.ajax({
            url:"<%=basePath%>order/updateOrderPrint.html",
            type:"post",
            dataType:"json",
            data:{orderId:orderId},
            success: function (data) {
                if (data.result) {
                    $("#td"+orderId).text("已打印");
                }
            }
        });

        $(".heibg").fadeOut();
        $(".popup002").slideUp();
    }

    function showDate(){
        var mydate = new Date();
        var str = mydate.getHours()+":"+mydate.getMinutes();
        return str;
    }
</script>
</body>
</html>
