<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="5%"><input type="checkbox"/></th>
			<th width="5%">排序</th>
			<th width="10%">所属类别</th>
			<th width="10%">标题</th>
			<th width="10%">技术平台网址</th>
			<th width="15%">内容</th>
			<th width="10%">答复短信</th>
			<th width="10%">提示短信</th>
			<th width="5%">备注</th>
			<th width="5%">更新时间</th>
			<th width="5%">发送短信</th>
			<th width="10%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td><input class="checkBox" type="checkbox" value="${data.id}"/></td>
			<td>${data.sort}</td>
			<td><c:forEach items="${articleTypeList}" var="articleType">${data.articleTypeId==articleType.id?articleType.typeName:""}</c:forEach> </td>
			<td>${data.title}</td>
			<td>${data.technologyUrl}</td>
			<td><div class="text-overflow" style="width:100px;" title="${data.content}">${data.content}</div></td>
			<td><div class="text-overflow" style="width:80px;" title="${data.replySms}">${data.replySms}</div></td>
			<td><div class="text-overflow" style="width:80px;" title="${data.promptSms}">${data.promptSms}</div></td>
			<td><div class="text-overflow" style="width:60px;" title="${data.remark}">${data.remark}</div></td>
			<td><fmt:formatDate value="${data.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${data.smsNum}</td>
			<td>
				<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${data.id}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
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