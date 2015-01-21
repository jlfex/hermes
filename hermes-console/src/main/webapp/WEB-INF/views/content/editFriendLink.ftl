 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">修改链接</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="editForm" method="post">
          <input type="hidden" value="${(friendLink.id)!}" name="id" />
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>网站名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="linkName"  name="linkName" placeholder="网站名称"  value="${friendLink.name}">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，汉字限定为8个字</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">网站网址</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="link" name="link" placeholder="网站网址" value="${friendLink.link}">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，请输入合法的地址</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">所属分类</label>
                <div class="col-sm-5">
                   <select id="type" name="type" class="form-control">
                      <option value="${friendLink.type}">${friendLink.type}</option>
                   </select>
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>        
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="order" name="order" placeholder="1" value="${friendLink.order}">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，只能输入数字</span>
				</div>                                
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="editFreiendLink">保存</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelFreiendLink">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>
<script type="text/javascript">
jQuery(function($) {      
    //点击编辑页面中保存按钮
	$("#editFreiendLink").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/handerEditFriendLink',
			data: $("#editForm").serialize(),
			target: 'main'
		});
	});
	//点击编辑页面中取消按钮
	$("#cancelFreiendLink").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/friendLink',
			target: 'main'
		});
	});
	
});
</script>       