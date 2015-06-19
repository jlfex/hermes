<style type="text/css">
  ._df-mg-top{ margin-top:15px;}
</style>
 <div class="panel panel-primary" >
        <div class="panel-heading"><@messages key="home.loan" /></div>
<div id="shortcut" class="data row">
	<#if backRoleResourceList?seq_contains("back_home_audit")>
		<div class="col-xs-4">
		<div class="flow">
			<a href="#" data-url="${app}/loan/loanaudit/${auditFirst.status}" data-target="main"><div class="icon audit pull-left"></div></a>
			<span class="number"><a href="#" data-url="${app}/loan/loanaudit/${auditFirst.status}" data-target="main">${auditFirst.count}</a></span><br>
			<span class="status">${auditFirst.homeStatusName}</span>
		</div>
	    </div>		
	</#if>
	<#if backRoleResourceList?seq_contains("back_home_audit")>
		<div class="col-xs-4 _df-mg-top">
		<div class="flow">
			<a href="#" data-url="${app}/loan/loanaudit/${auditFinal.status}" data-target="main"><div class="icon audit pull-left"></div></a>
			<span class="number"><a href="#" data-url="${app}/loan/loanaudit/${auditFinal.status}" data-target="main">${auditFinal.count}</a></span><br>
			<span class="status">${auditFinal.homeStatusName}</span>
		</div>
	    </div>		
	</#if>
	<#if backRoleResourceList?seq_contains("back_home_full")>
		<div class="col-xs-4">
		<div class="flow">
			<a href="#" data-url="${app}/loan/loanfullaudit" data-target="main"><div class="icon out pull-left"></div></a>
			<span class="number"><a href="#" data-url="${app}/loan/loanfullaudit" data-target="main">${out.count}</a></span><br>
			<span class="status">${out.homeStatusName}</span>
		</div>
	</div>		
	</#if>
	<#if backRoleResourceList?seq_contains("back_home_debt")>
		<div class="col-xs-4">
		<div class="flow">
			<div class="icon collection pull-left"></div>
			<span class="number">${demand.count}</span><br>
			<span class="status">${demand.homeStatusName}</span>
		</div>
	</div>		
	</#if>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('#shortcut').find('a').link();
});
//-->
</script>
