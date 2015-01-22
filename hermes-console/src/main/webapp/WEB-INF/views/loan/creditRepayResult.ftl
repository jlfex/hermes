<meta charset="utf-8">
<div class="panel panel-default">
	<div class="panel-body">
		<#if code == '00'>
		    还款成功
		<#else>
		   还款失败<br/>
		   ${ msg!''}
		</#if>
	</div>
</div>
