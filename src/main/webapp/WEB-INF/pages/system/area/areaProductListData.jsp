<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="7%">货号</th>
			<th width="20%">产品信息</th>
			<th width="10%">授权</th>
			<th width="10%">市场评价</th>
			<th width="10%">公司通知</th>
			<th width="10%">经销商赠送</th>
			<th width="15%">有效时间</th>
			<th width="18%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td style="font-weight: bold">${data.productNum}</td>
			<td class="text-l">
				<ul>
					<li><strong>产品名称：</strong><span class="c-999">${data.productName}</span></li>
					<li><strong>规格：</strong><span class="c-999">${data.spec}</span></li>
				</ul>
			</td>
			<td>${empty data.author_dealer?'无':data.author_dealer}</td>
			<td>${data.comment}</td>
			<td>${empty data.notice_dealer?'无':data.notice_dealer}</td>
			<td>${empty data.give_dealer?'无':data.give_dealer}</td>
			<td><fmt:formatDate value="${data.effect_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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