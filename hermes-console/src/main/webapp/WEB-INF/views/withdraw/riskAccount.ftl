<div class="panel panel-primary">
	<div class="panel-heading">风险金账户</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<button id="chargeBtn" type="button" class="btn btn-primary btn-block" style="height: 30px;width: 70px;" onclick="chargeAccount();" >充 值</button>
				</div>
			    <div class="col-xs-2 hm-col form-group">
					<label for="name">风险金账户余额：<span>${riskAmount}元</span></label>
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="name">总收入：<span>${riskIn} 元</span></label>
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="name">总支出：<span>${riskOut} 元</span></label>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="data"></div>

<script type="text/javascript">
jQuery(function($) {
 	init("${app}/withdraw/riskAccountData","data");
});
function init(_url,_content){
		$.ajax({
		     url: _url,
		     success:function(data) {
		       $("#"+_content).html(data)
			 }
	   })
}

</script>