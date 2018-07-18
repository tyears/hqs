<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%">类别</th>
			<th width="7%">货号</th>
			<th width="15%">产品信息</th>
			<th width="7%">零售价/袋</th>
			<th width="7%">零售价/箱</th>
			<th width="7%">经销价/袋</th>
			<th width="7%">经销价/箱</th>
			<th width="11%">备注</th>
			<th width="10%">编辑时间</th>
			<th width="5%">排序</th>
			<th width="10%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<td style="font-weight: bold">${data.typeName}</td>
			<td style="font-weight: bold">${data.product_num}</td>
			<td class="text-l">
				<ul>
					<li><strong>产品名称：</strong><span class="c-999">${data.product_name}</span></li>
					<li><strong>规格：</strong><span class="c-999">${data.spec}</span></li>
					<li><strong>每箱袋数：</strong><span class="c-999">${data.num_box}</span></li>
				</ul>
			</td>
			<td>￥${empty data.retail_price_bag?0:data.retail_price_bag}</td>
			<td>￥${empty data.retail_price_box?0:data.retail_price_box}</td>
			<td>￥${empty data.sell_price_bag?0:data.sell_price_bag}</td>
			<td>￥${empty data.sell_price_box?0:data.sell_price_box}</td>
			<td>${data.remark}</td>
			<td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${data.sort}</td>
			<td>
				<c:if test="${data.reserved1=='0'}">
					<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${data.id}');" title="编辑">
						<i class="Hui-iconfont">&#xe6df;</i>编辑
					</a>
					<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onClick="deleteObjs('${data.id}');" title="删除">
						<i class="Hui-iconfont">&#xe6e2;</i>删除
					</a>
				</c:if>
				<c:if test="${data.reserved1=='1'}">
					<span class="btn btn-danger radius">已停用</span>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>