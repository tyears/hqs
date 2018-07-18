<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="5%"><input type="checkbox"/></th>
			<th width="13%">用户名</th>
			<th width="12%">姓名</th>
			<th width="10%">状态</th>
			<th width="15%">最后登录时间</th>
			<th width="15%">创建时间</th>
			<th width="30%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-l">
			<td class="text-c"><input class="checkBox" type="checkbox" value="${data.id}"/></td>
			<td class="text-c">${data.userName}</td>
			<td class="text-c">${data.name}</td>
			<td class="text-c">
				<c:if test="${data.state=='1'}">
				<span class="label badge-success radius">启用</span>
				</c:if>
				<c:if test="${data.state!='1'}">
				<span class="label label-danger radius">禁用</span>
				</c:if>
			</td>
			<td class="text-c">
				<c:if test="${empty data.lastLoginTime}">未登录过</c:if>
				<c:if test="${not empty data.lastLoginTime}"><fmt:formatDate value="${data.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
			</td>
			<td class="text-c"><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="text-c">
				<c:choose>
					<c:when test="${data.userType!='0'}">
					<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onclick="edit('${data.id}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
					</a>
					<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onclick="deleteData('${data.id}');" title="删除">
						<i class="Hui-iconfont">&#xe6e2;</i>删除
					</a>
					<a style="text-decoration:none" class="ml-5 btn btn-success-outline radius size-S" onClick="roleGive('${data.id}');" title="分配角色">
						<i class="Hui-iconfont">&#xe63c;</i>分配角色
					</a>
					<c:if test="${data.state=='1'}">
					<a style="text-decoration:none" class="ml-5 btn btn-warning-outline radius size-S" onclick="updateState('${data.id}','${data.state}');" title="禁用">
						<i class="Hui-iconfont">&#xe60e;</i>禁用
					</a>
					</c:if>
					<c:if test="${data.state!='1'}">
					<a style="text-decoration:none" class="ml-5 btn btn-secondary-outline radius size-S" onclick="updateState('${data.id}','${data.state}');" title="启用">
						<i class="Hui-iconfont">&#xe605;</i>启用
					</a>
					</c:if>
					</c:when>
					<c:otherwise>
						不能操作超级管理员
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>