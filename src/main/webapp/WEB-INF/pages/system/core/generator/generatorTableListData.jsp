<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="20%">汉语表名</th>
			<th width="20%">表名</th>
			<th width="20%">更新时间</th>
			<th width="40%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td>${data.name}</td>
			<td>${data.tableName}</td>
			<td><fmt:formatDate value="${data.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
				<a style="text-decoration:none" class="ml-5 btn btn-success-outline radius size-S" onClick="showItems('${data.id}');" title="字段列表">
					<i class="Hui-iconfont">&#xe627;</i>字段列表
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${data.id}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onClick="deleteObjs('${data.id}');" title="删除">
					<i class="Hui-iconfont">&#xe6e2;</i>删除
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-secondary-outline radius size-S" onClick="createTable('${data.id}');" title="生成表">
					<i class="Hui-iconfont">&#xe6df;</i>生成表
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-secondary-outline radius size-S" onClick="createCode('${data.id}');" title="生成表">
					<i class="Hui-iconfont">&#xe6df;</i>创建代码
				</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>