<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="5%">序号</th>
			<th width="12%">字段名</th>
			<th width="13%">类型</th>
			<th width="10%">是否主键</th>
			<th width="10%">注释</th>
			<th width="10%">是否查询条件</th>
			<th width="10%">是否列表显示</th>
			<th width="10%">是否必填</th>
			<th width="20%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="data" varStatus="status">
		<tr class="text-c">
			<td>${status.index+1}</td>
			<td>${data.name}</td>
			<td>
				<c:choose>
					<c:when test="${data.type=='1'}">varchar(${data.length})</c:when>
					<c:when test="${data.type=='2'}">int(${data.length})</c:when>
					<c:when test="${data.type=='3'}">double(${data.length},2)</c:when>
					<c:when test="${data.type=='4'}">datetime</c:when>
					<c:when test="${data.type=='5'}">decimal(${data.length},2)</c:when>
				</c:choose>
			</td>
			<td>${data.isPrimaryKey=='y'?'是':'否'}</td>
			<td>${empty data.remark?'无':data.remark}</td>
			<td>${data.isQuery=='y'?'是':'否'}</td>
			<td>${data.isList=='y'?'是':'否'}</td>
			<td>${data.isRequired=='y'?'是':'否'}</td>
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
<script type="text/javascript">
$(function(){
	//设置记录总数
	$("#totalCount").text('${dataList.size()}');
});
</script>