<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
    <title>区域树</title>
    <%@include file="/common/htmlhead.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
    <link rel="stylesheet" type="text/css" href="js/zTree/v3/css/metroStyle/metroStyle.css"/>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="js/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="js/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
    <style type="text/css">
        .line{
            border-top: 0px;
        }
    </style>
</head>
<body>
<nav class="breadcrumb"></nav>
<table class="table">
    <tr>
        <td width="200px;" class="va-t">
            <div id="treeDiv" style="height:100%;overflow:auto;">
                <div class="loading"></div>
                <ul id="moduleTree" class="ztree"></ul>
            </div>
        </td>
        <td  class="va-t">
            <iframe id="contentIframe" name="contentIframe" style="overflow: scroll;" frameborder="0"  width="100%"  height="100%" src="area/toQuery.htm?fkId=0"></iframe>
        </td>
    </tr>
</table>

<div class="contextMenu" id="treeRightMenu">
    <ul>
        <li id="reload"><i class="Hui-iconfont">&#xe68f;</i>刷新</li>
        <div class="line"></div>
        <li id="closethis"><i class="Hui-iconfont">&#xe631;</i>关闭当前</li>
        <div class="line"></div>
        <li id="closeall"><i class="Hui-iconfont">&#xe706;</i>关闭全部</li>
    </ul>
</div>
<script type="text/javascript">
    var zTree;
    var setting = {
        async: {
            enable: true,
            url: "<%=basePath%>area/getAreaNodes.htm",
            autoParam: ["id"]
        },
        view: {
            dblClickExpand: true,
            showLine: true,
            showIcon: true,
            selectedMulti: false
        },
        data: {
            key: {
                name: "areaName",
                title: "areaName",
                children: "areaList"
            }
        },
        callback: {
            beforeClick: function(treeId, treeNode, clickFlag){
                if(treeNode.level>2){
                    //layer.msg("资源只能有三级");
                    return false;
                }else{
                    return true;
                }
            },
            onClick: function(event, treeId, treeNode) {
                //查询对应的资源下的菜单
                var contentIframe = $("#contentIframe");
                var src = "<%=basePath%>area/toQuery.htm?fkId="+treeNode.id+"&level="+treeNode.level;
                contentIframe.attr("src",src);
            },
            onAsyncSuccess: function(event, treeId, treeNode, msg) {
                if($(".loading").is(":visible")){
                    $(".loading").hide();
                }
                if(!treeNode){
                    var rootNode = zTree.getNodeByTId("1");
                    zTree.expandNode(rootNode, true, false, true, true);
                }
            }
        }
    };
    $(document).ready(function(){
        var zHeight = $(this.body).height();
        $("#contentIframe").css("height",zHeight-$(".breadcrumb").height()-25);
        $("#treeDiv").css("height",zHeight-$(".breadcrumb").height()-25);
        //设置GPS导航区
        var breadcrumb = $(".breadcrumb");
        if(breadcrumb&&!breadcrumb.html()){
            var currTab = $(window.top.document).find(".Hui-tabNav-wp .active");
            var header = currTab.data("header");
            breadcrumb.append(header);
        }
        zTree = $.fn.zTree.init($("#moduleTree"), setting ,null);
    });
    //重新加载选中节点数据
    function onloadNode(){
        var selectedNode = zTree.getSelectedNodes()[0];
        if(!selectedNode){
            //获取根节点
            selectedNode = zTree.getNodeByTId("1");
            //选中节点
            zTree.selectNode(selectedNode);
        }
        selectedNode.isParent = true;
        zTree.reAsyncChildNodes(selectedNode, "refresh");
        //查询对应的权限集下的权限
        var contentIframe = $("#contentIframe");
        var src = "<%=basePath%>area/toQuery.htm?fkId="+selectedNode.id+"&level="+selectedNode.level;
        contentIframe.attr("src",src);
    }
</script>
</body>
</html>