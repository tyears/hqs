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
    <div class="biaoti"><div><span></span><b>经销商导入</b></div><h6>
        <form id="queryForm" action="dealerPc/toDealerImport.html" method="post">
            <input type="hidden" id="pageNum" name="pageNum" value="${param.importType}"/>
            <input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize}"/>
            <input type="hidden" id="importType1" name="importType" value="${params.importType}">
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
                        <form enctype="multipart/form-data" id="myForm" action="dealerPc/dealerImport.html" method="post">
                            <input type="hidden" id="importType" name="importType" value="${params.importType}">
                            <span>批量导入</span>
                            <input style="width:300px; border-radius:0px 4px 4px 0;" type="text" size="20"  id="upfile" placeholder="选择导入的excel表格">
                            <input class="upload_btn" type="button" value="上传" id="uploadBtn" onclick="path.click()">
                            <input type="file" id="path" name="upfile" style="display:none" >
                            <p><i>${pageInfo.total}</i>条信息</p>
                        </form>
                    </dd>
                    <dd><a href="javascript:window.open('template/templet.zip');" style="width: 100px; height: 36px; background-color: #41973c; border-radius: 4px; display: block; text-align: center; line-height: 36px; color: #fff;">下载模板</a></dd>
                    <dt class="abtn">
                        <a href="javascript:" ${params.importType==1?'class="on"':''} data-importtype="1">经销商总量月均</a>
                        <a href="javascript:" ${params.importType==2?'class="on"':''} data-importtype="2">经销商品种月均</a>
                        <a href="javascript:" ${params.importType==3?'class="on"':''} data-importtype="3">市场总量月均</a>
                        <a href="javascript:" ${params.importType==4?'class="on"':''} data-importtype="4">市场品种月均</a>
                        <a href="javascript:" ${params.importType==5?'class="on"':''} data-importtype="5">最新进货日期</a>
                        <a href="javascript:" ${params.importType==6?'class="on"':''} data-importtype="6">经销商品种授权</a>
                        <a href="javascript:" ${params.importType==7?'class="on"':''} data-importtype="7">交往记录</a>
                    </dt>
                </dl>
                <div class="bigbiaoge">
                    <c:choose>
                        <c:when test="${params.importType==1}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>经销商编号</th>
                                    <th>评价</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.dealer_num}</td>
                                        <td>${data.overall_merit}</td>
                                        <td><fmt:formatDate value="${data.import_time}" pattern="yyyy-MM-dd"/> </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${params.importType==2}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>经销商编号</th>
                                    <th>产品货号</th>
                                    <th>评价</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.dealer_num}</td>
                                        <td class="num_font">${data.product_num}</td>
                                        <td>${data.comment}</td>
                                        <td><fmt:formatDate value="${data.import_time}" pattern="yyyy-MM-dd"/> </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${params.importType==3}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>单位市场</th>
                                    <th>评价</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.area_name}</td>
                                        <td>${data.comment}</td>
                                        <td><fmt:formatDate value="${data.import_time}" pattern="yyyy-MM-dd"/> </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${params.importType==4}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>单位市场</th>
                                    <th>产品货号</th>
                                    <th>评价</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.area_name}</td>
                                        <td>${data.product_num}</td>
                                        <td>${data.comment}</td>
                                        <td><fmt:formatDate value="${data.import_time}" pattern="yyyy-MM-dd"/> </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${params.importType==5}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>经销商编号</th>
                                    <th>产品货号</th>
                                    <th>最新进货日期</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.dealer_num}</td>
                                        <td class="num_font">${data.product_num}</td>
                                        <td><fmt:formatDate value="${data.last_purchase_time}" pattern="yyyy-MM-dd"/></td>
                                        <td><fmt:formatDate value="${data.import_purchase_time}" pattern="yyyy-MM-dd"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${params.importType==6}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>授权经销商编号</th>
                                    <th>产品货号</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.author_dealer}</td>
                                        <td class="num_font">${data.product_num}</td>
                                        <td><fmt:formatDate value="${data.author_import_time}" pattern="yyyy-MM-dd"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${params.importType==7}">
                            <table class="hov_mou">
                                <tr>
                                    <th>序号</th>
                                    <th>注册手机号</th>
                                    <th>操作日期</th>
                                    <th>交往记录</th>
                                    <th>导入时间</th>
                                </tr>
                                <c:forEach items="${pageInfo.list}" var="data" varStatus="vs">
                                    <tr>
                                        <td>${vs.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                        <td>${data.orderTel}</td>
                                        <td><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd"/></td>
                                        <td>${data.content}</td>
                                        <td><fmt:formatDate value="${data.is_import_time}" pattern="yyyy-MM-dd"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>
                    <div class="fenye" id="pageDiv">
                    </div>
                    <c:if test="${pageInfo.list.size()==0}">
                        <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.jingxiaoshangdaoru}</div>
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
                    url:"<%=basePath%>dealerPc/dealerImport.html",
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
                        layer.msg(data.msg,{time:1000},function(){
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

    $(function () {
        $(".abtn a").click(function () {
            $(".abtn a").removeClass("on");
            $(this).addClass("on");
            var importType=$(this).attr("data-importtype");
            $("#importType").val(importType);
            $("#importType1").val(importType);
            queryForm();
        })
    })
</script>
</body>
</html>
