 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">新增参数配置</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="addForm" method="post">
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>参数名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="name" name="name">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                      
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>参数编码</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="code"  name="code" placeholder="格式示例：app.operation.nickname">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且只能为字母与.的组合</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>参数值</label>
                <div class="col-sm-5">
                   <input type="text" class="form-control" id="value" name="value">                
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>  
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">参数状态</label>
                <div class="col-sm-5">
                  <select id="status" name="status" class="form-control">
                   	  <option value="0">有效</option>
					  <option value="1">无效</option>
                   </select>              
                </div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">参数类型</label>
                <div class="col-sm-5">
						<select id="type" name="typeId" class="form-control">
							<#list dicts as d>
							<option value="${d.id}">${d.name}</option>
							</#list>
						</select>
                </div>                                                        
              </div> 
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addBtn">保存</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelBtn">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
jQuery(function($) {
	$("#code,#name,#value").on('blur',function(i,item){
		checkInput(this);
	});
	//对输入元素进行校验
	function checkInput(e){
		var $this = $(e);
		if($this.val() == ''){
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}else if ('code' == e.id && !/[a-z.]+/.test($("#code").val())){
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}
		$this.parent().parent().find(".alert-danger:eq(0)").hide();
		return true;
	}
	//元素失去焦点时，触发数据校验事件
	function checkAll(){
		$("#code,#name,#value").each(function(i,item){
			checkInput(this);
		});
		return $("span.alert-danger:visible").length==0;
	}
	
	//点击添加按钮
	$("#addBtn").on("click",function(){
	 if(checkAll()){
		$.link.html(null, {
			url: '${app}/properties/handleAddProperties',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	  };
	});
    //点击取消按钮
	$("#cancelBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/properties/index',
			target: 'main'
		});
	});

});
</script>
