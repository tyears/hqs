<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<% Date nowDate = new Date(); request.setAttribute("nowDate", nowDate); %>
<table class="hov_mou" >
<c:forEach items="${infoList}" var="data" varStatus="vs">
<tr <c:if test="${vs.index%2==1}">class="bg_hui"</c:if>>
    <td class="wh_01">${data.product_num}</td>
    <td class="wh_02">${data.product_name}</td>
    <%--<td class="wh_03">${data.give_num}</td>--%>
    <td class="wh_04"><fmt:formatDate value="${data.first_time}" pattern="yyyy-MM-dd"/> </td>
    <td class="wh_05"><fmt:formatDate value="${data.last_time}" pattern="yyyy-MM-dd"/> </td>
    <td class="wh_06">${data.dealer_give_num}</td>
    <td class="wh_07">${data.company_give_num}</td>
    <td class="wh_08">${data.notice_num}</td>
    <td class="wh_09">${data.comment}</td>
    <td class="wh_10"><fmt:formatDate value="${data.last_purchase_time}" pattern="yyyy-MM-dd"/> </td>
    <td class="wh_11">${data.author_dealer}</td>
    <td class="wh_12">${not empty data.apId?data.notice_dealer1:'H0000'}</td>
    <td class="wh_13">${data.give_dealer}</td>
    <td class="wh_14" ${data.effect_time.getTime() - nowDate.getTime() - 15*24*60*60*1000 < 0 ? 'style="color: red"' : ''}><fmt:formatDate value="${data.effect_time}" pattern="yyyy-MM-dd"/> </td>
    <td class="wh_15">${data.remark}</td>
    <%--<td class="wh_16"><input name="a" type="checkbox" value="${data.product_id}" data-show="${vs.index%2}" data-pname="${data.product_num}${data.product_name}"></td>--%>
</tr>
</c:forEach>
</table>
<script type="text/javascript">
    $.getScript("<%=basePath%>js/base.js");
</script>