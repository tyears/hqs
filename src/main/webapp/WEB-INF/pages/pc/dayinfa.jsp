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

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>打印告知单</b></div><h6>
        <form id="queryForm" action="givePc/toPrintInvoice.html" method="post">
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
                            <%--<th><label class="checkbox_all"><span>全选</span><input type="checkbox"></label></th>--%>
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
                                <td id="td${data.id}" class="zs_btn" style="text-align:left; cursor:pointer;">${data.give_content}</td>
                                <td>${data.remark}</td>
                                <td>${data.overall_merit}</td>
                                <td>${data.cooperation_state}</td>
                                <td>${data.give_man_name}</td>
                                <td>
                                    <button class="shenhe sh01" onclick="printGoodsOrder('${data.id}')">打印发货单</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="caozuo_box">
                        <%--<input type="button" value="打印发货单">--%>
                    </div>
                    <div class="fenye" id="pageDiv"></div>
                    <c:if test="${pageInfo.list.size()==0}">
                        <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.fahuo}</div>
    </div>
</div>
<!--弹窗---->
<div class="heibg"></div>
<div class="popup popup02">
    <h2><span>打印通知单</span><i>×</i></h2>
    <form>
        <dl>
            <dd><input type="text" placeholder="接单人（举例：部门-姓名）"></dd><!--
          <dd><input type="text" placeholder="手机号码"></dd>
          <dd><input type="text" placeholder="姓名"></dd>
          <dd><select><option>客户类型</option></select></dd>
          <dd><textarea rows="3" placeholder="客户留言"></textarea></dd>
          <dd><textarea rows="3" placeholder="操作留言"></textarea></dd>
          <dd><textarea rows="3" placeholder="经理意见"></textarea></dd>-->
            <dd><textarea rows="3" placeholder="内容"></textarea></dd>
            <dd><label><input type="checkbox"><span>是否加急</span></label></dd>
            <dd class="submit"><input type="button" value="打印"></dd>
        </dl>
    </form>
</div>

<div class="duanxintanchuang tztanchuang a3">
    <div class="dxbox">
        <div class="dtop">
            <p>给经销商发短信</p>
            <span></span>
        </div>
        <div class="ddmidd zssma zssma1">
            <div class="dmidd zssm" contenteditable="true">
                内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容
            </div>
            <div class="d_bt">确认</div>
        </div>
    </div>
</div>

<div class="duanxintanchuang tztanchuang a1">
    <div class="dxbox">
        <div class="dtop">
            <p>赠送产品</p>
            <span></span>
        </div>
        <div style="max-height:480px; overflow-y:auto">
            <div class="biao">
                <table class="append_t">
                    <tr>
                        <th>选择分类</th>
                        <th>选择产品</th>
                        <th>数量</th>
                        <th>操作</th>
                    </tr>
                    <tr>
                        <td>
                            <select>
                                <option>请选择分类</option>
                            </select>
                        </td>
                        <td>
                            <select>
                                <option>请选择产品</option>
                            </select>
                        </td>
                        <td><input type="number"></td>
                        <td><a href="javascript:">删除</a></td>
                    </tr>
                </table>
                <h3 class="tianjia">添加一个产品</h3>
            </div>
            <div class="ddmidd zssma">
                <div class="d_bt d_bt_01">确认赠送</div>
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

    $(function(){
        /*产品赠送添加*/

        $(".zs_btn").click(function(){
            $(".heibg").fadeIn();
            $(".a1").slideDown();
        })
        $(".heibg,.popup >h2 i").click(function(){
            $(".popup02,.heibg").fadeOut();
        })
    })
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
                //设置页面 ，10mm 页面底部边距 ，3和CreateCustomPage高度为自适应
                LODOP.ADD_PRINT_HTM(-1, -411, 1100, 735, document.getElementById("printStyle").innerHTML);
                LODOP.SET_PRINT_PAGESIZE(1, 800, 1800, "");
                        LODOP.PREVIEW();
//                LODOP.PRINT_DESIGN();
            }
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
