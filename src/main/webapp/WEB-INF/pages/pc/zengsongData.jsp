<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>

<table class="hov_mou">
    <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
        <tr <c:if test="${vs.index%2==1}">class="bg_hui"</c:if>>
            <td class="ww1">${(pageInfo.pageNum * pageInfo.pageSize) + vs.index + 1}</td>
            <td class="ww2">${data.give_type!='1'?data.tel:data.register_tel}</td>
            <td class="ww3">${data.give_type!='1'?data.name:data.dname}</td>
            <td class="ww4">${data.give_type!='1'?data.address:data.delivery_address}</td>
            <td class="ww5">${data.give_content}</td>
            <c:if test="${data.give_type==1}">
                <td class="ww6">${data.ddealer_type==1?'经销商':data.ddealer_type==2?'面粉厂':'食品厂'}</td>
            </c:if>
            <c:if test="${data.give_type!=1}">
                <td class="ww6">
                    <c:if test="${data.dealer_type==0}">普通客户</c:if>
                    <c:if test="${data.dealer_type==1}">经销商</c:if>
                    <c:if test="${data.dealer_type==2}">面粉厂</c:if>
                    <c:if test="${data.dealer_type==3}">食品厂</c:if>
                    <c:if test="${data.dealer_type==4}">其他</c:if>
                </td>
            </c:if>
            <td class="ww6">${data.give_type==1?'赠给经销商':data.give_type==2?'公司赠送':'经销商赠送'}</td>
            <td class="ww7" style="width: 6%"><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd"/></td>
            <td class="ww8" style="cursor: pointer;">${data.goods_num}</td>
            <td class="ww9">${data.give_man_name}</td>
            <td class="ww10">${data.dealer_num}</td>
            <td class="ww11"><div class="text" ${sessionScope.userType==1?'contenteditable="true"':''} style="overflow: hidden; height: 20px; padding: 0;/*width: 90%; margin: 0 auto; text-overflow: ellipsis; white-space: nowrap;*/" oninput="beizhu('${data.id}',this)">${data.remark}</div></td>
        </tr>
    </c:forEach>
</table>
<div class="fenye" id="pageDiv" style="margin-top: 12px;margin-bottom: 10px;"></div>
<style>
    .hauto{ height: auto !important; text-overflow: clip !important; white-space: normal !important;}

</style>

<script type="text/javascript">
    console.log("${pageInfo}")
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
                query();
            }
        }
    });
    </c:if>

    $(function(){
        var text = '';
        var phone = '';
        $('.dongdaibox .dongtai .dbox .bigbiaoge table tr .ww8').click(function(){
            var goodsnum = $(this).text();
            $("#tongzhi").html("海韦力告知：我们已经为您邮寄了免费试用的海韦力产品。申通快递单号："+goodsnum+"，请注意查收！欢迎了解使用质量稳定、用着放心的海韦力产品！");
            text = $("#tongzhi").val();
            phone = $(this).parent().find(".ww2").text();
            $(".heibg").fadeIn();
            $(".popup02").slideDown();
        })

        $(".heibg,.popup >h2 i ").click(function(){
                    $(".popup02,.heibg").fadeOut();
        })

        $(".submit input").click(function(){
            if(!phone){
                layer.msg("手机号为空，不能发送");
                return false;
            }
            var index=layer.load();
            $.post("<%=basePath%>sendOutSms.html",{text:text,phone:phone},function (data) {
                if(data.result){
                    $(".popup02,.heibg").fadeOut();
                    layer.msg(data.msg,{time:1000},function () {
                        layer.close(index)
                    });
                }else{
                    layer.msg(data.msg,{time:1000},function () {
                        layer.close(index)
                    });
                    return;
                }
            },"json")
        })
    })

    function beizhu(id,obj) {
        var text = $(obj).text();
        text = $.trim(text);
        $.post("givePc/updateBeizhu.html",{id:id,text:text})
    }

    $.getScript("<%=basePath%>js/base.js");


    $(".ww11 .text").mouseenter(function(){
        $(this).addClass("hauto");
    })
    $(".ww11 .text").mouseleave(function(){
        $(this).removeClass("hauto");
    })
</script>
