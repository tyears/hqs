<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<html>
<head>
<meta charset="utf-8">
<title>海韦力系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
<link href="css/base.css" rel="stylesheet" type="text/css">
<link href="css/index.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/base.js"></script>
<script src="js/layer.js"></script>
<script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script>
<script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>

</head>

<body>
    <div class="dongdaibox">
    	<div class="biaoti"><div><span></span><b>价格查询</b></div><h6>
            <select onchange="queryPage(this.value)" >
                <option  disabled>显示条数</option>
                <option ${pageInfo.pageSize==10?'selected':''} >10</option>
                <option ${pageInfo.pageSize==20?'selected':''} >20</option>
                <option ${pageInfo.pageSize==50?'selected':''}>50</option>
                <option ${pageInfo.pageSize==100?'selected':''}>100</option>
            </select>
        </h6></div>
        <div class="dongtai">
        	<div class="dbox">
                <div class="left dbox_none">
                    <dl>
                        <dd>
                            <form id="queryForm" action="product/productPage.html" method="post">
                                <!--<select>
                                    <option>请选择产品分类</option>
                                    <option>请选择产品分类</option>
                                    <option>请选择产品分类</option>
                                    <option>请选择产品分类</option>
                                </select>-->
                                <input type="hidden" id="pageNum" name="pageNum" value="1"/>
                                <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
                                <input placeholder="请输入货号" id="productNum" name="productNum" value="${params.productNum}">
                                <input placeholder="请输入品名" id="name" name="name" value="${params.name}">
                                <button class="chaxun" onclick="queryForm()">查询</button>
                                <button class="chongzhi" onclick="resetCondition()">重置</button>
                            </form>

                        </dd>
                    </dl>
                    <div class="bigbiaoge danwei">
                    	<table class="hov_mou">
                        	<tr>
								<th>货号</th>
                                <th>品名</th>
                                <th>规格</th>
                                <th>零售价/袋</th>
                                <th>零售价/箱</th>
                                <th>经销价/袋</th>
                                <th>经销价/箱</th>
                            </tr>
                        <c:forEach items="${pageInfo.list}" var="data">
                            <tr>
								<td class="num_font">${data.product_num}</td>
                                <td>${data.product_name}</td>
                                <td>${data.spec}</td>
                                <td>￥${empty data.retail_price_bag?0:data.retail_price_bag}</td>
                                <td>￥${empty data.retail_price_box?0:data.retail_price_box}</td>
                                <td>￥${empty data.sell_price_bag?0:data.sell_price_bag}</td>
                                <td>￥${empty data.sell_price_box?0:data.sell_price_box}</td>
                            </tr>
                        </c:forEach>
                        </table>
                        <div class="fenye">
                            <dl id="pageDiv">

                            </dl>
                        </div>
                        <div class="beizhu_box">${sessionScope.remarks.jiagezixun}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    





<script>
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
                queryForm();
            }
        }
    });
    </c:if>

    function queryForm() {
        $("#queryForm").submit();
    }
    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            queryForm();
        }
    });
    function queryPage(pn) {
        $("#pageSize").val(pn);
        queryForm();
    }

    function resetCondition(){
        $("#productNum").val("");
        $("#name").val("");
        queryForm();
    }
</script>


</body>
</html>
