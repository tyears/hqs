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
    <script language="javascript" src="js/lodop/LodopFuncs.js"></script>
</head>

<body>
<div class="topbox">
    <div class="top">
        <div class="logobox"><img src="images/logo.png"></div>
        <div class="namebox">
            <p id="cue1" style="margin-left: 10px"></p>
            <p id="cue2" style="margin: 0 10px"></p>
            <p>你好，${userInfo.name} !</p>
            <span>${depart.departName}</span>
            <a style="background:none" href="javascript:void(0)" onclick="updatepw('${userInfo.userId}')">修改密码</a>
            <a href="loginOut.html">退出</a>
        </div>
    </div>
</div>

<div class="content">
    <div class="main">
        <iframe src="toJsfw.html" name="left" frameborder="0" width="100%" id="jsfw"></iframe>
    </div>
    <div class="right_side">
        <iframe src="toRightSide.html" frameborder="0" width="100%" scrolling="no" id="rightPage"></iframe>
    </div>
</div>
<script src="js/base.js"></script>
<div class="heibg"></div>
<div class="popup popup01">
    <h2><span>信息录入</span><i>×</i></h2>
    <dl>
        <%--<dd><input type="text" placeholder="客户编号" id="dealerNum"></dd>--%>
        <dd><input type="text" placeholder="联系电话" id="dealerTel"></dd>
        <dd><input type="text" placeholder="姓名" id="dealerName"></dd>
        <dd><select id="dealerType">
            <option disabled>客户类型</option>
            <option value="0">普通客户</option>
            <option value="1">经销商</option>
            <option value="2">面粉厂</option>
            <option value="3">食品厂</option>
            <option value="4">其他</option>
        </select></dd>
        <dd><select id="callState">
            <option disabled>工作转交</option>
            <option value="1">市场部</option>
            <option value="2">市场部经理</option>
            <option value="3">大厂部</option>
            <option value="4">技术部</option>
            <option value="5">货运部</option>
            <option value="6">财务部</option>
            <option value="11">订货部</option>
            <option value="12">调货部</option>
            <option value="13">办公室</option>
            <option value="16">网络部</option>
            <option value="17">信息部</option>
            <option value="14">其他1</option>
            <option value="15">其他2</option>
        </select></dd>
        <dd><input type="text" placeholder="地址" id="address"></dd>
        <dd><textarea rows="4" placeholder="备注" id="dealerMessage"></textarea></dd>
        <dd class="submit"><input type="button" value="提交" onclick="callOrder('y')"></dd>
        <dd class="submit"><input type="button" value="去处理" onclick="callOrder('n')"></dd>
        <div class="beizhu_box">${sessionScope.remarks.xinxiluru}</div>
    </dl>
</div>


<!--转单-->
<div class="popup popup03">
    <h2><span>转单留言</span><i>×</i></h2>
    <dl>
        <input type="hidden" id="orderState">
        <dd><textarea rows="4" placeholder="备注" id="userMessage"></textarea></dd>
        <dd class="submit"><input type="button" value="确认" onclick="giveOrder()"></dd>
    </dl>
</div>

<script type="text/javascript">
    function giveOrder() {
        var index = layer.load();
        $.ajax({
            type: "post",
            url: "<%=basePath%>order/giveOrder.html",
            dataType: "json",
            async: true,
            data: {"state": $("#orderState").val(), "userMessage": $("#userMessage").val()},
            success: function (data) {
                if (data.result) {
                    layer.msg(data.msg, {
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(index);
                        window.location.reload();
                    });
                } else {
                    layer.msg(data.msg, {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        });
    }

    //来电导入
    function callOrder(isSave) {
        var tel = $("#dealerTel").val();
        var pattern = /^1[34578]\d{9}$/;
        if (!$.trim(tel)) {
            layer.msg("联系电话不能为空");
            return false;
        }
//        if (!pattern.test(tel)) {
//            layer.msg("手机号格式不正确");
//            return false;
//        }

        var dealerName = $("#dealerName").val();
        if (!$.trim(dealerName)) {
            layer.msg("姓名不能为空");
            return false;
        }
        var address = $("#address").val();
        if (!$.trim(address)) {
            layer.msg("地址不能为空");
            return false;
        }
        var index = layer.load();
        $.ajax({
            type: "post",
            url: "<%=basePath%>order/callImprot.html",
            dataType: "json",
            async: true,
            data: {
//                "dealerNum": $("#dealerNum").val(),
                "tel": tel,
                "dealerType": $("#dealerType").val(),
                "state": $("#callState").val(),
                "dealerMessage": $("#dealerMessage").val(),
                "name":dealerName,
                "address":address,
                "isSave":isSave
            },
            success: function (data) {
                if (data.result) {
//                    layer.msg(data.msg, {
//                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
//                    }, function () {
//                        layer.close(index);
//                        window.location.reload();
//                    });

                    layer.msg(data.msg, {time: 1000},function () {
                        $(".heibg").fadeOut();
                        $(".popup01").slideUp();
                        $("#dealerTel").val("");
                        $("#dealerName").val("");
                        $("#dealerType").val("0");
                        $("#callState").val("1");
                        $("#dealerMessage").val("");
                        layer.close(index);
                        document.getElementById("jsfw").contentDocument.location.href="<%=basePath%>toJsfw.html";
                        document.getElementById("rightPage").contentDocument.location.reload();
                        if(data.obj){
                            tabTox1(data.obj);
                        }
                    });


                } else {
                    layer.msg(data.msg, {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        });
    }
</script>

<!--产品选择-->
<div class="duanxintanchuang tztanchuang a99">
    <div class="dxbox">
        <div class="dtop">
            <p>咨询产品</p>
            <span></span>
        </div>
        <div class="biao bi" id="bix">
            <div class="fl_box" id="productData">
            </div>
            <%--<table class="append_in">--%>


            <%--</table>--%>
            <button class="tijiao buttn" onclick="zxcpSubmit()">提交</button>
            <div class="beizhu_box">${sessionScope.remarks.zixunchanpin}</div>
        </div>
    </div>
</div>


<script type="text/javascript">
    function zxcpSubmit() {
        var zxcpIds = "";
        var zxcpNames = "";
        $("input:checkbox[name=cxzp_name]:checked").each(function (i) {
            if (0 == i) {
                zxcpIds = $(this).val();
                zxcpNames = $(this).parent().next().children().text();
            } else {
                zxcpIds += ("," + $(this).val());
                zxcpNames += (";" + $(this).parent().next().children().text());
            }
        });
        if (!zxcpIds) {
            layer.msg("请选择产品");
            return false;
        }
        $(document.getElementById("jsfw").contentDocument.getElementById('left_top').contentDocument.getElementById('zxcpName')).text(zxcpNames);
        $(document.getElementById("jsfw").contentDocument.getElementById('left_top').contentDocument.getElementById('zxcpIds')).val(zxcpIds);
        $(".a99").slideUp();
        $(".heibg").fadeOut();
    }
</script>

<div class="duanxintanchuang tztanchuang a100">
    <div class="dxbox">
        <div class="dtop">
            <p>食品查询</p>
            <span></span>
        </div>
        <div class="tan_box">
            <div style="overflow: hidden;" >
                <input placeholder="请输入食品名称或简拼" id="keywordFood" style="display: block;float: left;width: 80%;border: 1px solid #ededed;height: 40px;line-height: 40px;padding-left: 10px;">
                <button type="button" onclick="foodandp()" style="display: block;float: left;width: 18%;margin-left: 2%;border: none;background-color: #41973c;color: #fff;border-radius: 4px;cursor: pointer;height: 40px;line-height: 40px;">确认</button>
            </div>
            <div class="tan_02" id="foodData">
            </div>
            <div class="beizhu_box">${sessionScope.remarks.shipinchaxun}</div>
        </div>
    </div>
</div>

<div class="popup popup002">
    <h2><span>打印通知单</span><i>×</i></h2>
    <form>
        <dl>
            <dd><input type="text" placeholder="接单人（举例：部门-姓名）" id="beNoticePerson"></dd>
            <dd><textarea rows="3" placeholder="内容" id="text"></textarea></dd>
            <dd><label><input type="checkbox" id="expedited"><span>是否加急</span></label></dd>
            <dd class="submit"><input type="button" value="打印" onclick="dytzd()"></dd>
            <div class="beizhu_box">${sessionScope.remarks.tongzhi}</div>
        </dl>
    </form>
</div>

<div class="popup popup003">
    <h2><span>技术建议</span><i>×</i></h2>
    <form>
        <dl>
            <dd><textarea rows="3" placeholder="请输入" id="proposal"></textarea></dd>
            <dd class="submit"><input type="button" value="提交" onclick="addTechnicalProposal()"></dd>
            <div class="beizhu_box">${sessionScope.remarks.jishujianyi}</div>
        </dl>
    </form>
</div>

<div class="popup popup004">
    <h2><span>短信发送</span><i>×</i></h2>
    <form>
        <dl>
            <dd><input type="text" placeholder="手机号" id="sendTel"></dd>
            <dd><textarea rows="3" placeholder="内容" id="sendContent"></textarea></dd>
            <dd class="submit"><input type="button" value="确认" onclick="sendSms()"></dd>
            <div class="beizhu_box">${sessionScope.remarks.dxfs}</div>
        </dl>
    </form>
</div>

<script type="text/javascript">
    function sendSms() {
        var sendTel=$("#sendTel").val();
        if(!(/^1[34578]\d{9}$/.test(sendTel))){
            layer.msg("手机号输入有误");
            return false;
        }

        var sendContent=$("#sendContent").val();
        if(!$.trim(sendContent)){
            layer.msg("内容不能为空");
            return false;
        }

        var lo = layer.load();
        $.post("<%=basePath%>sendOutSms.html",{phone:sendTel,text:sendContent},function (data) {
            layer.close(lo);
            layer.msg(data.msg,{time:1500});
            if(data.result){
                $("#sendTel").val("");
                $("#sendContent").val("");
                $(".heibg").fadeOut();
                $(".popup004").slideUp();
            }
        },"json");

    }
</script>

<style>
    .print_table table {
    }

    .print_table table td {
    }
</style>
<div id="tzd" style="display: none;">
    <div class='print'>
        <div><span>通知单</span><i id="tzdExpedited">加急！</i></div>
        <div class="none bot"><em id="orderNumP"></em><i class="none" id="timeP"></i></div>
        <table cellpadding='0' cellspacing='0' style='border-collapse:collapse;'>
            <tr><td>To：<span id="tzdBeNoticePerson"></span></td></tr>
            <tr><td id="tzdText" valign="top"></td></tr>
        </table>
        <div class="none">
            <%--<span style='text-align:right; min-height: 40px; width: 80%; font-size: 15px;'></span>--%>
            <i><b >${userInfo.name}</b></i>
        </div>
    </div>

    <style>
        .print{ width:260px; margin-right:-10px}
        .print div{ overflow:hidden; line-height: 40px; min-height: 40px; padding: 0 10px; position: relative;}
        .print div span{ display:block; font-size:22px; color: #111; text-align: center;}
        .print div em{ display:block; font-size:20px; color: #333; font-style: normal;}
        .print div i{ position: absolute; right: 0; top: 0; font-style: normal; text-align: right; }
        .print div.none{ border-top: none;}
        .print div.bot{ border-bottom: none;}
        .print div i.none { border:none;}
        .print table{ width: 100%; font-size: 15px; color: #111;}
        .print table td{ padding:10px 5px; border: 1px solid #111;width: 230px; line-height:20px;word-break:break-all}
        .print table td span{ width: 80%; display: inline-block; text-align: center;font-weight:bold}
        #tzdText{height:500px;}
        #timeP{margin-right:20px}

    </style>
</div>

<div id="updatepw" style="display: none">   <!-- 弹窗 -->
    <form id="pwForm">
        <p><span>原密码</span><input type="password" id="oldpw" placeholder="请输入原密码"></p>
        <p><span>新密码</span><input type="password" id="newpw" placeholder="请输入新密码"></p>
        <p><span>新密码</span><input type="password" id="newpw2" placeholder="请确认新密码"></p>
    </form>
    <div class="btn">
        <a onclick="layer.close(pwtan);">取消</a>
        <a onclick="subpassword()">确定</a>
    </div>
</div>
<style>
    #updatepw{ padding: 20px; background-color: #fff; border-radius: 8px; font-size: 14px; line-height: 36px; color: #555;}
    #updatepw p{ overflow: hidden; padding: 5px 0;}
    #updatepw p span{ display:block; width: 60px; float: left; }
    #updatepw p input{ text-indent: 8px; display: block; border-radius: 2px; border: 1px solid #dedede; width: 200px; line-height: 34px; color: #555; float: left; }
    #updatepw p i{ display: block; float: left; margin-left: 14px; font-size: 12px; line-height: 36px; color: #41973c; }
    #updatepw .btn{ text-align: center; margin-top: 10px;}
    #updatepw .btn a{ display: inline-block; border-radius: 5px; line-height: 30px; font-size: 14px; color: #fff; background-color: #41973c; margin: 0 10px; padding: 0 15px;}
    #updatepw .btn a:first-child{ background-color: #eee; color: #555;}
</style>


<div class="xundan_box xuan_01">
    <div class="dxbox">
        <div class="dtop">
            <p>询单查询</p>
            <span></span>
        </div>
        <div class="tan_box aatbox" style=" overflow-y: scroll;">
            <div class="tan_01">
                <input placeholder="询单编号/手机号" id="keyword">
                <button onclick="chadanTrue()">确认</button>
            </div>
            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
            <div id="cdContent">
            </div>
            <div class="beizhu_box">${sessionScope.remarks.xundanchaxun}</div>
        </div>
    </div>
</div>

<script>
    $(function(){
        var hh = $(window).height()-100;
        $(".aatbox").height(hh)
    })
</script>


<div class="xundan_box xuan_02">
    <div class="dxbox">
        <div class="dtop">
            <p>匹配客户</p>
            <span></span>
        </div>
        <div class="tan_box">
            <div class="tan_01">
                <input id="matchTel" type="hidden">
                <button onclick="matchTrue()" style="float: right">确认</button>
            </div>
            <input type="hidden" id="pageNumMatch"  value="1"/>
            <div id="matchContent">
            </div>
            <div class="beizhu_box">${sessionScope.remarks.pipeijingxiaoshang}</div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function queryOrderData(){
        $.ajax({
            type:"post",
            url:"<%=basePath%>order/queryOrderData.html",
            data:{keyword:$("#keyword").val(),pageNum:$("#pageNum").val()},
            success:function(data){
                $("#cdContent").empty();
                $("#cdContent").html(data);
            }
        });
    }

    function queryDeaterData(){
        $.ajax({
            type:"post",
            url:"<%=basePath%>order/dealerData.html",
            data:{matchTel:$("#matchTel").val(),dealerType:"1",pageNum:$("#pageNumMatch").val()},
            success:function(data){
                $("#matchContent").empty();
                $("#matchContent").html(data);
            }
        });
    }

    function matchTrue() {
        var num = $('input[name="mTd"]:checked').val();
        if (!num) {
            layer.msg("请选择经销商", {time: 1000});
            return false;
        }
        var index=layer.load();
        $.post("<%=basePath%>order/matchSave.html", {dealerId: num}, function (data) {
            layer.close(index);
            if (data.result) {
                layer.msg(data.msg, {time: 1000},function () {
                    window.top.location.reload();
                });
            } else {
                layer.msg(data.msg, {time: 2000});
                return;
            }
        }, "json")
    }

    $("#keyword").keyup(function () {
        queryOrderData();
    })

    function chadanTrue() {
        var num = $('input[name="cdTd"]:checked').val();
        if (!num) {
          layer.msg("请选择询单", {time: 1000});
            return false;
        }
        var index=layer.load();
        $.post("<%=basePath%>order/chadan.html", {num: num}, function (data) {
            if (data.result) {
                layer.msg(data.msg, {time: 1000},function () {
                    $(".xundan_box.xuan_01").slideUp();
                    layer.close(index);
                    document.getElementById("jsfw").contentDocument.location.href="<%=basePath%>toJsfw.html";
                    document.getElementById("rightPage").contentDocument.location.reload();
                    if(data.obj){
                        tabTox1(data.obj);
                    }
                });
            } else {
                layer.close(index);
                layer.msg(data.msg, {time: 2000});
                return;
            }
        }, "json")
    }
</script>

<script>
    function showDate(){
        var mydate = new Date();
        var str = mydate.getFullYear()+"-"+mydate.getMonth()+"-"+mydate.getDay()+"  "+mydate.getHours()+":"+mydate.getMinutes();
        return str;
    }
    function dytzd() {
        $.post("<%=basePath%>order/queryNowOrder.html",{},function (data) {
            if(data.result){
                    if(data.obj){
                        $("#orderNum").text("NO."+data.obj.orderNum);
                    }
                    $("#tzdBeNoticePerson").text($("#beNoticePerson").val());
                    $("#tzdText").text($("#text").val());
                    if ($('#expedited').is(':checked')) {
                        $("#tzdExpedited").text("加急!");
                    } else {
                        $("#tzdExpedited").text("普通!");
                    }
                    $("#timeP").text(showDate());
                    printTZD("tzd");
                    $("#beNoticePerson").val("");
                    $("#text").val("");
                    $("#expedited")[0].checked = false;
                    $("#expedited").next().removeClass("on");
                $(".heibg").fadeOut();
                $(".popup002").slideUp();
            }
        },"json");
    }

    function printTZD(id) {
        var LODOP;
        LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        LODOP.PRINT_INIT("通知单");
        LODOP.SET_PRINT_MODE("POS_BASEON_PAPER",true);
        LODOP.SET_PRINT_PAGESIZE(1, 800, 2100, "");
        LODOP.ADD_PRINT_HTM(0, 0, "68mm", "200mm", document.getElementById(id).innerHTML);
        LODOP.SET_PRINT_STYLEA(0,"Horient",2);
        LODOP.PREVIEW();
//      LODOP.PRINT_DESIGN();
    }
</script>


<script>
    function aa() {
        $(".heibg").fadeIn();
        $(".popup01").slideDown();
    }

    $(function () {
        $(".heibg,.popup >h2 i,.dtop span,.tan_01 button").click(function () {
            $(".heibg").fadeOut();
            $(".popup01,.popup03,.a99,.a100,.popup002,.popup003,.popup004").slideUp();
        })
    })

    function zhuandan(id) {
        $("#orderState").val(id);
        if(id=='7'){
            giveOrder();
        }else {
            var userMessage = $("#jsfw").contents().find('#left_top').contents().find('#userMessage').text();
            $("#userMessage").val(userMessage);
            $(".heibg").fadeIn();
            $(".popup03").slideDown();
        }
    }

    function jishujianyi() {
        $(".heibg").fadeIn();
        $(".popup003").slideDown();
    }

    function cpxz(ids) {
        var idArry=ids.split(",");
        $.ajax({
            type: "post",
            url: "<%=basePath%>order/queryAllProduct.html",
            async: true,
            dataType: "json",
            success: function (data) {
                if (data.result) {
                    var html = '';
                    for (var i = 0; i < data.obj.list.length; i++) {
                        html += '<table class="fl_left">';
                        html += '<th>' + data.obj.list[i].name + '</th>';
                        for (var j = 0; j < data.obj.maxnum; j++) {
                            if(j<data.obj.list[i].productList.length) {
                                html += '<tr><td><dl style="overflow: hidden"><dd style=" float: left; margin: 4px 4px 0 0"><input ';
                                if($.inArray(data.obj.list[i].productList[j].id,idArry)!=-1){
                                    html += 'checked';
                                }
                                html += ' type="checkbox" id="cp' + data.obj.list[i].productList[j].id + '" name="cxzp_name" value="' + data.obj.list[i].productList[j].id + '"></dd><dt style=" float: left"><label for="cp' + data.obj.list[i].productList[j].id + '">' + data.obj.list[i].productList[j].productNum +' '+ data.obj.list[i].productList[j].productName + '</label></dt></dl></td></tr>';
                            }
                            else{
                                html += '<tr><td><dl style="overflow: hidden"><dd style=" float: left; margin: 4px 4px 0 0"><input type="checkbox"></dd><dt style=" float: left"><label >无</label></dt></dl></td></tr>'
                            }
                        }
                        html += '</table>';
                    }
                    $("#productData").html(html);
                } else {
                    return false;
                }
                /*if(data.obj.list.length>5){
                    $("#bix").css({"overflow":"auto","overflow-x":"scroll"})
                }else if(data.obj.list.length<=5){
                    $("#bix").css({"overflow":"auto","overflow-x":"visible"})
                }*/
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        });
        $(".heibg").fadeIn();
        $(".a99").slideDown();
    }

    function dayin() {

//        $("#beNoticePerson").val("");
//        $("#text").val("");
//        $("#expedited")[0].checked = false;
//        $("#expedited").next().removeClass("on");
        $(".heibg").fadeIn();
        $(".popup002").slideDown();
    }

    function showSms() {

        $(".heibg").fadeIn();
        $(".popup004").slideDown();
    }

    function spcx(ids) {
        var idArry=ids.split(",");
        $.ajax({
            type: "post",
            url: "<%=basePath%>order/queryAllFood.html",
            async: true,
            dataType: "json",
            data:{"keyword":""},
            success: function (data) {
                if (data.result) {

                    var html='';
                    for(var i=0;i<data.obj.length;i++){
                        html += '<p>适合“'+data.obj[i].food_name+'”的产品有：</p>';
                        for (var n = 0; n < data.obj[i].products.length; n++) {
                            html+='<label><dl class="che"><dd class="che">' +
                                '<input ' ;
                            if($.inArray(data.obj[i].products[n].id,idArry)!=-1){
                                html += ' checked ';
                            }
                            html += ' name="candp" cpid="'+data.obj[i].products[n].id+'" ming="'+data.obj[i].products[n].product_num+' '+data.obj[i].products[n].product_name+'" class="che che1" type="checkbox">' +
                            '<b class="che">'+data.obj[i].products[n].product_num+' '+data.obj[i].products[n].product_name+'</b>' +
                            '</dd></dl></label>';
                        }
                    }
                    $("#foodData").html(html);
                    $(".tan_02").show();
                } else {
                    return false;
                }
            }
        });
        $(".heibg").fadeIn();
        $(".a100").slideDown();
    }

    function tabTox(){
        queryOrderData();
        $(".xundan_box.xuan_01").slideDown();
    }

    function tabTox1(tel){
        $("#matchTel").val(tel);
        queryDeaterData();
        $(".xundan_box.xuan_02").slideDown();
    }

    $(function(){
        $(".xundan_box .dxbox .dtop span").click(function(){
            $(".xundan_box").slideUp();
        })
    })


    function foodandp(){
        var id='';
        var ming='';
        $("input:checkbox[name=candp]:checked").each(function (i) {
            if(i==0){
                if(id.indexOf($(this).attr("cpid"))==-1)id+=$(this).attr("cpid");
                if(ming.indexOf($(this).attr("ming"))==-1)ming+=$(this).attr("ming")
            }else{
                if(id.indexOf($(this).attr("cpid"))==-1)id+=","+$(this).attr("cpid");
                if(ming.indexOf($(this).attr("ming"))==-1)ming+=";"+$(this).attr("ming")
            }
        });
        if (!id) {
            layer.msg("请选择产品");
            return false;
        }
        $(document.getElementById("jsfw").contentDocument.getElementById('left_top').contentDocument.getElementById('zxcpName')).text(ming);
        $(document.getElementById("jsfw").contentDocument.getElementById('left_top').contentDocument.getElementById('zxcpIds')).val(id);
        $(".heibg").fadeOut();
        $(".a100").slideUp();
    }

    function queryFood() {
        $.ajax({
            type: "post",
            url: "<%=basePath%>order/queryAllFood.html",
            async: true,
            dataType: "json",
            data:{"keyword":$("#keywordFood").val()},
            success: function (data) {
                if (data.result) {
                    var html=''
                    for(var i=0;i<data.obj.length;i++){
                        html += '<p>适合“'+data.obj[i].food_name+'”的产品有：</p>';
                        for (var n = 0; n < data.obj[i].products.length; n++) {
                            html+='<label><dl class="che"><dd class="che">' +
                                '<input name="candp" cpid="'+data.obj[i].products[n].id+'" ming="'+data.obj[i].products[n].product_num+' '+data.obj[i].products[n].product_name+'" class="che che1" type="checkbox">' +
                                '<b class="che">'+data.obj[i].products[n].product_num+' '+data.obj[i].products[n].product_name+'</b>' +
                                '</dd></dl></label>'
                        }
                    }
                    $("#foodData").html(html);
                } else {
                    return false;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        });
    }
    /*$(function(){
        var n = 1;
        $(".qingxuanze").click(function(){
            if(n==1){
                $(this).siblings('div').slideDown();
                $(this).parent(".dingwei").addClass("on");
                n=0;
            }
            else{
                $(this).siblings('div').slideUp();
                $(this).parent(".dingwei").removeClass("on");
                n=1;
            }
            //return false;
        })
        $(".dtd .dingwei>div dl").click(function(){
            //alert("1")
            var _this = $(this)
            var _parent = $(this).parents(".dingwei>div")
            if(_this.find(".che1").is(':checked')){
                _this.addClass("on");
            }else{
                _this.removeClass("on");
            }
            var l =_parent.children("label").length;
            var str = '' ;
            console.log(l)
            for(var i = 0; i< l ; i++){
                if(_parent.children("label").eq(i).find("dl").hasClass("on")){
                    str = str + _parent.children("label").eq(i).find("b").text() +';';
                }
            }
            if(str==''){
                str = '请选择产品'
            }
            _this.parents(".dingwei").find(".qingxuanze").text(str)
            //$(".khlx>div").css({"display":'block !important'});

        })
    })	*/

    /*产品赠送添加*/

    $('.tianjia1').click(function () {
        $(".append_in").append("<tr><td class='dtd'><input class='shuru' value='请输入内容'><div class='neir'><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p><p>分类 - 产品名 - 适用食品</p></div></td><td class='shanchu'><a href='javascript:'>删除</a></td></tr>");
        $('.dtd .shuru').focus(function () {
            $(this).parent('.dtd').find('.neir').slideDown();
        })

        $('.dtd .shuru').blur(function () {
            $(this).parent('.dtd').find('.neir').slideUp();
        })

        $('.dtd .neir p').click(function () {
            $(this).parent('.dtd').find('.neir').slideUp();
        })


        /*var n = 1;
        $(".qingxuanze").click(function(){
            if(n==1){
                $(this).siblings('div').slideDown();
                $(this).parent(".dingwei").addClass("on");
                n=0;
            }
            else{
                $(this).siblings('div').slideUp();
                $(this).parent(".dingwei").removeClass("on");
                n=1;
            }
            //return false;
        })
        $(".dtd .dingwei>div dl").click(function(){
            //alert("1")
            var _this = $(this)
            var _parent = $(this).parents(".dingwei>div")
            if(_this.find(".che1").is(':checked')){
                _this.addClass("on");
            }else{
                _this.removeClass("on");
            }
            var l = _parent.children("label").length;
            var str = '' ;
            console.log(l)
            for(var i = 0; i< l ; i++){
                if(_parent.children("label").eq(i).find("dl").hasClass("on")){
                    str = str + _parent.children("label").eq(i).find("b").text() +';';
                }
            }
            if(str==''){
                str = '请选择产品'
            }
            _this.parents(".dingwei").find(".qingxuanze").text(str)
            //$(".khlx>div").css({"display":'block !important'});

        })*/

    })

    /*$(window).click(function(e){
        if(e.target.className!='che che1' && e.target.className!='che' && e.target.className!='che on' && e.target.className!='chea'){
            $(".dingwei>div").slideUp();
        }
    })
    */

    $('.dtd .shuru').focus(function () {
        $(this).parent('.dtd').find('.neir').slideDown();
    })

    $('.dtd .shuru').blur(function () {
        $(this).parent('.dtd').find('.neir').slideUp();
    })

    $('.dtd .neir p').click(function () {
        $(this).parent('.dtd').find('.neir').slideUp();
    })


    $('.popup dl dd label span').click(function () {
        if ($(this).siblings("input").is(':checked')) {
            $(this).removeClass('on')
        }
        else {
            $(this).addClass('on')
        }
    })

    function al() {
        alert("待定")
    }

    $("#keywordFood").keyup(function () {
        queryFood();
    })
    $(".tan_01 input").keyup(function () {
        queryFood();
        $(".tan_02").show();	//只要输入就显示列表框  
    })
    $(".tan_02 button").click(function () {
        $(".tan_02").hide();	//只要输入就显示列表框  
    })

    $(".a99 .append_in tr").attr("adc", 0);
    $(".a99 .append_in tr").click(function () {
        var n = $(this).attr("adc");
        if (n == 0) {
            $(this).find(".ac").attr('checked', true);
            n = $(this).attr("adc", 1);
        }
        else {
            $(this).find(".ac").attr('checked', false);
            n = $(this).attr("adc", 0);
        }
    })

    function yjdd() {
        var index = layer.load();
        $.ajax({
            type: "post",
            url: "<%=basePath%>order/oneKeyOrder.html",
            dataType: "json",
            async: true,
            data: {},
            success: function (data) {
                layer.close(index);
                if (data.result) {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    },function () {
                        document.getElementById("jsfw").contentDocument.location.href="<%=basePath%>toJsfw.html";
                        document.getElementById("rightPage").contentDocument.location.reload();
                        if(data.obj){
                            tabTox1(data.obj);
                        }
                    });
                } else {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        });
    }


    /*添加技术建议*/
    function addTechnicalProposal() {
        var proposal = $("#proposal").val();
        if (proposal.length > 2000) {
            layer.msg("不能超过2000字", {time: 1000, icon: 2, offset: '200px'});
            return;
        }
        $.ajax({
            type: "post",
            data: {"text": proposal},
            url: "<%=basePath%>technicalProposal/editSave.html",
            async: false,
            success: function (data) {
                data = eval("(" + data + ")");
                if (data.result) {
                    //清空输入
                    $("#proposal").val("");
                    $(".popbg").hide();
                    $(".popbz").hide();
                    layer.msg(data.msg, {time: 1000, icon: 1, offset: '200px'});
                } else {
                    layer.msg(data.msg, {time: 1000, icon: 2, offset: '200px'});
                }
            },
            error: function () {
                layer.msg("提示：系统内部出现问题！", {time: 1000, icon: 2, offset: '200px'});
            }
        })
    }

    $(function () {
        var n = $('.fl_left').length;
        $('.fl_left').css({"width": (100 / n) + '%'})
        // console.log(wq)
        if (n > 8) {
            $('.fl_left').css({"min-width": 170 + "px", "width": "auto"})
            $('.fl_box').css({"overflow-x": "scroll", "white-space": "nowrap"})

        }

    })

    var pwtan;

    function updatepw() {
        $("#pwForm").find("input").val("");
        pwtan = layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shadeClose: true,
            skin: 'yourclass',
            content: $("#updatepw")
        });
    }

    function subpassword(){
        var oldpw = $("#oldpw").val();
        var newpw = $("#newpw").val();
        var newpw2 = $("#newpw2").val();
        if(!oldpw){
            layer.msg("请输入原密码",{time:1500})
            return;
        }
        if(!newpw){
            layer.msg("请输入新密码",{time:1500})
            return;
        }
        if(!newpw2){
            layer.msg("请确认新密码",{time:1500})
            return;
        }
        if(oldpw==newpw){
            layer.msg("原密码和新密码相同",{time:1500})
            return;
        }
        if(newpw!=newpw2){
            layer.msg("两次输入的新密码不同",{time:1500})
            return;
        }
        $.post("updatePsw.html",{oldpw:oldpw,newpw:newpw},function (data) {
            layer.msg(data.msg,{time:1500});
            layer.close(pwtan);
        },"json")
    }

</script>

<div id="callcenter" align="left" style="height:82px;"></div>
<script type="text/javascript" src="swfobject.js"></script>
<script>
<c:if test="${not empty userInfo.extensionNum}">
    var flashvars = {
        extno: "${userInfo.extensionNum}", <!--分机号-->
        seatpass: "",<!--MD5加密后的密码-->
        ctiip: "${sessionScope.remarks.ipinfo}", <!--服务器地址-->
        ipport: "2000", <!--监听端口号-->
        isshowcall: "0", <!--不显示控件-->
        isautologin: "1" ,<!--自动登录-->
        ishidetel: "0"   //不隐藏来电号码
    };
    var params = {
        menu: "false"
    };
    var attributes = {
        id: "cti",
        name: "cti"
    };
    swfobject.embedSWF("cti.swf", "callcenter", "0", "0", "9.0.0", "cti.swf", flashvars, params, attributes);

    function gosearch(thestr){
        if(thestr!=""){
            if(thestr=="CTI连接中断")
            {
                document.getElementById("outlineinfo").innerHTML="CTI连接中断";
                document.getElementById("inlineinfo").innerHTML="CTI连接中断";
                document.getElementById("conferenceinfo").innerHTML="CTI连接中断";
                document.getElementById("waitnum").innerHTML="";
                document.getElementById("waitnumme").innerHTML="";
            }
            else
            {
                $("#cue2").text("坐席${userInfo.extensionNum}已登录");
                var arrTmp = thestr.split("|");  // 分隔信息
                //数组中第1位表示是否是来电信息，只有是"1"才表示是来电信息，"2"表示其它信息
                //数组中第2位表示分机号
                //数组中第3位表示来电号码
                //数组中第4位表示来电流程
                //数组中第5位表示来电时间
                //数组中第6位表示用户按键
                //数组中第7位表示用户自己定义参数，可在IVR接口查询时返回
                //数组中第8位表示用户在IVR流程中输入的内容,多个参数之间用@分隔
                //数组中第9位表示此次通话的录音文件名
                //数组中第10位表示此次通话的IVR的名称
                //数组中第11位表示此次通话的外线通道号
                //数组中第12位表示此次通话的分机通道号
                //数组中第13位表示号码归属地

                if(arrTmp[0]=="1")  //处理来电或弹屏信息
                {
                    if("${userInfo.extensionNum}"==arrTmp[1]){
                        $(".heibg").fadeIn();
                        $(".popup01").slideDown();
                        $("#dealerTel").val(arrTmp[2]);
                    }
                    if(msgtype=="1")//弹屏
                    {
                        document.form1.mobile.value=thestr;
                        document.form1.submit();
                    }
                    if(msgtype=="2")//当前页面刷新，也可以通过AJax来获取其它信息来显示在本页面上
                    {
                        document.getElementById("newcallinfo").innerHTML=thestr;//来电信息
                        document.getElementById("ivrback").innerHTML=arrTmp[6];//自己定义参数
                    }
                }

                if(arrTmp[0]=="2") //处理排队及状态信息
                {

                    //示例："2|0-0@0$801$801$空闲$1$105536,1$802$802$空闲$0$105536@2$空闲[√]$$$$105536,3$空闲[√]$$$$105536"，
                    //最前面的2是固定标志位，后面0-0分别是排队数量与自己分组内的排队数量，
                    //座席的数据0$801$801$空闲$1$105536，用$分隔开来分别是通道号\分机\姓名\状态\是否登录\状态持续时间，后面是802分机的信息，
                    //外线的数据2$空闲[√]$$$$105536，用$分隔开来分别是通道号\状态\呼叫类型\主叫号码\被叫\状态持续时间，后面是下一个外线的信息，
                    //开发时建议先用|分隔成数组，再用@分隔成数组，再用","分隔成数组，最后再用$分隔成数组

                    var infostr=arrTmp[1];
                    var infoarr=infostr.split("@");// 分隔信息
                    //数组中第1位表示排队数量信息
                    //数组中第2位表示分机状态信息
                    //数组中第3位表示外线状态信息
                    //数组中第4位表示会议信息

                    var waitarr=infoarr[0].split("-");// 分隔排队信息
                    document.getElementById("waitnum").innerHTML=waitarr[0];//显示排队数量，也可以根据需要在其它地方进行显示
                    document.getElementById("waitnumme").innerHTML=waitarr[1]//自己分组内的排队数量

                    //下面处理分机状态数据
                    var isinlineinfo=getCookie("inlineinfo");//根据cookie是否显示分机状态

                    var backinlinestr="<span style=\"text-align:left;\">";
                    if(isinlineinfo=="none")
                    {
                        backinlinestr=backinlinestr+"<a href=\"#\" onclick=\"javascript:setCookie('inlineinfo','block',365);\">显示分机状态</a>";
                    }
                    else
                    {
                        backinlinestr=backinlinestr+"<a href=\"#\" onclick=\"javascript:setCookie('inlineinfo','none',365);\">隐藏分机状态</a>";
                    }

                    backinlinestr=backinlinestr+"</span>";
                    backinlinestr=backinlinestr+"<table  border=\"0\" style=\"display:"+isinlineinfo+"\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"#B7D9FE\" width=\"520\">";
                    backinlinestr=backinlinestr+"<tr bgcolor=\"#EEEEEE\">";
                    backinlinestr=backinlinestr+"<td width=\"15%\">分机</td>";
                    backinlinestr=backinlinestr+"<td width=\"20%\">姓名</td>";
                    backinlinestr=backinlinestr+"<td width=\"25%\">状态</td>";
                    backinlinestr=backinlinestr+"<td width=\"25%\">登录</td>";
                    backinlinestr=backinlinestr+"<td width=\"15%\">持续</td>";
                    backinlinestr=backinlinestr+"</tr>";

                    var inlineinfoarr=infoarr[1].split(",");//所有分机状态信息
                    for(var i=0;i<inlineinfoarr.length;i++)
                    {
                        var nowinlineinfoarr=inlineinfoarr[i].split("$");
                        if(nowinlineinfoarr.length>=4)
                        {
                            backinlinestr=backinlinestr+"<tr bgcolor=\"#F8F8F8\">";
                            backinlinestr=backinlinestr+"<td><a href=\"javascript:callExternal('Switch"+nowinlineinfoarr[1]+"');\" title=\"转分机\">"+nowinlineinfoarr[1]+"</a></td>";
                            backinlinestr=backinlinestr+"<td><a href=\"javascript:gochat('"+nowinlineinfoarr[1]+"');\" title=\"发消息\">"+nowinlineinfoarr[2]+"</a></td>";

                            if(nowinlineinfoarr[3]=="置忙" || nowinlineinfoarr[3]=="摘机" || nowinlineinfoarr[3]=="振铃")
                            {
                                backinlinestr=backinlinestr+"<td><font color=\"red\">"+nowinlineinfoarr[3]+"</font></td>";
                            }
                            else
                            {
                                backinlinestr=backinlinestr+"<td><font color=\"green\">"+nowinlineinfoarr[3]+"</font></td>";
                            }

                            if(extno==nowinlineinfoarr[1])
                            {
                                if(nowinlineinfoarr[3]=="摘机")
                                {
                                    document.getElementById("closetalk").style.display="";//显示结束通话按钮
                                }else
                                {
                                    document.getElementById("closetalk").style.display="none";//隐藏结束通话按钮
                                }
                            }


                            if(nowinlineinfoarr[4]=="1")
                            {
                                backinlinestr=backinlinestr+"<td><font color=\"green\">是</font></td>";
                            }
                            else
                            {
                                backinlinestr=backinlinestr+"<td></td>";
                            }
                            backinlinestr=backinlinestr+"<td>"+MillisecondToDate(nowinlineinfoarr[5])+"</td>";
                            backinlinestr=backinlinestr+"</tr>";
                        }
                    }
                    backinlinestr=backinlinestr+"</table>";
                    document.getElementById("inlineinfo").innerHTML=backinlinestr;

                    //下面处理外线状态数据
                    var isoutlineinfo=getCookie("outlineinfo");//根据cookie是否显示外线状态

                    var backoutlinestr="<span style=\"text-align:left;\">";
                    if(isoutlineinfo=="none")
                    {
                        backoutlinestr=backoutlinestr+"<a href=\"#\" onclick=\"javascript:setCookie('outlineinfo','block',365);\">显示外线状态</a>";
                    }
                    else
                    {
                        backoutlinestr=backoutlinestr+"<a href=\"#\" onclick=\"javascript:setCookie('outlineinfo','none',365);\">隐藏外线状态</a>";
                    }

                    backinlinestr=backinlinestr+"</span>";
                    backoutlinestr=backoutlinestr+"<table  border=\"0\" style=\"display:"+isoutlineinfo+"\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"#B7D9FE\" width=\"520\">";
                    backoutlinestr=backoutlinestr+"<tr bgcolor=\"#EEEEEE\">";
                    backoutlinestr=backoutlinestr+"<td width=\"5%\">ID</td>";
                    backoutlinestr=backoutlinestr+"<td width=\"15%\">状态</td>";
                    backoutlinestr=backoutlinestr+"<td width=\"20%\">呼叫类型</td>";
                    backoutlinestr=backoutlinestr+"<td width=\"25%\">主叫</td>";
                    backoutlinestr=backoutlinestr+"<td width=\"10%\">被叫</td>";
                    backoutlinestr=backoutlinestr+"<td width=\"15%\">持续</td>";
                    backoutlinestr=backoutlinestr+"<td width=\"10%\">优先</td>";
                    backoutlinestr=backoutlinestr+"</tr>";

                    var outlineinfoarr=infoarr[2].split(",");//所有外线状态信息
                    for(var i=0;i<outlineinfoarr.length;i++)
                    {
                        var nowoutlineinfoarr=outlineinfoarr[i].split("$");
                        if(nowoutlineinfoarr.length>=6)
                        {
                            backoutlinestr=backoutlinestr+"<tr bgcolor=\"#F8F8F8\">";
                            backoutlinestr=backoutlinestr+"<td>"+nowoutlineinfoarr[0]+"</td>";
                            if(nowoutlineinfoarr[1].indexOf("空闲")>-1)
                            {
                                backoutlinestr=backoutlinestr+"<td><font color=\"green\">"+nowoutlineinfoarr[1]+"</font></td>";
                            }
                            else
                            {
                                backoutlinestr=backoutlinestr+"<td><font color=\"red\">"+nowoutlineinfoarr[1]+"</font></td>";
                            }
                            backoutlinestr=backoutlinestr+"<td>"+nowoutlineinfoarr[2]+"</td>";
                            backoutlinestr=backoutlinestr+"<td>"+nowoutlineinfoarr[3]+" "+nowoutlineinfoarr[7]+"</td>";
                            backoutlinestr=backoutlinestr+"<td>"+nowoutlineinfoarr[4]+"</td>";
                            backoutlinestr=backoutlinestr+"<td>"+MillisecondToDate(nowoutlineinfoarr[5])+"</td>";
                            if(nowoutlineinfoarr[6]=="0")
                            {
                                backoutlinestr=backoutlinestr+"<td>否 <a href=\"javascript:callExternal('PriorityYes|"+nowoutlineinfoarr[0]+"');\";>设置</a></td>";
                            }
                            else
                            {
                                backoutlinestr=backoutlinestr+"<td>是 <a href=\"javascript:callExternal('PriorityNo|"+nowoutlineinfoarr[0]+"');\";>取消</a></td>";
                            }
                            backoutlinestr=backoutlinestr+"</tr>";
                        }
                    }
                    backoutlinestr=backoutlinestr+"</table>";
                    document.getElementById("outlineinfo").innerHTML=backoutlinestr;

                    //下面处理会议信息
                    var conferencestr="<table  border=\"0\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"#B7D9FE\" width=\"520\">";
                    conferencestr=conferencestr+"<tr bgcolor=\"#EEEEEE\">";
                    conferencestr=conferencestr+"<td width=\"10%\">会议ID</td>";
                    conferencestr=conferencestr+"<td width=\"40%\">会议名称</td>";
                    conferencestr=conferencestr+"<td width=\"30%\">创建时间</td>";
                    conferencestr=conferencestr+"<td width=\"10%\">人数</td>";
                    conferencestr=conferencestr+"<td width=\"10%\">操作</td>";
                    conferencestr=conferencestr+"</tr>";

                    var conferencearr=infoarr[3].split(",");//所有会议信息
                    for(var i=0;i<conferencearr.length;i++)
                    {
                        var nowconferencearr=conferencearr[i].split("$");
                        if(nowconferencearr.length>=5)
                        {
                            conferencestr=conferencestr+"<tr bgcolor=\"#F8F8F8\">";
                            conferencestr=conferencestr+"<td>"+nowconferencearr[0]+"</td>";
                            conferencestr=conferencestr+"<td>"+nowconferencearr[1]+"</td>";
                            conferencestr=conferencestr+"<td>"+nowconferencearr[2]+"</td>";
                            conferencestr=conferencestr+"<td>"+nowconferencearr[3]+"</td>";
                            conferencestr=conferencestr+"<td><a href=\"javascript:callExternal('closeConference|"+nowconferencearr[0]+"')\">关闭</a></td>";
                            conferencestr=conferencestr+"</tr>";

                            //取此会议所有成员信息
                            if(nowconferencearr[3]!="")
                            {
                                conferencestr=conferencestr+"<tr>";
                                conferencestr=conferencestr+"<td bgcolor=\"#EEEEEE\"></td>";
                                conferencestr=conferencestr+"<td colspan=\"4\">";

                                conferencestr=conferencestr+"<table  border=\"0\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"#B7D9FE\" width=\"100%\"align=\"center\">";
                                conferencestr=conferencestr+"<tr bgcolor=\"#EEEEEE\">";
                                conferencestr=conferencestr+"<td width=\"15%\">通道号</td>";
                                conferencestr=conferencestr+"<td width=\"40%\">电话号码</td>";
                                conferencestr=conferencestr+"<td width=\"15%\">状态</td>";
                                conferencestr=conferencestr+"<td width=\"30%\">操作</td>";
                                conferencestr=conferencestr+"</tr>";

                                var memberarr=nowconferencearr[4].split("^");
                                for(var j=0;j<memberarr.length;j++)
                                {
                                    var nowmemberarr=memberarr[j].split("*");//当前成员
                                    conferencestr=conferencestr+"<tr bgcolor=\"#F8F8F8\">";
                                    conferencestr=conferencestr+"<td>"+nowmemberarr[0]+"</td>";
                                    conferencestr=conferencestr+"<td>"+nowmemberarr[1]+"</td>";
                                    if(nowmemberarr[2]=="1")
                                    {
                                        conferencestr=conferencestr+"<td>可言</td>";
                                        conferencestr=conferencestr+"<td><a href=\"javascript:callExternal('updateConferenceMember|"+nowmemberarr[0]+"');\";>禁止发言</a> <a href=\"javascript:callExternal('exitConference|"+nowmemberarr[0]+"');\">退出</a></td>";
                                    }
                                    if(nowmemberarr[2]=="2")
                                    {
                                        conferencestr=conferencestr+"<td>禁言</td>";
                                        conferencestr=conferencestr+"<td><a href=\"javascript:callExternal('updateConferenceMember|"+nowmemberarr[0]+"');\">开通发言</a> <a href=\"javascript:callExternal('exitConference|"+nowmemberarr[0]+"');\">退出</a></td>";
                                    }
                                    conferencestr=conferencestr+"</tr>";

                                }
                                conferencestr=conferencestr+"</table>";
                                conferencestr=conferencestr+"</td>";
                                conferencestr=conferencestr+"</tr>";
                            }
                        }
                    }
                    conferencestr=conferencestr+"</table>";


                    document.getElementById("conferenceinfo").innerHTML=conferencestr;

                }

                if(arrTmp[0]=="3") //处理通话信息
                {
                    //请参考相关手册文档
                    $("#cue1").text(arrTmp[1]);
                }

                if(arrTmp[0]=="4") //座席之间相互之间发的送的信息
                {
                    //4|发送人|接收人|消息内容
                    var chatwin=getchatwin(arrTmp[1]);
                    if(chatwin==null || chatwin.closed)
                    {
                        //窗口不存在或已关闭
                        <%--var chatwin=window.open("<%=chaturl1%>?myextno="+arrTmp[2]+"&toextno="+arrTmp[1]+"&msg="+thestr,"Chat"+arrTmp[1]);--%>
                        <%--addchatwin(arrTmp[1],chatwin);--%>
                    }
                    else
                    {
                        //窗口存在
                        chatwin.recemsg(thestr);
                    }
                }
            }
        }
    }
</c:if>
//    function onclickCall(tel) {
//        var layerIndex = layer.confirm('确认要拨号吗？',{icon:3},function(index){
//            callPhone(tel);
//        });
//    }


    function callPhone(phone){
        var num="${userInfo.extensionNum}";

        if(!$.trim(num)){
            layer.msg("请先设置坐席");
            return false;
        }

        if(!$.trim(phone)){
            layer.msg("号码不能为空");
            return false;
        }
        $.post("<%=basePath%>order/queryNumberAttribution.html",{phone:phone},function (data) {
            if(data.obj.indexOf("郑州")!=-1 || data.obj.indexOf("开封")!=-1){
                swfobject.getObjectById('cti').callTel("9"+phone);
            }else {
                swfobject.getObjectById('cti').callTel("90"+phone);
            }
        },"json");
    }
</script>
<script>
   /* $(function(){
        var nn = $("#productData .fl_left").index();
        //alert(nn)
        if(nn>5){
            $("#bix").css({"overflow":"auto","overflow-x":"scroll"})
        };
    })*/
   $(function(){
       var h_window = $(window).height();
       var h_n = h_window - 54;
       $("#bix").height(h_n);

   })
</script>

</body>
</html>
