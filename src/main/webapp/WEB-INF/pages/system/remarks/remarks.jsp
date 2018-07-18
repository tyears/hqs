<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<%@ taglib prefix="dictionary" uri="/dictionary-tags"%>
<html>
<head>
    <title>后台用户编辑</title>
    <%@include file="/common/htmlhead.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/jquery.validation/1.14.0/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/jquery.validation/1.14.0/validate-methods.js"></script>
    <script type="text/javascript" src="js/jquery.validation/1.14.0/messages_zh.min.js"></script>
    <script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
    <style type="text/css">
        .form-horizontal .form-label {text-align: right;}
        .row{width: 90%;}
        .textarea{ height: 110px;}
        .page-container{ overflow: hidden;}
        .page-container .row{ width: 48%; float: left; margin-right: 2%;}
    </style>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" method="post" action="saveRemarks.htm" id="myForm">
        <c:forEach items="${list}" var="l">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">${l.make}：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <textarea type="text" class="textarea" oninput="save('${l.id}',this)" autocomplete="off" placeholder="${l.make}" >${l.text}</textarea>
                </div>
            </div>
        </c:forEach>
    </form>
</article>
<script type="text/javascript">
    function save(id,obj) {
        var text = $(obj).val();
        $.post("remarks/updateRemark.htm",{id:id,text:text},function (data) {
            
        },"json");
    }
</script>
</body>
</html>