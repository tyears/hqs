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
    <script type="text/javascript" src="js/common/queryPage.js"></script>
    <script src="js/layer.js"></script>
    <link rel="stylesheet" type="text/css" href="js/jquery-ui-1.12.1/jquery-ui.min.css" />
    <script type="text/javascript" src="js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <style>
        .ui-autocomplete {
            max-height: 260px;
            max-width: 260px;
            overflow-y: auto;
            /* 防止水平滚动条 */
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<div class="left_top">
    <iframe id="left_top" src="order/toLeftTop.html" frameborder="0" width="100%" scrolling="no" ></iframe>
</div>

<div class="dongdaibox">
    <div class="biaoti bt_box"><div><span></span><b id="areaNum">&nbsp;&nbsp;单位市场<a href="queryDealer('y')">合作商（<i></i>）</a><a href="queryDealer('n')">未合作商（<i></i>）</a></b></div>
        <h5 class="">
            <input class="in_btn" placeholder="请输入单位市场" id="areaName">
            <input type="hidden" >
            <%--<div class="shengshiqu" id="areaList">--%>
            <%--</div>--%>
        </h5>
    </div>
    <div class="dongtai">
        <div class="dbox">
            <div class="left dbox_none">
                <dl>
                    <dd>
                        <form action="dealerPc/toAreaMarketData.html" method="post" id="queryForm">
                            <input type="hidden" name="areaId" value="${params.areaId}" id="areaId">
                            <input type="hidden" name="ishezuo" value="${params.ishezuo}" id="ishezuo">
                            <input type="hidden" name="sortNum" value="${params.sortNum}" id="sortNum">
                            <input placeholder="请输入货号或品名" name="keyword" value="${params.keyword}" id="key_word">
                            <button class="chaxun" onclick="query();">查询</button>
                            <button class="chongzhi" type="button" onclick="$('#key_word').val('')">重置</button>
                        </form>
                    </dd>
                </dl>
                <div class="bigbiaoge danwei dwsc_box birth" id="mainContent"></div>
            </div>
        </div>
        <div class="beizhu_box">${sessionScope.remarks.danweishichang}</div>

    </div>
</div>

<!--弹窗-->
<div class="heibg"></div>
<div class="duanxintanchuang aa1">
    <div class="dxbox">
        <input id="smsTel" type="hidden"/>
        <input id="smsType" type="hidden"/>
        <div class="dtop">
            <p>发短信</p>
            <span></span>
        </div>
        <div class="ddmidd">
            <div class="dmidd">
                <p id="smsTitle">南阳经销商联系方式：</p>
                <textarea id="smsContent"></textarea>
            </div>
            <div onclick="sendSms();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;margin-top: 20px;cursor: pointer;font-size: 15px;font-weight: bold;">确认</div>
        </div>
    </div>
</div>
<div class="duanxintanchuang aa2">
    <div class="dxbox">
        <input id="tzDealerId" type="hidden"/>
        <input id="tzOrderId" type="hidden"/>
        <div class="dtop">
            <p>发短信</p>
            <span></span>
        </div>
        <div class="ddmidd">
            <div class="dmidd">
                <textarea id="tzSmsContent"></textarea>
            </div>
            <div onclick="sendSmstz();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;margin-top: 20px;cursor: pointer;font-size: 15px;font-weight: bold;">确认</div>
        </div>
    </div>
</div>

<div class="duanxintanchuang tztanchuang a4">
    <div class="dxbox">
        <div class="dtop">
            <p>公司通知</p>
            <span></span>
        </div>
        <div style="max-height:480px; overflow-y:auto">
            <div class="biao">
                <table>
                    <tr>
                        <th>赠送产品</th>
                        <th>数量</th>
                    </tr>
                    <tr>
                        <td>A101亮白馒头改良剂</td>
                        <td><input type="number"></td>
                    </tr>
                    <tr class="hui_bg">
                        <td>A101亮白馒头改良剂</td>
                        <td><input type="number"></td>
                    </tr>
                    <tr>
                        <td>A101亮白馒头改良剂</td>
                        <td><input type="number"></td>
                    </tr>
                </table>
            </div>
            <div class="ddmidd zssma">
                <div class="d_bt d_bt_01">确认</div>
            </div>
        </div>
    </div>
</div>

<div class="duanxintanchuang tztanchuang a1" style="width: 470px">
    <input id="productId" type="hidden"/>
    <div class="dxbox">
        <div class="dtop">
            <p id="giveTitle">公司通知</p>
            <span></span>
        </div>
        <div style="max-height:480px; overflow-y:auto">
            <div class="biao">
                <table class="append_t" id="productTr">
                    <tr>
                        <th>赠送产品</th>
                        <%--<th>数量</th>--%>
                    </tr>
                    <tr>
                        <td id="pName" ></td>
                        <%--<td><input id="productZSN" type="number" min="1" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"></td>--%>
                    </tr>
                </table>
            </div>
            <div class="ddmidd zssma">
                <div onclick="saveGive();" style="width: 100%;height: 54px;background: #41973c;text-align: center;line-height: 54px;color: #fff;margin-top: 0px;cursor: pointer;font-size: 15px;font-weight: bold;">确认</div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        var tzSmsM="今有您市场客户";
        updateNum('');
    })
    function queryDealer(type) {
        $("#ishezuo").val(type);
        query();
    }
    function query(){
        queryData("#queryForm","#mainContent");
    }
   //回车搜索
   $("body").keydown(function(e){
       var curKey = e.which;
       if(curKey == 13){
           query();
       }
   });
    $(function(){
        $("body").click(function () {
            $(".shengshiqu").hide();
        });

        /*	$("#city .ssq").click(function(){
         $("#dis").show();
         return false;
         })*/

        //文本框输入
//        $(".bt_box>h5 .in_btn").keyup(function () {
//            $(".shengshiqu").show();	//只要输入就显示列表框
//            queryAllArea();
//            if ($(".bt_box>h5 .in_btn").val().length <= 0) {
//                $(".shengshiqu p").show();//如果什么都没填，跳出，保持全部显示状态
//                return;
//            }
//        });


        $("#areaName").autocomplete({
            minLength: 0,
            scrollHeight:100,
            source: function(request,response){
                $.ajax({
                    url:"<%=basePath%>dealerPc/queryAllArea.html",
                    type:"post",
                    dataType:"json",
                    data:{"areaName":$("#areaName").val()},
                    success:function(data){
                        response($.map(data.obj.slice(0, 100),function(item){
                            return{
                                label:item.areaName,
                                value:item.id
                            }
                        }));
                    }
                });
            },
            focus:function(event,ui){
                return false;
            },
            select:function(event,ui){
                $("#areaName").val(ui.item.label);
                $("#areaId").val(ui.item.value);
                updateNum(ui.item.value);
                query();
                return false;
            }
        });
    });

    $(function(){
        $(".dandu").click(function(){
            $(".heibg").fadeIn();
            $(".a4").slideDown();
        })

        $(".dtop span,.heibg,.d_bt").click(function(){
            $(".heibg").fadeOut();
            $(".a4").slideUp();
        })
    })
    
    function updateNum(areaId) {
        $.ajax({
            type: "post",
            url: "<%=basePath%>dealerPc/queryAreaNum.html",
            async: true,
            dataType: "json",
            data:{"areaId":areaId},
            success: function (data) {
                if (data.result) {
                    var html = '';
                        html += data.obj.areaName+'&nbsp;&nbsp;单位市场' +
                            '<a href="javascript:" onclick="queryDealer(\'y\')">合作商（<i>'+data.obj.hzNum+'</i>）</a>' +
                            '<a href="javascript:" onclick="queryDealer(\'n\')">未合作商（<i>'+data.obj.whzNum+'</i>）</a>';
                    $("#areaNum").html(html);
                    var smsTitle=data.obj.areaName+"经销商联系方式：";
                    $("#smsTitle").html(smsTitle);
                } else {
                    return false;
                }
            },
            error: function () {

            }
        });
    }

    function tzDealler() {
        var orderId ="${info.id}";
        if(!orderId){
            layer.msg('请先调单');
            return false;
        }
        var productNames="${info.productNames}";
        if(!productNames){
            layer.msg('当前询单无咨询产品');
            return false;
        }
        if($('input[name="a"]:checked').length!=1){
            layer.msg('请选择一个经销商');
            return false;
        }

        var dealerId="";
        var smsTel="";
        $('input[name="a"]:checked').each(function () {
            dealerId=$(this).attr("data-dealerid");
            smsTel=$(this).attr("smsTel");
        });

        if(!smsTel){
            layer.msg('该经销商短信号码为空');
            return false;
        }

        $("#tzDealerId").val(dealerId);
        $("#tzOrderId").val(orderId);
        $("#tzSmsContent").val("贵地${info.name} ${info.tel}咨询${info.productNames},请联系客户如需要可去您处购买。");
        $(".heibg").fadeIn();
        $(".duanxintanchuang.aa2").slideDown();
    }

    function sendSmstz() {
        var smsContent=$("#tzSmsContent").val();
        if(!$.trim(smsContent)){
            layer.msg('内容不能为空');
            return false;
        }

        var index = layer.load();
        $.ajax({
            url:"<%=basePath%>dealerPc/tzDealer.html",
            type:"post",
            dataType:"json",
            data:{dealerId:$("#tzDealerId").val(),orderId:$("#tzOrderId").val(),smsContent:smsContent},
            success:function (data) {
                if(data.result){
                    layer.msg(data.msg, {
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    },function () {
                        layer.close(index);
                        $(".heibg").fadeOut();
                        $(".duanxintanchuang.aa2").slideUp();
                    });
                }else {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(index);
                    });
                    return;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        })
    }



//    填充短信内容
    function uptSms(id) {
        var tel="${info.smsTel}";
        var orderId ="${info.id}";
        if(!orderId){
            layer.msg('请先调单');
            return false;
        }
        if(!tel){
            layer.msg('询单短信号码不能为空');
            return false;
        }


        var dealerType="${info.dealerType}";
        if(dealerType=="1"){
            layer.msg('当前客户为经销商不需发短信');
            return false;
        }
        if(id){
            if($('input[name="a"]:checked').length!=1){
                layer.msg('请选择一个经销商');
                return false;
            }
            $("#smsTitle").hide();
            var smsTel=$('input[name="a"]:checked').attr("dealerTel");
            $("#smsTel").val(smsTel);
            $("#smsType").val("1");
            $('#smsContent').val("");
        }else {

            $("#smsTitle").show();
            var smsStr='';
            $.each($('input[name="a"]:checked'),function(){
                smsStr += $(this).val();
            });
            $('#smsContent').val(smsStr);
            $("#smsTel").val(tel);
            $("#smsType").val("0");
        }
        $(".heibg").fadeIn();
 		$(".duanxintanchuang.aa1").slideDown();
    }

    function sendSms() {
        var smsType=$("#smsType").val();
        var smsContent=$("#smsContent").val();
        var tel=$("#smsTel").val();
        if(!$.trim(smsContent)){
            layer.msg('内容不能为空');
            return false;
        }

        if(!$.trim(tel)){
            layer.msg('号码不能为空');
            return false;
        }
        if(smsType==0){
            smsContent=$("#smsTitle").text()+smsContent;
        }

        var index = layer.load();
        $.ajax({
            url:"<%=basePath%>sendOutSms.html",
            type:"post",
            dataType:"json",
            data:{phone:tel,text:smsContent},
            success:function (data) {
                if(data.result){
                    layer.msg(data.msg, {
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(index);
                        $(".heibg").fadeOut();
                        $(".duanxintanchuang.aa1").slideUp();
                    });
                }else {
                    layer.msg(data.msg, {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(index);
                    });
                    return;
                }
            },
            error: function () {
                layer.alert("提示：系统内部出现问题！");
            }
        })
    }

   queryAllArea();
   function queryAllArea() {
       var areaName=$("#areaName").val();
       $.ajax({
           type:"post",
           url:"<%=basePath%>dealerPc/queryAllArea.html",
           dataType:"json",
           async:true,
           data:{"areaName":areaName},
           success:function(data){
               if(data.result){
                   var html = '';
                   for (var i=0;i<data.obj.length;i++){
                       html +='<p onclick="areaOnclick(this);" id="'+data.obj[i].id+'">'+data.obj[i].areaName+'</p>';
                   }
                   $("#areaList").html(html);
               }
           }
       });
   }
   function areaOnclick(obj) {
       $(".bt_box>h5 .in_btn").val($(obj).text());
       $("#areaId").val($(obj).attr('id'));
       updateNum($(this).attr('id'));
       query();
   }

 function changeSort() {
     var sort=$("#sortNum").val();
     if(sort=='asc'){
         $("#sortNum").val("desc");
     }else {
         $("#sortNum").val("asc");
     }
     query();
 }

   function zsjxOnclick(productId,pName) {
           var id="${info.id}";
           if(!id){
               layer.msg("请先调单");
               return false;
           }
           var type="${info.dealerType}";
           if(type && type != '1'){
               var isGive=false;
               $.ajax({
                   url:"<%=basePath%>givePc/queryIsGive.html",
                   type:'post',
                   dataType:'json',
                   data:{orderId:id,productId:productId,isDealer:"n"},
                   async:false,
                   success:function (data) {
                       isGive=data.result;
                       if(!isGive){
                           layer.msg(data.msg);
                       }
                   }
               });
               if(!isGive){
                   return false;
               }
               $("#productId").val(productId);
               $("#pName").text(pName);
               $(".heibg").fadeIn();
               $(".a1").slideDown();
           }

   }

   function saveGive() {
       var productIds=$("#productId").val()+",";


       var productNums=$("#productZSN").val();
//       if(!productNums){
//           layer.msg("请填写数量");
//           return false;
//       }
       var giveContent=$("#pName").text()+";";

       var index = layer.load();
       $.ajax({
           url:"<%=basePath%>givePc/saveHGive.html",
           type:"post",
           dataType:"json",
           data:{productIds:productIds,productNums:productNums+",",orderId:"${info.id}"
               ,giveType:"2",giveContent:giveContent},
           success: function (data) {
               if (data.result) {
                   layer.msg(data.msg, {
                       time: 2000 //2秒关闭（如果不配置，默认是3秒）
                   },function () {
                       $(".heibg").fadeOut();
                       $(".a1").slideUp();
                   });
                   layer.close(index);
               } else {
                   layer.msg(data.msg, {
                       time: 2000 //2秒关闭（如果不配置，默认是3秒）
                   });
                   layer.close(index);
                   return;
               }
           },
           error: function () {
               layer.alert("提示：系统内部出现问题！");
           }
       })
   }
</script>

</body>
</html>
