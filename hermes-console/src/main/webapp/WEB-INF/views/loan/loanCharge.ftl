
 <div class="panel panel-primary">
        <div class="panel-heading">债权发售列表</div>
 </div>
	<div class="body">
		<form id="chargeForm" method="post" action="#" class="form-horizontal">
		    <div class="form-group">
				<label for="channel" class="col-xs-2 u-col control-label">充值渠道</label>
				<div class="col-xs-10 u-col">
					<p id="selectChannel" class="form-control-static">线下充值 </p>
					<input id="accountId" name="accountId" type="hidden" value="${account.id!''}">
				</div>
			</div>
			<div class="form-group">
				<label for="channel" class="col-xs-2 u-col control-label">账户余额</label>
				<div class="col-xs-3 u-col">
			    ${account.balance?string("#,##0.00")} <@messages key="common.unit.cny" />
				</div>
			</div>
			<div class="form-group">
				<label for="amount" class="col-xs-2 u-col control-label">充值金额</label>
				<div class="col-xs-3 u-col">
					<input id="amount" name="amount" type="number" placeholder="请输入金额" class="form-control">
				</div>
				<div class="col-xs-1 u-col">
					<p class="form-control-static"><@messages key="common.unit.cny" /></p>
				</div>
				<div class="col-xs-1 u-col">
					<p class="form-control-static" id="amountError"></p>
				</div>
			</div>
		
			<div class="form-group">
				<div class="col-xs-2 col-xs-offset-2 u-col">
					<button id="addChargeBtn" type="button"  class="btn btn-default btn-block">充值</button>
				</div>
			</div>
		</form>
	</div>
</div>


<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$('#amount').on('blur', function() {
		var _elem = $(this);
		var el = /^[1-9]*[1-9][0-9]*$/;
		if(!el.test(_elem.val())) {
		  $("#amountError").html('充值金额必须是正整数');
		   return;
		}else{
		   $("#amountError").html('格式正确');
		}
	});
	
	$("#addChargeBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/loan/charge',
			data: $("#chargeForm").serialize(),
			target: 'main'
		});
	});
	
});
//-->
</script>

