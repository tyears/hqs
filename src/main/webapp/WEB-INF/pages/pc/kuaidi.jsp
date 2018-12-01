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
    <link rel="stylesheet" type="text/css" href="js/jquery-ui-1.12.1/jquery-ui.min.css" />
    <script type="text/javascript" src="js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="js/laydate/laydate.js"></script>
    <script language="javascript" src="js/lodop/LodopFuncs.js"></script>
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
<body onkeydown="keyOnClick(event);">

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>快递信息告知</b></div><h6>
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
                    <dd style="width:70%;">
                        <input id="luru" style="float:left; cursor:pointer; background-color:#41973c; border:none; color:#fff; width:104px; padding:0" type="button" value="快递单号录入">
                        <form id="queryForm" action="givePc/toExpressInfo.html" method="post">
                            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
                            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
                            <input type="hidden"  name="giveTypeKD" value="${params.giveTypeKD}" id="giveTypeKD"/>
                            <input style="width:150px;" type="text" name="keyTel" placeholder="手机号码" value="${params.keyTel}" id="keyTel">
                            <input style="width:150px;" type="text" name="goodsNumForSearch" placeholder="快递单号" value="${params.goodsNumForSearch}" id="goodsNumForSearch">
                            <input style="width:150px;" type="text" name="createTimeStart" placeholder="开始时间" value="${params.createTimeStart}" id="start">
                            <input style="width:150px;" type="text" name="createTimeEnd" value="${params.createTimeEnd}" id="end" placeholder="结束时间">

                            <c:if test="${params.giveTypeKD=='1'}">
                                <select style="width:100px;" name="reserved1" id="reserved1">
                                    <option ${empty params.reserved1?'selected':''} value="">全部</option>
                                    <option ${params.reserved1=='y'?'selected':''} value="y">已打印</option>
                                    <option ${params.reserved1=='n'?'selected':''} value="n">未打印</option>
                                </select>
                            </c:if>
                            <button type="button" onclick="queryForm()" class="chaxun">查询</button>
                            <button class="chongzhi" type="button" onclick="resetKey();">重置</button>
                        </form>
                        <p><a><i>${pageInfo.total}</i>条信息</a></p>
                    </dd>
                    <dt class="abtn">
                        <a href="javascript:" ${params.giveTypeKD==4?'class="on"':''} data-importtype="4">宣传给客户</a>
                        <a href="javascript:" ${params.giveTypeKD==1?'class="on"':''} data-importtype="1">宣传给经销商</a>
                    </dt>
                </dl>
                <div class="bigbiaoge kd">
                    <table class="hov_mou">
                        <tr>
                            <th>序号</th>
                            <th>宣传编号</th>
                            <th>日期</th>
                            <th>手机号码</th>
                            <th>姓名</th>
                            <th>地址</th>
                            <th>宣传产品</th>
                            <th>操作员</th>
                            <th>快递单号</th>
                            <c:if test="${params.giveTypeKD=='1'}">
                            <th>状态</th>
                            <th>操作</th>
                            </c:if>
                            <th><label class="checkbox_all"><span>全选</span><input type="checkbox"></label></th>
                        </tr>
                        <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                            <tr>
                                <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                <td>${data.only_num}</td>
                                <td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd"/></td>
                                <td>${data.give_type=='1'?data.register_tel:data.tel}</td>
                                <td contenteditable="true" oninput="updateInfo('${data.give_type}',this,'y')" data-oid="${data.oid}" data-did="${data.did}">${data.give_type=='1'?data.dname:data.name}</td>
                                <td style="text-align:left;" contenteditable="true" oninput="updateInfo('${data.give_type}',this,'n')" data-oid="${data.oid}" data-did="${data.did}">${data.give_type=='1'?data.delivery_address:data.address}</td>
                                <td id="td${data.id}" class="zs_btn" style="text-align:left; cursor:pointer;" onclick="showZSCP('${data.id}','${data.dealer_id}','${data.give_type}');">${data.give_content}</td>
                                <td>${data.give_man_name}</td>
                                <td style=" color:#f00;" id="num${data.id}">${data.goods_num}</td>
                                <%--<td>--%>
                                    <%--<c:if test="${data.is_notice==1}">已通知</c:if>--%>
                                    <%--<c:if test="${data.is_notice==0}"><button class="shenhe sh03" data-only="${data.only_num}" data-num="${data.goods_num}" data-giveid="${data.id}">发送通知</button></c:if>--%>
                                <%--</td>--%>
                                <c:if test="${params.giveTypeKD=='1'}">
                                    <td id="p${data.id}">${data.reserved1=='y'?'已打印':'未打印'}</td>
                                    <td><button onclick="printGoodsOrder('${data.id}');" style="border: none;background-color: #41973c;color: #fff;padding: 3px 5px;border-radius: 3px;margin: 0 2px;cursor: pointer;">打印</button></td>
                                </c:if>
                                <td><input class="checkbox_input" value="${data.id}" type="checkbox" name="giveIds"></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="caozuo_box">
                        <c:if test="${pageInfo.list.size()!=0}">
                            <input type="button" value="下载全部" onclick="importExcel('')">
                            <input type="button" value="下载无快递单号" onclick="importExcel('2')">
                            <input type="button" onclick="deleteIds()" value="删除">
                        </c:if>
                    </div>
                    <div class="fenye" id="pageDiv"></div>
                    <c:if test="${pageInfo.list.size()==0}">
                        <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.kuaidi}</div>
    </div>
</div>
<!--弹窗-->
<div class="heibg"></div>
<div class="popup popup02 po01">
    <h2><span>发送通知</span><i>×</i></h2>
    <form>
        <dl>
            <dd><textarea rows="3" placeholder="">您的免费试用产品快递单号:申通 海韦力官网 http://m.haiweili.com</textarea></dd>
            <dd class="submit"><input type="button" value="发送通知"></dd>
        </dl>
    </form>
</div>



<div class="popup popup02 po02">
    <h2><span>快递单号录入</span><i>×</i></h2>
    <input type="hidden" id="giveId"/>
        <dl>
            <dd class="on_bd"><input id="numFront" oninput="queryGive();"><input id="numBehind"  oninput="queryGive();"></dd>
            <dd><input value="" id="goodsNum"></dd>
            <dd><textarea rows="3" placeholder="" id="smsContent"></textarea></dd>
            <dd class="submit"><input type="button" value="发送通知" onclick="saveGoodsNum();"></dd>
        </dl>
</div>
<div class="duanxintanchuang tztanchuang a1" style="width: 470px">
    <input type="hidden" id="giveId2"/>
    <input type="hidden" id="dealerId"/>
    <input type="hidden" id="giveType"/>
    <div class="dxbox">
        <div class="dtop">
            <p>产品修改</p>
            <span></span>
        </div>
        <div style="max-height:480px; overflow-y:auto">
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
                <div onclick="saveGive();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;cursor: pointer;font-size: 15px;font-weight: bold;">确认</div>
            </div>
        </div>
    </div>
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

    laydate.render({
        elem: '#start' //指定元素
    });
    laydate.render({
        elem: '#end' //指定元素
    });

    function queryForm() {
        $("#queryForm").submit();
    }

    function queryPage(pn) {
        $("#pageSize").val(pn);
        queryForm();
    }

    function resetKey() {
        $("#keyTel").val('');
        $("#start").val('');
        $("#end").val('');
        $("#reserved1").val('');
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
            var onlyNum=$(this).attr("data-only");
            $("#giveId").val($(this).attr("data-giveid"));
            $("#numFront").val(onlyNum.substring(0,8));
            $("#numBehind").val(onlyNum.substring(8));
            $("#goodsNum").val($(this).attr("data-num"));
            $("#smsContent").val("您的免费试用产品快递单号:申通"+$(this).attr("data-num")+" 海韦力官网 http://m.haiweili.com");
            $(".heibg").fadeIn();
            $(".popup02").slideDown();
        })
        $(".heibg,.popup >h2 i").click(function(){
            $(".popup02,.heibg").fadeOut();
        })
    })

    $(function(){
        $("#luru").click(function(){
            $("#numFront").val(showDate());
            $("#giveId").val("");
            $("#numBehind").val("");
            $("#goodsNum").val("");
            $("#smsContent").val("您的免费试用产品快递单号:申通 海韦力官网 http://m.haiweili.com");
            $(".heibg").fadeIn();
            $(".po02").slideDown();
            $("#numBehind").focus();
        })
    });
    
    function queryGive() {
        var onlyNum=$("#numFront").val()+$("#numBehind").val();
       if($.trim(onlyNum)){
            $.ajax({
                url:"<%=basePath%>givePc/queryGive.html",
                type:'post',
                dataType:'json',
                data:{onlyNum:onlyNum,giveTypeKD:"${params.giveTypeKD}"},
                async:true,
                success:function (data) {
                    if(data.result){
                        var goodsNum="";
                        var giveId="";
                        if(data.obj){
                            giveId=data.obj.id;
                            if(data.obj.goodsNum){
                                goodsNum=data.obj.goodsNum;
                            }
                        }
                        $("#giveId").val(giveId);
                        $("#goodsNum").val(goodsNum);
                        $("#smsContent").val("您的免费试用产品快递单号:申通 "+goodsNum+" 海韦力官网 http://m.haiweili.com");
                    }
                }
            });
        }
    }
    
    function importExcel(num) {
        window.location="<%=basePath%>givePc/expressInfoExcel.html?numKd="+num+"&giveTypeKD=${params.giveTypeKD}";
    }

    //回车事件
    function keyOnClick(e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {  //回车键的键值为13
            if("block" == $(".po02").css("display")){
                saveGoodsNum();  //调用通知方法
            }
        }
    }

    function saveGoodsNum(){
        var giveId=$("#giveId").val();
        if(!$.trim(giveId)){
            layer.msg("请先输入编号查单");
            return false;
        }

        var goodsNum=$("#goodsNum").val();
        if(!$.trim(goodsNum)){
            layer.msg("请输入快递单号");
            return false;
        }

        var smsContent=$("#smsContent").val();
        if(!$.trim(smsContent)){
            layer.msg("请输入短信内容");
            return false;
        }

        var index = layer.load();
        $.ajax({
            url:"<%=basePath%>givePc/updateGoodsNum.html",
            type:"post",
            dataType:"json",
            data:{giveId:giveId,goodsNum:goodsNum,smsContent:smsContent},
            success: function (data) {
                if (data.result) {
                    $("#num"+giveId).text(goodsNum);
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    },function () {
                        $("#giveId").val("");
                        $("#numBehind").val("");
                        $("#goodsNum").val("");
                        $("#smsContent").val("您的免费试用产品快递单号:申通 海韦力官网 http://m.haiweili.com");
                        $("#numBehind").focus();
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

        function showDate(){
            var now = new Date();

            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日

            var clock = year ;

            if(month < 10)
                clock += "0";

            clock += String(month) ;

            if(day < 10)
                clock += "0";

            clock += String(day);
            return clock;
        }

        $("#goodsNum").keyup(function () {
            $("#smsContent").val("您的免费试用产品快递单号:申通"+$(this).val()+" 海韦力官网 http://m.haiweili.com");
        });

    function updateInfo(type,obj,isName) {
        var id="";
        if(type=='1'){
            id=$(obj).attr("data-did");
        }else {
            id=$(obj).attr("data-oid");
        }
        var text = $(obj).text();
        text = $.trim(text);
        $.post("<%=basePath%>givePc/updateInfo.html",{id:id,text:text,giveType:type,isName:isName})
    }
    var trNum=1;
    function addTr() {
        var dealerId=$("#dealerId").val();
        if(!dealerId && $(".addTd").length>2){
            layer.msg("H0000的公司宣传最多为三个品种");
            return false;
        }
        var html= '';
        html += '<tr><td><input type="text"  id="auto'+trNum+'"></td>';
        html += '<td class="addTd" data-id=""></td>';
        html +='<td><a  href="javascript:void(0)" onclick="removeTr(this)">删除</a></td></tr>';
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
        html += '$("#auto'+trNum+'").parent().next().attr("data-name",ui.item.label+" "+ui.item.value.split(";")[1]);}';
        html += 'return false;}});';
        html += '<\/script>';
        $("#productTr").append(html);
        trNum += 1;
    }
    /*产品宣传添加*/
    function showZSCP(giveId,dealerId,giveType){
        $.ajax({
            url:"<%=basePath%>givePc/queryGiveProduct.html",
            type:"post",
            dataType:"json",
            data:{giveId:giveId},
            success:function (data) {
                var html= '<tr><th>产品货号</th><th>产品名称</th><th>操作</th></tr>';
                var giveList=data.obj;
                for(var i=0;i<giveList.length;i++){
                    html += '<tr><td><input type="text"  id="auto'+trNum+'" value="'+giveList[i].productNum+'"></td>';
                    html += '<td class="addTd" data-id="'+giveList[i].id+'" data-name="'+giveList[i].productNum+' '+giveList[i].productName+'">'+giveList[i].productName+'</td>' ;
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
                    html += '$("#auto'+trNum+'").parent().next().attr("data-name",ui.item.label+" "+ui.item.value.split(";")[1]);}';
                    html += 'return false;}});';
                    html += '<\/script>';
                    trNum +=1
                }
                $("#productTr").html(html);
            }
        });
        $("#giveId2").val(giveId);
        $("#dealerId").val(dealerId);
        $("#giveType").val(giveType);
        $(".heibg").fadeIn();
        $(".a1").slideDown();
    }

    function removeTr(obj) {
        $(obj).parent().parent().remove();
    }

    function saveGive() {
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

        if(isStop=="0"){
            var index = layer.load();
            $.ajax({
                url:"<%=basePath%>givePc/updateGiveExamine.html",
                type:"post",
                dataType:"json",
                data:{productIds:productIds,dealerId:$("#dealerId").val()
                    ,giveContent:giveContent,id:$("#giveId2").val(),giveType:$("#giveType").val()},
                success: function (data) {
                    if (data.result) {
                        layer.msg(data.msg, {
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        });
                        $("#td"+$("#giveId2").val()).html(giveContent);
                        $(".heibg").fadeOut();
                        $(".a1").slideUp();
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

    $(function () {
        $(".abtn a").click(function () {
            $(".abtn a").removeClass("on");
            $(this).addClass("on");
            var importType=$(this).attr("data-importtype");
            $("#giveTypeKD").val(importType);
            queryForm();
        })
    });
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
                var LODOP;
                LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
                LODOP.PRINT_INITA(4, 0, 837, 878, "发货单");
                //设置页面，10mm 页面底部边距，3和CreateCustomPage高度为自适应
                LODOP.SET_PRINT_PAGESIZE(1, 800, 2100, "CreateCustomPage");
                LODOP.ADD_PRINT_HTM(0, 0, "68mm", "200mm", document.getElementById("printStyle").innerHTML);
                LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
                LODOP.SET_PRINT_STYLEA(0, "Vorient", 2);
                LODOP.PREVIEW();
//                LODOP.PRINT_DESIGN();
            }
        });

        var layerIndex = layer.confirm('确认已打印？',{icon:3},function(index){
            $.ajax({
                type:"post",
                url:"<%=basePath%>givePc/surePrint.html",
                dataType:"json",
                async:false,
                data:{"id":id},
                success:function(data){
                    layer.close(layerIndex);
                    if(data.result){
                        $("#p"+id).text("已打印");
                    }
                },
                error:function(){
                    layer.alert("提示：系统内部出现问题！");
                }
            });
        });
    }
</script>
</body>
</html>
