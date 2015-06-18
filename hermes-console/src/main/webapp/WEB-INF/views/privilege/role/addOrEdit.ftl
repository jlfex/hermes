<div class="panel panel-primary">
	<div class="panel-heading"><#if role??>角色编辑<#else>角色新增</#if></div>
	<div class="panel-body">
		<form id="addForm" method="post" action="">
			<div class="row hm-row form-group">
				<label for="creditorNo" class="col-xs-1 control-label">角色代码</label>
				<div class="col-xs-2">
					<input id="code" name="code" type="text" class="form-control" value="<#if role??>${(role.code)!''}</#if>"> 
					<#if role??>
					 <input id="id" name="id" type="hidden" class="form-control" value="${(role.id)}">
					</#if>
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="name" class="col-xs-1 control-label">角色名称</label>
				<div class="col-xs-2">
					<input id="name" name="name" type="text" class="form-control" value="${(role.name)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<div class="col-xs-offset-1 col-xs-3">
				     <#if creditorKind?? && creditorKind=='01' >
				     <#else>
				      <button id="pushIn" type="button" class="btn btn-promary">提交</button>
				     </#if>
					<button id="retreat" type="button" class="btn btn-promary">取消</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	if('${role!''}'.length > 0) {
		$("#code").attr("readonly","readonly");
	} else {
		$("#code").removeAttr("readonly");
	}
	
	$("#code,#name").on("blur",function(){
		checkInput(this);
	});
	
	function checkInput(e){
		var $this = $(e);
		var val = $this.val();
		if($this.val()==''){
			$this.parent().parent().find(".alert-danger:eq(0)").attr("e_id",e.id);
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}else{
			if(e.id == 'code') {
				var flag = isValid(e);
				if(!flag) {
					$this.parent().parent().find(".alert-danger:eq(0)").attr("e_id",e.id);
					$this.parent().parent().find(".alert-danger:eq(0)").html("您输入的角色代码非法，重复了！");
					$this.parent().parent().find(".alert-danger:eq(0)").show();
					return false;
				}
			}
			var e_id = $this.parent().parent().find(".alert-danger:eq(0)").attr("e_id");
			if(e_id=='' || e_id==e.id){
				$this.parent().parent().find(".alert-danger:eq(0)").hide();
			}
			return true;
		}
	}
	
	function checkAll(){
		$("#code,#name").blur();
		return $("span.alert-danger:visible").length==0;
	}
	
	function isValid(e) {
		var addOrEdit = $('#id').length > 0 ? $('#id').val() : "-1";
		var flag = false;
		
		$.ajax({
			url:"${app}/privilege/isValidCode?addOrEdit="+addOrEdit+"&code="+$("#code").val(),
			type:"POST",
			dataType:'json',
			async: false,
			success:function(data) {
				if(data.type == "SUCCESS") {
					flag = true;
				} else {
					flag = false;
				}
			},
			error:function() {
				flag = false;
			}
		});
		
		return flag;
	}
	
	$("#pushIn").on("click",function(){
	   if(checkAll()){
	   		
			$.confirm({
			confirmButton: '确定',
			cancelButton: '取消',
			cancelButtonClass: 'btn-danger',
		    title: '提示',
		    content: $("#id").length > 0 ? "您确定要编辑吗？":"您确定要新增吗？",
		    confirm: function(){
		       $.ajax({
     				url:"${app}/privilege/saveOrEdit/",
     				type:"POST",
     				dataType:'json',
     				data: $("#addForm").serialize(),
     				success:function(data) {
     					if(data.type == "SUCCESS") {
     						$.alert({
							    title: '结果',
							    content: data.messages[0],
							    icon: 'glyphicon glyphicon-heart',
							    confirm: function(){
							     		$.link.html(null, {
											url: '${app}/privilege/roleIndex',
											target: 'main'
										});
							    }
							});
     					} else {
     						$.alert({
							    title: '结果',
							    icon: 'fa fa-warning',
							    content: data.message
							});
     					}
     				}
		       });
		    },
		    cancel: function(){
		}
	});
		
			
		};
	});
	
	$("#retreat").on("click",function(){
		$.link.html(null, {
			url: '${app}/privilege/roleIndex',
			target: 'main'
		});
	});	
});
//-->
</script>
