<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${not empty pageInfo}">
<div class="dataTables_info">
    <c:if test="${pageInfo.list.size()>0}">
    显示 ${(pageInfo.pageNum-1)*pageInfo.pageSize+1} 到 ${(pageInfo.pageNum-1)*pageInfo.pageSize+pageInfo.list.size()} , 共 ${pageInfo.list.size()} 条
    <span class="pipe">|</span>每页显示：
    <select onchange="setPageSize(this);">
    	<option value="10" ${pageInfo.pageSize=='10'?'selected':''}>10</option>
    	<option value="20" ${pageInfo.pageSize=='20'?'selected':''}>20</option>
    	<option value="50" ${pageInfo.pageSize=='50'?'selected':''}>50</option>
    	<option value="100" ${pageInfo.pageSize=='100'?'selected':''}>100</option>
    </select> 条
    </c:if>
    <c:if test="${pageInfo.list.size()==0}">
        暂未查询到数据
    </c:if>
    </div>
<div id="pageDiv" style="float: right;" class="dataTables_laypage"><!-- 这里显示分页 --></div>
</c:if>
<script type="text/javascript">
<c:if test="${not empty pageInfo}">
laypage({
	  cont: 'pageDiv',
	  pages: '${pageInfo.pages}', //总页数
	  curr: '${pageInfo.pageNum}', //当前页
	  groups: 3,//连续显示页数
	  first: '首页',
	  last: '尾页',
	  skin: 'default',//皮肤，目前支持：molv、yahei、flow 和16进制颜色值
	  skip: true,//是否支持跳转页
	  jump: function(e, first){ //触发分页后的回调
	    if(!first){ //一定要加此判断，否则初始时会无限刷新
	    	$("#pageNum").val(e.curr);
			query();
	    }
	  }
});
</c:if>
//设置记录总数
$("#totalCount").text('${pageInfo.total}');
//每页显示改变
function setPageSize(obj){
	var pageSize = $(obj).val();
	$("#pageSize").val(pageSize);
	$("#pageNum").val("1");
	query();
}
</script>