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
</head>
<body>
<div class="left_top">
    <iframe id="left_top" src="order/toLeftTop.html" frameborder="0" width="100%" scrolling="no"></iframe>
</div>

<div class="dongdaibox">
    <div class="biaoti biaoti1"><div><span></span><b><a>${area.areaName}</a>&nbsp;&nbsp;${not empty dealer.dealerNum?'合作经销商':'未合作经销商'}</b>
    </div>
        <c:if test="${not empty dealer}">
            <button type="button" onclick="pipei('${dealer.id}')" style="position:absolute; top:7px; right:10px; background-color:#41973c; padding:6px 6px; color:#fff; border-radius:3px; border:none; cursor:pointer;">匹配客户</button>
        </c:if>
    </div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left dbox_none dangqian">
                <div class="dangqianbox">
                    <div style="overflow: hidden;"><h2 style="float: left">当前客户信息</h2>
                    <c:if test="${not empty dealer}">
                        <a style="float: right; background-color:#41973c; padding:6px 10px;color: #fff; border-radius: 3px;" href="javascript:void(0)" onclick="jjssave('${dealer.id}')">保存</a>
                    </c:if></div>
                    <div class="biao">
                        <table>
                            <tr>
                                <td class="bg_hui mc_ziduan">客户编号</td>
                                <td class="kehubianhao" <c:if test="${not empty dealer && not empty dealer.dealerNum}">onclick="location.href='dealerPc/toDealerDetail.html?dealerId=${dealer.id}'" </c:if>>
                                    <c:if test="${not empty dealer}">
                                        <c:if test="${dealer.dealerType=='1'}">
                                            ${empty dealer.dealerNum?'<button onclick="shengcheng()">生成编号</button>':dealer.dealerNum}
                                            ${not empty dealer.dealerNum?'<button onclick="delNum()">取消编号</button>':dealer.dealerNum}
                                        </c:if>
                                    </c:if>
                                </td>
                                <td class="bg_hui mc_ziduan">单位名称</td>
                                <td class="juz"><div>${dealer.companyName}</div></td>
                            </tr>
                            <tr class="bg_hui">
                                <td class="bg_hui mc_ziduan">注册手机号</td>
                                <td><div id="jiben_zcsjh" ${not empty dealer?'contenteditable="true"':''}>${dealer.registerTel}</div></td>
                                <td class="bg_hui mc_ziduan">收货地址</td>
                                <td class="juz"><div id="jiben_shdz" ${not empty dealer?'contenteditable="true"':''}>${dealer.deliveryAddress}</div></td>
                            </tr>
                            <tr>
                                <td class="bg_hui mc_ziduan">姓名</td>
                                <td><div >${dealer.name}</div></td>
                                <td class="bg_hui mc_ziduan">收货电话</td>
                                <td class="juz"><div id="jiben_shdh" ${not empty dealer?'contenteditable="true"':''}>${dealer.deliveryTel}</div></td>
                            </tr>
                            <tr class="bg_hui">
                                <td class="bg_hui mc_ziduan">单位市场</td>
                                <td><div >${dealer.districtName}</div></td>
                                <td class="bg_hui mc_ziduan">传真</td>
                                <td class="juz"><div id="jiben_cz" ${not empty dealer?'contenteditable="true"':''}>${dealer.fax}</div></td>
                            </tr><tr>
                            <td class="bg_hui mc_ziduan">收货人</td>
                            <td><div id="jiben_shr" ${not empty dealer?'contenteditable="true"':''}>${dealer.consignee}</div></td>
                            <td class="bg_hui mc_ziduan">合作情况</td>
                            <td class="juz"><div >${dealer.cooperationState}</div></td>
                        </tr>
                            <tr class="bg_hui">
                                <td class="bg_hui mc_ziduan">邮编</td>
                                <td><div id="jiben_yb" ${not empty dealer?'contenteditable="true"':''}>${dealer.postcode}</div></td>
                                <td class="bg_hui mc_ziduan">短信地址</td>
                                <td class="juz"><div >${dealer.smsAddress}</div></td>
                            </tr>
                            <tr>
                                <td class="bg_hui mc_ziduan">合作日期</td>
                                <td><div ><fmt:formatDate value="${dealer.cooperationTime}" pattern="yyyy-MM-dd" /></div></td>
                                <td class="bg_hui mc_ziduan" rowspan="2">信用评价</td>
                                <td class="juz" rowspan="2"><div >${dealer.creditEvaluation}</div></td>
                            </tr>
                            <tr class="bg_hui">
                                <td class="bg_hui mc_ziduan">总评价</td>
                                <td><div >${dealer.overallMerit}</div></td>
                            </tr>
                            <tr class="">
                                <td class="bg_hui mc_ziduan">短信接收号</td>
                                <td><div id="jiben_dxjsh" ${not empty dealer?'contenteditable="true"':''}>${dealer.smsTel}</div></td>
                                <td class="bg_hui mc_ziduan" rowspan="2">备注</td>
                                <td class="juz" rowspan="2"><div id="jiben_bz" ${not empty dealer?'contenteditable="true"':''}>${dealer.remark}</div></td>
                            </tr>
                            <tr class="">
                                <td class="bg_hui mc_ziduan">经销区域</td>
                                <td class=""><div >${dealer.distributionArea}</div></td>
                            </tr>

                            <tr class="bg_hui">
                                <td class="bg_hui mc_ziduan">通讯地址</td>
                                <td class="juz" colspan="3"><div >${dealer.postalAddress}</div></td>
                            </tr>
                            <tr>
                                <td class="bg_hui">其他号码</td>
                                <td colspan="3" align="left" id="otherTel"
                                    <c:if test="${not empty dealer}">
                                    onclick="updateqita('${dealer.id}')"
                                    </c:if>
                                >${dealer.otherTel}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="jiaowangjilb">
                    <div class="tianjaiyitiao">
                        <h2><a>${dealer.dealerNum}</a> 交往记录<input style="background-color: #41973c; padding: 6px 10px; color: #fff; border-radius:3px; border: none; margin-left: 10px" type="button" value="添加" onclick="addItem()" id="addItem"></h2>
                        <form action="order/queryChangeRecord.html" method="post" id="queryForm">
                            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
                            <input type="hidden" id="pageSize" name="pageSize" value="10"/>
                            <input type="hidden" name="orderTel" value="${params.orderTelStr}"/>
                        </form>
                    </div>
                    <div class="biao" id="mainContent"></div>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.jiaowangjilu}</div>
    </div>
</div>
<!--弹窗-->
<div class="heibg"></div>
<div class="duanxintanchuang tj01">
    <div class="dxbox">
        <div class="dtop">
            <p id="itemP">添加记录</p>
            <span></span>
        </div>
        <div class="ddmidd">
            <div class="dmidd tjone" style="padding: 0px;">
                <input id="itemId" type="hidden"/>
                <textarea id="itemStr"></textarea>
            </div>
            <div class="d_bt" onclick="saveItem()" style="outline: none;">确认</div>
        </div>
    </div>
</div>
<div id="otherphone" style="display: none">   <!-- 弹窗 -->
    <form id="pinjie">
        <%--<p><span>手机号</span><input type="text" id="onephone" name="otherphone" placeholder="请输入手机号"></p>--%>
    </form>
    <div class="btn">
        <a onclick="layer.close(tan);">取消</a>
        <a onclick="subphone()">确定</a>
        <a onclick="addphone()">增加</a>
    </div>
</div>
<style>
    #otherphone{ padding: 20px; background-color: #fff; border-radius: 8px; font-size: 14px; line-height: 36px; color: #555;}
    #otherphone p{ overflow: hidden; padding: 5px 0;}
    #otherphone p span{ display:block; width: 60px; float: left; }
    #otherphone p input{ text-indent: 8px; display: block; border-radius: 2px; border: 1px solid #dedede; width: 200px; line-height: 34px; color: #555; float: left; }
    #otherphone p i{ display: block; float: left; margin-left: 14px; font-size: 12px; line-height: 36px; color: #41973c; }
    #otherphone .btn{ text-align: center; margin-top: 10px;}
    #otherphone .btn a{ display: inline-block; border-radius: 5px; line-height: 30px; font-size: 14px; color: #fff; background-color: #41973c; margin: 0 10px; padding: 0 15px;}
    #otherphone .btn a:first-child{ background-color: #eee; color: #555;}

</style>


<script type="text/javascript">
    var tan;

    //显示添加交往记录弹窗
    function addItem(id,content){
        if(id){
            $("#itemP").html("编辑记录");
            $("#itemId").val(id);
            $("#itemStr").html(content);
        }else{
            $("#itemP").html("添加记录");
            $("#itemId").val("");
            $("#itemStr").html("");
        }
        $(".duanxintanchuang").show();
        $(".heibg").show();
//        var orderId = $("#left_top").contents().find('#orderId').val();
//        alert(orderId);
//        if(!orderId){
//            layer.msg("当前没有调单，不能添加交往记录")
//        }
    }
    //交往记录保存
    function saveItem() {
        var id = $("#itemId").val();
        var content = $("#itemStr").val();
        var type = "${params.get('type')}";
        if(type == "0"){//关联询单
            var orderId = $("#left_top").contents().find('#orderId').val();
        }
        var orderTel = "${params.get('orderTel')}";
        $.post("order/saveChangeRecord.html",{id:id,content:content,orderId:orderId,orderTel:orderTel},function (data) {
            if(data.result){
                window.location.reload();
            }else{
                layer.alert(data.message);
            }
        },"json");

    }

    function updateqita(id) {
        $.post("order/getDealerById.html",{id:id},function (data) {
            //先初始化div
            $("#pinjie").empty();

            $("#pinjie").append('<input type="hidden" name="xiugaiid" value="${dealer.id}"/>')
            $("#pinjie").append('<p><span>号码</span><input type="text" id="onephone" name="otherphone" placeholder="请输入号码"></p>')

            if(data.obj.length>0) {
                $("#onephone").val(data.obj[0]);
            }
            var html = '';
            for(var i=1;i<data.obj.length;i++){
                html += '<p><span>号码</span><input type="text" name="otherphone" value="'+data.obj[i]+'" placeholder="请输入号码"><i onclick="$(this).parent().remove()">删除</i></p>'
            }
            $("#pinjie").append(html);
        },"json");
        tan = layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shadeClose: true,
            skin: 'yourclass',
            content: $("#otherphone")
        });
    }

    function addphone() {
        var html = '<p><span>号码</span><input type="text" name="otherphone" placeholder="请输入号码"><i onclick="$(this).parent().remove()">删除</i></p>'
        $("#pinjie").append(html);
    }

    function subphone() {
//        var regex = /^1\d{10}$/;
//        var b = true;
//        $('input[name=otherphone]').each(function () {
//            if(!$(this).val()){
//                b = false;
//                layer.msg("号码不能为空",{time:1000});
//                return;
//            }
//        })
//        if(!b){
//            return;
//        }else {
            $.get("order/updateOtherPhoneById.html", $("#pinjie").serialize(), function (data) {
                if (data.result) {
                    layer.msg(data.msg, {time: 1000}, function () {
                        $("#otherTel").html(data.obj);
                        layer.close(tan);
                    })
                } else {
                    layer.msg(data.msg, {time: 1000})
                }
            }, "json")
//        }
    }


    //查询数据(注意：方法名统一为query)
    function query(){
        queryData("#queryForm","#mainContent");
    }

    function shengcheng() {
        var did='${dealer.id}';
        $.post("order/shengchengcode.html",{id:did},function (data) {
            if(data.result){
                layer.msg(data.msg,{time:1000},function () {
                    $(".kehubianhao").html(data.obj+'<button onclick="delNum()">取消编号</button>');
                })
            }else{
                layer.msg(data.msg,{time:1000})
            }
        },"json")
    }
    function delNum() {
        var did='${dealer.id}';
        $.post("order/delNum.html",{id:did},function (data) {
            if(data.result){
                layer.msg(data.msg,{time:1000},function () {
                    $(".kehubianhao").html('<button onclick="shengcheng()">生成编号</button>');
                })
            }else{
                layer.msg(data.msg,{time:1000})
            }
        },"json")
    }

    function pipei(id) {
        $.post("order/pipei.html",{did:id},function (data) {
            if(data.result){
                layer.msg(data.msg,{time:1000},function () {
                    window.location.reload();
                });
            }else{
                layer.msg(data.msg,{time:1000});
            }
        },"json")
    }

    function jjssave() {
        var regex = /^1\d{10}$/;

        var id = '${dealer.id}'
        var shr = $("#jiben_shr").text();
        var yb = $("#jiben_yb").text();
        var dxjsh = $("#jiben_dxjsh").text();
        var zcsjh = $("#jiben_zcsjh").text();
        var shdz = $("#jiben_shdz").text();
        var shdh = $("#jiben_shdh").text();
        var cz = $("#jiben_sc").text();
        var bz = $("#jiben_bz").text();
        if(!regex.test(zcsjh)){
            layer.msg("请输入正确的注册手机号",{time:1500});
            return;
        }
        if(!dxjsh){
            layer.msg("短信接收号不能为空",{time:1500});
            return;
        }
        if(!regex.test(dxjsh)){
            layer.msg("请输入正确的短信接收号",{time:1500});
            return;
        }
        if(!regex.test(shdh)){
            layer.msg("请输入正确的收货电话",{time:1500});
            return;
        }
        $.post("dealerPc/updatedealer.html",{id:id,consignee:shr,postcode:yb,smsTel:dxjsh,registerTel:zcsjh,deliveryAddress:shdz,deliveryTel:shdh,fax:cz,remark:bz},function (data) {
            layer.msg(data.msg,{time:1500})
        },"json");
    }
</script>
</body>
</html>
