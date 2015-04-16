 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">修改类型</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="editForm" method="post">
          <input type="hidden" value="${(parameter.id)!}" name="id" />
             <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>类型编码</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="typeCode" id ="typeCode" value="${(parameter.code)!''}"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且不能输入中文</span>
				</div>   
			    <div class="form-group">
				</div>                                                                                      			                                                                                                       
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>类型名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="parameterType" id ="parameterType" value="${parameter.name!}"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                                        
              </div>  
               <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="editParameterType">保存</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelParameterType">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">

jQuery(function($) {
  
	$("#editParameterType").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/handerEditParameterType',
			data: $("#editForm").serialize(),
			target: 'main'
		});
	});
	
	$("#cancelParameterType").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/index',
			target: 'main'
		});
	});
	
	$('#parameterType').blur(function(){
	   var parameterType  = $("#parameterType").val();
	   if(parameterType == '' || parameterType == null){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();
		   document.getElementById("addParameterType").disabled = true;		     		       
	   }else{
	   	   $(this).parent().parent().find(".alert-danger:eq(0)").hide();	
	   	   document.getElementById("addParameterType").disabled = false;		     	   	      
	   }
	});
	$('#typeCode').blur(function(){
	    var typeCode = $("#typeCode").val();
		if(typeCode == '' || typeCode == null){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();	
		   document.getElementById("editParameterType").disabled = true;		     	              
		}else if(typeCode != '' && !/^[^\u4e00-\u9fa5]{0,}$/.test(typeCode)){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();	
		   document.getElementById("editParameterType").disabled = true;		     	              
		}else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();	
	       document.getElementById("editParameterType").disabled = false;
	    }
	});

});
</script>  