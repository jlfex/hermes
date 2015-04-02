 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">编辑参数配置</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="editForm" method="post">
          <input type="hidden" value="${(parameter.id)!}" name="id" />
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">参数类型</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="parameterType1"  name="parameterType" placeholder="参数类型" readonly="readonly" value="${parameter.type.name!}">
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>参数值</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="parameterValue1" name="parameterValue" placeholder="参数值" value="${parameter.name}">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且只能输入1~100的数字</span>
				</div>                                                                                        
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="editParameter">保存</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelParameter">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>
<script type="text/javascript">
jQuery(function($) {     
    //点击编辑页面中保存按钮
	$("#editParameter").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/handerEditParameter',
			data: $("#editForm").serialize(),
			target: 'main'
		});
	});
	//点击编辑页面中取消按钮
	$("#cancelParameter").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/index',
			target: 'main'
		});
	});
	//如果参数类选择产品招标期限，在此加入校验
	$('#parameterValue1').keyup(function(){
	   var parameterType  = $("#parameterType1").val();
	   var parameterValue = $("#parameterValue1").val(); 
	   if(parameterType == '产品招标期限' && !/^([0-9]{1,2}|100)$/.test(parameterValue)){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();    
	   }else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();
	   }
	});
	
});
</script>       