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
            max-height: 222px;
            overflow-y: hidden;
            /* 防止水平滚动条 */
            overflow-x: hidden;
            z-index: 999;
        }
    </style>
</head>
<body>

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>经销商赠送审查</b></div><h6>
        <form id="queryForm" action="givePc/toGiveExamine.html" method="post">
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
                            <p><a><i>${pageInfo.total}</i>条信息</a></p>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge zssh">
                    <table class="hov_mou">
                        <tr>
                            <th>序号</th>
                            <th>日期</th>
                            <th>单位市场</th>
                            <th>经销商编号</th>
                            <th>手机号码</th>
                            <th>姓名</th>
                            <th>名称</th>
                            <th>赠送产品内容</th>
                            <th>赠送说明</th>
                            <th>总评价</th>
                            <th>合作情况</th>
                            <th>操作员</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                        <tr>
                            <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                            <td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd"/> </td>
                            <td>${data.area_name}</td>
                            <td>${data.dealer_num}</td>
                            <td>${data.tel}</td>
                            <td>${data.name}</td>
                            <td>${data.delivery_address}</td>
                            <c:choose>
                                <c:when test="${data.state==1}">
                                    <td id="td${data.id}" class="zs_btn" style="text-align:left; cursor:pointer;" onclick="showZSCP('${data.id}','${data.dealer_id}');">${data.give_content}</td>
                                </c:when>
                                <c:otherwise>
                                    <td id="td${data.id}" class="zs_btn" style="text-align:left; cursor:pointer;">${data.give_content}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${data.remark}</td>
                            <td>${data.overall_merit}</td>
                            <td>${data.cooperation_state}</td>
                            <td>${data.give_man_name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${data.state==1}">
                                        <button class="shenhe sh01" onclick="passBtn('${data.id}')">通过</button><button class="shenhe sh02" onclick="updateStateNo('${data.id}')">驳回</button>
                                    </c:when>
                                    <c:otherwise>
                                        ${data.state==2?'已通过':'已驳回'}
                                    </c:otherwise>
                                </c:choose>
                            </td>
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
        <div class="beizhu_box">${sessionScope.remarks.zengsong}</div>
    </div>
</div>
<!--弹窗---->
<div class="heibg"></div>
<div class="popup popup02">
    <h2><span>打印通知单</span><i>×</i></h2>
    <form>
        <dl>
            <dd><input type="text" placeholder="接单人（举例：部门-姓名）"></dd>
            <dd><textarea rows="3" placeholder="内容"></textarea></dd>
            <dd><label><input type="checkbox"><span>是否加急</span></label></dd>
            <dd class="submit"><input type="button" value="打印"></dd>
        </dl>
    </form>
</div>

<div class="duanxintanchuang tztanchuang a3">
    <input type="hidden" id="giveIdState"/>
    <div class="dxbox">
        <div class="dtop">
            <p>给经销商发短信</p>
            <span></span>
        </div>
        <div class="ddmidd zssma zssma1">
            <div class="dmidd zssm" contenteditable="true" id="smsContent"></div>
            <div style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;margin-top: 20px;cursor: pointer;font-size: 15px;font-weight: bold;" onclick="updateState();">确认</div>
        </div>
    </div>
</div>

<div class="duanxintanchuang tztanchuang a1" style="width: 470px">
    <input type="hidden" id="giveId"/>
    <input type="hidden" id="dealerId"/>
    <div class="dxbox">
        <div class="dtop">
            <p>赠送给经销商</p>
            <span></span>
        </div>
        <div style="max-height:480px; overflow-y:auto">
            <div class="biao">
                <table class="append_t" id="productTr">
                    <tr>
                        <th>产品货号</th>
                        <th>产品名称</th>
                        <%--<th>数量</th>--%>
                        <th>操作</th>
                    </tr>
                </table>
                <h3 class="tianjia" onclick="addTr();">添加一个产品</h3>
            </div>
            <div class="ddmidd zssma">
                <div onclick="saveGive();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;cursor: pointer;font-size: 15px;font-weight: bold;">确认赠送并提交</div>
            </div>
        </div>
    </div>
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
            $(".heibg").fadeIn();
            $(".popup02").slideDown();
        })
        $(".heibg,.popup >h2 i").click(function(){
            $(".popup02,.heibg").fadeOut();
        })
    })

    function passBtn(giveId){
        $("#giveIdState").val(giveId);
        $(".heibg").fadeIn();
        $(".a3").slideDown();
    }

    $(function(){
        $(".heibg,.popup >h2 i").click(function(){
            $(".tztanchuang,.heibg").fadeOut();
        })
    });



    $(function(){
        $(".heibg,.popup >h2 i").click(function(){
            $(".popup02,.heibg").fadeOut();
        })
    });

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

        var productNums="";
//        $(".productZSN").each(function() {
//            if(!$(this).val()){
//                isStop="2";
//                return false;
//            }
//            productNums += $(this).val()+",";
//            giveContent += $(this).parent().prev().attr("data-name")+$(this).val()+"箱;";
//        });
//        if(isStop=="2"){
//            layer.msg("请填写数量");
//            return false;
//        }
        if(isStop=="0"){
            var index = layer.load();
            $.ajax({
                url:"<%=basePath%>givePc/updateGiveExamine.html",
                type:"post",
                dataType:"json",
                data:{productIds:productIds,productNums:productNums,giveType:"1",dealerId:$("#dealerId").val()
                    ,giveContent:giveContent,id:$("#giveId").val()},
                success: function (data) {
                    if (data.result) {
                        layer.msg(data.msg, {
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        });
                        $("#td"+$("#giveId").val()).html(giveContent);
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

    function updateStateNo(giveId) {
        var layerIndex = layer.confirm('确认要驳回吗？',{icon:3},function(index){
            $.ajax({
                type:"post",
                url:"<%=basePath%>givePc/updateGiveState.html",
                dataType:"json",
                async:false,
                data:{giveId:giveId,state:"3"},
                success:function(data){
                    layer.close(layerIndex);
                    if(data.result){
                        layer.msg(data.msg,{time:1000},function(){
                            queryForm();
                        });
                    }else{
                        layer.msg(data.msg, {time:1000});
                        return;
                    }
                },
                error:function(){
                    layer.alert("提示：系统内部出现问题！");
                }
            });
        });
    }

    function updateState() {
        var smsContent=$("#smsContent").text();
        if(!$.trim(smsContent)){
            layer.msg("请填写短信内容");
            return false;
        }

        var index = layer.load();
        $.ajax({
            url:"<%=basePath%>givePc/updateGiveState.html",
            type:"post",
            dataType:"json",
            data:{giveId:$("#giveIdState").val(),smsContent:smsContent,state:"2"},
            success: function (data) {
                if (data.result) {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    },function(){
                        queryForm();
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
    var trNum=1;
    addTr();
    function addTr() {
        var html= '';
        html += '<tr><td><input type="text"  id="auto'+trNum+'"></td>';
        html += '<td class="addTd" data-id=""></td>';
//            '<td><input class="productZSN" type="number" min="1" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\D/g,\'\')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\D/g,\'\')}"></td>' +
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
        html += '$("#auto'+trNum+'").parent().next().attr("data-name",ui.item.label+"&nbsp;"+ui.item.value.split(";")[1]);}';
        html += 'return false;}});';
        html += '<\/script>';
        $("#productTr").append(html);
        trNum += 1;
    }

    /*产品赠送添加*/
    function showZSCP(giveId,dealerId){
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
                    html += '<td class="addTd" data-id="'+giveList[i].id+'" data-name="'+giveList[i].productNum+'&nbsp;'+giveList[i].productName+'">'+giveList[i].productName+'</td>' ;
//                    html += '<td><input class="productZSN" value="'+giveList[i].giveNum+'" type="number" min="1" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\D/g,\'\')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,\'\')}else{this.value=this.value.replace(/\D/g,\'\')}"></td>';
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
                    html += '$("#auto'+trNum+'").parent().next().attr("data-name",ui.item.label+"&nbsp;"+ui.item.value.split(";")[1]);}';
                    html += 'return false;}});';
                    html += '<\/script>';
                    trNum +=1
                }
                $("#productTr").html(html);
            }
        });
        $("#giveId").val(giveId);
        $("#dealerId").val(dealerId);
        $(".heibg").fadeIn();
        $(".a1").slideDown();
    }
</script>
</body>
</html>
