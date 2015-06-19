<meta charset="utf-8">
<div>
	<!--Nav Tabs-->
	<ul  id="navTab" class="nav nav-tabs">
	<#if backRoleResourceList?seq_contains("back_inveter_mgr")>
		<li><a href="#finance" data-toggle="tab"><@messages key="user.finance.manage"/></a></li>
	</#if>
	<#if backRoleResourceList?seq_contains("back_loaner_mgr")>
		<li><a href="#loan" data-toggle="tab"><@messages key="user.loan.manage"/></a></li>
	</#if>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content" id="tabContent"></div>
</div>
<div id="data"></div>


<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
 	$('#navTab a:first').tab('show') // Select first tab
 	$("#navTab").find('li').click(function(){
 		var index = $(this).index();
 		if(index==1){
 			init("${app}/user/loadLoanUser","tabContent");
 		}else{
 			init("${app}/user/loadFinanceUser","tabContent");
 		}
 	});
 	init("${app}/user/loadFinanceUser","tabContent");
});
function init(_url,_content){
		$.ajax({
		     url: _url,
		     success:function(data) {
		       $("#"+_content).html(data) ;
			 }
	   })
}
//-->
</script>