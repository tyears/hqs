<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="5%"><input type="checkbox"/></th>
			<#if tableColumns?exists>
			<#list tableColumns as column>
			<#if column.isList=='y'>
			<th width="">${column.remark}</th>
			</#if>
			</#list>
			</#if>
			<th width="120">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${r"${pageInfo.list}"}" var="data">
		<tr class="text-c">
			<td><input class="checkBox" type="checkbox" value="${r"${data.id}"}"/></td>
			<#if tableColumns?exists>
			<#list tableColumns as column>
			<#if column.isList=='y'>
			<#if column.type=='4'>
			<td><fmt:formatDate value="${r'${data.'+column.filedName+'}'}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<#else>
			<td>${r'${data.'+column.filedName+'}'}</td>
			</#if>
			</#if>
			</#list>
			</#if>
			<td>
				<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${r"${data.id}"}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onClick="deleteObjs('${r"${data.id}"}');" title="删除">
					<i class="Hui-iconfont">&#xe6e2;</i>删除
				</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>