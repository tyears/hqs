<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<%--<th width="5%"><input type="checkbox"/></th>--%>
			<th width="30%">短信内容</th>
			<th width="15%">接收号码</th>
			<th width="20%">操作人</th>
			<th width="20%">操作时间</th>
			<th width="10%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<%--<td><input class="checkBox" type="checkbox" value="${data.id}"/></td>--%>
			<td>${data.content}</td>
			<td>${data.tel}</td>
			<td>${data.userName}</td>
			<td><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
			<td><a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onclick="deleteObjs('${data.id}');" title="删除">
				<i class="Hui-iconfont">&#xe6e2;</i>删除
			</a></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>