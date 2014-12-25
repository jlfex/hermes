<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div id="phoneForm" class="panel embed">
	<div class="panel-body">
		<form class="form-horizontal" role="form" id="authPhoneFrm" name="authPhoneFrm">
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label "><@messages key="account.info.auth.phone" /></label>
				<div id="test" class="col-xs-4 u-col" >
					<input type="text" class="form-control" id="cellphone" name="cellphone" >
					<label for="cellphone" generated="true" class="error valid"></label>
				</div>
				<div class="col-xs-2 u-col" >
					<button id="getValidateCodeBtn" type="button"  class="btn btn-primary btn-block"><@messages key="account.info.auth.getValidateCode" /></button>
				</div>
			</div>
			<div id="message" class="form-group hidden">
				<div id="result" class="alert alert-info col-xs-4 col-xs-offset-4 u-col" >
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label"><@messages key="account.info.auth.veritifyCode" /></label>
				<div class="col-xs-4 u-col" >
					<input type="text" class="form-control" id="validateCode" name="validateCode"  >
					<label for="validateCode" generated="true" class="error valid"></label>
				</div>
			</div>
			<div id="authPhoneMessage" class="form-group hidden">
				<div id="authPhoneResult" class="alert alert-info col-xs-4 col-xs-offset-4 u-col" >
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-2 col-xs-offset-4 u-col">
					<button id="confirmAuthPhoneBtn" type="submit" class="btn btn-primary btn-block"><@messages key="common.op.confirm" /></button>
				</div>
				<div class="col-xs-2 u-col">
					<button id="cancelAuthPhoneBtn" type="button" class="btn btn-default btn-block"><@messages key="common.op.cancel" /></button>
				</div>
			</div>
		</form>
	</div>
	
	<script type="text/javascript" charset="utf-8">
	<!--
	var seconds;
	jQuery(function($) {
		$('#cancelAuthPhoneBtn').on('click', function() {
			$('#phoneForm').hide('fast', function() {
				$(this).remove();
				$('#authCellphoneBtn').show();
			});
		});
		$("#getValidateCodeBtn").on("click",function(){
			$("#authPhoneMessage").addClass("hidden");
			seconds = 60;
			var cellphone=$("#cellphone").val();
			if(cellphone==''){
				$("#cellphone").closest('.col-xs-4').addClass("has-error");
				return false;
			}
			if($("#cellphone").closest('.col-xs-4').hasClass("has-error")){
				return false;
			}
			$("#getValidateCodeBtn").addClass("disabled");
			$.ajax('${app}/auth/sendPhoneCode/'+cellphone, {
				type: 'post',
				dataType: 'json',
				timeout: 10000,
				success: function(data, textStatus, xhr) {
					countDown(seconds);
					$("#message").removeClass("hidden");
					 if(data.type=="FAILURE"){
					 	$("#result").removeClass("alert-info").addClass("alert-warning");
					 }else{
					   $("#result").removeClass("alert-warning").addClass("alert-info");
						
					 }
					$("#result").html(data.firstMessage);
				}
			});
		});
		//$("#confirmAuthPhoneBtn").on("click",function(){});
	});
	function countDown(){
		if(seconds>0){
		 	$('#getValidateCodeBtn').text(seconds+'s');
		 	seconds=seconds-1;
			setTimeout("countDown(seconds)",1000);
		}else{
			$("#getValidateCodeBtn").removeClass("disabled");
			$('#getValidateCodeBtn').text('获取验证码');
			$("#result").removeClass("alert-info").removeClass("alert-warning");
			$("#message").addClass("hidden");
		}
	}
	
	function subAuthPhone(){
			var cellphone=$("#cellphone").val();
			var validateCode=$("#validateCode").val();
			$.ajax('${app}/auth/authCellphone/?cellphone='+cellphone+'&validateCode='+validateCode, {
				type: 'post',
				dataType: 'json',
				timeout: 10000,
				success: function(data, textStatus, xhr) {
					$("#authPhoneMessage").removeClass("hidden");
					 if(data.type=="FAILURE"){
					 	$("#authPhoneResult").removeClass("alert-info").addClass("alert-warning");
					 	$("#authPhoneResult").html(data.firstMessage);
					 }else{
					   $('#phoneForm').hide('fast', function() {
							$(this).remove();
							 $('.authphone p').removeClass('non').html('<span><i class="fa fa-check-circle"></i></span> <@messages key="account.info.auth.passed" />');
							$('#authPhoneOk').removeClass("hidden");
						});
					 }
				}
		});
	}
	//-->
	</script>
</div>
