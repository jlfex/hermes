<div class="panel panel-default">
	<div class="panel-heading"><@messages key="account.password.modify"/></div>
  <div class="panel-body">
     <div class="form-group">
	 	<div class="col-sm-offset-2 col-sm-10">
			<div id="back-info" class="col-sm-4 control-group" ></div>
		</div>
	</div>
    <form class="form-horizontal" role="form" id="passwordForm" name="passwordForm">
	  <div class="form-group">
	    <label class="col-sm-2 control-label">*<@messages key="account.password.original"/>：</label>
	  	 <div id="originalDiv" class="col-sm-4 control-group " >
			<input type="password" class="form-control " id="orginalPwd" name="orginalPwd" placeholder="Password" onBlur="check()"  >
		</div>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">*<@messages key="account.password.new"/>：</label>
	  	 <div id="newDiv" class="col-sm-4 control-group" >
			<input type="password" class="form-control" id="password" name="password" placeholder="Password"  onBlur="verify(this.value)" >
		</div>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">*<@messages key="account.password.confirm"/>：</label>
	  	 <div id="confirmDiv" class="col-sm-4 control-group" >
			<input type="password" class="form-control" id="confirm" name="confirm" placeholder="Password" onBlur="comparePwd()"  >
		</div>
	  </div>
	  <div class="form-group">
	 	<div class="col-sm-offset-2 col-sm-10">
			<button type="button" class="btn btn-primary " id="modifyPwd" ><@messages key="common.op.save"/></button>
		</div>
	</div>
    </form>
  </div>
</div>

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
	 	$("#originalDiv").addClass("has-success"); 
	 	return true;
	 }else{
	 	 $("#originalDiv").removeClass("has-success"); 
	 	$("#originalDiv").addClass("has-error"); 
	 	return false;
	 }
}	
function verify(pwd){
	if(pwd.length>5){
	$("#newDiv").removeClass("has-error"); 
		 $("#newDiv").addClass("has-success");
		 return true; 
	}else{
		 $("#newDiv").removeClass("has-success"); 
		 $("#newDiv").addClass("has-error"); 
		 return false;
	}
}

function comparePwd(){
  var password=$("#password").val();
  var confirm=$("#confirm").val();
  if(password==confirm){
  	$("#confirmDiv").addClass("has-success");
  	return true;
  }else{
  	$("#confirmDiv").removeClass("has-success"); 
	$("#confirmDiv").addClass("has-error"); 
	return false;
  }
}
//-->
</script>

</body>
</html>
