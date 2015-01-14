<#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="panel panel-primary">
	<div class="panel-heading">${title!}</div>
	<div class="panel-body">
		<form id="addForm" method="post" action="${app}/product/doadd">
			<input type="hidden" value="${(prodtl.id)!}" name="id" />
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.code" /></label>
				<div class="col-xs-3">
					<input id="code" name="code" type="text" class="form-control" value="<#if (prodtl.code)??>${(prodtl.code)!}<#else>000001</#if>">
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，数字、汉字、英文字母等，限20个字符</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.name" /></label>
				<div class="col-xs-3">
					<input id="name" name="name" type="text" class="form-control" value="${(prodtl.name)!}">
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，2-8个汉字</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.amount" /></label>
				<div class="col-xs-1">
					<input id="amount1" name="amount" type="text" class="form-control" value="<#if (prodtl.amount)??>${prodtl.amount?substring(0,prodtl.amount?index_of(','))}</#if>">
				</div>
				<div class="col-xs-1">
					<input id="amount2" name="amount" type="text" class="form-control" value="<#if (prodtl.amount)??>${prodtl.amount?substring(prodtl.amount?index_of(',')+1)}</#if>">
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，并且为整数</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.period" /></label>
				<div class="col-xs-1">
					<input id="period1" name="period" type="text" class="form-control" value="<#if (prodtl.amount)??>${prodtl.period?substring(0,prodtl.period?index_of(','))}</#if>">
				</div>
				<div class="col-xs-1">
					<input id="period2" name="period" type="text" class="form-control"  value="<#if (prodtl.amount)??>${prodtl.period?substring(prodtl.period?index_of(',')+1)}</#if>">
				</div>
				<div class="col-xs-1">
					<select class="form-control" name="periodType" id="periodType">
						<option value="月">月</option>
						<option value="天">天</option>
					</select>
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，并且为整数</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.rate" /></label>
				<div class="col-xs-1">
					<input id="rate1" name="rate" type="text" class="form-control" value="<#if (prodtl.amount)??>${prodtl.rate?substring(0,prodtl.rate?index_of(','))}</#if>">
				</div>
				<div class="col-xs-1">
					<input id="rate2" name="rate" type="text" class="form-control" value="<#if (prodtl.amount)??>${prodtl.rate?substring(prodtl.rate?index_of(',')+1)}</#if>">
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，最多保留2位小数</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.repay" /></label>
				<div class="col-xs-4">
					<#list repay as l>
					<label class="radio-inline">
						<input type="radio" <#if (prodtl.repay.id)?? && prodtl.repay.id == l.id >checked</#if> name="repayId" value="${l.id}" />${l.name}
					</label>
					</#list>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.purpose" /></label>
				<div class="col-xs-4">
					<#list purpose as l>
					<label class="radio-inline">
						<input type="radio" <#if (prodtl.purpose.id)?? && prodtl.purpose.id == l.id >checked</#if> name="purposeId" value="${l.id}" />${l.name}
					</label>
					</#list>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label class="col-xs-1 control-label"><@messages key="model.product.guarantee" /></label>
				<div class="col-xs-4">
					<#list guarantee as l>
						<label class="radio-inline">
							<input <#if (prodtl.guarantee.id)??>${(prodtl.guarantee.id==l.id)?string('checked','')}</#if> type="radio" name="guaranteeId" value="${l.id}" />${l.name!}
						</label>
					</#list>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.startingamt" /></label>
				<div class="col-xs-3">
					<input id="startingAmt" name="startingAmt" type="text" class="form-control" value="<#if (prodtl.startingAmt)??>${(prodtl.startingAmt)?string('0')}</#if>"/>
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，并且为整数</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.deadline" /></label>
				<div class="col-xs-2">
					<select name="deadline" class="form-control">
						<#list deadline as l>
							<option value="${l.name}" <#if (prodtl.deadline)?? && prodtl.deadline?string('0')==l.name>selected="selected"</#if>>${l.name}</option>
						</#list>
					</select>
				</div>
				<div class="col-xs-1">
					天
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，限20字以内</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="code" class="col-xs-1 control-label"><@messages key="model.product.description" /></label>
				<div class="col-xs-3">
					<textarea class="form-control" rows="3" name="description" id="description">${(prodtl.description)!}</textarea>
				</div>
				<div class="col-xs-3">
					<span class="alert-danger" style="display:none;background:none">必填项，限20字以内</span>
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
	
	$("#code,#name,#amount1,#amount2,#period1,#period2,#rate1,#rate2,#startingAmt,#description").on("keyup",function(){
		checkSignInput(this);
	});
	
	if($("input[name=repayId]:checked").length==0){
		$("input[name=repayId]:eq(0)").prop("checked","checked")
	}

	if($("input[name=purposeId]:checked").length==0){
		$("input[name=purposeId]:eq(0)").prop("checked","checked")
	}
	var num_0 = /^(([1-9]\d{0,9}))$/;
	
	var num_2 = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	
	var zw_2_8 = /^[\u4e00-\u9fa5]{2,8}$/;
	
	var zf_20 = /^[\w\u4e00-\u9fa5]{1,20}$/
	
	function checkSignInput(e){
		var $this = $(e);
		if($this.val()=='' 
			|| (e.id == 'startingAmt' && !num_0.test($this.val()))
			|| ((e.id == 'rate1' || e.id == 'rate2') && !num_2.test($this.val()))
			|| ((e.id == 'period1' || e.id == 'period2') && !num_0.test($this.val()))
			|| ((e.id == 'name') && !zw_2_8.test($this.val()))
			|| ((e.id == 'amount1' || e.id == 'amount2') && !num_0.test($this.val()))
			|| ((e.id == 'description') && !zf_20.test($this.val()))
			|| ((e.id == 'code') && !zf_20.test($this.val()))
			
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
	
	function checkInput(){
		$("#code,#name,#amount1,#amount2,#period1,#period2,#rate1,#rate2,#startingAmt,#description").keyup();
		return $("span.alert-danger:visible").length==0;
	}

	$("#pushIn").on("click",function(){
	if(checkInput()){
		$.link.html(null, {
			url: '${app}/product/doadd',
			data: $("#addForm").serialize(),
			target: 'main'
		});
		};
	});
	$("#retreat").on("click",function(){
		$.link.html(null, {
			url: '${app}/product/index',
			target: 'main'
		});
	});
});
//-->
</script>
