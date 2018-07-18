<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<%--<th width="5%"><input type="checkbox"/></th>--%>
			<th width="5%">客户编号</th>
			<th width="5%">客户类型</th>
			<th width="10%">姓名</th>
			<th width="7%">注册手机号</th>
			<th width="5%">总评价</th>
			<th width="10%">单位市场</th>
			<th width="10%">单位名称</th>
			<th width="7%">收货电话</th>
			<th width="11%">其他号码</th>
			<th width="10%">通讯地址</th>
			<th width="20%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-c">
			<%--<td><input class="checkBox" type="checkbox" value="${data.id}"/></td>--%>
			<td>${data.dealer_num}</td>
			<td><c:if test="${data.dealer_type=='1'}">经销商</c:if>
				<c:if test="${data.dealer_type=='2'}">面粉厂</c:if>
				<c:if test="${data.dealer_type=='3'}">食品厂</c:if>
			</td>
			<td>${data.name}</td>
			<td>${data.register_tel}</td>
			<td>${data.overall_merit}</td>
			<td>${data.area_name}</td>
			<td>${data.company_name}</td>
				<td>${data.delivery_tel}</td>
				<td>${data.other_tel}</td>
				<td>${data.postal_address}</td>
			<td>
				<a style="text-decoration:none" class="ml-5 btn btn-primary-outline radius size-S" onClick="edit('${data.id}');" title="编辑">
					<i class="Hui-iconfont">&#xe6df;</i>编辑
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-danger-outline radius size-S" onClick="deleteObjs('${data.id}');" title="删除">
					<i class="Hui-iconfont">&#xe6e2;</i>删除
				</a>
				<a style="text-decoration:none" class="ml-5 btn btn-success-outline radius size-S" onClick="showProduct('${data.id}');" title="关联产品">
					<i class="Hui-iconfont">&#xe627;</i>关联产品
				</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>