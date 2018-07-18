<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
    <title>后台管理</title>
    <%@include file="/common/htmlhead.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
    <script type="text/javascript" src="js/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl">
            <a class="logo navbar-logo f-l">海韦力平台管理系统</a>
            <nav class="nav navbar-nav nav-collapse" id="Hui-navbar">
            	<c:if test="${not empty topTabList}">
				<ul class="cl">
					<c:forEach items="${topTabList}" var="data" end="4" varStatus="status">
						<li class="${status.index==0?'current':''}" menuId="${data.id}" menu="${data.name}" icon="${data.icon_value}"><a><i class="Hui-iconfont f-18">&#${data.icon_value};</i>${data.name}</a></li>
					</c:forEach>
					<c:if test="${topTabList.size()>5}">
					<li class="dropDown dropDown_hover"><a class="dropDown_A">更多 <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<c:forEach items="${topTabList}" var="data" begin="5" varStatus="status">
							<li menuId="${data.id}" menu="${data.name}" icon="${data.icon_value}"><a><i class="Hui-iconfont f-18">&#${data.icon_value};</i>${data.name}</a></li>
							</c:forEach>
						</ul>
					</li>
					</c:if>
				</ul>
				</c:if>
			</nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>欢迎用户<i class="Hui-iconfont">&#xe60a;</i>：</li>
                    <li class="dropDown dropDown_hover">
                        <a class="dropDown_A">${sessionScope.sysUserInfo.name}<i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:updatePsw();">修改密码</a></li>
                            <li><a href="javascript:loginOut();">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover">
                        <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size: 18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <div class="menu_dropdown bk_2" id="leftMenu">
	    <dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i>测试管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="sysModule/toModuleTreeManager.htm" data-title="测试菜单" href="javascript:void(0)">测试菜单</a></li>
					<li><a data-href="sysUser/toQuery.htm" data-title="测试菜单1" href="javascript:void(0)">测试菜单1</a></li>
					<li><a data-href="sysRole/toQuery.htm" data-title="测试菜单2" href="javascript:void(0)">测试菜单2</a></li>
				</ul>
			</dd>
		</dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs">
    <a class="pngfix" onClick="displaynavbar(this)"></a>
</div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <!-- 这里是默认页面桌面start 如果不需要删除就行-->
                <li class="active">
                    <span title="我的桌面" data-href="toDesktop.htm">我的桌面</span>
                    <em></em>
                </li>
                <!-- 这里是默认页面桌面end -->
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group">
            <a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a>
            <a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
        </div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <!-- 这里是默认页面桌面start 如果不需要删除就行-->
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="toDesktop.htm"></iframe>
        </div>
        <!-- 这里是默认页面桌面end -->
    </div>
</section>
<div class="contextMenu" id="Huiadminmenu">
    <ul>
        <li id="reload"><i class="Hui-iconfont">&#xe68f;</i>刷新</li>
        <div class="line"></div>
        <li id="closethis"><i class="Hui-iconfont">&#xe631;</i>关闭当前</li>
        <div class="line"></div>
        <li id="closeall"><i class="Hui-iconfont">&#xe706;</i>关闭全部</li>
    </ul>
</div>
</body>
<script type="text/javascript">
    $(function () {
        //限制index不能再iframe中打开
        if (window != top) {
            window.top.location.href = "<%=basePath%>toIndex.htm";
        }
    });
    //修改密码
    function updatePsw() {
        var index = layer.open({
            type: 2,
            title: "<i class='Hui-iconfont'>&#xe60c;</i>修改密码",
            maxmin: false,
            area: ['600px', '400px'],
            btn: ["<i class='Hui-iconfont'>&#xe632;</i>确定", "取消"],
    		yes: function(index, layero){
    			var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
    			iframeWin.save();
    	    },
        	//按钮【按钮二】的回调
    	    cancel: function(index, layero){
    	    	layer.close(index);
    	  	},
            content: "<%=basePath%>toUpdatePsw.htm",
        });
    }
    //退出
    function loginOut() {
        layer.confirm('确定退出？', {icon: 3, title: '提示'}, function (index) {
            location.href = "<%=basePath%>loginOut.htm"
        });
    }
</script>
</html>