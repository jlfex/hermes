<div class="result result-success clearfix">
	<div class="col-xs-offset-2 col-xs-8">
		<div class="icon pull-left"></div>
		<div class="text pull-left">
			<p><strong><@messages key="common.result.success" /></strong></p>
			<p><@messages key="account.fund.charge.payment" /></p>
			<a id="toPay" href="${app}/account/payment/${payment.id}" target="_blank" class="sr-only">topay</a>
		</div>
	</div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	document.getElementById('toPay').click();
});
//-->
</script>
