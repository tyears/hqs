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
<link href="css/style01.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/jquery/form/jquery.form.min.js"></script>
<script type="text/javascript" src="js/layer/2.1/layer.js"></script>
<script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
</head>

<body>

    <div class="dongdaibox">
    	<div class="biaoti"><div><span></span><b>建议下载</b></div>
            <h6>
                <form id="queryForm" action="technicalProposal/toQuery.html" method="post">
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
            </h6>
        </div>
        <div class="dongtai">
        	<div class="dbox">
                <div class="left dbox_none">
                    <dl>
                        <dd style="width:100%;">
                            <form>
                                <p class="fl_ri"><a><i>${pageInfo.total}</i>条信息</a><a>您已选中<i id="checkBoxNum">0</i>条</a></p>
                            </form>
                        </dd>
                    </dl>
                    <div class="bigbiaoge jsbjy">
                    	<table class="hov_mou">
                        	<tr>
								<th width="10%">序号</th>
                                <th width="10%">提交人</th>
                                <th width="40%">意见和建议</th>
                                <th width="20%">建议时间</th>
                                <th width="10%">下载状态</th>
                                <th width="10%"><label class="checkbox_all"><span>全选</span><input type="checkbox"></label></th>
                            </tr>
                        <c:forEach items="${pageInfo.list}" var="data" varStatus="status">
                            <tr>
								<td>${status.count+(pageInfo.pageNum-1)*pageInfo.pageSize}</td>
                                <td>${data.name}</td>
                                <td style="text-overflow:ellipsis;overflow:hidden; white-space: nowrap;" title="${data.text}">${data.text}</td>
                                <td><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${not empty data.state && data.state=='0'?'未下载':not empty data.state && data.state=='1'?'已下载':''}</td>
                                <td><input class="checkbox_input" type="checkbox" name="choosed" value="${data.id}"></td>
                             </tr>
                        </c:forEach>
                        </table>
                        <c:if test="${pageInfo.list.size()!=0}">
                            <div class="caozuo_box">
                               <input type="button" value="技术建议下载" onclick="exportExcel()">
                            </div>
                        </c:if>
                        <div class="fenye">
                        	<dl  id="pageDiv">

                            </dl>
                        </div>
                        <c:if test="${pageInfo.list.size()==0}">
                            <div class="zanwu"><img style="margin:200px auto" src="images/zanwu.png"></div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="beizhu_box">${sessionScope.remarks.jianyi}</div>
        </div>
    </div>
<!--弹窗---->
<div class="heibg"></div>
<div class="popup popup02">
   <h2><span>打印告知单</span><i>×</i></h2>
   <form>
       <dl>
          <dd><input type="text" placeholder="接单人（举例：部门-姓名）"></dd>
          <dd><input type="text" placeholder="手机号码"></dd>
          <dd><input type="text" placeholder="姓名"></dd>
          <dd><select><option>客户类型</option></select></dd>
          <dd><textarea rows="3" placeholder="客户留言"></textarea></dd>
          <dd><textarea rows="3" placeholder="操作留言"></textarea></dd>
          <dd><textarea rows="3" placeholder="经理意见"></textarea></dd>
          <dd><textarea rows="3" placeholder="内容"></textarea></dd>
          <dd><label><input type="checkbox"><span>是否加急</span></label></dd>
          <dd class="submit"><input type="button" value="打印"></dd>
       </dl>
   </form>
</div>
<!--弹窗---->
<script src="js/base.js"></script>
<!--全选---->
<script>
	$(".checkbox_all input").click(function(){
	  if($(this).attr('checked')=='checked'){
		  $(".checkbox_input").attr('checked','checked');
	  }else{
		  $(".checkbox_input").removeAttr('checked','checked');
      }
        $("#checkBoxNum").html($("input[name='choosed']:checked").length);
	});

    $(".checkbox_input").click(function () {
        $("#checkBoxNum").html($("input[name='choosed']:checked").length);
    });


	$(function(){
	   $(".print_btn").click(function(){
		 $(".heibg").fadeIn();
		$(".popup02").slideDown();
	   })
	   $(".heibg,.popup >h2 i").click(function(){
		   $(".popup02,.heibg").fadeOut();
		}) 
	})
  </script>


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

    function queryPage(pn) {
        $("#pageSize").val(pn);
        queryForm();
    }
    
    
    function exportExcel() {
        var values="";
        $("input[name='choosed']:checked").each(function () {
            values+=this.value;
            values+=",";
        })
//        alert(values);
        if(!($("input[name='choosed']:checked").length>0)){
            layer.msg("未选中要导出的条目",{time:1000,icon:2,offset: '200px'});
        }else{
            /*$.ajax({
                type:"post",
                data:{"values":values},
                url:"<%=basePath%>technicalProposal/exportProposalExcel.html",
                async:false,
                success:function(data){
                    data = eval("("+data+")");
                    if(data.result){
                        layer.msg(data.msg,{time:1000,icon:1,offset: '200px'});
                    }else{
                        layer.msg(data.msg,{time:1000,icon:2,offset: '200px'});
                    }
                },
                error:function(){
                    layer.msg("提示：系统内部出现问题！",{time:1000,icon:2,offset: '200px'});
                }
            })*/
            window.location="technicalProposal/exportProposalExcel.html?values="+values;

        }



    }
    


</script>

</body>
</html>
