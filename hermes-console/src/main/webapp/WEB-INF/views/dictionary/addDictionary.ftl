 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">新增字典项</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="addForm" method="post">
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">所属字典类型</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="dictId" id ="dictId"  value="${(dictionaryType.name)!}" disabled="true"/>
                  <input type="hidden" value="${(dictionaryType.id)!}" name="typeId" id="typeId"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且不能输入中文</span>
				</div>                                                                        
              </div>                   
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>字典项编码</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="code" id ="code"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且不能输入中文</span>
				</div>                                                                        
              </div>          
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>字典项名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="name" id ="name"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">状态</label>
                <div class="col-sm-5">
                   <select id="status" name="status" class="form-control">
                   	  <option value="00">启用</option>
					  <option value="09">禁用</option>
                   </select>
                </div>
              </div>              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addParameterType">添加</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelParameterType">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
jQuery(function($) {
     //点击新增分类页面中新增按钮
	$("#addParameterType").on("click",function(){
		$.link.html(null, {
			url: '${app}/dictionary/handerAddDictionary',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});
	//点击新增分类页面中取消按钮
	$("#cancelParameterType").on("click",function(){
	    var typeId = $("#typeId").val();
		$.link.html(null, {
			url: '${app}/dictionary/index?id='+typeId,
			target: 'main'
		});
	});
	$('#name').blur(function(){
	   var name  = $("#name").val();
	   if(name == '' || name == null){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();
		   document.getElementById("addParameterType").disabled = true;		     		       
	   }else{
	   	   $(this).parent().parent().find(".alert-danger:eq(0)").hide();	
	   	   document.getElementById("addParameterType").disabled = false;		     	   	      
	   }
	});
	$('#code').blur(function(){
	    var code = $("#code").val();
		if(code == '' || code == null){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();	
		   document.getElementById("addParameterType").disabled = true;		     	              
		}else if(code != '' && !/^[^\u4e00-\u9fa5]{0,}$/.test(code)){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();	
		   document.getElementById("addParameterType").disabled = true;		     	              
		}else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();	
	       document.getElementById("addParameterType").disabled = false;
	    }
	});
});
</script>  