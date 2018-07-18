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
    <script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
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
<div class="left_top">
    <iframe id="left_top" src="order/toLeftTop.html" frameborder="0" width="100%" scrolling="no" ></iframe>
</div>

<div class="dongdaibox">
    <div class="biaoti biaoti1 jingxiao_box"><div><span></span><b id="areaNum"><a id="weihe_area" href="javascript:void(0)" ${empty params.type?"style='color:black'":""} onclick="weihe_type('',this)"></a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" ${params.type==0?"style='color:black'":""} onclick="weihe_type('0',this)">合作商信息（<i id="weihe_type_0"></i>）</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" ${params.type==1?"style='color:black'":""} onclick="weihe_type('1',this)">未合作商信息（<i id="weihe_type_1"></i>）</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="weihe_type('2',this)">面粉厂（<i id="weihe_type_2"></i>）</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="weihe_type('3',this)">食品厂（<i id="weihe_type_3"></i>）</a>
    </b></div>
        <h5 class="">
            <input class="in_btn" placeholder="请输入单位市场" id="areaName">
            <%--<div class="shengshiqu" id="areaList">--%>
            <%--</div>--%>
        </h5>
        <h6>
            <select onchange="queryPage(this.value)" >
                <option  disabled>显示条数</option>
                <option  >10</option>
                <option  >20</option>
                <option >50</option>
                <option >100</option>
            </select>
        </h6></div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left dbox_none">
                <dl>
                    <dd>
                        <form action="dealerPc/dealerData.html" method="post" id="queryForm">
                            <input type="hidden" name="type" id="type" value="${params.type}"/>
                            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
                            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
                            <input placeholder="请输入关键字"  id="keyword" name="keyword" value="${params.keyword}">
                            <%--<input placeholder="请输入姓名" id="name" name="name" value="${params.name}">--%>
                            <%--<input placeholder="请输入地址" id="deliveryAddress" name="deliveryAddress" value="${params.deliveryAddress}">--%>
                            <input type="hidden" id="areaId" name="areaId" value="${params.areaId}"/>
                            <button class="chaxun" onclick="queryAll();">查询</button>
                            <button class="chongzhi" type="button" onclick="resetKey();">重置</button>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge danwei jiben" id="mainContent"></div>
            </div>
        </div>
        <div class="beizhu_box" style="margin-top: 0px">${sessionScope.remarks.kehuchaxun}</div>
    </div>
</div>

<%--<script src="js/base.js"></script>--%>
<script>
    $(function(){
        $("body").click(function () {
            $(".shengshiqu").hide();
        });

        /*	$("#city .ssq").click(function(){
         $("#dis").show();
         return false;
         })*/

        //文本框输入
//        $(".jingxiao_box>h5 .in_btn").keyup(function () {
//            $(".shengshiqu").show();	//只要输入就显示列表框
//            queryAllArea();
//            if ($(".jingxiao_box>h5 .in_btn").val().length <= 0) {
//                $(".shengshiqu p").show();//如果什么都没填，跳出，保持全部显示状态
//                return;
//            }
//        });

    });

    updateDealerNum('${params.areaId}');
    function query(){
        queryData("#queryForm","#mainContent");
    }

    function queryAll() {
        $("#areaId").val("");
        updateDealerNum("");
        query();
    }
    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            queryAll();
        }
    });
    function queryPage(pn) {
        $("#pageSize").val(pn);
        query();
    }

    function resetKey() {
        $("#keyword").val('');
        queryAll();
    }

    function updateDealerNum(areaId) {
        $("#areaNum").find("a").css("color",'');
        $("#weihe_area").css("color","black");
        $("#type").val("");
        $.ajax({
            type: "post",
            url: "<%=basePath%>dealerPc/queryAreaNum.html",
            async: true,
            dataType: "json",
            data:{"areaId":areaId},
            success: function (data) {
                if (data.result) {
                    $("#weihe_area").html(data.obj.areaName);
                    $("#weihe_type_0").html(data.obj.hzNum);
                    $("#weihe_type_1").html(data.obj.whzNum);
                    $("#weihe_type_2").html(data.obj.spNum);
                    $("#weihe_type_3").html(data.obj.mfNum);
                } else {
                    return false;
                }
            },
            error: function () {

            }
        });
    }

    function weihe_type(type,obj) {
        $(obj).parent().find("a").css("color",'');
        $(obj).css("color","black");
        $('#type').val(type);
        query();
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
        $("#type").val("");
        $(".jingxiao_box>h5 .in_btn").val($(obj).text());
        $("#areaId").val($(obj).attr('id'));
        updateDealerNum($(obj).attr('id'));
        query();
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
            $(".jingxiao_box>h5 .in_btn").val(ui.item.label);
            $("#areaId").val(ui.item.value);
            updateDealerNum(ui.item.value);
            query();
            return false;
        }
    });
</script>

</body>
</html>
