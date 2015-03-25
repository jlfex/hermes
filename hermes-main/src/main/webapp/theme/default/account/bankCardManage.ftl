<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>

<div class="account_content_right">
    <div class="f_money">
        <p class="h_h04">银行卡管理</p>
        <div class="m_tda">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr style="background-color:#F5F5F5;">
                    <th style="border:none;">银行名称</th>
                    <th style="border:none;">银行卡号</th>
                    <th style="border:none;">开户行</th>
                    <th style="border:none;">省份</th>
                    <th style="border:none;">城市</th>
                    <th style="border:none;">状态</th>
                    <th style="border:none;">操作</th>
                </tr>
                <tr>
                    <td style="border:none;">${bankAccount.bank.name}</td>
                    <td style="border:none;">${'${bankAccount.account}'?substring(0,4)}******${'${bankAccount.account}'?substring('${bankAccount.account}'?length-4)}</td>
                    <td style="border:none;">${bankAccount.deposit}</td>
                    <td style="border:none;">${area.name}</td>
                    <td style="border:none;">${bankAccount.city.name}</td>
                    <td style="border:none;">${(bankAccount.status)!}</td>
                    <td style="border:none;">
                    	<button type="button" class="btn btn-link editBtn" id="editBtn">更换</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" style="border:none;"></td>
                    <td style="border:none;">
                    	<button type="button" class="btn btn-link addBtn" id="addBtn">新增</button>
                    </td>
                </tr>                                                    
            </table>
        </div>
    </div>       
</div>
<div class="clearfix"></div>
<script type="text/javascript">
	jQuery(function($) {
		//新增银行卡
		$(".addBtn").on("click",function(){
			window.location.href="${app}/account/index?type=addBankCard";			
		});
		//更改银行卡
		$(".editBtn").on("click",function(){
			window.location.href="${app}/account/index?type=editBankCard";						
		});						
	});
</script> 