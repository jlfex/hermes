<style type="text/css">
.ml_180{ margin-left:185px;}
.mr_10{margin-right:10px;}
.low,.middle,.high{ width:60px; height:10px; display:inline-block; border:1px solid #e4e4e4;}
.rank_bg{ background:#e68f07;}
</style>
 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary" >
        <div class="panel-heading">用户新增</div>
        <div class="panel-body" style="width:850px;">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="addUserForm" method="post">
               <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>用户名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="userName"  name="userName">
                </div>
				<div class="col-xs-2">
				    <span id="alert-uniqName" class="alert-success"></span>
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>密码</label>
                <div class="col-sm-5">
                  <input type="password" class="form-control" autocomplete="off"  id="userPwd"  name="userPwd" >
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"></label>
                <div class="col-sm-5">
                  <div class="ml_180" style="margin-top:-0px;margin-left:12px">
		             <span class="low  mr_10" style="background:#C8C8C8;"></span>
		             <span class="middle  mr_10" style="background:#C8C8C8;"></span>
		             <span class="high" style="background:#C8C8C8;"></span>
		          </div>
                </div>
				<div class="col-xs-2">
				</div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>确认密码</label>
                <div class="col-sm-5">
                  <input type="password" class="form-control" id="configPassword" autocomplete="off"  name="configPassword" >
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;"> </span>备注</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="remark" name="remark">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addBtn">添加</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelBtn">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
jQuery(function($) {
	$("#userName,#userPwd,#configPassword").on('blur',function(i,item){
		checkInput(this);
	});
	//判断用户名称唯一
	function chenckUserNameUniq(userName){
	  var flag = false;
	  $.ajax({
			type : 'POST',
			async: false,
			url : '${app}/user/checkAccount',
			data : 'account='+userName,
			 success : function(data)
				{
				   flag=data.account;
				   return ;
				},
				error : function(data)
				{
				   return false;
				}
	  });
	  return flag;
	}
	//对输入元素进行校验
	function checkInput(e){
		var $this = $(e);
		if($.trim($this.val()) == ''){
		    $this.val('');
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false; 
		}else if('userName' == e.id){
		    if(!chenckUserNameUniq($("#userName").val())){
		       $("#alert-uniqName").html("").hide();
		      $this.parent().parent().find(".alert-danger:eq(0)").html("该用户已经被使用").show();
			  return false;
		    }else{
		      $("#alert-uniqName").show().html("账户可以使用");
		    }
		}else if('userPwd' == e.id  && !/^[A-Za-z0-9_!@#$%^&*()]{6,16}$/.test($("#userPwd").val()) ){
			$this.parent().parent().find(".alert-danger:eq(0)").html("长度6-16位数字或字母组合").show();
			return false;
		}else if('configPassword' == e.id  && !/^[A-Za-z0-9_!@#$%^&*()]{6,16}$/.test($("#configPassword").val()) ){
			$this.parent().parent().find(".alert-danger:eq(0)").html("长度6-16位数字或字母组合").show();
			return false;
		}else if('configPassword' == e.id  && $("#configPassword").val() != $("#userPwd").val() ){
			$this.parent().parent().find(".alert-danger:eq(0)").html("密码不一致").show();
			return false;
		}
		$this.val($.trim($this.val()));
		$this.parent().parent().find(".alert-danger:eq(0)").hide();
		return true;
	}
	//元素失去焦点时，触发数据校验事件
	function checkAll(){
		$("#userName,#userPwd,#userPwdConfirm").each(function(i,item){
			checkInput(this);
		});
		return $("span.alert-danger:visible").length==0;
	}
	
	//点击添加按钮
	$("#addBtn").on("click",function(){
	 if(checkAll()){
		$.link.html(null, {
			url: '${app}/privilege/saveUser',
			data: $("#addUserForm").serialize(),
			target: 'main'
		});
	  };
	});
    //点击取消按钮
	$("#cancelBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/privilege/userIndex',
			target: 'main'
		});
	});
	

function checkPassWrodEqual() {
	var signPwd = $("#userPwd").value;
	var confPwd = $("#configPassword").value;
	if (signPwd == confPwd) {
		return true;
	} else {
		return false;
	}
}

var securityLevel;
$(function(){
	$(".ml_180").hide();
	$("#userPwd").bind('input propertychange', function() {
		$(".ml_180").show();
		if($("#userPwd").val()==""){
			$(".ml_180").hide();
		}
		calLevel();
	});
 });
 	
 function calLevel() {
		var value = $.trim($("#userPwd").val());
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

});
</script>
