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
    <script src="js/base.js"></script>
    <script src="js/layer.js"></script>
</head>

<body>
	<div class="left_top">
    	<iframe id="left_top" src="order/toLeftTop.html" frameborder="0" width="100%" scrolling="no" ></iframe>
</div>
    <user:rights funCode="Fun_JSFW">
<div class="dongdaibox">
    <div class="biaoti">
        <div><span></span><b>技术服务</b></div>
    </div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left lefton">
                <dl >
                    <dd><p ><i >分类：</i>
                        <a href="javascript:changeArticleList('')" class="on" id="allType">全部</a>
                        <c:forEach items="${articleTypeList}" var="articleType"><a href="javascript:changeArticleList('${articleType.id}')">${articleType.typeName}</a></c:forEach>
                    </p></dd>
                    <dt > <input placeholder="请输入文章标题" id="articleTitle" style=" width:230px; height:32px; background-color:#f5f5f5; border:1px solid #ededed; border-radius:4px; color:#555; padding-left:6px;"  >
                        <button class="chaxun" onclick="changeArticleList('0');" >查询</button>
                        <button class="chongzhi" type="button" onclick="resetKey();">重置</button>
                    </dt>
                </dl>
                <ul id="articleList">
                    <c:forEach items="${articleList}" var="article">
                        <li style=" overflow:hidden;"><a style="display: block; float: left; max-width: 86%; overflow:hidden; white-space:nowrap; text-overflow: ellipsis;" href="javascript:changeArticle('${article.id}')">${article.title}</a><b style=" display: block; float:left;margin-left: 10px;color: red" class="sms${article.id}">${article.smsNum}</b></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="zhengwenbox">
            <div class="zhengwen" id="article">
                <h2>${firstArticle.title}<b style="color: red;margin-left: 10px;" class="sms${firstArticle.id}">${firstArticle.smsNum}</b></h2>
                <p>${firstArticle.content}</p>
            </div>
            <div class="dx_box">
                <input id="typeId" type="hidden" />
                <input type="hidden" id="articleId" value="${firstArticle.id}"/>
                <div class="dx_00 dx_01">
                    <%--<p>短信答复</p>--%>
                    <textarea id="replySms" style="float: left;width: 81%;margin-right: 1%;border: 1px solid #ededed;height: 100px; resize: none;padding: 5px;border-radius: 5px;">${firstArticle.replySms}</textarea>
                    <%--<div contenteditable="true" id="replySms"></div>--%>
                    <button style="width: 97px;" onclick="duanxin('replySms')">短信答复1</button>
                </div>
                <div class="dx_00 dx_02">
                    <%--<p>扫描二维码</p>--%>
                    <textarea id="promptSms" style="float: left;width: 81%;margin-right: 1%;border: 1px solid #ededed;height: 100px; resize: none;padding: 5px;border-radius: 5px;">${firstArticle.promptSms}</textarea>
                    <%--<div contenteditable="true" id="promptSms">${firstArticle.promptSms}</div>--%>
                    <button style="width: 97px;" onclick="duanxin('promptSms')">短信答复2</button>
                </div>
            </div>

        </div>
        <div class="beizhu_box">${sessionScope.remarks.jishufuwu}</div>

    </div>
</div>
    </user:rights>

    <style>
        .dongdaibox .dongtai .dbox .left dl dd p a.on{ color: red;}
    </style>
    <script>
        $(function(){
            $(".dongdaibox .dongtai .dbox .left dl dd p a").click(function () {
                $(this).addClass('on').siblings('a').removeClass('on')
        })
        })
    </script>

<!--弹窗-->
<div class="heibg"></div>
<!--弹窗-->
<script type="text/javascript">
    function duanxin(divid) {
        layer.confirm('确认要发送吗？',{icon:3},function(index){
            var text = $("#"+divid).val();
            var lo = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.post("<%=basePath%>sendOutSms.html",{text:text,articleId:$("#articleId").val()},function (data) {
                layer.close(lo);
                if (data.result) {
                    layer.msg(data.msg, {
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        if(data.obj){
                            $(".sms"+$("#articleId").val()).each(function () {
                                $(this).text(data.obj)
                            })
                        }
                    });
                } else {
                    layer.msg(data.msg, {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                layer.msg(data.msg,{time:1500});
            },"json");
        });
    }

    function changeArticleList(typeId) {
            if(typeId!='0'){
                $("#typeId").val(typeId);
            }
        var lo = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            type: "post",
            url: "<%=basePath%>queryArticleList.html",
            dataType: "json",
            async: true,
            data: {"articleTypeId":$("#typeId").val(),articleTitle:$("#articleTitle").val()},
            success: function (data) {
                layer.close(lo);
                if (data.result) {
                    var html = '';
                    for (var i = 0; i < data.obj.length; i++) {
                        html += '<li style=" overflow:hidden;"><a style="display: block; float: left; max-width: 86%; overflow:hidden; white-space:nowrap; text-overflow: ellipsis;" href="javascript:changeArticle(' + data.obj[i].id + ')">' + data.obj[i].title + '</a><b style=" display: block; float:left;margin-left: 10px;color: red" class="sms'+data.obj[i].id+'">'+data.obj[i].smsNum+'</b></li>';
                    }
                    $("#articleList").html(html);
                }
            },
            error: function () {
                parent.layer.alert("提示：系统内部出现问题！");
            }
        })
    }

    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            changeArticleList('0');
        }
    });
    function resetKey() {
        $("#articleTitle").val('');
        $("#typeId").val('');
        $(".dongdaibox .dongtai .dbox .left dl dd p a").siblings('a').removeClass('on');
        $("#allType").addClass("on");
        changeArticleList("");
    }

    function changeArticle(id) {
        $.ajax({
            type: "post",
            url: "<%=basePath%>queryArticle.html",
            dataType: "json",
            async: true,
            data: {"id": id,},
            success: function (data) {
                if (data.result) {
                    var html = '';
                    html += '<h2>' + data.obj.title + '<b style="color: red;margin-left: 10px;" class="sms'+data.obj.id+'">'+data.obj.smsNum+'</b></h2>';
                    html += '<p>' + data.obj.content + '</p>';
                    $("#article").html(html);
                    $("#replySms").val(data.obj.replySms);
                    $("#promptSms").val(data.obj.promptSms);
                    $("#articleId").val(id);
                }
            },
            error: function () {
                parent.layer.alert("提示：系统内部出现问题！");
            }
        })
    }
</script>
</body>
</html>
