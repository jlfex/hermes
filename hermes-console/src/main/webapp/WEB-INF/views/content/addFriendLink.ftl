 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">添加链接</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="addForm" method="post">
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>网站名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="linkName"  name="linkName" placeholder="网站名称">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，汉字限定为8个字</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">网站网址</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="link" name="link" placeholder="网站网址">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，请输入合法的地址</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="order" name="order" placeholder="1">
                </div>
                <span class="vlight">请以，号隔开</span>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，只能输入数字</span>
				</div>                                
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-xs-1">
                  <button type="button" class="btn btn-primary btn-block" id="addFreiendLink">添加</button>
                  <button type="button" class="btn btn-primary btn-block" id="cancelFreiendLink">取消</button>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
jQuery(function($) {
	//点击添加按钮
	$("#addFreiendLink").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/handerAddFriendLink',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});

});
</script>
