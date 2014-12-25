<h3><@messages key="home.loan" /></h3>
<div id="shortcut" class="data row">
	<div class="col-xs-4">
		<div class="flow">
			<a href="#" data-url="${app}/loan/loanaudit" data-target="main"><div class="icon audit pull-left"></div></a>
			<span class="number"><a href="#" data-url="${app}/loan/loanaudit" data-target="main">${audit.count}</a></span><br>
			<span class="status">${audit.homeStatusName}</span>
		</div>
	</div>
	<div class="col-xs-4">
		<div class="flow">
			<a href="#" data-url="${app}/loan/loanfullaudit" data-target="main"><div class="icon out pull-left"></div></a>
			<span class="number"><a href="#" data-url="${app}/loan/loanfullaudit" data-target="main">${out.count}</a></span><br>
			<span class="status">${out.homeStatusName}</span>
		</div>
	</div>
	<div class="col-xs-4">
		<div class="flow">
			<div class="icon collection pull-left"></div>
			<span class="number">${demand.count}</span><br>
			<span class="status">${demand.homeStatusName}</span>
		</div>
	</div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('#shortcut').find('a').link();
});
//-->
</script>