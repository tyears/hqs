<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<style>
    tr:nth-child(2n-1){background-color: #f5f5f5;}
    tr {height: 51px;}
</style>
<table>
<tr>
    <th style="width: 5%;">操作</th>
    <th style="width: 8%;">询单编号</th>
    <th style="width: 6%;">调单人</th>
    <th style="width: 8%;">手机号</th>
    <th style="width: 10%;">姓名</th>
    <th style="width: 20%;">客户留言</th>
    <th style="width: 17%;">咨询产品</th>
    <th style="width: 20%;">转单留言</th>
    <th style="width: 8%;">来电/留言</th>
</tr>
<c:forEach items="${pageInfo.list}" var="data">
<tr>
    <td><input type="radio" name="cdTd" value="${data.order_num}"></td>
    <td>${data.order_num}</td>
    <td>${data.userName}</td>
    <td>${data.tel}</td>
    <td>${data.name}</td>
    <td>${data.dealer_message}</td>
    <td>${data.product_names}</td>
    <td>${data.user_message}</td>
    <td>${data.import_type == '0' ? '留言' : '来电'}</td>
</tr>
</c:forEach>
</table>
<div class="fenye" id="pageDiv"></div>
<script type="text/javascript">
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
                queryOrderData();
            }
        }
    });
    </c:if>
</script>
