<meta charset="utf-8">
<div  class="panel panel-tab">
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<input id="type" name="type" value="${type}"type="hidden"/>
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="account"><@messages key="user.nickname" /></label>
					<input id="account" name="account" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="cellphone"><@messages key="user.cellphone" /></label>
					<input id="cellphone" name="cellphone" type="text" value="" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="realname"><@messages key="user.realname" /></label>
					<input id="realname" name="realname" type="text" value="" class="form-control">
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.search" /></button>
					<input id="page" name="page" type="hidden" value="0">
				</div>
			</div>
		</form>
	</div>
</div>


<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 鍒濆鍖�
	$('#tabContent .col-xs-1 .fa').on('click', function() {
		if ($(this).hasClass('fa-plus-circle')) {
			$(this).removeClass('fa-plus-circle').addClass('fa-minus-circle');
			$('#moreCodition').removeClass("hidden");
		} else {
			$(this).removeClass('fa-minus-circle').addClass('fa-plus-circle');
			$('#moreCodition').addClass("hidden");
		}
	});
	$.page.withdraw({
		search: '${app}/user/table'
	});
});
//-->
</script>
