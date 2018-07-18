/*-----------查询页面js-------------
* Created & Modified by zhaixiaoliang
* Date modified 2017-07.07
*/
$(function(){
	//取消回车提交表单
	$("#queryForm").submit(function () {
		return false;
	});

	//设置GPS导航区
	var breadcrumb = $(".breadcrumb");
	if(breadcrumb&&!breadcrumb.html()){
		var currTab = $(window.top.document).find(".Hui-tabNav-wp .active");
		var header = currTab.data("header");
		breadcrumb.append(header);
	}
	query();
});

/*查询*/
function queryData(form,mainContent){
	var loadIndex = layer.load();
	var queryForm = $(form);
	var mainContent = $(mainContent);
	if(queryForm&&queryForm.length==1&&mainContent&&mainContent.length==1){
		$.ajax({
			type:"post",
			url:queryForm.attr("action"),
			data:queryForm.serialize(),
			success:function(data){
				layer.close(loadIndex);
				$(mainContent).empty();
				$(mainContent).html(data);
				//全选
				$("table thead th input:checkbox").on("click",function() {
					$(this).closest("table").find("tr > td:first-child input:checkbox").prop("checked", $("table thead th input:checkbox").prop("checked"));
				});
			},
			error:function(){
				layer.close(loadIndex);
				layer.msg("提示：系统内部出现问题！");
			}
		});
	}else{
		layer.close(loadIndex);
		layer.msg("查询方法参数错误");
	}
}