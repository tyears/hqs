<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%"><input type="checkbox"></th>
			<th width="10%">序号</th>
			<th width="20%">资源名称</th>
			<th width="35%">资源地址</th>
			<th width="25%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td><input class="checkBox" type="checkbox" value="${data.id}" isParent="${data.isParent}"/></td>
			<td>${data.sort}</td>
			<td>
				<c:if test="${not empty data.iconValue}">
					<i class="Hui-iconfont">&#${data.iconValue};</i>
				</c:if>
				${data.name}
			</td>
			<td>${empty data.path ? '无':data.path}</td>
			<td>
				<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${data.id}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onClick="deleteData('${data.id}','${data.isParent}');" title="删除">
					<i class="Hui-iconfont">&#xe6e2;</i>删除
				</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>