<div class="panel panel-primary">
	<div class="panel-heading"><#if creditor??>债权人编辑<#else>债权人新增</#if></div>
	<div class="panel-body">
		<form id="addForm" method="post" action="">
			<div class="row hm-row form-group">
				<label for="creditorNo" class="col-xs-1 control-label">债权人编号</label>
				<div class="col-xs-2">
					<input  disabled="true" type="text" class="form-control" value=" <#if creditor??>${(creditor.creditorNo)!''}<#else>${creditorNo!''}</#if>"> 
					<input id="creditorNo" name="creditorNo" type="hidden" class="form-control" value="<#if creditor??>${(creditor.creditorNo)!''}<#else>${creditorNo!''}</#if>">
					<#if creditor??>
					 <input  name="id" type="hidden" class="form-control" value="${(creditor.id)}">
					</#if>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="creditorName" class="col-xs-1 control-label">债权人名称</label>
				<div class="col-xs-2">
					<input id="creditorName" name="creditorName" type="text" class="form-control" value="${(creditor.creditorName)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">请输入2-10个字符，支持中文,英文,数字, “_”,“-”</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="certType" class="col-xs-1 control-label">证件类型</label>
				<div class="col-xs-2">
				    <select class="form-control" name="certType" id="certType">
						<option value="00"  >身份证</option>
						<option value="01"  >组织机构代码</option>
					</select>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="certificateNo" class="col-xs-1 control-label">证件号码</label>
				<div class="col-xs-2">
					<input id="certificateNo" name="certificateNo" type="text" class="form-control" value="${(creditor.certificateNo)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="bankAccount" class="col-xs-1 control-label">银行卡号</label>
				<div class="col-xs-2">
					<input id="bankAccount" name="bankAccount" type="text" class="form-control" value="${(creditor.bankAccount)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项:请输入有效的银行卡号</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="bankName" class="col-xs-1 control-label">银行名称</label>
				<div class="col-xs-2">
					<input id="bankName" name="bankName" type="text" class="form-control" value="${(creditor.bankName)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="bankBrantch" class="col-xs-1 control-label">所在地</label>
				<div class="col-xs-2">
					<input id="bankBrantch" name="bankBrantch" type="text" class="form-control" value="${(creditor.bankBrantch)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="contacter" class="col-xs-1 control-label">联系人</label>
				<div class="col-xs-2">
					<input id="contacter" name="contacter" type="text" class="form-control" value="${(creditor.contacter)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="cellphone" class="col-xs-1 control-label">电话</label>
				<div class="col-xs-2">
					<input id="cellphone" name="cellphone" type="text" class="form-control" value="${(creditor.cellphone)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">格式有误</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="mail" class="col-xs-1 control-label">邮箱</label>
				<div class="col-xs-2">
					<input id="mail" name="mail" type="text" class="form-control" value="${(creditor.mail)!}" >
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">无效的邮箱</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="assoureType" class="col-xs-1 control-label">担保方式</label>
				<div class="col-xs-2">
					<input id="assoureType" name="assoureType" type="text" class="form-control" value="${(creditor.assoureType)!}">
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="source" class="col-xs-1 control-label">来源</label>
				<div class="col-xs-2">
					<input id="source" name="source" type="text" class="form-control" value="${(creditor.source)!}">
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="remark" class="col-xs-1 control-label">简介</label>
				<div class="col-xs-2">
				    <textarea class="form-control" rows="5" cols="3" name="remark" id="description">${(creditor.remark)!}</textarea>
				</div>
			</div>
			<div class="row hm-row form-group">
				<div class="col-xs-offset-1 col-xs-3">
					<button id="pushIn" type="button" class="btn btn-promary">提交</button>
					<button id="retreat" type="button" class="btn btn-promary">取消</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$("#creditorName,#cert_type,#certificateNo,#bankAccount,#bankName,#bankBrantch,#contacter,#cellphone,#mail").on("blur",function(){
		checkInput(this);
	});
	
	function checkInput(e){
		var $this = $(e);
		var val = $this.val();
		if($this.val()==''
		   ||( e.id == 'creditorName' && (!/^[a-zA-Z0-9\u4e00-\u9fa5_-]+$/.test(val) || val.length < 2 || val.length > 10) )
		   ||( e.id == 'bankAccount' && !/^\d{16,19}$/.test(val) )
		   ||( e.id == 'cellphone' && !/^1[3458]\d{9}$/.test(val) )
		   ||( e.id == 'mail' && !/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(val) )
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
	
	function checkAll(){
		$("#creditorName,#cert_type,#certificateNo,#bankAccount,#bankName,#bankBrantch,#contacter,#cellphone,#mail").blur();
		return $("span.alert-danger:visible").length==0;
	}
	
	$("#pushIn").on("click",function(){
	   if(checkAll()){
		$.link.html(null, {
			url: '${app}/credit/add',
			data: $("#addForm").serialize(),
			target: 'main'
		});
		};
	});
	$("#retreat").on("click",function(){
		$.link.html(null, {
			url: '${app}/credit/index',
			target: 'main'
		});
	});	
});
//-->
</script>
