<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<dl>
    <dt>
        <b><a>${dealer.dealerNum}</a> ${dealer.name}
            市场情况&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;总评价：<a>${dealer.overallMerit}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;合作情况：<a>${dealer.cooperationState}</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经销商电话：<a href="javascript:window.parent.parent.callPhone('${dealer.registerTel}')">${dealer.registerTel}</a></b>
    </dt>
</dl>
<div class="bigbiaoge danwei deta">
    <div>
        <table class="hov_mouo">
            <tr>
                <th class="wh_01 " onclick="changeSortOne();">货号</th>
                <th class="wh_02">品名</th>
                <%--<th class="wh_03">赠送件数</th>--%>
                <th class="wh_04">第一次宣传</th>
                <th class="wh_05">最后宣传</th>
                <th class="wh_06">经销商宣传</th>
                <th class="wh_07">公司宣传</th>
                <th class="wh_08">通知信息数</th>
                <th class="wh_09">评价</th>
                <th class="wh_10">最后进货</th>
                <th class="wh_11">授权</th>
                <th class="wh_12">公司通知</th>
                <th class="wh_13">经销商宣传</th>
                <th class="wh_14">确定月数</th>
                <th class="wh_15">备注</th>
            </tr>
        </table>
    </div>
    <div class="over_flow_02">
        <table>
            <c:forEach items="${infoList}" var="info" varStatus="vs">
                <tr <c:if test="${vs.index%2==1}">class="bg_hui"</c:if>>
                    <td class="wh_01 num_font">${info.product_num}</td>
                    <td class="wh_02">${info.product_name}</td>
                    <%--<td class="wh_03">${info.give_num}</td>--%>
                    <td class="wh_04"><fmt:formatDate value="${info.first_time}"
                                                      pattern="yyyy-MM-dd"/></td>
                    <td class="wh_05"><fmt:formatDate value="${info.last_time}"
                                                      pattern="yyyy-MM-dd"/></td>
                    <td class="wh_06">${info.dealer_give_num}</td>
                    <td class="wh_07">${info.company_give_num}</td>
                    <td class="wh_08">${info.notice_num}</td>
                    <td class="wh_09">${info.comment}</td>
                    <td class="wh_10"><fmt:formatDate value="${info.last_purchase_time}"
                                                      pattern="yyyy-MM-dd"/></td>
                    <td class="wh_11">${info.author_dealer}</td>
                    <td class="wh_12">${not empty info.apId?info.notice_dealer1:'H0000'}</td>
                    <td class="wh_13">${info.give_dealer}</td>
                    <td class="wh_14"><fmt:formatDate value="${info.true_month_time}"
                                                      pattern="yyyy-MM-dd"/></td>
                    <td class="wh_15">
                        <div ${sessionScope.userType==1?'contenteditable="true"':''} oninput="updateRemark('${info.id}',this)">${info.remark}</div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
