<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<html>
<head>
    <title>海韦力系统</title>
    <%@include file="/common/htmlhead.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <link href="css/base.css" rel="stylesheet" type="text/css">
    <link href="css/index.css" rel="stylesheet" type="text/css">
    <link href="css/style01.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
</head>

<body>

<div class="dongdaibox">
    <div class="biaoti"><div><span></span><b>留言导入</b></div><h6>
        <form id="queryForm" action="order/toLiuYan.html" method="post">
            <input type="hidden" id="pageNum" name="pageNum" value="1"/>
            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
        </form>
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
                        <form enctype="multipart/form-data" id="myForm" action="order/uploadExcel.html" method="post">
                            <span>批量导入</span>
                            <input style="width:300px; border-radius:0px 4px 4px 0;" type="text" size="20"  id="upfile" placeholder="选择导入的excel表格">
                            <input class="upload_btn" type="button" value="上传" id="uploadBtn" onclick="path.click()">
                            <input type="file" id="path" name="upfile" style="display:none" >
                            <p><i>${pageInfo.total}</i>条信息</p>
                        </form>
                    </dd>
                    <dd><a href="javascript:window.open('template/templet.xlsx');" style="width: 100px; height: 36px; background-color: #41973c; border-radius: 4px; display: block; text-align: center; line-height: 36px; color: #fff;">下载模板</a></dd>
                </dl>
                <div class="bigbiaoge">
                    <table class="hov_mou">
                        <tr>
                            <th>序号</th>
                            <th>客户类型</th>
                            <th>留言类别</th>
                            <th>手机号码</th>
                            <th>姓名</th>
                            <th>号码归属地</th>
                            <th>客户留言</th>
                            <th>地址</th>
                            <th>咨询产品</th>
                            <th>操作员</th>
                            <th>导入日期</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach items="${pageInfo.list}" var="order" varStatus="orderSta">
                        <tr>
                            <td>${orderSta.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                            <td>
                                <c:if test="${order.dealerType=='0'}">普通客户</c:if>
                                <c:if test="${order.dealerType=='1'}">经销商</c:if>
                                <c:if test="${order.dealerType=='2'}">面粉厂</c:if>
                                <c:if test="${order.dealerType=='3'}">食品厂</c:if>
                                <c:if test="${order.dealerType=='4'}">其他</c:if>
                            </td>
                            <td>${order.messageType}</td>
                            <td>${order.tel}</td>
                            <td>${order.name}</td>
                            <td>${order.numberAttribution}</td>
                            <td style="text-align:left;">${order.dealerMessage}</td>
                            <td style="text-align:left;">${order.address}</td>
                            <td style="text-align:left;">${order.exProductNames}</td>
                            <td>${order.userName}</td>
                            <td><fmt:formatDate value="${order.importTime}" pattern="yyyy-MM-dd"/> </td>
                            <td><a class="del_btn" href="javascript:deleteData('${order.id}')">删除</a></td>
                        </tr>
                        </c:forEach>
                    </table>
                    <div class="fenye" id="pageDiv">
                    </div>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.liuyandaoru}</div>
    </div>
</div>
<script src="js/base.js"></script>
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
                queryForm();
            }
        }
    });
    </c:if>

    function queryForm() {
        $("#queryForm").submit();
    }

    function queryPage(pn) {
        $("#pageSize").val(pn);
        queryForm();
    }


    $(function () {
        $('#path').change(function(){

            if(checkData()){
                layer.load();
                $("#myForm").ajaxSubmit({
                    url:"<%=basePath%>order/uploadExcel.html",
                    dataType:"json",
                    success:function(data){
                        if(data.result){
                            layer.msg(data.msg, {
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            }, function(){
                                var index = layer.getFrameIndex(window.name);
                                layer.close(index);
                                queryForm();
                            });
                        }else{
                            layer.msg(data.msg, {
                                icon: 2,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }
                    },
                    error:function(){
                        layer.alert("提示：系统内部出现问题！");
                    }
                });
            }
        });
    });


        //JS校验form表单信息
        function checkData(){
            var fileDir = $("#path").val();
            var suffix = fileDir.substr(fileDir.lastIndexOf("."));
            if("" == fileDir){
                layer.msg("请选择需要导入的Excel文件！", {
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                $("#path").val("");
                return false;
            }
            if(".xls" != suffix && ".xlsx" != suffix ){
                layer.msg("请选择Excel格式的文件导入！", {
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                $("#path").val("");
                return false;
            }
            $("#upfile").val(fileDir);
            return true;
        }

    /*删除*/
    function deleteData(id){
        var layerIndex = layer.confirm('确认要删除吗？',{icon:3},function(index){
            $.ajax({
                type:"post",
                url:"<%=basePath%>order/deleteData.html",
                dataType:"json",
                async:false,
                data:{"id":id},
                success:function(data){
                    layer.close(layerIndex);
                    if(data.result){
                        layer.msg(data.msg,{icon:1,time:1000},function(){
                        queryForm();
                        });
                    }else{
                        layer.msg(data.msg, {icon:2,time:2000});
                        return;
                    }
                },
                error:function(){
                    layer.alert("提示：系统内部出现问题！");
                }
            });
        });
    }
</script>
</body>
</html>
