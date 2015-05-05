 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">修改字典项</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="editForm" method="post">
          <input type="hidden" value="${(parameter.id)!}" name="id" />
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">类型名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="parameterType1"  name="parameterType"  readonly="readonly" value="${parameter.type.name!}">
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>字典项编码</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="code" id ="code" value="${(parameter.code)!''}"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且不能输入中文</span>
				</div>   
			    <div class="form-group">
				</div>                                                                                      			                                                                                                 
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>字典项名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="parameterValue1" name="parameterValue"  value="${parameter.name}">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
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
	$('#parameterValue1').blur(function(){
	   var parameterType  = $("#parameterType1").val();
	   var parameterValue = $("#parameterValue1").val(); 
	   if(parameterType == '176c9150-7103-11e3-ae10-6cae8b21aead' && !/^([0-9]{1,2}|100)$/.test(parameterValue)){
		    $(this).parent().parent().find(".alert-danger:eq(0)").show();  
		     $("#editParameter").attr("disabled",true);	     
	   }else if(parameterValue == '' || parameterValue == null){
		    $(this).parent().parent().find(".alert-danger:eq(0)").show();  
		    $("#editParameter").attr("disabled",true);		     
	   }else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();	
	        $("#editParameter").attr("disabled",false);	     	              
	   }
	});
	$('#code').blur(function(){
	    var code = $("#code").val();
		if(code == '' || code == null){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();	
		   $("#editParameter").attr("disabled",true);		 		     	              
		}else if(code != '' && !/^[^\u4e00-\u9fa5]{0,}$/.test(code)){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();	
		   $("#editParameter").attr("disabled",true);	 		     	              
		}else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();	
	       $("#editParameter").attr("disabled",false);	 
	    }
	});
});
</script>       