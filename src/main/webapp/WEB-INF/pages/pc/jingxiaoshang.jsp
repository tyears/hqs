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
    <link href="js/laydate/theme/default/laydate.css" rel="stylesheet" type="text/css">
    <link href="js/jedate/jedate.css" rel="stylesheet" type="text/css">
    <script src="js/layer.js"></script>
    <script src="js/laydate/laydate.js"></script>
    <script src="js/jedate/jquery.jedate.js"></script>
    <link rel="stylesheet" type="text/css" href="js/jquery-ui-1.12.1/jquery-ui.min.css" />
    <script type="text/javascript" src="js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <style>
        .ui-autocomplete {
            max-height: 260px;
            max-width: 260px;
            overflow-y: auto;
            /* 防止水平滚动条 */
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<jsp:useBean id="now" class="java.util.Date" scope="page"></jsp:useBean>
<c:choose>
    <c:when test="${not empty area.id}">
        <div class="dongdaibox">
            <div class="biaoti bt_box clearfix">
                <div><span></span><b>${area.areaName}&nbsp;&nbsp;经销商选择&nbsp;&nbsp;&nbsp;单位市场总评价：<em
                        style="color:#41973c; font-style:normal">${area.comment}</em></b></div>
                <h5 class="">
                    <input class="in_btn" placeholder="请输入单位市场" value="${params.areaAllName}" id="areaName">
                    <%--<div class="shengshiqu" id="areaList">--%>
                    <%--</div>--%>
                </h5>
                </h6>
            </div>
            <div class="dongtai">
                <div class="dbox">
                    <div class="left dbox_none">
                        <dl>
                            <dd>
                                <form id="queryForm" action="dealerPc/toDealerChoose.html" method="post">
                                    <input type="hidden" name="areaId" value="${params.areaId}" id="areaId">
                                    <input type="hidden" name="dealerId" value="${params.dealerId}" id="dealerId">
                                    <input type="hidden" name="areaAllName" id="areaAllName" value="${params.areaAllName}">
                                    <input type="hidden" name="sortNum" value="${params.sortNum}" id="sortNum">
                                    <input placeholder="请输入货号或品名" name="keyword" value="${params.keyword}" id="key_word">
                                    <button class="chaxun" onclick="queryForm();">查询</button>
                                    <button class="chongzhi" type="button" onclick="$('#key_word').val('')">重置</button>
                                </form>
                            </dd>
                        </dl>
                        <div class="bigbiaoge danwei jxs">
                            <div id="big_box">
                                <div id="leftBox">
                                    <div class="leftTop_box">
                                        <div class="thTop1">
                                            <table>
                                                <tr>
                                                    <th class="new_wi01" onclick="changeSort();">货号</th>
                                                    <th class="new_wi02">品名</th>
                                                    <th class="new_wi03">授权</th>
                                                    <th class="new_wi04">市场评价</th>
                                                    <th class="new_wi05">公司通知</th>
                                                    <th class="new_wi06">经销商宣传</th>
                                                    <th class="new_wi07">有效时间</th>
                                                </tr>
                                            </table>
                                        </div>
                                        <div class="thMiddle1" onScroll="moveUp_Left();" >
                                            <table>
                                                <c:forEach items="${productList}" var="product" varStatus="vs">
                                                    <tr>
                                                        <td class="new_wi01 num_font">${product.productNum}</td>
                                                        <td class="new_wi02">${product.productName}</td>
                                                        <td class="new_wi03">${product.author_dealer}</td>
                                                        <td class="new_wi04">${product.comment}</td>
                                                        <td class="new_wi05" id="gs${product.productId}" onClick="updateNotice('${product.id}','${product.productId}')">${not empty product.id?product.notice_dealer1:'H0000'}</td>
                                                        <td class="new_wi06" id="jss${product.productId}">${product.give_dealer}</td>
                                                        <td class="new_wi07">
                                                            <input readonly style="text-align:center;border-style:none; ${product.effect_time.getTime() - now.getTime() - 15*24*60*60*1000 < 0 ? 'color:red;' : ''}" type="text" id="${product.productId}" apid="${product.id}"  value='<fmt:formatDate value="${product.effect_time}" pattern="yyyy-MM-dd"/>'>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="leftBottom_box">
                                        <table>
                                            <tr>
                                                <td class="new_wi01"></td>
                                                <td class="new_wi02"></td>
                                                <td class="new_wi03"></td>
                                                <td class="new_wi04"></td>
                                                <td class="new_wi05"></td>
                                                <td class="new_wi06"></td>
                                                <td class="new_wi07">总评价</td>
                                            </tr>
                                            <tr>
                                                <td class="new_wi01 ovXzhi"></td>
                                                <td class="new_wi02 ovXzhi"></td>
                                                <td class="new_wi03 ovXzhi"></td>
                                                <td class="new_wi04 ovXzhi"></td>
                                                <td class="new_wi05 ovXzhi"></td>
                                                <td class="new_wi06 ovXzhi"></td>
                                                <td class="new_wi07 ovXzhi ovHe">合作情况</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div id="rightBox">
                                    <div class="rightLittle_box">
                                        <div class="rightTop_box">
                                            <div class="thTop2">
                                                <table>
                                                    <tr>
                                                        <c:forEach items="${dealerList}" var="dealer">
                                                            <th  class="new_wi08" onclick="dealerNumOnclick('${dealer.id}')"><p style="line-height: 21px;overflow: hidden;  white-space: nowrap;">${dealer.dealerNum}<br>${dealer.name}</p></th>
                                                        </c:forEach>
                                                        <c:set var="setNum" value="${14-dealerList.size()}"/>
                                                        <c:if test="${setNum>0}">
                                                            <c:forEach begin="1" end="${setNum}" >
                                                                <th class="new_wi08"></th>
                                                            </c:forEach>
                                                        </c:if>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="thMiddle2" onScroll="moveUp_Right();">
                                                <table>
                                                    <c:forEach items="${productList}" var="product" varStatus="vs">
                                                        <tr>
                                                            <c:forEach items="${dealerList}" var="dealer">
                                                                <td class="new_wi08 dw8" jss="${product.give_dealer}" gs="${product.notice_dealer}" apid="${product.id}" productId="${product.productId}"
                                                                    jssid="${dealer.id}">
                                                                    <c:forEach items="${dealer.dpList}" var="dp">
                                                                        <c:if test="${dp.productId==product.productId}">${dp.comment}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                            </c:forEach>
                                                            <c:if test="${setNum>0}">
                                                                <c:forEach begin="1" end="${setNum}" >
                                                                    <td class="new_wi08"></td>
                                                                </c:forEach>
                                                            </c:if>
                                                        </tr>
                                                    </c:forEach>

                                                </table>
                                            </div>
                                        </div>
                                        <div class="rightBottom_box">
                                            <table>
                                                <tr>
                                                    <c:forEach items="${dealerList}" var="dealer">
                                                        <td class="new_wi08 "  style="width:64px;word-break: break-all;cursor: pointer;">
                                                                ${dealer.overallMerit}
                                                        </td>
                                                    </c:forEach>
                                                    <c:if test="${setNum>0}">
                                                        <c:forEach begin="1" end="${setNum}" >
                                                            <td class="new_wi08" style="width:64px;word-break: break-all;cursor: pointer;"></td>
                                                        </c:forEach>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <c:forEach items="${dealerList}" var="dealer">
                                                        <td class="new_wi08 ovXzhi"  style="width:64px;word-break: break-all;cursor: pointer;">
                                                            <div ${sessionScope.userType==1?'contenteditable="true"':''} oninput="pingtext('${dealer.id}','1',this)"
                                                                                                                         title="${dealer.cooperationState}">${dealer.cooperationState}</div>
                                                        </td>
                                                    </c:forEach>
                                                    <c:if test="${setNum>0}">
                                                        <c:forEach begin="1" end="${setNum}" >
                                                            <td class="new_wi08 ovXzhi" style="width:64px;word-break: break-all;cursor: pointer;"></td>
                                                        </c:forEach>
                                                    </c:if>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="shic" id="dealerContent"></div>
                    <input type="hidden"  id="sortNumOne">
                    <input type="hidden"  id="delerId" value="${params.dealerId}">
                </div>
                <div class="beizhu_box">${sessionScope.remarks.jingxiaoshangxuanze}</div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    function queryForm() {
        $("#queryForm").submit();
    }
    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            queryForm();
        }
    });
    function queryDealer(id) {
        $("#dealerId").val(id);
        queryForm();
    }
</script>
<!--弹窗-->
<div class="heibg"></div>
<div class="duanxintanchuang">
    <div class="dxbox">
        <div class="dtop">
            <p>发短信</p>
            <span></span>
        </div>
        <div class="ddmidd">
            <div class="dmidd">
                <p>海韦力南阳经销商联系方式：</p>
                <textarea>李先锋经理  13633865525 （白一路）；
李天增经理 13633865525 （体育城北800米）；
马主任 13933865525（解放路集贸市场）；
郭士军 13633865525；（东风路同乐小区）。</textarea>
            </div>
            <div class="d_bt">确认</div>
        </div>
    </div>
</div>

<div class="duanxintanchuang a88">
    <div class="dxbox">
        <div class="dtop">
            <p>请选择</p>
            <span></span>
        </div>
        <div class="ddmidd">
            <div class="dmidd a88_bt">
                <input type="hidden" id="guanlianid" value="">
                <input type="hidden" id="productId" value="">
                <input type="hidden" id="jingxiaoshangid" value="">
                <p id="gs">公司</p>
                <p id="jss">经销商</p>
            </div>
            <div style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px; color: #fff;margin-top: 20px;cursor: pointer;font-size: 15px;font-weight: bold;"
                 onclick="sub()">确认
            </div>
        </div>
    </div>
</div>
<script src="js/base.js"></script>
<script>
    /*查询*/
    queryDealerData();
        function queryDealerData(){
            var dealerId=$("#dealerId").val();
            if(!dealerId){
                dealerId="${params.dealerId}";
            }
            $.ajax({
                type:"post",
                url:"<%=basePath%>dealerPc/toDealerChooseData.html",
                data:{dealerId:dealerId,sortNum:$("#sortNumOne").val()},
                success:function(data){
                    $("#dealerContent").empty();
                    $("#dealerContent").html(data);
                }
            });
        }

    function dealerNumOnclick(id) {
        $("#dealerId").val(id);
        queryDealerData();
    }

    $(function () {
        $("body").click(function () {
            $(".shengshiqu").hide();
        });

        var productListJson = JSON.parse('${productListJson}');
        for(var i=0;i<productListJson.length;i++){
            $("#"+productListJson[i].productId).jeDate({
                isClear:true,
                isToday:false,
                isok:false,
                format: "YYYY-MM-DD",
                clearfun:function(obj) {
                    var apid = $(obj.elem).attr("apid");
                    var productId = $(obj.elem).attr("id");
                    var time = obj.val;
                    $.post("dealerPc/updateAPtime.html",{apid:apid,time:time,areaId:${params.areaId},productId:productId},function(data){
                        if(data.result){
                            layer.msg(data.msg,{time:1000});
                        }else{
                            layer.msg(data.msg,{time:1000});
                            return;
                        }
                    },"json")
                },
                okfun:function(obj) {
                //这里传中间表id和时间
                    var apid = $(obj.elem).attr("apid");
                    var productId = $(obj.elem).attr("id");
                    var time = obj.val;
                    $.post("dealerPc/updateAPtime.html",{apid:apid,time:time,areaId:${params.areaId},productId:productId},function(data){
                        if(data.result){
                            layer.msg(data.msg,{time:1000});
                        }else{
                            layer.msg(data.msg,{time:1000});
                            return;
                        }
                    },"json")
                }//点击确定后的回调,obj包含{ elem当前输入框ID, val当前选择的值, date当前的日期值}
            })
        }

        /*	$("#city .ssq").click(function(){
         $("#dis").show();
         return false;
         })*/

        //文本框输入
//        $(".bt_box>h5 .in_btn").keyup(function () {
//            $(".shengshiqu").show();	//只要输入就显示列表框
//            queryAllArea();
//            if ($(".bt_box>h5 .in_btn").val().length <= 0) {
//                $(".shengshiqu p").show();//如果什么都没填，跳出，保持全部显示状态
//                return;
//            }
//        });

        $(".dongdaibox .dongtai .dbox .danwei.jxs>div table tr .dw8").click(function () {
            var gs = $(this).attr("gs");
            var jss = $(this).attr("jss");
            $("#jingxiaoshangid").val($(this).attr("jssid"));
            $("#productId").val($(this).attr("productId"));
            $("#guanlianid").val($(this).attr("apid"));
            if (gs != '') {
                $("#jss").removeClass("on");
                $("#gs").addClass("on");
            }
            if (jss != '') {
                $("#jss").addClass("on");
                $("#gs").removeClass("on");
            }
            if (gs == '' && jss == '') {
                $("#jss").removeClass("on");
                $("#gs").removeClass("on");
            }
            $(".heibg").fadeIn();
            $(".a88").slideDown();
        })

        $(".a88_bt p").click(function () {
            $(".a88_bt p").removeClass("on");
            $(this).addClass("on");
        });
    })

    function sub() {
        var productId=$("#productId").val();
        var apid = $("#guanlianid").val();
        var jssid = $("#jingxiaoshangid").val();
        var type = $(".a88_bt").find(".on").attr("id");     // jss/gs  经销商或公司通知
        if(!type){
            layer.msg("请选择公司或经销商");
            return false;
        }
        var index = layer.load();
        $.post("dealerPc/updateJssOrGs.html", {apid: apid, jssid: jssid, type: type,areaId:"${params.areaId}",productId:productId}, function (data) {
            if (data.result) {
                layer.msg(data.msg,{time:1000},function () {
                    if(type=='jss'){
                        $("#gs"+productId).text("");
                        $("#jss"+productId).text(data.obj);
                    }else {
                        $("#gs"+productId).text(data.obj);
                        $("#jss"+productId).text("");
                    }
                    $(".heibg").fadeOut();
                    $(".a88").slideUp();
                    layer.close(index);
                });
            } else {
                layer.close(index);
                layer.msg(data.msg);
                return;
            }
        },"json");
    }

    function pingtext(id,type,obj) {    // 0:总评价  1:合作情况
        var text = $(obj).text();
        text=$.trim(text);
        $.post("dealerPc/updatePingText.html", {id: id, text:text, type: type}, function (data) {

        },"json")
    }

    function updateRemark(id,obj) {
        var text = $(obj).text();
        text=$.trim(text);
        $.post("dealerPc/updateRemark.html", {id: id, text:text}, function (data) {

        },"json")
    }

    queryAllArea();
    function queryAllArea() {
        var areaName=$("#areaName").val();
        $.ajax({
            type:"post",
            url:"<%=basePath%>dealerPc/queryAllArea.html",
            dataType:"json",
            async:true,
            data:{"areaName":areaName},
            success:function(data){
                if(data.result){
                    var html = '';
                    for (var i=0;i<data.obj.length;i++){
                        html +='<p onclick="areaOnclick(this);" id="'+data.obj[i].id+'">'+data.obj[i].areaName+'</p>';
                    }
                    $("#areaList").html(html);
                }
            }
        });
    }
    function areaOnclick(obj) {
        $(".bt_box>h5 .in_btn").val($(obj).text());
        $("#areaAllName").val($(obj).text());
        $("#areaId").val($(obj).attr("id"));
        $("#dealerId").val('');
        queryForm();
    }

    function updateNotice(id,productId) {
        var layerIndex = layer.confirm('确认要变为H0000吗？',{icon:3},function(index){
            $.ajax({
                type:"post",
                url:"dealerPc/updateJssOrGs.html",
                dataType:"json",
                async:false,
                data:{apid: id, type: "gsh",productId:productId,areaId:"${params.areaId}"},
                success:function(data){
                    layer.close(layerIndex);
                    if(data.result){
                        layer.msg(data.msg,{time:1000},function () {
                            $('#gs'+productId).text("H0000");
                            $("#jss"+productId).text("");
                        });
                    }else{
                        layer.msg(data.msg, {icon:2,time:2000});
                        return;
                    }
                },
                error:function(){
                    layer.alert("提示：系统内部出现问题！");
                }
            });
        });
    }

    $("#areaName").autocomplete({
        minLength: 0,
        scrollHeight:100,
        source: function(request,response){
            $.ajax({
                url:"<%=basePath%>dealerPc/queryAllArea.html",
                type:"post",
                dataType:"json",
                data:{"areaName":$("#areaName").val()},
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
            $(".bt_box>h5 .in_btn").val(ui.item.label);
            $("#areaAllName").val(ui.item.label);
            $("#areaId").val(ui.item.value);
            $("#dealerId").val('');
            queryForm();
            return false;
        }
    });

    function changeSort() {
        var sort=$("#sortNum").val();
        if(sort=='asc'){
            $("#sortNum").val("desc");
        }else {
            $("#sortNum").val("asc");
        }
        queryForm();
    }

    function changeSortOne() {
        var sort=$("#sortNumOne").val();
        if(sort=='desc'){
            $("#sortNumOne").val("");
        }else {
            $("#sortNumOne").val("desc");
        }
        queryDealerData();
    }
</script>

<script>
    $(function(){
        var num = $(".dongdaibox .dongtai .dbox .danwei.jxs>div table tr th").length;
        //console.log(num)
        var chang = 0;
        for(var i=0;i<num;i++){
            chang+=$(".dongdaibox .dongtai .dbox .danwei.jxs>div table tr th").eq(i).width();
        }

        if(num-7>14){
            $(".jxs").css({"overflow-x":"auto !important"});
            // var duan = chang;
            var duan = chang+29;
            var ccchang = chang + 46
            $(".nangaox").width(duan);
            $(".over_flow.nangaox").width(ccchang);
        }
    })
</script>
<script>
    //JS代码
    var timer = null;

    //左侧DIV的滚动事件
    function moveUp_Left()
    {
        //先删除右侧DIV的滚动事件，以免自动触发
        $("#big_box .rightTop_box .thMiddle2").removeAttr("onScroll");

        //正常设值，同步两个DIV的滚动幅度
        $("#big_box .rightTop_box .thMiddle2").scrollTop($("#big_box  .leftTop_box .thMiddle1").scrollTop());

        //取消延迟预约。【重点】鼠标滚动过程中会多次触发本行代码，相当于不停的延迟执行下面的预约
        clearTimeout(timer);

        //延迟恢复（预约）另一个DIV的滚动事件，并将本预约返回给变量[timer]
        timer = setTimeout(function() {
            $("#big_box .rightTop_box .thMiddle2").attr("onScroll","moveUp_Right();");
        }, 300 );
    }

    //右侧DIV的滚动事件（原理同上）
    function moveUp_Right()
    {
        $("#big_box  .leftTop_box .thMiddle1").removeAttr("onScroll");
        $("#big_box  .leftTop_box .thMiddle1").scrollTop($("#big_box .rightTop_box .thMiddle2").scrollTop());
        clearTimeout(timer);
        timer = setTimeout(function() {
            $("#big_box  .leftTop_box .thMiddle1").attr("onScroll","moveUp_Left();");
        }, 300 );
    }
</script>

<script>
    $(function(){

        var num = 0;
        var n = $("#big_box #rightBox .thTop2 th").length;
        for(var i=0;i<n;i++){
            var nn = $("#big_box #rightBox .thTop2 th").eq(i).width();
            num += nn;
        }
        $("#big_box #rightBox .rightLittle_box").width(num-n);
        $("#big_box #rightBox .rightLittle_box .thMiddle2").width(num-n);


        //鼠标经过
        $(".thMiddle1 tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".thMiddle1 tr").eq(b).addClass('active1')
            $(".thMiddle2 tr").eq(b).addClass('active1')
            $(".thTop1 th").eq(a).addClass('active1')
            $(".thMiddle1 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".leftBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".thMiddle2 tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();  //行
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".thMiddle1 tr").eq(b).addClass('active1')
            $(".thMiddle2 tr").eq(b).addClass('active1')
            $(".thTop2 th").eq(a).addClass('active1')
            $(".thMiddle2 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".rightBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".rightBottom_box tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();  //行
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".rightBottom_box tr").eq(b).addClass('active1')
            $(".leftBottom_box tr").eq(b).addClass('active1')
            $(".thTop2 th").eq(a).addClass('active1')
            $(".thMiddle2 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".rightBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".leftBottom_box tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();  //行
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".rightBottom_box tr").eq(b).addClass('active1')
            $(".leftBottom_box tr").eq(b).addClass('active1')
            $(".thTop1 th").eq(a).addClass('active1')
            $(".thMiddle1 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".leftBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".bigbiaoge").mouseleave(function(){
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
        })

        $(".thTop2 th").click(function(){
            if(!$(this).hasClass("on")){
                $(".thTop2 th").removeClass("on");
                $(this).addClass("on");
            }
            else{
                $(".thTop2 th").addClass("on");
                $(this).removeClass("on");
            }
        })

    })
</script>
</body>
</html>
