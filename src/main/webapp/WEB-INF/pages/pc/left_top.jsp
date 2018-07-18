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
    <script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
    <link rel="stylesheet" type="text/css" href="js/jquery-ui-1.12.1/jquery-ui.min.css" />
    <script type="text/javascript" src="js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <style>
        .ui-autocomplete {
            max-height: 100px;
            overflow-y: auto;
            /* 防止水平滚动条 */
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<input type="hidden" value="${info.id}" id="orderId">
<div class="mainbox">
    <div class="kehuxinxi">
        <div class="biaoti"><div
                <c:if test="${not empty info and info.telNum>1}"> onclick="window.parent.parent.tabTox1('${info.tel}');"</c:if>
        ><span></span><b>客户信息</b><b  style="color: red">${not empty info?info.telNum:'0'}</b></div>
            <c:if test="${empty info}">
                <button class="alert-hover" onclick="parent.parent.layer.msg('请先调单')">完善确认</button>
            </c:if>
            <c:if test="${not empty info}">
                <button class="alert-hover" onclick="submitOrder()">完善确认</button>
            </c:if>

        </div>
        <div class="biaoge">
            <table>
                <tr>
                    <td class="mingcheng">来源</td>
                    <td class="w254">${info.importType=='0'?'留言':info.importType=='1'?'来电':''}</td>
                    <td class="mingcheng" rowspan="2">客户<br>留言</td>
                    <td class="w390" rowspan="2">${info.dealerMessage}</td>
                    <td class="mingcheng">客户类型</td>
                    <td class="w214 khlx">
                    	<span class="shipin">
                        	<select name="dealerType" id="dealerType">
                            	<option value="0" ${info.dealerType=='0'?'selected':''}>普通客户</option>
                                <option value="1" ${info.dealerType=='1'?'selected':''}>经销商</option>
                                <option value="2" ${info.dealerType=='2'?'selected':''}>面粉厂</option>
                                <option value="3" ${info.dealerType=='3'?'selected':''}>食品厂</option>
                               <option value="4" ${info.dealerType=='4'?'selected':''}>其他</option>
                            </select>
                        </span>
                    </td>
                    <td class="mingcheng">单位市场</td>
                    <td class="w435" id="city">
                        <div class="ssq" contenteditable="true" id="areaName">${info.areaName}</div>
                        <input type="hidden" id="areaId" value="${info.areaId}">
                        <div id="dis">
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="mingcheng">电话</td>
                    <td class="w254" onclick="window.parent.parent.callPhone('${info.tel}')">${info.tel}<span class="lvse"><c:if test="${not empty info.numberAttribution}" >【${info.numberAttribution}】</c:if></span></td>
                    <td class="mingcheng">短信号码</td>
                    <td class="w214 inpt"><div contenteditable="true" id="smsTel">${info.smsTel}</div></td>
                    <td class="mingcheng">咨询产品</td>
                    <td class="w435" id="cpmc">
                        <h3 class="two_btn">
                            <input type="hidden" id="zxcpIds" value="${info.productIds}">
                            <span class="cpmc" onclick="window.parent.parent.cpxz($('#zxcpIds').val())" id="zxcpName" title="${info.productNames}">${not empty info.productNames?info.productNames:'产品名称'}</span>
                            <i class="shipin_btn" onclick="window.parent.parent.spcx($('#zxcpIds').val())" style="float: right;">食品查询</i>
                        </h3>
                    </td>
                </tr>
                <tr>
                    <td class="mingcheng"
                            <c:if test="${not empty info.id}">
                        onClick="window.top.left.location.href='order/toChangeRecord.html?orderId=${info.id}&dealerId=${info.dealerId}&orderTel=${info.tel}'"
                    </c:if>
                            <c:if test="${empty info.id}">
                                onClick="parent.parent.layer.msg('请先调单')"
                            </c:if>>姓名</td>
                    <td class="w254 inpt" style="cursor:pointer;"
                    ><span id="orderName" style="width: 100%;padding: 5px 0;border: none;overflow: hidden;resize: none;padding-left: 5px;height: 100%;${empty info.dealerNum?'display:block':''}" contenteditable="true" >${info.name}</span><c:if test="${not empty info.dealerNum}"><span class="lvse">【${info.dealerNum}】</span></c:if></td>
                    <td class="mingcheng" rowspan="2">转单<br>留言</td>
                    <td class="w390" rowspan="2" id="userMessage">${info.userMessage}</td>
                    <td class="mingcheng" rowspan="2">地址</td>
                    <td class="w214 inpt" rowspan="2"><div contenteditable="true" class="zeng" id="address">${info.address}</div></td>
                    <td class="mingcheng" rowspan="2">操作记录</td>
                    <td class="w435 inpt" rowspan="2"><div contenteditable="true" class="zeng" id="operationRecord"></div></td>
                </tr>
                <tr>
                    <td class="mingcheng" >咨询产品</td>
                    <td class="w254">${info.exProductNames}</td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script src="js/base.js"></script>
<script>
    $(function(){
        $("body").click(function () {
            $("#dis").hide();
        });

        /*	$("#city .ssq").click(function(){
         $("#dis").show();
         return false;
         })*/

        //文本框输入
//        $("#city .ssq").keyup(function () {
//            $("#dis").show();	//只要输入就显示列表框
//            queryAllArea();
//            if ($("#city .ssq").text().length <= 0) {
//                $("#dis p").show();//如果什么都没填，跳出，保持全部显示状态
//                return;
//            }
//
//            $("#dis p").show();
//        });


    })
    $(function(){
        var n = 1;
        $(".zhankai").click(function(){
            if(n == 1){
                $(this).html("收缩").addClass("on");
                $('.kehuxinxi .biaoge').slideUp();
                n = 0;
            }
            else{
                $(this).html("展开").removeClass("on");
                $('.kehuxinxi .biaoge').slideDown();
                n = 1;
            }
        })
    })

    function submitOrder() {
        var orderName=$("#orderName").text();
        if(!$.trim(orderName)){
            parent.layer.msg("姓名不能为空");
            return false;
        }
        var dealerType=$("#dealerType").val();
        var areaName=$("#areaName").text();
        var areaId=$("#areaId").val();
        var smsTel=$("#smsTel").text();
        if(!$.trim(smsTel)){
            parent.layer.msg("短信号码不能为空");
            return false;
        }
        var zxcpIds=$("#zxcpIds").val();
        var zxcpName=$("#zxcpName").text();
        if(zxcpName=='产品名称'){
            zxcpName='';
        }
        var address=$("#address").text();
        var operationRecord=$("#operationRecord").text();
        var index=parent.parent.layer.load();
        $.ajax({
            type:"post",
            url:"<%=basePath%>order/editSave.html",
            dataType:"json",
            async:true,
            data:{"name":orderName,tel:"${info.tel}","dealerType":dealerType,"areaName":areaName,"areaId":areaId,"smsTel":smsTel,"productIds":zxcpIds,"productNames":zxcpName,"address":address,"operationRecord":operationRecord},
            success:function(data){
                if(data.result){
                    parent.parent.layer.msg(data.msg, {
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        parent.parent.layer.close(index);
                        parent.window.location.reload();

                    });
                }else{
                    parent.parent.layer.msg(data.msg, {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
            },
            error:function(){
                parent.parent.layer.alert("提示：系统内部出现问题！");
            }
        });
    }

    <%--queryAllArea();--%>
       <%--function queryAllArea() {--%>
           <%--var areaName=$("#areaName").text();--%>
           <%--$.ajax({--%>
               <%--type:"post",--%>
               <%--url:"<%=basePath%>dealerPc/queryAllArea.html",--%>
               <%--dataType:"json",--%>
               <%--async:true,--%>
               <%--data:{"areaName":areaName},--%>
               <%--success:function(data){--%>
                   <%--if(data.result){--%>
                       <%--var html = '';--%>
                      <%--for (var i=0;i<data.obj.length;i++){--%>
                      <%--html +='<p onclick="areaOnclick(this);" id="'+data.obj[i].id+'">'+data.obj[i].areaName+'</p>';--%>
                      <%--}--%>
                      <%--$("#dis").html(html);--%>
                   <%--}--%>
               <%--}--%>
           <%--});--%>
       <%--}--%>
    function areaOnclick(obj) {
        $("#city .ssq").text($(obj).text());
        $("#areaId").val($(obj).attr('id'));
    }

    $("#areaName").autocomplete({
        minLength: 0,
        scrollHeight:100,
        source: function(request,response){
            $.ajax({
                url:"<%=basePath%>dealerPc/queryAllArea.html",
                type:"post",
                dataType:"json",
                data:{"areaName":$("#areaName").text()},
                success:function(data){
                    response($.map(data.obj.slice(0, 100),function(item){
                        return{
                            label:item.areaName,
                            value:item.id
                        }
                    }));
                }
            });
        },
        focus:function(event,ui){
            return false;
        },
        select:function(event,ui){
            $("#areaName").text(ui.item.label);
            $("#areaId").val(ui.item.value);
            return false;
        }
    });
</script>
</body>
</html>
