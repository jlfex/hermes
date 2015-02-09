<script type="text/javascript">
$(function(){
	$(".iconleft").css("background-image", "url(${(investPicture.image)!})");
});
$(function(){
	$(".iconright").css("background-image", "url(${(loanPicture.image)!})");
});

</script>
<!-- shortcut -->
<div class="shortcut clearfix">
	<div class="col-xs-5 col-xs-offset-1">
		<div class="item invest clearfix">
			<a href="${app}/invest/index"><i class="iconleft pull-left"></i></a>
			<div class="text pull-left">
				<p><a href="${app}/invest/index"><@messages key="nav.main.invest" /></a></p>
				<p><@messages key="nav.main.invest.hint" /></p>
			</div>
		</div>
	</div>
	<div class="col-xs-5">
		<div class="item loan clearfix">
			<a href="${app}/loan/display"><i class="iconright pull-left"></i></a>
			<div class="text pull-left">
				<p><a href="${app}/loan/display"><@messages key="nav.main.loan" /></a></p>
				<p><@messages key="nav.main.loan.hint" /></p>
			</div>
		</div>
	</div>
</div>
<!-- /shortcut -->
