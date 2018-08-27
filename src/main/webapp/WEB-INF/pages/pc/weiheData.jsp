<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<table class="hov_mou">
    <tbody>
<tr style="cursor:pointer;">
<th>序号</th>
    <th>编号</th>
<th>手机号码</th>
<th>姓名</th>
<th>单位名称</th>
<th>其它号码</th>
<th>登记时间</th>
<th>地址</th>
<th>备注</th>
</tr>
<c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
    <tr style="cursor:pointer;" onClick="tiaozhuan('${data.id}','${empty data.register_tel?data.delivery_tel:data.register_tel}')" <c:if test="${vs.index%2==1}">class="bg_hui"</c:if>>
        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
        <td>${data.dealer_num}</td>
        <td>${data.register_tel}</td>
        <td>${data.name}</td>
        <td>${data.company_name}</td>
        <td>${data.other_tel}</td>
        <td><fmt:formatDate value="${data.create_time}" pattern="yyyy-MM-dd" /></td>
        <td>${data.postal_address}</td>
        <td>${data.remark}</td>
    </tr>
</c:forEach>
    </tbody>
</table>
<div class="fenye" id="pageDiv" style="margin-top: 12px;margin-bottom: 10px;"></div>
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
                $("#pageNum").val(e.curr);
                query();
            }
        }
    });
    </c:if>
    $.getScript("<%=basePath%>js/base.js");

    function tiaozhuan(id,orderTel) {
        console.log(orderTel);
        window.location.href="order/toChangeRecord.html?dealerId="+id+"&orderTel="+orderTel
    }
</script>
