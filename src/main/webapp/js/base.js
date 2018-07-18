// JavaScript Document
//右侧栏iframe
	var h=$(window).height();
	h = h-21;
	$(".navbox").height(h);
	$(".right_side iframe").height(h);
	$(window).resize(function(){
		var h=$(window).height();
		h = h-21;
		$(".navbox").height(h);
		$(".right_side iframe").height(h);
    })
//右iframe
	var m=$(window).height()-42;
	$(".main iframe").height(m);
	$(window).resize(function(){
		var m=$(window).height()-42;
		$(".main iframe").height(m);
    })	



/*$(function(){
	$(".khlx .shipin").click(function(){
		$(this).siblings('div').slideToggle();	
		return false;	
	})
	$(".khlx div dl").click(function(){
		if($(this).find(".che1").is(':checked')){
			$(this).addClass("on");
		}else{
			$(this).removeClass("on");
		}
		var l = $(".khlx div dl").length;
		var str = '' ;
		for(var i = 0; i< l ; i++){
			if($(".khlx div dl").eq(i).hasClass("on")){
				str = str + $(".khlx div dl").eq(i).find("b").text() +';';
			}
		}
		if(str==''){
			str = '食品加工'
		}
		$(".khlx .shipin").text(str)
		//$(".khlx>div").css({"display":'block !important'});	
		
	})
})	
*/
/*$(window).click(function(e){
	if(e.target.className!='che che1' && e.target.className!='che' && e.target.className!='che on' && e.target.className!='chea'){
		$(".khlx>div").slideUp();
	}
})
*/
/*$(window).click(function(e){
	if(e.target.className!='chee'){
		$("#dis").slideUp();
	}
})*/
	
/*$(function(){
	$(".cpmc").click(function(){
		$(this).siblings('div').slideToggle();	
		return false;	
	})
	$("#cpmc div dl").click(function(){
		if($(this).find(".che1").is(':checked')){
			$(this).addClass("on");
		}else{
			$(this).removeClass("on");
		}
		var l = $("#cpmc div dl").length;
		var str = '' ;
		for(var i = 0; i< l ; i++){
			if($("#cpmc div dl").eq(i).hasClass("on")){
				str = str + $("#cpmc div dl").eq(i).find("b").text() +';';
			}
		}
		if(str==''){
			str = '食品加工'
		}
		$(".cpmc").text(str)
		//$(".khlx>div").css({"display":'block !important'});	
		
	})
})	

$(window).click(function(e){
	if(e.target.className!='che che1' && e.target.className!='che' && e.target.className!='che on'){
		$("#cpmc div").slideUp();
	}
})	
*/	
// $(".faduanxin.faduanxin1").click(function(){
// 		$(".heibg").fadeIn();
// 		$(".duanxintanchuang.aa1").slideDown();
// })
//
// $(".faduanxin.faduanxin2").click(function(){
//         $(".heibg").fadeIn();
//         $(".duanxintanchuang.a4").slideDown();
// })

$(".duanxin").click(function(){
		$(".heibg").fadeIn();
		$(".duanxintanchuanga").slideDown();
})

$("#tz1").click(function(){
		$(".heibg").fadeIn();
		$(".a3").slideDown();
})

	
$(".dtop span,.heibg,.d_bt").click(function(){
		$(".heibg").fadeOut();
		$(".duanxintanchuang").slideUp();
})	
	
$(".erweima").click(function(){
		$(".heibg").fadeIn();
		$(".saomiao").slideDown();
})
	
$(".dtop span,.heibg,.d_bt").click(function(){
		$(".heibg").fadeOut();
		$(".duanxintanchuang").slideUp();
})	
	
	
// $('.alert-hover').on('click', function(){
//   layer.msg('保存成功');
// });
	
// $('#tijiao').on('click', function(){
//   layer.msg('提交成功');
// });
//
//
// $('.baocun').on('click', function(){
//   layer.msg('保存成功');
// });
	
	
	/*单位市场全选*/
	$("#all").click(function(){
		if($(this).is(':checked')){
			  $("input[name='a']").prop('checked', true)
		}
		else{ 
      	  $("input[name='a']").removeAttr('checked', false)
    	} 
	})
	
	$("#chongzhi").click(function(){
		if($("input[name='a']").is(':checked')){
			  $("input[name='a']").removeAttr('checked', false)
		}
        $("input[name='a1']").removeAttr('checked', false);
	})

		/*单位市场全选*/
		
	/*通知经销商赠送*/	
	$(window).click(function(e){
	if(e.target.className!='che che1' && e.target.className!='che' && e.target.className!='che on' ){
		$("#cpmc div").slideUp();
	}
})	
	

	
$(".dtop span,.heibg,.d_bt").click(function(){
		$(".heibg").fadeOut();
		$(".a1").slideUp();
})

$(".tianjaiyitiao h3").click(function(){
		$(".heibg").fadeIn();
		$(".tj01").slideDown();
})	

$(".dtop span,.heibg,.d_bt").click(function(){
		$(".heibg").fadeOut();
		$(".a2").slideUp();
})			
		
		
		
/*通知经销商赠送*/			
		
var ht = $('.danwei.jxs tr.jiagao td textarea').height();
$('.danwei.jxs tr.jiagao td').height(ht);

	
	
	
	/*产品赠送添加*/
	
	// $('.tianjia').click(function(){
	// 	$(".append_t").append("<tr><td><select><option>请选择分类</option></select></td><td><select><option>请选择产品</option></select></td><td><input type='number'>"+"</td><td><a href='javascript:'>删除</a></td></tr>");
	// })
		
		
	
		$(".dongtai .hov_mou tr td").mouseenter(function(){
			var i = $(this).parent('tr').index();
			var n  =  $(this).index();
			$(this).parents('.hov_mou').find('tr').removeClass('active1');
			$('.dongtai .hov_mou tr td').removeClass('active1');
			$(".dongtai .hov_mou tr th").removeClass('active1');
				$(this).parents('.hov_mou').find('tr').eq(i).addClass('active1');
				for(var j=0; j<$(".hov_mou tr").length;j++){
					$(".dongtai .hov_mou tr").eq(j).find("td").eq(n).addClass('active1');
				}
				$(".dongtai .hov_mou tr th").eq(n).addClass('active1');
				//$(this).parents('tr').siblings("tr").find('td').eq(n).addClass('active1');
				//$('table tr td').eq(n).addClass('active1');
		})
		
		$(".dongtai .hov_mou tr td").mouseleave(function(){
			var i = $(this).parent('tr').index();
			var n  =  $(this).index();
			$(this).parents('.hov_mou').find('tr').removeClass('active1');
			$('.dongtai .hov_mou tr td').removeClass('active1');
			$(".dongtai .hov_mou tr th").removeClass('active1');
		})
		
		$(".dongtai .hov_mouo tr td").mouseenter(function(){
			var i = $(this).parent('tr').index();
			var n  =  $(this).index();
			$(this).parents('.hov_mouo').find('tr').removeClass('active1');
			$('.dongtai .hov_mouo tr td').removeClass('active1');
			$(".dongtai .hov_mouo tr th").removeClass('active1');
				$(this).parents('.hov_mouo').find('tr').eq(i).addClass('active1');
				for(var j=0; j<$(".hov_mouo tr").length;j++){
					$(".dongtai .hov_mouo tr").eq(j).find("td").eq(n).addClass('active1');
				}
				$(".dongtai .hov_mouo tr th").eq(n).addClass('active1');
				//$(this).parents('tr').siblings("tr").find('td').eq(n).addClass('active1');
				//$('table tr td').eq(n).addClass('active1');
		})
		
		$(".dongtai .hov_mouo tr td").mouseleave(function(){
			var i = $(this).parent('tr').index();
			var n  =  $(this).index();
			$(this).parents('.hov_mouo').find('tr').removeClass('active1');
			$('.dongtai .hov_mouo tr td').removeClass('active1');
			$(".dongtai .hov_mouo tr th").removeClass('active1');
		})
		
	$('.popup dl dd label span')	.click(function(){
		if($(this).siblings("input").is(':checked')){
			$(this).removeClass('on')	
		}	
		else{
			$(this).addClass('on')		
		}
	})
		
		
		
		
		