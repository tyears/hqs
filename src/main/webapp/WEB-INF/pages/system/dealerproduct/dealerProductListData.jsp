<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%">货号</th>
			<th width="15%">产品信息</th>
			<th width="10%">第一次赠送</th>
			<th width="10%">最后赠送</th>
			<th width="5%">经销商赠送</th>
			<th width="5%">公司赠送</th>
			<th width="5%">评价</th>
			<th width="10%">最后进货</th>
			<th width="10%">确定月数</th>
			<th width="5%">备注</th>
			<th width="15%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td style="font-weight: bold">${data.product_num}</td>
			<td class="text-l">
				<ul>
					<li><strong>产品名称：</strong><span class="c-999">${data.product_name}</span></li>
					<li><strong>规格：</strong><span class="c-999">${data.spec}</span></li>
				</ul>
			</td>
			<td><fmt:formatDate value="${data.first_time}" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${data.last_time}" pattern="yyyy-MM-dd"/></td>
			<td>${data.dealer_give_num}</td>
			<td>${data.company_give_num}</td>
			<td>${data.comment}</td>
			<td><fmt:formatDate value="${data.last_purchase_time}" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${data.true_month_time}" pattern="yyyy-MM-dd"/></td>
			<td>${data.remark}</td>
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