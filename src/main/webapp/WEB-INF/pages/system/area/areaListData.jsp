<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%">排序</th>
			<th width="30%">区域名字</th>
			<th width="15%">拼音</th>
			<th width="10%">评价</th>
			<th width="35%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td>${data.sort}</td>
			<td>${data.areaName}</td>
			<td>${data.spell}</td>
			<td>${data.comment}</td>
			<td>
				<c:if test="${params.level=='2'}">
				<a style="text-decoration:none" class="ml-5 btn btn-success-outline radius size-S" onClick="showItems('${data.id}');" title="关联产品">
					<i class="Hui-iconfont">&#xe627;</i>关联产品
				</a>
				</c:if>
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