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
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>参数类</label>
                <div class="col-sm-5">
                      <select class="form-control selector" name="parameterType" id="parameterType1">
                        <option value="176c9150-7103-11e3-ae10-6cae8b21aead" selected="selected">产品招标期限</option>
                        <option value="176c9150-7103-11e3-ae10-6cae8b21aeaa">产品用途</option>                        
                        <option value="176c9150-7103-11e3-ae10-6cae8b21aeab">产品担保方式</option>
                        <option value="b6f885cb-956c-11e4-90ca-b87932903a74">还款方式</option>
				      </select>
                </div>
               </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>参数值</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" name="parameterValue" id ="parameterValue1" placeholder="参数值"/>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，且只能输入1~100的数字</span>
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
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addParameter">添加</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelParameter">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">

jQuery(function($) {
     //点击新增分类页面中新增按钮
	$("#addParameter").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/handerAddDictionary',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});
	//点击新增分类页面中取消按钮
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
	   if(parameterType == '176c9150-7103-11e3-ae10-6cae8b21aead' && !/^([0-9]{1,2}|100)$/.test(parameterValue)){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();    
	   }else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();
	   }
	});

});
</script>  