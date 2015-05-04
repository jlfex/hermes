 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">修改接口</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="updateForm" method="post">
          <input type="hidden" value="${(apiConfig.id)!}" name="id" />
              <div class="form-group">
              <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>平台名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="platName"  name="platName" readonly="true" value="${(apiConfig.platName)!''}">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div> 
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>平台编码</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="platCode"  name="platCode" readonly="true" value="${(apiConfig.platCode)!''}">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>本地证书名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="clientStoreName" name="clientStoreName" value="${(apiConfig.clientStoreName)!''}">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>本地证书密码</label>
                <div class="col-sm-5">
                   <input type="text" class="form-control" id="clientStorePwd" name="clientStorePwd" value="${(apiConfig.clientStorePwd)!''}">                
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>服务端证书名称</label>
                <div class="col-sm-5">
                   <input type="text" class="form-control" id="truestStoreName" name="truestStoreName" value="${(apiConfig.truestStoreName)!''}">                
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>服务端密码</label>
                <div class="col-sm-5">
                   <input type="text" class="form-control" id="truststorePwd" name="truststorePwd" value="${(apiConfig.truststorePwd)!''}">                
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>服务端URL</label>
                <div class="col-sm-5">
                   <input type="text" class="form-control" id="apiUrl" name="apiUrl" placeholder="以http或https开头的url" value="${(apiConfig.apiUrl)!''}">                
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">服务端URL格式错误,且不能为空</span>
				</div>                                                          				                                                      
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>接口状态</label>
                <div class="col-sm-5">
                  <select id="status" name="status" class="form-control">
                   	  <option value="0">有效</option>
					  <option value="1">无效</option>
                   </select>              
                </div>                                                        
              </div>              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="updateBtn">添加</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelBtn">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
jQuery(function($) {
	$("#platName,#platCode,#clientStoreName,#clientStorePwd,#truestStoreName,#truststorePwd,#apiUrl").on('blur',function(i,item){
		checkInput(this);
	});
	//对输入元素进行校验
	function checkInput(e){
		var $this = $(e);
		if($this.val() == ''){
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		} else if ('apiUrl' == e.id && !/(http|https):\/\/([\w.]+\/?)\S*/.test($("#apiUrl").val())){
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}
		$this.parent().parent().find(".alert-danger:eq(0)").hide();
		return true;
	}
	//元素失去焦点时，触发数据校验事件
	function checkAll(){
		$("#platCode,#clientStoreName,#clientStorePwd,#truestStoreName,#truststorePwd,#apiUrl").each(function(i,item){
			checkInput(this);
		});
		return $("span.alert-danger:visible").length==0;
	}
	
	//点击添加按钮
	$("#updateBtn").on("click",function(){
	 if(checkAll()){
		$.link.html(null, {
			url: '${app}/apiConfig/handleUpdateApiConfig',
			data: $("#updateForm").serialize(),
			target: 'main'
		});
	  };
	});
    //点击取消按钮
	$("#cancelBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/apiConfig/index',
			target: 'main'
		});
	});
    $("#status").val("${(apiConfig.status)!''}");
});
</script>
