<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<table>
    <tr><th>操作</th><th>编号</th><th>手机号码</th><th>姓名</th><th>单位名称</th><th>其它号码</th><th>登记时间</th><th>地址</th><th>备注</th></tr>
    <c:forEach items="${pageInfo.list}" var="data">
        <tr>
            <td><input type="radio" name="mTd" value="${data.id}"></td>
            <td>${data.dealer_num}</td>
            <td>${data.register_tel}</td>
            <td>${data.name}</td>
            <td>${data.company_name}</td>
            <td>${data.other_tel}</td>
            <td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd" /></td>
            <td>${data.delivery_address}</td>
            <td>${data.remark}</td>
        </tr>
    </c:forEach>
</table>
<div class="fenye" id="pageDiv"></div>
<script type="text/javascript">
    <c:if test="${not empty pageInfo}">
    laypage({
        cont: 'pageDiv',
        pages: '${pageInfo.pages}', //总页数
        curr: '${pageInfo.pageNum}', //当前页
        groups: 5,//连续显示页数
        first:'首页',
        last:'尾页',
        prev: '&lt;',
        next: '&gt;',
        skin: '#41973c',//皮肤，目前支持：molv、yahei、flow 和16进制颜色值
        skip: false,//是否支持跳转页
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#pageNumMatch").val(e.curr);
                queryDeaterData();
            }
        }
    });
    </c:if>
</script>
