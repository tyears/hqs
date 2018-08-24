<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<div class='print'>
    <div><span>告知单</span><i></i></div>
    <div class="none     bot"><em id="orderNumP">NO.${give.only_num}</em><i class="none" id="timeP">${give.now_date}</i>
    </div>
    <table cellpadding='0' cellspacing='0' style='border-collapse:collapse;'>
        <tr>
            <td>To：<span id="orderManP">订货部</span></td>
        </tr>
        <tr>
            <td id="telP" style="text-align: center">${give.register_tel}</td>
        </tr>
        <tr>
            <td id="orderNameP" style="text-align: center">${give.dname}【${give.area_name}】</td>
        </tr>
        <tr>
            <td id="orderTypeP" style="text-align: center">经销商【${give.dealer_num}】</td>
        </tr>
        <tr>
            <td id="dealerMessageP" valign="top"><span style="float: left; width:50px;">宣传：</span><i
                    style="float: right; width:176px;"><c:forEach items="${giveProductList}" var="data"><em
                    style="display: inline-block; font-style: normal;">${data.productNum}${data.productName};</em></c:forEach></i>
            </td>
        </tr>
        <tr>
            <td id="orderContentP">${give.remark}</td>
        </tr>
    </table>
    <div class="none"><span style='text-align:right;  width:  70%;  font-size:  15px;'>下 单：</span><i><b
            id="">${userInfo.name}</b></i></div>
</div>

<style>
    .print{ width:260px; margin:0 auto; }
    .print div{ overflow:hidden; line-height: 40px; padding: 0 10px; position: relative;}
    .print div span{ display:block; font-size:22px; color: #111; text-align: center;}
    .print div em{ display:block; font-size:20px; color: #333; font-style: normal;}
    .print div i{ position: absolute; right: 0; top: 0; width: 30%; font-style: normal; text-align: center; }
    .print div.none{ border-top: none;}
    .print div.bot{ border-bottom: none;}
    .print div i.none { border:none;}
    .print table{ font-size: 15px; width: 260px; color: #111;}
    .print table td{ padding:10px 5px; border: 1px solid #111;width: 230px}
    .printtable td span{ width: 70%; display: inline-block; font-weight: bold; text-align: center;}
    #dealerMessageP{height:450px}
</style>


