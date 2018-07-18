<script type="text/javascript">
    $(function(){
        //回车搜索
        $("body").keydown(function(e){
            var curKey = e.which;
            if(curKey == 13){
                query();
            }
        });
    });
</script>
