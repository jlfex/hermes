<div class="panel panel-default">
	<div class="panel-heading"><@messages key="account.password.modify"/></div>
  <div class="panel-body">
     <div class="form-group">
	 	<div class="col-sm-offset-2 col-sm-10">
			<div id="back-info" class="col-sm-12 control-group" style="padding: 10px 0 15px 0;color:#ff451f;" ></div>
		</div>
	</div>
    <form class="form-horizontal" role="form" id="passwordForm" name="passwordForm">
	  <div class="form-group">
	    <label class="col-sm-2 control-label">*<@messages key="account.password.original"/>：</label>
	  	 <div id="originalDiv" class="col-sm-4 control-group " >
			<input type="password" autocomplete = "off" class="form-control " id="orginalPwd" name="orginalPwd" placeholder="Password" onBlur="check()"  >
		</div>
		<span class="col-sm-6 error_tip" style="display:none;padding:7px 0 0 0;color:#ff451f;">请输入原密码</span>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">*<@messages key="account.password.new"/>：</label>
	  	 <div id="newDiv" class="col-sm-4 control-group" >
			<input type="password" autocomplete = "off" class="form-control" id="password" name="password" placeholder="Password"  onBlur="verify(this.value)" >
		</div>
		<span class="col-sm-6 error_tip" style="display:none;padding:7px 0 0 0;color:#ff451f;">请输入原密码</span>
	  </div>
	  <div class="ml_180" style="margin-top:-10px;margin-left:122px">
             <span class="low  mr_10" style="background:#C8C8C8;"></span>
             <span class="middle  mr_10" style="background:#C8C8C8;"></span>
             <span class="high" style="background:#C8C8C8;"></span>
      </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">*<@messages key="account.password.confirm"/>：</label>
	  	 <div id="confirmDiv" class="col-sm-4 control-group" >
			<input type="password" autocomplete = "off"  class="form-control" id="confirm" name="confirm" placeholder="Password" onBlur="comparePwd()"  >
		</div>
		<span class="col-sm-6 error_tip" style="display:none;padding:7px 0 0 0;color:#ff451f;">请输入原密码</span>
	  </div>
	  <div class="form-group">
	 	<div class="col-sm-offset-2 col-sm-10">
			<button type="button" class="btn btn-primary " id="modifyPwd" ><@messages key="common.op.save"/></button>
		</div>
	</div>
    </form>
  </div>
</div>
<style type="text/css">
	 .ml_180{ margin-left:185px;}
     .mr_10{margin-right:10px;}
     .low,.middle,.high{ width:60px; height:10px; display:inline-block; border:1px solid #e4e4e4;}
     .rank_bg{ background:#e68f07;}
</style>
<script type="text/javascript">
<!--
jQuery(function($) {
	$('#modifyPwd').on('click',function(){
		 var password=$("#password").val();
		 var originalPwd = $("#orginalPwd").val();
		 if(check()&&comparePwd()&&verify(password)){
		 	  $.ajax({
		        data: $("#passwordForm").serialize(),
		        url: "${app}/account/resetPassword",
			    type: "POST",
			    dataType: 'json',
		        success: function(data) {
		           $(":input").val("");
		           if (data.typeName === 'success') {
		               $("#back-info").html(data.data);
		           }else{
		               $("#back-info").html(data.data);
		           }
		        },
		        error: function(data){
		           $(":input").val("");
		           $("#back-info").html("error!");
		        }
		    });
		 }else{
		    
		 }
	});
});
function check(){
	 var confpwd=$("#orginalPwd").val();
	 if(confpwd.length>0){
	 	if(!/^[A-Za-z0-9_!@#$%^&*()]{6,16}$/.test(confpwd)){
		 	$("#originalDiv").removeClass("has-success"); 
		 	$("#originalDiv").addClass("has-error"); 
	 		$("#orginalPwd").parent().next().html("6-16个字符，建议使用字母加数字或符号的组合密码");
	 		$("#orginalPwd").parent().next().show();
	 		return false;
	 	}
	 	$("#originalDiv").addClass("has-success"); 
	 	$("#orginalPwd").parent().next().hide();
	 	return true;
	 }else{
	 	$("#originalDiv").removeClass("has-success"); 
	 	$("#originalDiv").addClass("has-error"); 
	 	$("#orginalPwd").parent().next().html("请输入原始密码");
	 	$("#orginalPwd").parent().next().show();
	 	return false;
	 }
}	
function verify(pwd){

	var password = $("#password").val();
	if(password.length == 0){
		$("#newDiv").removeClass("has-success"); 
		$("#newDiv").addClass("has-error");
	 	$("#password").parent().next().html("请输入新密码");
	 	$("#password").parent().next().show();
	 	return false;
	}else{
		if(!/^[A-Za-z0-9_!@#$%^&*()]{6,16}$/.test(password)){
			$("#newDiv").removeClass("has-success"); 
			$("#newDiv").addClass("has-error");
	 		$("#password").parent().next().html("6-16个字符，建议使用字母加数字或符号的组合密码");
	 		$("#password").parent().next().show();
	 		return false;
	 	}
	 	$("#newDiv").removeClass("has-error"); 
		 $("#newDiv").addClass("has-success");
		 $("#password").parent().next().hide();
		 return true; 
	}
}

function comparePwd(){
  var password=$("#password").val();
  var confirm=$("#confirm").val();
  
  if(password==confirm){
  	$("#confirmDiv").addClass("has-success");
	$("#confirm").parent().next().hide();
  	return true;
  }else{
  	$("#confirmDiv").removeClass("has-success"); 
	$("#confirmDiv").addClass("has-error"); 
	$("#confirm").parent().next().html("两次输入密码不一致");
	$("#confirm").parent().next().show();
	return false;
  }
}
//-->

var securityLevel;
$(function(){
	$(".ml_180").hide();
	$("#password").bind('input propertychange', function() {
		$(".ml_180").show();
		if($("#password").val()==""){
			$(".ml_180").hide();
		}
		calLevel();
	});
 });
 	
 function calLevel() {
		var value = $.trim($("#password").val());
		$(".low").css("background-color", "#C8C8C8");
		$(".middle").css("background-color", "#C8C8C8");
		$(".high").css("background-color", "#C8C8C8");
		if (value.length < 8 || countRepeat(value) >= 3) {
			securityLevel = 1;
		} else if (value.length >= 8 && value.length < 12) {
			securityLevel = 2;
		} else if (value.length >= 12) {
			securityLevel = 3;
		}
		switch (securityLevel) {
		case 1:
			$(".low").css("background-color", "#E68F07");
			break;
		case 2:
			$(".low").css("background-color", "#E68F07");
			$(".middle").css("background-color", "#E68F07");
			break;
		case 3:
			$(".low").css("background-color", "#E68F07");
			$(".middle").css("background-color", "#E68F07");
			$(".high").css("background-color", "#E68F07");
			break;
		}
	}
	
	function countRepeat(value) {
		var map = {};
		for ( var i = 0; i < value.length; i++) {
			var c = value.charAt(i);
			if (map[c]) {
				map[c]++;
			} else {
				map[c] = 1;
			}
		}
		var max = 0;
		for ( var v in map) {
			if (max < map[v]) {
				max = map[v];
			}
		}
		return max;
	}
	
</script>

</body>
</html>
