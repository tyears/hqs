<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%"><input type="checkbox"/></th>
			<th width="10%">序号</th>
			<th width="25%">字典项名称</th>
			<th width="25%">字典项值</th>
			<th width="30%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="data">
		<tr class="text-c">
			<td><input class="checkBox" type="checkbox" value="${data.id}"/></td>
			<td>${data.sort}</td>
			<td>${data.name}</td>
			<td>${data.value}</td>
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