<!-- header -->
<div class="header">
	<div class="top">
		<div class="u-container">
			<#if cuser??>
			<@messages key="index.current.user.top" />
			<a href="${app}/account/index">${cuser.name}</a><#t>
			&nbsp;&nbsp;<@messages key="index.current.user.bull" />&nbsp;&nbsp;<#t>
			<a href="${app}/userIndex/signOut"><@messages key="common.op.sign.out" /></a><#t>
			<#else>
			<a href="${app}/userIndex/skipSignIn"><@messages key="common.op.sign.in" /></a><#t>
			&nbsp;&nbsp;|&nbsp;&nbsp;<#t>
			<a href="${app}/userIndex/regNow"><@messages key="common.op.sign.up" /></a><#t>
			</#if>
		</div>
	</div>
	<div class="nav">
		<div class="u-container">
			<div class="logo"><a href="${app}/index"><img src="<@config key="app.logo.data"/>"><span class="logo2"><@config key="app.operation.nickname"/></span></a></div>
			<ul id="homeNav" class="nav-list">
				<li class="home"><a href="${app}/index"><@messages key="common.home" /></a></li>
				<li class="invest"><a href="${app}/invest/index"><@messages key="nav.main.invest" /></a></li>
				<li class="loan"><a href="${app}/loan/display"><@messages key="nav.main.loan" /></a></li>
				<li class="account"><a href="${app}/account/index"><@messages key="nav.main.user" /></a></li>
				<li class="help"><a href="${app}/help-center"><@messages key="nav.main.help" /></a></li>
			</ul>
		</div>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$.homeNav('${nav!'-'}');
});
//-->
</script>
<!-- /header -->
