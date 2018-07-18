<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<%--<th width="5%"><input type="checkbox"/></th>--%>
			<th width="20%">食品名称</th>
			<th width="10%">食品简拼</th>
			<th width="15%">食品分类</th>
			<th width="20%">创建时间</th>
			<th width="30%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<%--<td><input class="checkBox" type="checkbox" value="${data.id}"/></td>--%>
			<td>${data.food_name}</td>
			<td>${data.jianpin}</td>
			<td>${data.typeName}</td>
			<td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td>
				<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${data.id}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-success-outline radius size-S" onClick="canUseProducts('${data.id}');" title="适用产品">
					<i class="Hui-iconfont">&#xe627;</i>适用产品
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onClick="deleteObjs('${data.id}');" title="删除">
					<i class="Hui-iconfont">&#xe6e2;</i>删除
				</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>