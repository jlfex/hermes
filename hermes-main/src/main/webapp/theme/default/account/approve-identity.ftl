<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<!--<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>-->
<div id="identityForm" class="panel embed">
	<div class="panel-body">
		<form class="form-horizontal" role="form" id="authIdentityForm">
			<div class="form-group">
					<p class="text-warning text-center">实名验证需要支付验证费用%s元，请确认您输入的信息为您的真实信息</p>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label "><@messages key="account.info.auth.realName" /></label>
				<div class="col-xs-4 u-col" >
					<input type="text" class="form-control" id="realName" name="realName" onblur="verificationInf()" >
					<label for="realName" generated="true" class="error valid"></label>
					<span class="mv_msg col-xs-6" id="mv_realName" style="color:red;width:200px"></span>
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
					<span class="mv_msg col-xs-4" id="mv_idType" style="color:red;width:200px"></span>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label"><@messages key="account.info.auth.idNumber" /></label>
				<div class="col-xs-4 u-col" >
					<input type="text" class="form-control" id="idNumber" name="idNumber"  onblur="verificationInf()">
					<label for="idNumber" generated="true" class="error valid"></label>
					<span class="mv_msg col-xs-4" id="mv_idNumber" style="color:red;width:200px"></span>
				</div>
			</div>
			<!--
			<div id="authIdMessage" class="form-group hidden">
				<div id="authIdResult" class="alert alert-warning col-xs-4 col-xs-offset-4 u-col" >
						retgwergsdrgf
				</div>
			</div>
			-->
			<div class="form-group">
				<div class="col-xs-2 col-xs-offset-4 u-col">
					<button id="confirmAuthIdentityBtn" type="button" onClick="mysubmit()" class="btn btn-primary btn-block"><@messages key="common.op.confirm" /></button>
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
	
	function verificationInf(){
		var vname = /^[\u4e00-\u9fa5]{2,20}$/;
		var identityId1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{2}[xX\d]$/;
        var identityId2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[xX\d]$/;
        var passport=/^\w{5,20}$/;
        var realName=$("#realName").val();
        var idType=$("#idType").val();
        var idNumber=$("#idNumber").val();

		if(realName==""){
			$("#mv_realName").html("姓名不能为空");
			return false;
		}else if(!vname.test(realName)){
			$("#mv_realName").html("姓名只能为汉字");
			return false;
		}else{
			$("#mv_realName").html("");
		}
				
		if(idNumber==""){
			$("#mv_idNumber").html("证件号码不能为空");
			return false;
		}else{
			$("#mv_idNumber").html("");
		 }
		
		var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
		if(idType=="00"){
			if(idNumber.length==15){
				if(!identityId1.test(idNumber)){
					$("#mv_idNumber").html("身份证号码输入错误");
					return false;
				 }
			 }else{
			 	var iSum=0 ;
			 	
			    if(!/^\d{17}(\d|x)$/i.test(idNumber))  {
			    	$("#mv_idNumber").html("您输入的身份证长度或格式错误");
			    	return false;
			    }
		
			    idNumber=idNumber.replace(/x$/i,"a"); 
			    if(aCity[parseInt(idNumber.substr(0,2))]==null) {
			    	$("#mv_idNumber").html("您的身份证地区非法");
			    	return false;
			    }
			    	
			    sBirthday=idNumber.substr(6,4)+"-"+Number(idNumber.substr(10,2))+"-"+Number(idNumber.substr(12,2)); 
			    var d=new Date(sBirthday.replace(/-/g,"/")) ;
			    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())) {
			    	$("#mv_idNumber").html("您的身份证上的出生日期非法");
			    	return false;
			    }
			    	
			    for(var i = 17;i>=0;i --) {
			    	iSum += (Math.pow(2,i) % 11) * parseInt(idNumber.charAt(17 - i),11) ;
			    }
			    	
			    if(iSum%11!=1) {
			    	$("#mv_idNumber").html("您输入的身份证号非法");
			    	return false;
			    }
			    	
			    return true;
			 }
		 }else if(idType=="01"){
		 		if(!passport.test(idNumber)){
					$("#mv_idNumber").html("护照号码输入错误");
					return false;
				 }
		 }else{
		 	$("#mv_idType").html("证件类型错误");
		 	return false;
		 }
		 
		 return true;
	}
	
	
	function mysubmit(){
		if(verificationInf()){
			authIdentity();
		 }
	}
	
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
