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
    <script type="text/javascript" src="js/laydate/laydate.js"></script>
</head>
<body>

<div class="dongdaibox">
    <div class="biaoti">
        <div><span></span><b>综合查询</b></div>
        <h6>
            <select onchange="queryPage(this.value)">
                <option disabled>显示条数</option>
                <option ${pageInfo.pageSize==10?'selected':''} >10</option>
                <option ${pageInfo.pageSize==20?'selected':''} >20</option>
                <option ${pageInfo.pageSize==50?'selected':''}>50</option>
                <option ${pageInfo.pageSize==100?'selected':''}>100</option>
            </select>
        </h6>
    </div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left dbox_none">
                <dl>
                    <dd style="width:100%;">
                        <form id="queryForm" action="givePc/toZhonghe.html" method="post">
                            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
                            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
                            <input style="width:100px;" type="text" name="areaName" placeholder="单位市场" value="${params.areaName}">
                            <select style="width:100px;" name="dealerType">
                                <option  value="">全部</option>
                                <option ${params.dealerType=='0'?'selected':''} value="">普通客户</option>
                                <option ${params.dealerType=='1'?'selected':''} value="1">经销商</option>
                                <option ${params.dealerType=='2'?'selected':''} value="2">面粉厂</option>
                                <option ${params.dealerType=='3'?'selected':''} value="3">食品厂</option>
                                <option ${params.dealerType=='4'?'selected':''} value="4">其他</option>
                            </select>
                            <input style="width:150px;" type="text" name="smsTel" placeholder="手机号码" value="${params.smsTel}">
                            <input style="width:150px;" type="text" name="productNames" placeholder="咨询产品" value="${params.productNames}">
                            <input style="width:150px;" type="text" name="giveContent" placeholder="宣传产品" value="${params.giveContent}">
                            <select style="width:100px;" name="cType">
                                <option ${empty params.cType?'selected':''} value="">操作方案</option>
                                <option ${params.cType=='0'?'selected':''} value="0">咨询</option>
                                <option ${params.cType=='1'?'selected':''} value="1">咨询并通知</option>
                                <option ${params.cType=='2'?'selected':''} value="2">公司赠送</option>
                                <option ${params.cType=='3'?'selected':''} value="3">公司赠送并通知</option>
                                <option ${params.cType=='4'?'selected':''} value="4">经销商赠送</option>
                                <option ${params.cType=='5'?'selected':''} value="5">其他</option>
                                <option ${params.cType=='6'?'selected':''} value="6">通知经销商</option>
                            </select>
                            <input style="width:100px;" type="text" name="transferManName" placeholder="操作员" value="${params.transferManName}">
                            <input style="width:150px;" type="text" name="createTimeStart" placeholder="开始时间" value="${params.createTimeStart}" class="laydate-icon" id="start">
                            <input style="width:150px;" type="text" name="createTimeEnd" class="laydate-icon" value="${params.createTimeEnd}" id="end" placeholder="结束时间">
                            <button type="button" onclick="queryForm()" class="chaxun">查询</button>
                            <button class="chongzhi" type="button" onclick="resetKey();">重置</button>
                            <button class="chaxun" type="button" onclick="exportExcel()">下载</button>
                            <p><a><i>${pageInfo.total}</i>条信息</a>
                                <%--<a>您已选中<i id="xuanzhong">0</i>条</a>--%>
                            </p>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge">
                    <table class="hov_mou">
                        <tr>
                            <th>序号</th>
                            <th>最近联系日期</th>
                            <th>单位市场</th>
                            <th>客户类型</th>
                            <th>手机号码</th>
                            <th>姓名</th>
                            <th>地址</th>
                            <th>咨询产品</th>
                            <th>宣传产品</th>
                            <th>操作员</th>
                            <th>操作方案</th>
                            <th>经销商编号</th>
                            <th>经销商姓名</th>
                            <th>经销商手机号</th>
                            <%--<th><label class="checkbox_all"><span>全选</span><input type="checkbox"></label></th>--%>
                        </tr>


                        <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                            <tr onclick="tiaozhuan('${data.id}','${data.tel}')">
                                <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                <td><fmt:formatDate value="${data.import_time}" pattern="yyyy-MM-dd"/> </td>
                                <td>${data.area_name}</td>
                                <td>
                                    ${data.dealer_type==1?'经销商':data.dealer_type==2?'面粉厂':data.dealer_type==3?'食品厂':'普通用户'}
                                </td>
                                <td>${data.sms_tel}</td>
                                <td>${data.name}</td>
                                <td style="text-align:left;">${data.address}</td>
                                <td style="text-align:left;">${data.product_names}</td>
                                <td style="text-align:left;">${data.give_content}</td>
                                <td>${data.transfer_man_name}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${data.give_type=='3'}">经销商赠送</c:when>
                                        <c:when test="${data.give_type=='2'}">
                                            <c:choose>
                                                <c:when test="${not empty data.dealer_id}">公司赠送并通知</c:when>
                                                <c:otherwise>公司赠送
                                                    <%--<c:choose>--%>
                                                        <%--<c:when test="${not empty data.consult_dealer_id}">咨询并通知</c:when>--%>
                                                        <%--<c:otherwise>公司赠送</c:otherwise>--%>
                                                    <%--</c:choose>--%>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${not empty data.product_names}">
                                                    ${not empty data.consult_dealer_id?'咨询并通知':'咨询'}
                                                </c:when>
                                                <c:otherwise>其他</c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                    <c:choose>
                                        <c:when test="${data.give_type=='3'}">
                                            <td>${data.gdnum}</td><td>${data.gdname}</td><td>${data.gdtel}</td>
                                        </c:when>
                                        <c:when test="${data.give_type=='2'}">
                                            <c:choose>
                                                <c:when test="${not empty data.dealer_id}">
                                                    <td>${data.gdnum}</td><td>${data.gdname}</td><td>${data.gdtel}</td>
                                                </c:when>
                                                <c:otherwise><td>H0000</td><td></td><td></td>
                                                    <%--<c:choose>--%>
                                                        <%--<c:when test="${not empty data.consult_dealer_id}">--%>
                                                            <%--<td>${data.odnum}</td><td>${data.odname}</td><td>${data.odtel}</td>--%>
                                                        <%--</c:when>--%>
                                                        <%--<c:otherwise>--%>
                                                            <%--<td>H0000</td><td></td><td></td>--%>
                                                        <%--</c:otherwise>--%>
                                                    <%--</c:choose>--%>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${not empty data.product_names}">
                                                    <td>${data.odnum}</td><td>${data.odname}</td><td>${data.odtel}</td>
                                                </c:when>
                                                <c:otherwise><td></td><td></td><td></td></c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                <%--<td>--%>
                                <%--${not empty data.dealer_id?data.gdname:data.odname}--%>
                                    <%----%>
                                <%--</td>--%>
                                <%--<td>--%>
                                <%--${not empty data.dealer_id?data.gdtel:data.odtel}--%>
                                <%--</td>--%>

                                <%--<td><input class="checkbox_input" value="${data.id}" type="checkbox" name="giveIds"></td>--%>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="fenye" id="pageDiv"></div>
                    <c:if test="${pageInfo.list.size()==0}">
                        <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.zixun}</div>
    </div>
</div>
<form id="exportForm"  style="display: none" action="technicalProposal/zongheExcel.html" method="post">

</form>
<script src="js/base.js"></script>
<!--全选---->
<script>
    laydate.render({
        elem: '#start' //指定元素
    });
    laydate.render({
        elem: '#end' //指定元素
    });

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
        jump: function (e, first) { //触发分页后的回调
            if (!first) { //一定要加此判断，否则初始时会无限刷新
                $("#pageNum").val(e.curr);
                queryForm();
            }
        }
    });
    </c:if>
    function queryForm() {
        $("#queryForm").submit();
    }

    function resetKey() {
        $("#queryForm").find("select,input").val("");
        queryForm();
    }

    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            queryForm();
        }
    });

    function queryPage(pn) {
        $("#pageSize").val(pn);
        queryForm();
    }

    $(".checkbox_all input").click(function () {
        if ($(this).attr('checked') == 'checked') {
            $(".checkbox_input").attr('checked', 'checked');
        } else {
            $(".checkbox_input").removeAttr('checked', 'checked');
        }
    });

    $(".checkbox_input").change(function () {
        var obj=document.getElementsByName("giveIds");
        var i = 0;
        for(k in obj){
            if(obj[k].checked)
                i++
        }
        $("#xuanzhong").html(i)
        var n = ${pageInfo.pageSize}
        if('${pageInfo.total}'<20)n='${pageInfo.total}'
        if(i==n){
            $(".checkbox_all input").attr('checked','checked');
        }else{
            $(".checkbox_all input").removeAttr('checked');
        }
    })

    $(".checkbox_all input").click(function () {
        var n = ${pageInfo.pageSize}
        if('${pageInfo.total}'<20)n='${pageInfo.total}'
        if(!this.checked) {
            n=0;
        }
        $("#xuanzhong").html(n);
    })

    function exportExcel() {
            var queryForm = $("#queryForm");
            console.log($(queryForm).html());
            var exportForm=$("#exportForm");
            $(exportForm).html($(queryForm).html());
            exportForm.submit();
    }

    function exportExcel1() {
        var values="";
        $("input[name='giveIds']:checked").each(function () {
            values+=this.value;
            values+=",";
        })
        if(!($("input[name='giveIds']:checked").length>0)){
            layer.msg("未选中要下载的条目",{time:1000,offset: '200px'});
        }else {
        window.location = "technicalProposal/zongheExcel.html?values=" + values+"&cType=${params.cType}";
        }
    }
    function tiaozhuan(id,orderTel) {
        console.log(orderTel);
        window.location.href="order/toChangeRecord.html?orderId="+id+"&orderTel="+orderTel
    }
</script>
</body>
</html>
