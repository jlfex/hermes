<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div id="identityForm" class="panel embed">
	<div class="panel-body">
		<form class="form-horizontal" role="form" id="authIdentityForm">
			<div class="form-group">
					<p class="text-warning text-center">实名验证需要支付验证费用%s元，请确认您输入的信息为您的真实信息</p>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label "><@messages key="account.info.auth.realName" /></label>
				<div class="col-xs-4 u-col" >
					<input type="text" class="form-control" id="realName" name="realName"  >
					<label for="realName" generated="true" class="error valid"></label>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label"><@messages key="account.info.auth.idType" /></label>
				<div class="col-xs-4 u-col" >
					<select id="idType" name="idType"  class="form-control" >
						<#list idTypeMap?keys as key> 
							<option value="${key}" <#if userBasic?exists><#if userBasic.idType?exists&&userBasic.idType==key> selected</#if></#if>>${idTypeMap[key]}</option> 
						</#list>
					</select>
					<label for="idType" generated="true" class="error valid"></label>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label"><@messages key="account.info.auth.idNumber" /></label>
				<div class="col-xs-4 u-col" >
					<input type="text" class="form-control" id="idNumber" name="idNumber"  >
					<label for="idNumber" generated="true" class="error valid"></label>
				</div>
			</div>
			<div id="authIdMessage" class="form-group hidden">
				<div id="authIdResult" class="alert alert-warning col-xs-4 col-xs-offset-4 u-col" >
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-2 col-xs-offset-4 u-col">
					<button id="confirmAuthIdentityBtn" type="submit" class="btn btn-primary btn-block"><@messages key="common.op.confirm" /></button>
				</div>
				<div class="col-xs-2 u-col">
					<button id="cancelAuthIdentityBtn" type="button" class="btn btn-default btn-block"><@messages key="common.op.cancel" /></button>
				</div>
			</div>
		</form>
	</div>
	
	<script type="text/javascript" charset="utf-8">
	<!--
	jQuery(function($) {
		$('#cancelAuthIdentityBtn').on('click', function() {
			$('#identityForm').hide('fast', function() {
				$(this).remove();
				$('#authNameBtn').show();
			});
		});
	});
	function authIdentity(){
		$.ajax({
				data: $("#authIdentityForm").serialize(),
		        url: "${app}/auth/authId",
		        type: "POST",
		        dataType: 'json',
		        timeout: 10000,
		        success: function(data) {
		      	   if(data.type=="SUCCESS"){
	            		$('#identityForm').hide('fast', function() {
							$(this).remove();
							 $('.identity p').removeClass('non').html('<span><i class="fa fa-check-circle"></i></span> <@messages key="account.info.auth.passed" />');
							$('#authIdOk').removeClass("hidden");
						});
					}else if(data.type=="FAILURE"){
						$("#authIdMessage").removeClass("hidden");	
						$("#authIdResult").html(data.firstMessage);
					}
		        }
		    });
	}
	//-->
	</script>
</div>
