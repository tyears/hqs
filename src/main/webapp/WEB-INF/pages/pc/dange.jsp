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
    <script type="text/javascript" src="js/common/queryPage.js"></script>
    <script src="js/layer.js"></script>
    <script language="javascript" src="js/lodop/LodopFuncs.js"></script>
    <style>
        .smsStyle{
            display:block;resize: none;width: 100%;background-color: #f5f5f5;border: 0px; margin-bottom: 15px;padding: 10px;font-size: 16px;color: #333333;min-height: 140px;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="js/jquery-ui-1.12.1/jquery-ui.min.css" />
    <script type="text/javascript" src="js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <style>
        .ui-autocomplete {
            max-height: 222px;
            overflow-y: hidden;
            /* 防止水平滚动条 */
            overflow-x: hidden;
            z-index: 999;
        }
    </style>
</head>
<body>
<div class="left_top">
    <iframe id="left_top" src="order/toLeftTop.html" frameborder="0" width="100%" scrolling="no" ></iframe>
</div>

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>单个经销商情况</b></div></div>
    <div class="dongtai">
        <div class="dbox">
            <div class="shic">
                <div class="shuru_box">
                    <ul>
                        <li>
                            <form action="dealerPc/toDealerDetailData.html" method="post" id="queryForm">
                                <input placeholder="请输入货号或品名" id="keyword" name="keyword">
                                <input type="hidden" name="sortNum" value="${params.sortNum}" id="sortNum">
                                <input   name="dealerId" value="${dealer.id}" type="hidden">
                                <button class="chaxun" onclick="query()">查询</button>
                                <button class="chongzhi" onclick="$('#keyword').val('')">重置</button>
                                <button class="chaxun" onclick="location.href='order/toChangeRecord.html?dealerId=${dealer.id}&orderTel=${dealer.registerTel}'">客户详情</button>
                            </form>
                        </li>
                    </ul>
                    <dl>
                        <dt>
                            <b><a>${dealer.dealerNum}</a> ${dealer.name} 市场情况&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;总评价：<a>${dealer.overallMerit}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;合作情况：<a>${dealer.cooperationState}</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经销商电话：<a href="javascript:window.parent.parent.callPhone('${dealer.registerTel}')">${dealer.registerTel}</a></b>
                        </dt>
                        <dd></dd>
                    </dl>
                </div>
                <div class="bigbiaoge danwei dange">
                    <div>
                        <table class="hov_mou">
                            <tr>
                                <th class="wh_01" onclick="changeSort();">货号</th>
                                <th class="wh_02">品名</th>
                                <%--<th class="wh_03">赠送件数</th>--%>
                                <th class="wh_04">第一次赠送</th>
                                <th class="wh_05">最后赠送</th>
                                <th class="wh_06">经销商宣传</th>
                                <th class="wh_07">公司赠送</th>
                                <th class="wh_08">通知信息数</th>
                                <th class="wh_09">评价</th>
                                <th class="wh_10">最后进货</th>
                                <th class="wh_11">授权</th>
                                <th class="wh_12">公司通知</th>
                                <th class="wh_13">经销商赠送</th>
                                <th class="wh_14">有效时间</th>
                                <th class="wh_15">备注</th>
                                <%--<th class="wh_16"><a>全选</a><input id="all" type="checkbox"></th>--%>
                            </tr>
                        </table>
                    </div>
                    <div class="over_flow_01"id="mainContent"></div>
                    <div class="btnbox btnbox1">
                        <div class="right_bt">
                            <button id="zsjx" onclick="zsjxOnclick('1')">赠送给经销商</button>
                            <button id="zsgs" onclick="zsjxOnclick('2');">公司赠送</button>
                            <button id="zskh" onclick="zsjxOnclick('3');">经销商赠送</button>
                            <button id="czjl">操作记录</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.jingxiaoshang}</div>
    </div>
</div>

<!--弹窗-->
<div class="heibg"></div>
<div class="duanxintanchuang tztanchuang a3">
    <div class="dxbox">
        <div class="dtop">
            <p>咨询信息告知</p>
            <span></span>
        </div>
        <div class="ddmidd zssma zssma1">
            <div class="dmidd zssm" contenteditable="true">
                今有您市场客户：马春霞、13633865525，咨询A101亮白馒头改良剂；M102快速油条膨松剂，请您代表海韦力公司和客户联系，告诉客户如需要产品可从您处购买！-海韦力市场部
            </div>
            <div class="d_bt">确认告知</div>
        </div>
    </div>
</div>


<div class="duanxintanchuang tztanchuang a1 tan0111" style="width: 470px; margin-top: 0; top: 10%; overflow: auto !important">
    <input id="giveType" type="hidden"/>
    <input id="giveId" type="hidden"/>
    <div class="dxbox">
        <div class="dtop">
            <p id="giveTitle">赠送给经销商</p>
            <span></span>
        </div>
        <div style="max-height:670px; overflow-y:auto">
            <div class="biao">
                <table class="append_t" id="productTr">
                    <tr>
                        <th>产品货号</th>
                        <th>产品名称</th>
                        <th>操作</th>
                    </tr>
                </table>
                <h3 class="tianjia" onclick="addTr();">添加一个产品</h3>
            </div>
            <div class="ddmidd zssma">
                <textarea rows="4" placeholder="赠送说明:" id="zssm" class="smsStyle"></textarea>
                <textarea rows="4" placeholder="给客户发短信：" id="khSms" class="smsStyle" ></textarea>
                <textarea rows="4" placeholder="给经销商发短信：" id="jxsSms" class="smsStyle" ></textarea>
                <div onclick="saveGive();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;margin-top: 20px;cursor: pointer;font-size: 15px;font-weight: bold;">确认赠送并提交</div>
                <div id="dyTrue" onclick="printTrue();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;margin-top: 20px;cursor: pointer;font-size: 15px;font-weight: bold;">打印</div>
            </div>
        </div>
    </div>
</div>

<div class="duanxintanchuang tztanchuang a2">
    <div class="dxbox">
        <div class="dtop">
            <p>经销商赠送</p>
            <span></span>
        </div>
        <div style="max-height:480px; overflow-y:auto">
            <div class="biao">
                <table>
                    <tr>
                        <th>赠送产品</th>
                        <th>数量</th>
                    </tr>
                    <tr>
                        <td>A101亮白馒头改良剂</td>
                        <td><input type="number"></td>
                    </tr>
                    <tr class="hui_bg">
                        <td>A101亮白馒头改良剂</td>
                        <td><input type="number"></td>
                    </tr>
                    <tr>
                        <td>A101亮白馒头改良剂</td>
                        <td><input type="number"></td>
                    </tr>
                </table>
            </div>
            <div class="ddmidd zssma">
                <div class="dmidd zssm" contenteditable="true">
                    给客户发短信：
                </div>
                <div class="dmidd zssm zsx" contenteditable="true">
                    给经销商发短信：
                </div>
                <div class="d_bt">确认赠送并短信告知</div>
            </div>
        </div>
    </div>
</div>


<div class="caozuojilu" style="width: 470px; margin-top: 0; top: 10%; overflow: auto !important">
    <div class="dxbox">
        <div class="dtop">
            <p>经销商操作记录</p>
            <span></span>
        </div>
        <div style="width: 100%; padding: 25px; background: #fff;">
            <textarea id="contentJL" style="width: 100%; padding: 10px; border: 1px solid #ededed; min-height: 300px;" placeholder="请输入相关内容"></textarea>
            <button onclick="addRecord();" style="width: 100%; height: 44px; border: none; background: #41973c; color: #fff; font-size: 15px; margin-top: 25px;">确认</button>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#czjl").click(function () {
            $(".caozuojilu").slideDown();
            $(".heibg").fadeIn();
        })
        $(".caozuojilu .dtop span,.heibg").click(function () {
            $(".caozuojilu").slideUp();
            $(".heibg").fadeOut();
        })
    })
    function addRecord() {
        var content=$("#contentJL").val();
        if(!$.trim(content)){
            layer.msg("内容不能为空");
            return false;
        }
        var tel="${dealer.registerTel}";
        if(!$.trim(tel)){
            layer.msg("手机号不能为空");
            return false;
        }
        var index = layer.load();
        $.ajax({
            url:"<%=basePath%>dealerPc/addRecord.html",
            type:"post",
            dataType:"json",
            data:{content:content,tel:tel},
            success: function (data) {
                if (data.result) {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    },function () {
                        $(".caozuojilu").slideUp();
                        $(".heibg").fadeOut();
                        $("#contentJL").val("");
                        layer.close(index);
                    });
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
    
</script>

<script type="text/javascript">
    var gszsSms1='贵地${info.name} ${info.tel}咨询';
    var gszsSms2='，公司免费给他邮寄了产品试用，请联系客户如需要产品可去您处购买。';
    var gszsSmsk='免费试用产品已用快递寄出，请查收！${area.areaName}销售点：${dealer.postalAddress},${dealer.name}${dealer.registerTel}。';
    var jxszs1j='贵地${info.name}${info.tel}咨询';
    var jxszs2j='，我们告知客户您可免费提供1袋产品试用，请您联系客户。';
    var jxszs1k='已通知${area.areaName}经销商${dealer.name}${dealer.registerTel}给您免费提供';
    var jxszs2k='试用，如有问题请联系我们。';
    function zsjxOnclick(giveType) {
        if("2"==giveType || "3"==giveType){
            var id="${info.id}";
            if(!id){
                layer.msg("请先调单");
                return false;
            }

            var isGive=false;
            $.ajax({
                url:"<%=basePath%>givePc/queryIsGive.html",
                type:'post',
                dataType:'json',
                data:{orderId:id,isDealer:"y"},
                async:false,
                success:function (data) {
                    isGive=data.result;
                    if(!isGive){
                        layer.msg(data.msg);
                    }
                }
            });
            if(!isGive){
                return false;
            }
        }

        if(giveType=='2'){
            $("#jxsSms").val(gszsSms1+gszsSms2);
            $("#khSms").val(gszsSmsk);
            $("#zssm").hide();
            $("#dyTrue").hide();
            $("#khSms").show();
            $("#jxsSms").show();
            $("#giveTitle").text("公司赠送");
        }else if(giveType=='3'){
            $("#khSms").val(jxszs1k+jxszs2k);
            $("#jxsSms").val(jxszs1j+jxszs2j);
            $("#zssm").hide();
            $("#dyTrue").hide();
            $("#khSms").show();
            $("#jxsSms").show();
            $("#giveTitle").text("经销商赠送");
        }else if(giveType=='1'){
            $("#zssm").show();
            $("#dyTrue").show();
            $("#khSms").hide();
            $("#jxsSms").hide();
            $("#giveTitle").text("赠送给经销商");
        }
        $("#giveType").val(giveType);
        $("#giveId").val("");

        $("#productTr").html("");
        $("#productTr").html('<tr><th>产品货号</th><th>产品名称</th><th>操作</th></tr>');
        addTr();
        $(".heibg").fadeIn();
        $(".a1").slideDown();
    }

    function query(){
        queryData("#queryForm","#mainContent");
    }
    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            query();
        }
    });
    <%--function addTr() {--%>
        <%--var html= '';--%>
        <%--$.ajax({--%>
            <%--url:"<%=basePath%>dealerPc/queryProductType.html",--%>
            <%--type:'post',--%>
            <%--dataType:'json',--%>
            <%--success:function (data) {--%>
                <%--html += '<tr><td><select onchange="addOption(this);"><option value="">请选择分类</option>';--%>
                <%--for(var i=0;i<data.obj.length;i++){--%>
                    <%--html += '<option value="'+data.obj[i].id+'">'+data.obj[i].name+'</option>';--%>
                <%--}--%>
                <%--html += '</select></td><td><select class="productZS" onchange="checkONe(this);"><option value="">请选择产品</option></select></td><td><input class="productZSN" type="number" min="1" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\D/g,\'\')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\D/g,\'\')}"></td><td><a  href="javascript:void(0)" onclick="removeTr(this)">删除</a></td></tr>';--%>
                <%--$("#productTr").append(html);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    function removeTr(obj) {
        $(obj).parent().parent().remove();
        updateSms();
    }

    function addOption(obj) {
        var typeId=$(obj).val();
        if(typeId){
            $.ajax({
                url:"<%=basePath%>dealerPc/queryProduct.html",
                type:"post",
                dataType:"json",
                data:{typeId:typeId},
                success:function (data) {
                    var html= '<option value="">请选择产品</option>';
                    for(var i=0;i<data.obj.length;i++){
                        html += '<option value="'+data.obj[i].id+'" data-num="'+data.obj[i].productNum+'">'+data.obj[i].productName+'</option>';
                    }
                    $(obj).parent().next().children().html(html);
                }
            })
        }
    }
//
//    function checkONe(obj) {
//        var productId=$(obj).val();
//        $(obj).val("");
//        var isOne=false;
//        $(".productZS option:selected").each(function() {
//            if($(this).val()==productId){
//                isOne=true;
//                return false;
//            }
//        });
//        if(isOne){
//            layer.msg("不能选择相同的产品");
//            return false;
//        }else {
//            $(obj).val(productId);
//        }
//    }

    function saveGive() {
        var giveType=$("#giveType").val();
        if("1"==giveType){
            var isGive=false;
            $.ajax({
                url:"<%=basePath%>givePc/queryIsGiveDealer.html",
                type:'post',
                dataType:'json',
                data:{dealerId:"${dealer.id}"},
                async:false,
                success:function (data) {
                    isGive=data.result;
                }
            });
            if(!isGive){
                layer.msg("该经销商今日已经赠送");
                return false;
            }
        }

        var isStop="0";
        var productIds="";
        var giveContent="";
        $(".addTd").each(function() {
            if(!$(this).attr("data-id")){
                isStop="1";
                return false;
            }
            productIds += $(this).attr("data-id")+",";
            giveContent += $(this).attr("data-name")+";";
        });

        if(isStop=="1" || !productIds){
            layer.msg("请输入货号并选择");
            return false;
        }

        var productNums="";

//        if("1"==giveType){
//            $(".productZSN").each(function() {
//                if(!$(this).val()){
//                    isStop="2";
//                    return false;
//                }
//                productNums += $(this).val()+",";
//                giveContent += $(this).parent().prev().attr("data-name")+"&nbsp;"+$(this).val()+"箱;";
//            });
//        }else {
//            $(".productZSN").each(function() {
//                if(!$(this).val()){
//                    isStop="2";
//                    return false;
//                }
//                productNums += $(this).val()+",";
//                giveContent += $(this).parent().prev().attr("data-name")+"&nbsp;"+$(this).val()+"袋;";
//            });

            var khSms=$("#khSms").val();
//            if(giveType=='3'){
//                if(!$.trim(khSms)){
//                    layer.msg("请填写客户短信内容");
//                    return false;
//                }
//            }

            var jxsSms=$("#jxsSms").val();
//            if(!$.trim(jxsSms)){
//                layer.msg("请填写经销商短信内容");
//                return false;
//            }
//        }

//        if(isStop=="2"){
//            layer.msg("请填写数量");
//            return false;
//        }
        if(isStop=="0"){
            if("1"==giveType){
                var index = layer.load();
                $.ajax({
                    url:"<%=basePath%>givePc/saveGive.html",
                    type:"post",
                    dataType:"json",
                    data:{productIds:productIds,productNums:productNums,dealerId:"${dealer.id}",giveType:"1"
                        ,remark:$("#zssm").val(),giveContent:giveContent},
                    success: function (data) {
                        if (data.result) {
                            layer.msg(data.msg, {
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            },function () {
                                $("#giveId").val(data.obj);
                                layer.close(index);
                                query();
                            });
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
            }else {
                var index = layer.load();
                $.ajax({
                    url:"<%=basePath%>givePc/saveCompanyGive.html",
                    type:"post",
                    dataType:"json",
                    data:{productIds:productIds,productNums:productNums,dealerId:"${dealer.id}",orderId:"${info.id}"
                        ,giveType:$("#giveType").val(),giveContent:giveContent,khSms:khSms,jxsSms:jxsSms},
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


        }
    }

    function saveGSZS() {
        var productNums="";
        var isStop="0";
        var giveContent="";
        $(".productGSN").each(function() {
            if(!$(this).val()){
                isStop="2";
                return false;
            }
            productNums += $(this).val()+",";
            giveContent += $(this).parent().prev().text()+$(this).val()+"袋;";
        });
        if(isStop=="2"){
            layer.msg("请填写数量");
            return false;
        }

        var giveType=$("#giveType").val();
        var khSms=$("#khSms").val();
        if(giveType=='3'){
            if(!$.trim(khSms)){
                layer.msg("请填写客户短信内容");
                return false;
            }
        }

        var jxsSms=$("#jxsSms").val();
        if(!$.trim(jxsSms)){
            layer.msg("请填写经销商短信内容");
            return false;
        }

        if(isStop=="0"){

        }
    }

</script>
<script type="text/javascript">
    var trNum=1;
    function addTr() {
        var html= '';
        html += '<tr><td><input type="text"  id="auto'+trNum+'"></td>';
        html += '<td class="addTd" data-id=""></td>' ;
//        html += '<td><input class="productZSN" type="number" min="1"  ></td>';
        html += '<td><a  href="javascript:void(0)" onclick="removeTr(this)">删除</a></td></tr>';
        html += '<script type="text/javascript">';
        html += '$("#auto'+trNum+'").autocomplete({minLength: 0,';
        html += 'source: function(request,response){';
        html += '$.ajax({';
        html += 'url:"<%=basePath%>dealerPc/queryProductByNum.html",type:"post",dataType:"json",';
        html += 'data:{"productNum":$("#auto'+trNum+'").val()},';
        html += 'success:function(data){';
        html += 'response($.map(data.obj.slice(0, 100),function(item){';
        html += 'return{label:item.product_num,';
        html += 'value:item.id + ";" + item.product_name}}));}});},';
        html += 'focus:function(event,ui){return false;},';
        html += 'select:function(event,ui){';
        html += 'var isOne=false;';
        html += '$(".addTd").each(function () {';
        html += 'if($(this).attr("data-id")==ui.item.value.split(";")[0]){isOne=true;return false;}';
        html += '});';
        html += 'if(isOne){layer.msg("不能选择相同的产品");return false;}';
        html += 'else {$("#auto'+trNum+'").val(ui.item.label);';
        html += '$("#auto'+trNum+'").parent().next().text(ui.item.value.split(";")[1]);';
        html += '$("#auto'+trNum+'").parent().next().attr("data-id",ui.item.value.split(";")[0]);';
        html += '$("#auto'+trNum+'").parent().next().attr("data-name",ui.item.label+" "+ui.item.value.split(";")[1]);';
        html += 'updateSms();';
        html += '}';
        html += 'return false;}});';
        html += '<\/script>';
        $("#productTr").append(html);
        trNum += 1;
//        $(".productZSN").unbind();
//        $(".productZSN").bind("keyup",function () {
//          if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}
//        updateSms();
//        });
//        if(giveType=="2"){$("#jxsSms").val(gszsSms1+smsInfo+ui.item.label+ui.item.value.split(";")[1]+gszsSms2);}
//        else if(giveType=="3"){
//            $("#jxsSms").val(jxszs1k+smsInfo+ui.item.label+ui.item.value.split(";")[1]+jxszs2k);
//            $("#jxsSms").val(jxszs1j+smsInfo+ui.item.label+ui.item.value.split(";")[1]+jxszs2j);
//        }

    }

    function changeSort() {
        var sort=$("#sortNum").val();
        if(sort=='desc'){
            $("#sortNum").val("");
        }else {
            $("#sortNum").val("desc");
        }
        query();
    }
    
    function updateSms() {
        var giveType=$("#giveType").val();
        var sms="";
//        var smsNum="";
        if(giveType!=1){
            var len=$(".addTd").length;
            $(".addTd").each(function (index) {
                if($(this).attr("data-name")){
                    if(index==len-1){
                        sms += $(this).attr("data-name");
//                        smsNum += $(this).attr("data-name");
                    }else {
                        sms += $(this).attr("data-name")+";";
//                        smsNum += $(this).attr("data-name")+";";
                    }
                }
            });

            if(giveType=="2"){$("#jxsSms").val(gszsSms1+sms+gszsSms2);}
            else if(giveType=="3"){
                $("#jxsSms").val(jxszs1j+sms+jxszs2j);
                $("#khSms").val(jxszs1k+sms+jxszs2k)
            }
        }

    }
</script>

<div id="printStyle" style="display: none">

</div>

<script type="text/javascript">
    function printGoodsOrder(id) {
        $.ajax({
            url:"<%=basePath%>givePc/queryGivePrint.html",
            type:"post",
            data:{giveId:id},
            success: function (data) {
                $("#printStyle").html(data);
                var LODOP;LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));LODOP.PRINT_INITA(4, 0, 837, 878, "发货单");
                //设置页面，10mm 页面底部边距，3和CreateCustomPage高度为自适应
                LODOP.SET_PRINT_PAGESIZE(1, 800, 2100, "CreateCustomPage");
                LODOP.ADD_PRINT_HTM(0, 0, "68mm", "200mm",document.getElementById("printStyle").innerHTML);
                LODOP.SET_PRINT_STYLEA(0,"Horient",2);
                LODOP.SET_PRINT_STYLEA(0,"Vorient",2);
                LODOP.PREVIEW();
//                LODOP.PRINT_DESIGN();
            }
        });
    }

    function printTrue() {
        var giveId=$("#giveId").val();
        if(!giveId){
            layer.msg("保存成功后才能打印");
            return false;
        }
        printGoodsOrder(giveId);
        var layerIndex = layer.confirm('确认已打印？',{icon:3},function(index){
            $.ajax({
                type:"post",
                url:"<%=basePath%>givePc/surePrint.html",
                dataType:"json",
                async:false,
                data:{"id":giveId},
                success:function(data){
                    layer.close(layerIndex);
                },
                error:function(){
                    layer.alert("提示：系统内部出现问题！");
                }
            });
        });
    }
</script>
<script type="text/javascript">
    function showDate(){
        var mydate = new Date();
        var str = mydate.getHours()+":"+mydate.getMinutes();
        return str;
    }
</script>
</body>
</html>
