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
                <label for="" class="col-sm-2 control-label">所属分类</label>
                <div class="col-sm-5">
                   <select id="type" name="type" class="form-control">
                   	  <option value="友情链接">友情链接</option>
					  <option value="合作机构">合作机构</option>
                   </select>
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="order" name="order">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，只能输入数字</span>
				</div>                                
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addFreiendLink">添加</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelFreiendLink">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
jQuery(function($) {
	$("#order").on('blur',function(i,item){
		checkInput(this);
	});
	//对输入元素进行校验
	function checkInput(e){
		var $this = $(e);
		var val = $this.val();
		if($this.val()==''||(e.id == 'order' && !/^[0-9]+$/.test(val))		    
		    ){
			$this.parent().parent().find(".alert-danger:eq(0)").attr("e_id",e.id);
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}else{
			var e_id = $this.parent().parent().find(".alert-danger:eq(0)").attr("e_id");
			if(e_id=='' || e_id==e.id){
				$this.parent().parent().find(".alert-danger:eq(0)").hide();
			}
			return true;
		}
	}
	    //元素失去焦点时，触发数据校验事件
	function checkAll(){
		$("#order").each(function(i,item){
			checkInput(this);
		});
		return $("span.alert-danger:visible").length==0;
	}
	
	//点击添加按钮
	$("#addFreiendLink").on("click",function(){
	 if(checkAll()){
		$.link.html(null, {
			url: '${app}/content/handerAddFriendLink',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	  };
	});
    //点击取消按钮
	$("#cancelFreiendLink").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/friendLink',
			target: 'main'
		});
	});

});
</script>
