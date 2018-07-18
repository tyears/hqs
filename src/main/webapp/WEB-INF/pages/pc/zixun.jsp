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
</head>
<body>

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>咨询信息下载</b></div><h6>
        <form id="queryForm" action="givePc/toConsultInfo.html" method="post">
            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
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
                            <p><a><i>${pageInfo.total}</i>条信息</a><a>您已选中<i id="xuanzhong">0</i>条</a></p>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge">
                    <table class="hov_mou">
                        <tr>
                            <th>序号</th>
                            <th>日期</th>
                            <th>手机号码</th>
                            <th>姓名</th>
                            <th>地址</th>
                            <th>咨询产品</th>
                            <%--<th>操作方案</th>--%>
                            <th>操作员</th>
                            <th>合作商</th>
                            <th>经销商手机号</th>
                            <%--<th>通知经销商</th>--%>
                            <th><label class="checkbox_all"><span>全选</span><input type="checkbox"></label></th>
                        </tr>
                        <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                            <tr>
                                <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                <td><fmt:formatDate value="${data.import_time}" pattern="yyyy-MM-dd"/></td>
                                <td>${data.tel}</td>
                                <td>${data.name}</td>
                                <td style="text-align:left;">${data.address}</td>
                                <td style="text-align:left;">${data.product_names}</td>
                                <td>${data.transfer_man_name}</td>
                                <td style=" color:#f00;">${data.dealerNum}</td>
                                <th>${data.register_tel}</th>
                                <td><input class="checkbox_input" value="${data.id}" type="checkbox" name="giveIds"></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <c:if test="${pageInfo.list.size()!=0}">
                        <div class="caozuo_box">
                            <input type="button" onclick="importExcel()" value="下载">
                        </div>
                    </c:if>
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

<div class="heibg"></div>
<div class="popup popup02 po01">
    <h2><span>发送通知</span><i>×</i></h2>
    <input type="hidden" id="smsTel">
    <input type="hidden" id="giveId">
        <dl>
            <dd><textarea rows="3" placeholder="" id="smsContent">海韦力告知：我们已经为您邮寄了免费试用的海韦力产品。快递单号：ST454654656，请注意查收！欢迎了解使用质量稳定、用着放心的海韦力产品！</textarea></dd>
            <dd class="submit"><input type="button" value="发送通知" onclick="sendSms()"></dd>
        </dl>
</div>

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
            $("#giveId").val($(this).attr("data-giveid"));
            $("#smsTel").val($(this).attr("data-tel"));
            $("#smsContent").val("海韦力告知：我们已经为您邮寄了免费试用的海韦力产品。快递单号："+$(this).attr("data-num")+"，请注意查收！欢迎了解使用质量稳定、用着放心的海韦力产品！");
            $(".heibg").fadeIn();
            $(".popup02").slideDown();
        })
        $(".heibg,.popup >h2 i").click(function(){
            $(".popup02,.heibg").fadeOut();
        })
    });

    function sendSms() {
        var smsContent=$("#smsContent").val();
        if(!$.trim(smsContent)){
            layer.msg("请输入短信内容");
            return false;
        }

        var index = layer.load();
        $.ajax({
            url:"<%=basePath%>givePc/updateConsultInfo.html",
            type:"post",
            dataType:"json",
            data:{giveId:$("#giveId").val(),smsTel:$("#smsTel").val(),smsContent:smsContent},
            success: function (data) {
                if (data.result) {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    },function () {
                        window.location.reload();
                    });
                    layer.close(index);
                } else {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    layer.close(index);
                    return;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        })
    }

    function importExcel() {
        if($('input[name="giveIds"]:checked').length<1){
            layer.msg('请选择数据');
            return false;
        }

        var giveIds="";
        $.each($('input[name="giveIds"]:checked'),function(){
            giveIds += $(this).val()+",";
        });

        window.location="<%=basePath%>givePc/consultInfoExcel.html?giveIds="+giveIds;
    }

    function deleteIds() {
        if($('input[name="giveIds"]:checked').length<1){
            layer.msg('请选择数据');
            return false;
        }

        var giveIds="";
        $.each($('input[name="giveIds"]:checked'),function(){
            giveIds += $(this).val()+",";
        });

        var layerIndex = layer.confirm('确认要删除吗？',{icon:3},function(index){
            var index = layer.load();
            $.ajax({
                url:"<%=basePath%>givePc/deleteConsultInfo.html",
                type:"post",
                dataType:"json",
                data:{giveIds:giveIds},
                success: function (data) {
                    if (data.result) {
                        layer.msg(data.msg, {
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        },function () {
                            window.location.reload();
                        });
                        layer.close(index);
                    } else {
                        layer.msg(data.msg, {
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        });
                        layer.close(index);
                        return;
                    }
                },
                error: function () {
                    layer.alert("提示：系统内部出现问题！");
                }
            })
        });

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
</script>
</body>
</html>
