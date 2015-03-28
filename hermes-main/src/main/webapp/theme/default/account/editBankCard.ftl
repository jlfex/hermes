<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/jquery.autocomplete.css">

<div class="account_content_right">
	<div class="withdraw_amend zero_border">
        <div class="bkTitle">
            <p class="h_h04">更换银行卡</p>
            
        </div>
        <div style="width:750px;height:1px;margin:0px auto;padding:0px;background-color:#D5D5D5;overflow:hidden;"></div>
       <div class="addbank">
       		<form class="form-horizontal" role="form" id="authIdentityForm">
       		<input type="hidden" value="${bankAccount.id}" id="id" name="id">
            	<div class="m_block clearfix">
                	<label>持卡人</label>
                    <input type="text" id="realName" name="realName" value="${bankAccount.name}" readonly="readonly" style="border:0px;">
                </div>
                
                <div class="m_block clearfix">
                	<label>银行名称</label>
					<select id="bankId" name="bankId">
				        <#list banks as b>
					    <option value="${b.id}">${b.name}</option>
				        </#list>				    
					</select>
                </div>
                
               <div class="m_block clearfix">
                   <label>开户所在地</label>					
				   <select id="cityId2" name="cityId2"></select>
				   <select id="cityId" name="cityId" onchange="reloadBank();"></select>																																												
                </div>
                
                <div class="m_block clearfix">
                	<label>开户行</label>
					<input type="text" id="deposit" name="deposit" value="${bankAccount.deposit}" onblur="verificationInf()">
					<span id="mv_deposit" style="color:red;width:200px"></span>
                </div>
                
                <div class="m_block clearfix">
                	<label>银行账号</label>
					<input type="text" id="account" name="account" value="${bankAccount.account}" onblur="verificationInf()">
					<span  id="mv_account" style="color:red;width:200px"></span>
                </div>
                
                <div class="m_block clearfix">
                	<label>&nbsp;</label>
                    <input type="checkbox" id="isdefault" name="isdefault" checked="checked"> 设为默认银行卡
                </div>
            	
                <div class="databt" style="margin-left:30px;">
                   <button id="confirmAuthIdentityBtn" type="button" onClick="mysubmit()" class="m_btn3 m_bg1 a_middle" style="width:100px;height:30px;">确认</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   <button id="cancelAuthIdentityBtn" type="button" class="m_btn3 bt_red " style="width:100px;height:30px;">取消</button>
                </div>                
            </form>
       
       </div>
       <div class="bank" style="border-bottom:none; border-top:1px solid #e2e2e2;">
       		<p class="spBank">支持以下银行</p>
            <ul>
                <li><img src="${app.theme}/public/images/bank/bank01.png" /></li>
                <li><img src="${app.theme}/public/images/bank/bank02.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank03.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank04.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank05.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank06.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank07.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank08.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank09.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank10.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank11.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank12.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank13.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank14.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank15.png"></li>
                <li><img src="${app.theme}/public/images/bank/bank16.png"></li>                
                
            </ul>
            <div class="clearfix"></div>
       </div>
       
       
    </div>       
</div>
<script type="text/javascript" charset="utf-8">	
jQuery(function($) {
	$('#cancelAuthIdentityBtn').on('click', function() {
		     window.location.href="${app}/account/index?type=auth1";	   						
	});
    $.area({ data: ${area}, bind: [$('#cityId2'), $('#cityId')] });        
});
       //ajax获取后台数据
        function reloadBank(){
	 	    $.ajax({
		        data: $("#authIdentityForm").serialize(),
		        contentType: "application/json",
		        url: "${app}/userIndex/findBranchBankByBankAndCity",
		        dataType: "json",
		        success: function (msg) {
		            if (msg != null) {
		                $("#deposit").autocomplete(msg, {
		                    minChars: 1,                    //最少输入字条
		                    max: 30,
		                    autoFill: false,                //是否选多个,用","分开
		                    mustMatch: false,               //是否全匹配, 如数据中没有此数据,将无法输入
		                    matchContains: true,            //是否全文搜索,否则只是前面作为标准
		                    scrollHeight: 220,
		                    width: 190,
		                    multiple: false,
		                    formatItem: function (row, i, max) {                    //显示格式
		                    	return "<span>" + row.branchBankName + "</span>";
		                    },
		                    formatMatch: function (row, i, max) {               //以什么数据作为搜索关键词,可包括中文,
		                        return row.branchBankName;
		                    },
		                    formatResult: function (row) {                      //返回结果
		                        return row.branchBankName;
		                    }
		                }).result(function (event, data, formatted) {
		                    alert(data.id);
		                    //根据最终返回的数据做处理
		                    
		                });
		            }		            
		         }
		    });        
        }

	function mysubmit(){
		if(verificationInf()){
			bindBank();
		 }
	}

	function verificationInf(){
		var vdeposit = /^[\u4e00-\u9fa5]{2,20}$/;
		var vaccount=/^[0-9]{12,20}$/;
        var deposit=$("#deposit").val();
        var account=$("#account").val();
        var cityId=$("#cityId").val();
	

        if(cityId==""){
			$("#mv_cityId").html("请选择开户行所在地");
			return false;
		}
			
		if(deposit==""){
			$("#mv_deposit").html("开户行不能为空");
			return false;
		}else if(!vdeposit.test(deposit)){
			$("#mv_deposit").html("开户行只能为汉字");
			return false;
		}else{
			$("#mv_deposit").html("");
		}
				
		if(account==""){
			$("#mv_account").html("银行账号不能为空");
			return false;		
		}else if(!vaccount.test(account)){
			$("#mv_account").html("银行账号输入错误，长度12-20位，且只能是数字");
			return false;		
		}else{
			$("#mv_account").html("");
		}		
		 
		return true;
	}

	function bindBank(){
		$.ajax({
				data: $("#authIdentityForm").serialize(),
		        url: "${app}/account/handerEditBankCard/${bankAccount.id}",
		        type: "POST",
		        dataType: 'json',
		        timeout: 10000,
		        success: function(data) {
		      	   if(data.type=="SUCCESS"){
                       window.location.href="${app}/account/index?type=auth1";
					}else if(data.type=="FAILURE"){
						$("#authIdMessage").removeClass("hidden");	
						$("#authIdResult").html(data.firstMessage);
					}
		        }
		    });
	}
	$("#bankId").val("${bankAccount.bank.id}");
	$("#cityId2").val("${bankAccount.city.parent.id}");
	$("#cityId").val("${bankAccount.city.id}");
</script>
<style>
.zero_border{ height:auto;-moz-border-top-left-radius: 4px; -webkit-border-top-left-radius: 4px;border-top-left-radius: 4px; 
-moz-border-top-right-radius: 4px; -webkit-border-top-right-radius: 4px;border-top-right-radius: 4px; border-top:2px solid #e2e2e2;}
.bkTitle{ position:relative;}
.bkTitle a{ display:block; position:absolute; right:20px; height:35px; line-height:35px; top:10px; padding-left:30px; background:url(images/icon2/account_bank_add.png) no-repeat left center; z-index:5;color: #444444;}
.addbank{ margin:40px;}
.addbank .m_block{ margin:20px 0;}
.addbank .m_block label{ display:inline-block; width:80px; text-align:right; margin-right:15px;}
.addbank .m_block select{width:200px;}
.addbank .m_block input[type=text]{width:190px;}
.addbank .m_block input[type=checkbox]{width: 14px; height: 14px; padding: 0; margin: 0; vertical-align:middle;}
.addbank .m_block input,
.addbank .m_block select {
	border: 1px solid #ccc;
	font-size: 14px;
	color: #777;
	padding: 5px;
	background: #fff;
}
.addbank .databt{ margin-left:50px;}
p.spBank{ margin:10px 0 0 20px;}
p.h_h04{ margin:20px 0 10px 20px;color:#444444; font-size:18px;}
a.a_02_new{ background:url(images/icon1/account_small_02_new.png) no-repeat left center;}
a.a_020_new{ background:url(images/icon1/account_small_020_new.png) no-repeat left center;}
</style>
