<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/jsphead.jsp" %>
<html>
<head>
    <title>食品选择页面</title>
    <%@include file="/common/htmlhead.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="js/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
    <script type="text/javascript" src="js/laypage/1.3/laypage.js"></script>
    <script type="text/javascript" src="js/layer/2.1/layer.js"></script>
    <script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
</head>
<body>
<div class="page-container">
    <%--<form action="areaProduct/listPageData.htm" method="post" id="queryForm">--%>
        <div class="text-c">
            <input type="text" id="keyword" name="keyword" value="${params.keyword}" placeholder="食品名称/简拼" style="width:150px" class="input-text"/>
            <button class="btn btn-success" onclick="queryFood();" type="button"><i class="Hui-iconfont">&#xe665;</i> 搜食品</button>
        </div>
    <%--</form>--%>
    <div class="row cl" style="margin-top: 12px;">
        <label class="form-label col-xs-4 col-sm-3">适用食品：</label>
        <div class="formControls col-xs-12 col-sm-9 skin-minimal" id="foodContent" style="width: 1000px">
        </div>
    </div>
</div>
<script type="text/javascript">
    queryFood(true);
   function  queryFood(obj) {
       var ids="";
       if(obj){
           ids="${ids}";
       }
       $.post('<%=basePath%>product/queryAllFoodList.htm',{keyword:$("#keyword").val()},function (data) {
           if(data.result){
               var html = '';
               for(var i=0;i<data.obj.length;i++){
                   html += '<div class="check-box" style="width: 133px;line-height: 30px;padding-left: 10px;">';
                   html += '<input ';
                   if(ids){
                       var idArry=ids.split(",");
                       for(var j=0;j<idArry.length;j++){
                           if(idArry[j]==data.obj[i].id){
                               html += 'checked';
                           }
                       }
                   }
                   html +=' name="foodId" type="checkbox" value="'+data.obj[i].id+'" id="food-'+i+'" data-name="'+data.obj[i].food_name+'"/>';
                   html += '<label for="food-'+i+'">'+data.obj[i].food_name+'</label>';
                   html += '</div>';
               }
               $("#foodContent").html(html);
           }
       },'json')
   }

    function saveFood() {
        //获取适配食物id
        var foods = $("input[name='foodId']:checked");
        var foodIds = "";
        var foodNames="";
        if(foods.length>0){
            for(var i=0;i<foods.length;i++){
                if(i==foods.length-1){
                    foodIds += $(foods[i]).val();
                    foodNames += $(foods[i]).attr("data-name");
                }else{
                    foodIds += $(foods[i]).val()+",";
                    foodNames += $(foods[i]).attr("data-name")+",";
                }
            }
        }
        $('#foodIds', window.parent.document).val(foodIds);
        $('#foodNames', window.parent.document).val(foodNames);
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    //回车搜索
    $("body").keydown(function(e){
        var curKey = e.which;
        if(curKey == 13){
            queryFood();
        }
    });
</script>
</body>
</html>