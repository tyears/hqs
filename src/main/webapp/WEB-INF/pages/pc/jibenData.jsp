<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<table class="hov_mou">
    <tr>
        <th class="zhai">日期</th>
        <th>内容</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
        <tr <c:if test="${vs.index%2==1}">class="bg_hui"</c:if>>
            <td class="zhai"><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td style="text-align:left">${data.content}${not empty data.userId?'---':''}${data.userName}</td>
            <td><a style="padding-right: 10px;" href="javascript:void(0)" onclick="del('${data.id}')">删除</a><a style="padding-right: 10px;" href="javascript:void(0)" onclick="addItem('${data.id}','${data.content}')">编辑</a></td>
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
                $("#pageNum").val(e.curr);
                query();
            }
        }
    });
    </c:if>
    $.getScript("<%=basePath%>js/base.js");

    function del(id) {
        $.post("<%=basePath%>order/delrecord.html",{id:id},function (data) {
            if(data.result){
                layer.msg(data.msg,{time:1000});
                query();
            }else{
                layer.msg(data.msg,{time:1000,icon:2});
            }
        },"json")
    }
    function edit(id) {
        $.post("<%=basePath%>order/editRecord.html",{id:id},function (data) {
            if(data.result){
                layer.msg(data.msg,{time:1000});
                query();
            }else{
                layer.msg(data.msg,{time:1000,icon:2});
            }
        },"json")
    }
</script>
