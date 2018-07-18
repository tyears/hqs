/* -----------H-ui前端框架-------------
* H-ui.admin.js v3.0
* http://www.h-ui.net/
* Created & Modified by guojunhui
* Date modified 2017.02.03
* Copyright 2013-2017 北京颖杰联创科技有限公司 All rights reserved.
* Licensed under MIT license.
* http://opensource.org/licenses/MIT
*/
var num=0,oUl=$("#min_title_list"),hide_nav=$("#Hui-tabNav");

/*获取顶部选项卡总长度*/
function tabNavallwidth(){
	var taballwidth=0,
		$tabNav = hide_nav.find(".acrossTab"),
		$tabNavWp = hide_nav.find(".Hui-tabNav-wp"),
		$tabNavitem = hide_nav.find(".acrossTab li"),
		$tabNavmore =hide_nav.find(".Hui-tabNav-more");
	if (!$tabNav[0]){return}
	$tabNavitem.each(function(index, element) {
        taballwidth += Number(parseFloat($(this).width()+60))
    });
	$tabNav.width(taballwidth+25);
	var w = $tabNavWp.width();
	if(taballwidth+25>w){
		$tabNavmore.show()}
	else{
		$tabNavmore.hide();
		$tabNav.css({left:0})
	}
}

/*左侧菜单响应式*/
function Huiasidedisplay(){
	if($(window).width()>=768){
		$(".Hui-aside").show()
	} 
}
/*获取皮肤cookie*/
function getskincookie(){
	var v = $.cookie("Huiskin");
	var hrefStr=$("#skin").attr("href");
	if(v==null||v==""){
		v="default";
	}
	if(hrefStr!=undefined){
		var hrefRes=hrefStr.substring(0,hrefStr.lastIndexOf('skin/'))+'skin/'+v+'/skin.css';
		$("#skin").attr("href",hrefRes);
	}
}
/*菜单导航*/
function Hui_admin_tab(obj){
	var bStop = false,
		bStopIndex = 0,
		href = $(obj).attr('data-href'),
		title = $(obj).attr("data-title"),
		topWindow = $(window.parent.document),
		show_navLi = topWindow.find("#min_title_list li"),
		iframe_box = topWindow.find("#iframe_box");
	//console.log(topWindow);
	if(!href||href==""){
		alert("data-href不存在，v2.5版本之前用_href属性，升级后请改为data-href属性");
		return false;
	}if(!title){
		alert("v2.5版本之后使用data-title属性");
		return false;
	}
	if(title==""){
		alert("data-title属性不能为空");
		return false;
	}
	show_navLi.each(function() {
		if($(this).find('span').attr("data-href")==href){
			bStop=true;
			bStopIndex=show_navLi.index($(this));
			return false;
		}
	});
	if(!bStop){
		creatIframe(obj,href,title);
		min_titleList();
	}
	else{
		show_navLi.removeClass("active").eq(bStopIndex).addClass("active");			
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show().find("iframe").attr("src",href);
	}	
}

/*最新tab标题栏列表*/
function min_titleList(){
	var topWindow = $(window.parent.document),
		show_nav = topWindow.find("#min_title_list"),
		aLi = show_nav.find("li");
}

/*创建iframe*/
function creatIframe(obj,href,titleName){
	//自己添加的start
	var parentMenu = $(obj).attr("data-parentMenu");
	var parentMenuIcon = $(obj).attr("data-parentMenuIcon");
	var topTab = "";
	var topTabIcon = "";
	var currentTopTab = $("#Hui-navbar .current");
	if(currentTopTab){
		if(currentTopTab.hasClass("dropDown")){//更多
			topTab = currentTopTab.find(".on").attr("menu");
			topTabIcon = currentTopTab.find(".on").attr("icon");
		}else{
			topTab = currentTopTab.attr("menu");
			topTabIcon = currentTopTab.attr("icon");
		}
	}else{
		console.log("头部导航丢失");
	}
	var header = "";
	if(parentMenu&&parentMenuIcon&&topTab&&topTabIcon){
		header = "<i class='Hui-iconfont'>&#"+topTabIcon+";</i>"+topTab;
		header += "<span class='c-gray en'>&gt;</span>";
		header += "<i class='Hui-iconfont'>&#"+parentMenuIcon+";</i>"+parentMenu;
		header += "<span class='c-gray en'>&gt;</span>";
		header += titleName;
		header += "<a id='refash' class='btn btn-success radius r' style='line-height:1.6em;margin-top:3px' href='javascript:location.replace(location.href);' title='刷新' >";
		header += "<i class='Hui-iconfont'>&#xe68f;</i></a>";
	}else{
		console.log("未能获取完整的导航信息");
	}
	//自己添加的end
	
	var topWindow=$(window.parent.document),
		show_nav=topWindow.find('#min_title_list'),
		iframe_box=topWindow.find('#iframe_box'),
		iframeBox=iframe_box.find('.show_iframe'),
		$tabNav = topWindow.find(".acrossTab"),
		$tabNavWp = topWindow.find(".Hui-tabNav-wp"),
		$tabNavmore =topWindow.find(".Hui-tabNav-more");
	var taballwidth=0;
		
	show_nav.find('li').removeClass("active");	
	show_nav.append('<li class="active" data-header="'+header+'"><span data-href="'+href+'">'+titleName+'</span><i></i><em></em></li>');
	if('function'==typeof $('#min_title_list li').contextMenu){
		$("#min_title_list li").contextMenu('Huiadminmenu', {
			bindings: {
				'closethis': function(t) {
					var $t = $(t);				
					if($t.find("i")){
						$t.find("i").trigger("click");
					}
				},
				'closeall': function(t) {
					$("#min_title_list li i").trigger("click");
				},
				'reload': function(t) {
					var bStopIndex = $(t).index();
					var iframe_box = $("#iframe_box");
					$("#min_title_list li").removeClass("active").eq(bStopIndex).addClass("active");
					iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();
					var iframe = $(iframe_box.find(".show_iframe").eq(bStopIndex)).find("iframe");
					iframe.attr("src",iframe.attr("src"));
				}
			}
		});
	}else {
		
	}	
	var $tabNavitem = topWindow.find(".acrossTab li");
	if (!$tabNav[0]){return}
	$tabNavitem.each(function(index, element) {
        taballwidth+=Number(parseFloat($(this).width()+60))
    });
	$tabNav.width(taballwidth+25);
	var w = $tabNavWp.width();
	if(taballwidth+25>w){
		$tabNavmore.show()}
	else{
		$tabNavmore.hide();
		$tabNav.css({left:0})
	}	
	iframeBox.hide();
	iframe_box.append('<div class="show_iframe"><div class="loading"></div><iframe frameborder="0" src='+href+'></iframe></div>');
	var showBox=iframe_box.find('.show_iframe:visible');
	showBox.find('iframe').load(function(){
		showBox.find('.loading').hide();
	});
}



/*关闭iframe*/
function removeIframe(){
	var topWindow = $(window.parent.document),
		iframe = topWindow.find('#iframe_box .show_iframe'),
		tab = topWindow.find(".acrossTab li"),
		showTab = topWindow.find(".acrossTab li.active"),
		showBox=topWindow.find('.show_iframe:visible'),
		i = showTab.index();
	tab.eq(i-1).addClass("active");
	tab.eq(i).remove();
	iframe.eq(i-1).show();	
	iframe.eq(i).remove();
}

/*关闭所有iframe*/
function removeIframeAll(){
	var topWindow = $(window.parent.document),
		iframe = topWindow.find('#iframe_box .show_iframe'),
		tab = topWindow.find(".acrossTab li");
	for(var i=0;i<tab.length;i++){
		if(tab.eq(i).find("i").length>0){
			tab.eq(i).remove();
			iframe.eq(i).remove();
		}
	}
}

/*弹出层*/
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function layer_show(title,url,w,h){
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: title,
		content: url
	});
}
/*关闭弹出框口*/
function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

/*时间*/
function getHTMLDate(obj) {
    var d = new Date();
    var weekday = new Array(7);
    var _mm = "";
    var _dd = "";
    var _ww = "";
    weekday[0] = "星期日";
    weekday[1] = "星期一";
    weekday[2] = "星期二";
    weekday[3] = "星期三";
    weekday[4] = "星期四";
    weekday[5] = "星期五";
    weekday[6] = "星期六";
    _yy = d.getFullYear();
    _mm = d.getMonth() + 1;
    _dd = d.getDate();
    _ww = weekday[d.getDay()];
    obj.html(_yy + "年" + _mm + "月" + _dd + "日 " + _ww);
};

////////////////////////自己的东西///////////////////////////
//顶部导航切换
function getMenuData(obj){
	$("#leftMenu").html("");
	var leftLoadIndex = layer.load('0',{offset: ['150px', '50px']});
	var topMenuId = $(obj).attr("menuId");
	if(topMenuId){
		$.ajax({
			type:"post",
			url:"getLeftMenu.htm",
			dataType:"json",
			async:true,
			data:{"topTabId":topMenuId},
			success:function(data){
				if(data.result){
					changeLeftMenu(data.obj);
				}else{
					layer.msg(data.msg,{icon:2,time:2000});
				}
				layer.close(leftLoadIndex);
			},
			error:function(){
				layer.close(leftLoadIndex);
				layer.alert("提示：系统内部出现问题！");
			}
		});
	}
}
//改变左侧菜单
function changeLeftMenu(data){
	var content = "";
	if(data&&data.length>0){
		for(var i=0;i<data.length;i++){
			content += "<dl id='"+data[i].id+"'>";
	    	content += "<dt>";
	    	content += "<i class='Hui-iconfont'>&#"+data[i].icon_value+";</i>";
	    	content += "<cd>"+data[i].name+"</cd>";
	    	content += "<i class='Hui-iconfont menu_dropdown-arrow'>&#xe6d5;</i>";
	    	content += "</dt>";
	    	content += "<dd style='display: none;'>";
	    	content += "<ul>";
	    	var children = data[i].children;
	    	if(children){
    	    	for(var j=0;j<children.length;j++){
    	    		content += "<li><a data-parentMenu='"+data[i].name+"' data-parentMenuIcon='"+data[i].icon_value+"' data-href='"+children[j].path+"' data-title='"+children[j].name+"'>"+children[j].name+"</a></li>";
    	    	}
	    	}
	    	content += "</ul></dd></dl>";
		}
	}
	//更换左侧菜单
	$("#leftMenu").html(content);
	$(".Hui-aside").on("click",".menu_dropdown dd li a",function(){
		if($(window).width()<768){
			$(".Hui-aside").slideToggle();
		}
	});
	/*左侧菜单*/
	$.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
	
	/*选项卡导航*/
	$(".Hui-aside").on("click",".menu_dropdown a",function(){
		Hui_admin_tab(this);
	});
}

$(function(){
	getHTMLDate($("#top_time"));
	getskincookie();
	//layer.config({extend: 'extend/layer.ext.js'});
	Huiasidedisplay();
	var resizeID;
	$(window).resize(function(){
		clearTimeout(resizeID);
		resizeID = setTimeout(function(){
			Huiasidedisplay();
		},500);
	});
	
	$(".nav-toggle").click(function(){
		$(".Hui-aside").slideToggle();
	});
	$(".Hui-aside").on("click",".menu_dropdown dd li a",function(){
		if($(window).width()<768){
			$(".Hui-aside").slideToggle();
		}
	});
	/*左侧菜单*/
	$.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
	
	/*选项卡导航*/
	$(".Hui-aside").on("click",".menu_dropdown a",function(){
		Hui_admin_tab(this);
	});
	
	$(document).on("click","#min_title_list li",function(){
		var bStopIndex=$(this).index();
		var iframe_box=$("#iframe_box");
		$("#min_title_list li").removeClass("active").eq(bStopIndex).addClass("active");
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();
	});
	$(document).on("click","#min_title_list li i",function(){
		var aCloseIndex=$(this).parents("li").index();
		$(this).parent().remove();
		$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();	
		num==0?num=0:num--;
		tabNavallwidth();
	});
	$(document).on("dblclick","#min_title_list li",function(){
		var aCloseIndex=$(this).index();
		var iframe_box=$("#iframe_box");
		if(aCloseIndex>0){
			$(this).remove();
			$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();	
			num==0?num=0:num--;
			$("#min_title_list li").removeClass("active").eq(aCloseIndex-1).addClass("active");
			iframe_box.find(".show_iframe").hide().eq(aCloseIndex-1).show();
			tabNavallwidth();
		}else{
			return false;
		}
	});
	tabNavallwidth();
	
	$('#js-tabNav-next').click(function(){
		oUl = $("#min_title_list");
		num==oUl.find('li').length-1?num=oUl.find('li').length-1:num++;
		toNavPos();
	});
	$('#js-tabNav-prev').click(function(){
		num==0?num=0:num--;
		toNavPos();
	});
	
	function toNavPos(){
		oUl.stop().animate({'left':-num*100},100);
	}
	
	/*换肤*/
	$("#Hui-skin .dropDown-menu a").click(function(){
		var v = $(this).attr("data-val");
		$.cookie("Huiskin", v);
		var hrefStr=$("#skin").attr("href");
		var hrefRes=hrefStr.substring(0,hrefStr.lastIndexOf('skin/'))+'skin/'+v+'/skin.css';
		$(window.frames.document).contents().find("#skin").attr("href",hrefRes);
	});
	////////////////////自己添加的/////////////////////////////
	//顶部导航点击
	$("#Hui-navbar li").click(function(index){
		var pngfix = $(".pngfix");
		if(pngfix.hasClass("open")){
			pngfix.removeClass("open");
			$("body").removeClass("big-page");
		}
		if(!$(this).hasClass("dropDown")){
			if($(this).parent().hasClass("menu")){//更多下面的
				if(!$(this).hasClass("current")){
    				var moreTab = $(this).parent().parent();
    				moreTab.siblings().removeClass("current");
    				moreTab.addClass("current");
    				//增加一个当前点击项的标识
    				$(this).siblings().removeClass("on");
    				$(this).addClass("on");
    				getMenuData(this);
				}
			}else{
				if(!$(this).hasClass("current")){
    				//取消其他选中，并选中自己
    				$(this).siblings().removeClass("current");
    				$(this).addClass("current");
    				getMenuData(this);
				}
			}
		}
	});
	//默认展示左侧菜单
	var currentLi = $("#Hui-navbar .current");
	if(currentLi.length>0){
		getMenuData(currentLi[0]);
	}
});
