<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%">操作名称</th>
			<%--<th width="28%">访问方法</th>--%>
			<th width="32%">内容</th>
			<th width="12%">访问IP</th>
			<th width="6%">操作人</th>
			<th width="12%">访问时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td>${data.operation}</td>
			<td>${data.content}</td>
			<td>${data.request_ip}</td>
			<td>${empty data.method?'无':data.method}</td>
			<td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>