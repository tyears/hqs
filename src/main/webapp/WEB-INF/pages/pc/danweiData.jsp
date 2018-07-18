<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/common/jsphead.jsp" %>
<jsp:useBean id="now" class="java.util.Date" scope="page"></jsp:useBean>
<div id="big_box">
    <div id="leftBox">
        <div class="leftTop_box">
            <div class="thTop1">
                <table>
                    <tr>
                        <th class="new_wi01" onclick="changeSort();">货号</th>
                        <th class="new_wi02">品名</th>
                        <th class="new_wi03">授权</th>
                        <th class="new_wi04">市场评价</th>
                        <th class="new_wi05">公司通知</th>
                        <th class="new_wi06">经销商宣传</th>
                        <th class="new_wi07">有效时间</th>
                    </tr>
                </table>
            </div>
            <div class="thMiddle1" onScroll="moveUp_Left();" >
                <table>
                    <c:forEach items="${productList}" var="product" varStatus="vs">
                    <tr>
                        <td class="new_wi01 num_font">${product.productNum}</td>
                        <td class="new_wi02">${product.productName}</td>
                        <c:if test="${empty product.author_dealer}">
                        <td class="new_wi03" onClick="layer.msg('没有经销商')">${product.author_dealer}</td>
                        </c:if>
                        <c:if test="${not empty product.author_dealer}">
                            <td class="new_wi03" onClick="location.href='dealerPc/toDealerDetail.html?dealerId=${product.author_dealer_id}'">${product.author_dealer}</td>
                        </c:if>
                        <td class="new_wi04">${product.comment}</td>
                        <c:if test="${empty product.notice_dealer1}">
                            <c:if test="${empty product.id}">
                                <td class="new_wi05" onClick="zsjxOnclick('${product.productId}','${product.productNum} ${product.productName}');">H0000</td>
                            </c:if>
                            <c:if test="${not empty product.id}">
                                <td class="new_wi05" onClick="layer.msg('没有经销商')">${product.notice_dealer1}</td>
                            </c:if>
                        </c:if>
                        <c:if test="${not empty product.notice_dealer1}">
                            <c:if test="${product.notice_dealer1=='H0000'}">
                                <td class="new_wi05" onClick="zsjxOnclick('${product.productId}','${product.productNum} ${product.productName}');">${product.notice_dealer1}</td>
                            </c:if>
                            <c:if test="${product.notice_dealer1!='H0000'}">
                                <td class="new_wi05" onClick="location.href='dealerPc/toDealerDetail.html?dealerId=${product.notice_dealer_id}'">${product.notice_dealer1}</td>
                            </c:if>
                        </c:if>
                        <c:if test="${empty product.give_dealer}">
                            <td class="new_wi06" onClick="layer.msg('没有经销商')">${product.give_dealer}</td>
                        </c:if>
                        <c:if test="${not empty product.give_dealer}">
                            <td class="new_wi06" onClick="location.href='dealerPc/toDealerDetail.html?dealerId=${product.give_dealer_id}'">${product.give_dealer}</td>
                        </c:if>
                        <td class="new_wi07" ${product.effect_time.getTime() - now.getTime() - 15*24*60*60*1000 < 0 ? 'style="color:red;"' : ''}><fmt:formatDate value="${product.effect_time}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="leftBottom_box">
            <table>
                <tr>
                    <td class="new_wi01"></td>
                    <td class="new_wi02"></td>
                    <td class="new_wi03"></td>
                    <td class="new_wi04"></td>
                    <td class="new_wi05"></td>
                    <td class="new_wi06"></td>
                    <td class="new_wi07">总评价</td>
                </tr>
                <tr>
                    <td class="new_wi01 ovXzhi"></td>
                    <td class="new_wi02 ovXzhi"></td>
                    <td class="new_wi03 ovXzhi"></td>
                    <td class="new_wi04 ovXzhi"></td>
                    <td class="new_wi05 ovXzhi"></td>
                    <td class="new_wi06 ovXzhi"></td>
                    <td class="new_wi07 ovXzhi ovHe">合作情况</td>
                </tr>
                <tr>
                    <td class="new_wi01"></td>
                    <td class="new_wi02"></td>
                    <td class="new_wi03"></td>
                    <td class="new_wi04"></td>
                    <td class="new_wi05"></td>
                    <td class="new_wi06"></td>
                    <td class="new_wi07">全选<input id="all" name="a1" type="checkbox"></td>
                </tr>
            </table>
        </div>
    </div>
    <div id="rightBox">
        <div class="rightLittle_box">
            <div class="rightTop_box">
                <div class="thTop2">
                    <table>
                        <tr>
                            <c:forEach items="${dealerList}" var="dealer">
                                <th class="new_wi08" onClick="location.href='dealerPc/toDealerDetail.html?dealerId=${dealer.id}'"><p style="line-height: 21px;overflow: hidden;  white-space: nowrap;">${dealer.dealerNum}<br>${dealer.name}</p></th>
                            </c:forEach>

                            <c:set var="setNum" value="${14-dealerList.size()}"/>
                            <c:if test="${setNum>0}">
                                <c:forEach begin="1" end="${setNum}" >
                                    <th class="new_wi08"></th>
                                </c:forEach>
                            </c:if>
                        </tr>
                    </table>
                </div>
                <div class="thMiddle2" onScroll="moveUp_Right();">
                    <table>
                    <c:forEach items="${productList}" var="product" varStatus="vs">
                        <tr>
                            <c:forEach items="${dealerList}" var="dealer">
                                <td class="new_wi08">
                                    <c:forEach items="${dealer.dpList}" var="dp">
                                        <c:if test="${dp.productId==product.productId}">${dp.comment}</c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                            <c:if test="${setNum>0}">
                                <c:forEach begin="1" end="${setNum}" >
                                    <td class="new_wi08"></td>
                                </c:forEach>
                            </c:if>
                        </tr>
                    </c:forEach>

                    </table>
                </div>
            </div>
            <div class="rightBottom_box">
                <table>
                    <tr>
                        <c:forEach items="${dealerList}" var="dealer">
                            <td class="new_wi08" style="word-break: break-all;cursor: pointer;">${dealer.overallMerit}</td>
                        </c:forEach>
                        <c:if test="${setNum>0}">
                            <c:forEach begin="1" end="${setNum}" >
                                <td class="new_wi08" style="word-break: break-all;cursor: pointer;"></td>
                            </c:forEach>
                        </c:if>
                    </tr>
                    <tr>
                        <c:forEach items="${dealerList}" var="dealer">
                            <%--<td class="se_bg w_08" ${sessionScope.userType==1?'contenteditable="true"':''} oninput="pingtext('${dealer.id}','1',this)"--%>
                            <%--style="word-break: break-all;cursor: pointer;"><span title="${dealer.cooperationState}">${dealer.cooperationState}</span></td>--%>
                            <td class="new_wi08 ovXzhi"
                                style="word-break: break-all;cursor: pointer;"><span title="${dealer.cooperationState}">${dealer.cooperationState}</span></td>
                        </c:forEach>
                        <c:if test="${setNum>0}">
                            <c:forEach begin="1" end="${setNum}" >
                                <td class="new_wi08 ovXzhi" style="word-break: break-all;cursor: pointer;"><span></span></td>
                            </c:forEach>
                        </c:if>
                    </tr>
                    <tr>
                        <c:forEach items="${dealerList}" var="dealer" varStatus="vs">
                            <c:choose>
                                <c:when test="${vs.last==true}">
                                    <td class="new_wi08"><input name='a' type="checkbox" data-dealerid="${dealer.id}" dealerTel="${dealer.registerTel}" smsTel="${dealer.smsTel}" value="${dealer.smsAddress}，${dealer.name}${dealer.registerTel}。"></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="new_wi08"><input name='a' type="checkbox" data-dealerid="${dealer.id}" dealerTel="${dealer.registerTel}" smsTel="${dealer.smsTel}" value="${dealer.smsAddress}，${dealer.name}${dealer.registerTel};"></td>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                        <c:if test="${setNum>0}">
                            <c:forEach begin="1" end="${setNum}" >
                                <td class="new_wi08"></td>
                            </c:forEach>
                        </c:if>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="btnbox">
    <button class="faduanxin faduanxin1" onclick="uptSms(false)">给客户发短信</button>
    <button class="faduanxin faduanxin2" onclick="tzDealler()">通知经销商</button>
    <button id="chongzhi">重置</button>
</div>

<script type="text/javascript">
    $.getScript("<%=basePath%>js/base.js");

    function pingtext(id,type,obj) {    // 0:总评价  1:合作情况
        var text = $(obj).text();
        text=$.trim(text);
        $.post("dealerPc/updatePingText.html", {id: id, text:text, type: type}, function (data) {

        },"json")
    }
</script>
<script>
    //JS代码
    var timer = null;

    //左侧DIV的滚动事件
    function moveUp_Left()
    {
        //先删除右侧DIV的滚动事件，以免自动触发
        $("#big_box .rightTop_box .thMiddle2").removeAttr("onScroll");

        //正常设值，同步两个DIV的滚动幅度
        $("#big_box .rightTop_box .thMiddle2").scrollTop($("#big_box  .leftTop_box .thMiddle1").scrollTop());

        //取消延迟预约。【重点】鼠标滚动过程中会多次触发本行代码，相当于不停的延迟执行下面的预约
        clearTimeout(timer);

        //延迟恢复（预约）另一个DIV的滚动事件，并将本预约返回给变量[timer]
        timer = setTimeout(function() {
            $("#big_box .rightTop_box .thMiddle2").attr("onScroll","moveUp_Right();");
        }, 300 );
    }

    //右侧DIV的滚动事件（原理同上）
    function moveUp_Right()
    {
        $("#big_box  .leftTop_box .thMiddle1").removeAttr("onScroll");
        $("#big_box  .leftTop_box .thMiddle1").scrollTop($("#big_box .rightTop_box .thMiddle2").scrollTop());
        clearTimeout(timer);
        timer = setTimeout(function() {
            $("#big_box  .leftTop_box .thMiddle1").attr("onScroll","moveUp_Left();");
        }, 300 );
    }
</script>

<script>
    $(function(){

        var num = 0;
        var n = $("#big_box #rightBox .thTop2 th").length;
        for(var i=0;i<n;i++){
            var nn = $("#big_box #rightBox .thTop2 th").eq(i).width();
            num += nn;
            /*num+=1;*/
        }
        $("#big_box #rightBox .rightLittle_box").width(num-n);
        $("#big_box #rightBox .rightLittle_box .thMiddle2").width(num-n);


        //鼠标经过
        $(".thMiddle1 tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".thMiddle1 tr").eq(b).addClass('active1')
            $(".thMiddle2 tr").eq(b).addClass('active1')
            $(".thTop1 th").eq(a).addClass('active1')
            $(".thMiddle1 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".leftBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".thMiddle2 tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();  //行
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".thMiddle1 tr").eq(b).addClass('active1')
            $(".thMiddle2 tr").eq(b).addClass('active1')
            $(".thTop2 th").eq(a).addClass('active1')
            $(".thMiddle2 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".rightBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".rightBottom_box tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();  //行
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".rightBottom_box tr").eq(b).addClass('active1')
            $(".leftBottom_box tr").eq(b).addClass('active1')
            $(".thTop2 th").eq(a).addClass('active1')
            $(".thMiddle2 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".rightBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".leftBottom_box tr td").mouseenter(function(){
            var a = $(this).index();
            var b= $(this).parent('tr').index();  //行
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
            $(".rightBottom_box tr").eq(b).addClass('active1')
            $(".leftBottom_box tr").eq(b).addClass('active1')
            $(".thTop1 th").eq(a).addClass('active1')
            $(".thMiddle1 tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
            $(".leftBottom_box tr").each(function(){
                $(this).find('td').eq(a).addClass('active1')
            })
        })
        $(".bigbiaoge").mouseleave(function(){
            $(".bigbiaoge  tr,.bigbiaoge  tr td,.bigbiaoge  tr th").removeClass('active1')
        })

    })
</script>