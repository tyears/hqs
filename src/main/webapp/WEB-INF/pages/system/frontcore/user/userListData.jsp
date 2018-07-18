<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="table table-border table-bordered table-bg table-hover table-sort">
	<thead>
		<tr class="text-c">
			<th width="10%">用户名</th>
			<th width="10%">姓名</th>
			<th width="7%">分机号</th>
			<th width="10%">所属部门</th>
			<th width="7%">用户类型</th>
			<th width="7%">状态</th>
			<th width="10%">最后登录时间</th>
			<th width="10%">创建时间</th>
			<th width="29%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pageInfo.list}" var="data">
		<tr class="text-l">
			<td class="text-c">${data.user_name}</td>
			<td class="text-c">${data.name}</td>
			<td class="text-c">${data.extension_num}</td>
			<td class="text-c">${data.depart_name}</td>
			<td class="text-c">
				<c:if test="${data.user_type!='1'}">
				<span class="label badge-success radius">员工</span>
				</c:if>
				<c:if test="${data.user_type=='1'}">
				<span class="label label-danger radius">经理</span>
				</c:if>
			</td>
			<td class="text-c">
				<c:if test="${data.state=='1'}">
				<span class="label badge-success radius">启用</span>
				</c:if>
				<c:if test="${data.state!='1'}">
				<span class="label label-danger radius">禁用</span>
				</c:if>
			</td>
			<td class="text-c">
				<c:if test="${empty data.last_login_time}">未登录过</c:if>
				<c:if test="${not empty data.last_login_time}"><fmt:formatDate value="${data.last_login_time}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
			</td>
			<td class="text-c"><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="text-c">
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
				<a style="text-decoration:none" class="ml-5 btn btn-secondary-outline radius size-S" onclick="updatepw('${data.id}');" title="重置密码">
					<i class="Hui-iconfont">&#xe605;</i>重置密码
				</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/common/pageInfo.jsp" %>
<script>
	function updatepw(id) {
        //询问框
        layer.confirm('是否确认修改', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.post("user/resetpassword.htm",{id:id},function (data) {
                layer.msg(data.msg,{time:1000});
            },"json")
        }, function(){

        });
    }
</script>