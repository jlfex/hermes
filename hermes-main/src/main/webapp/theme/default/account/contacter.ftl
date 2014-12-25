<div class="row">
		<#list contacters as contacter>
			<div class="col-sm-6 col-md-6">
				<div class="panel panel-info ">
					<div class="panel-heading clearfix">
						<h6 class="pull-left"><@messages key="model.contacter.info"/></h6>
						<div class="pull-right" ><a href="#" onclick="modifyContacter('${contacter.id}')"><i class="fa fa-pencil-square-o" style="font-size:2em;"></i></a></div>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" role="form"  id="contacterForm${contacter_index}">
							<div class="form-group">
								 <label  class="col-sm-4 control-label"><@messages key="model.contacter.name" /></label>
								 <label class="form-control-static">
							  		<#if contacter.name?exists>${contacter.name}</#if>
				   				 </label>
				   			</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.contacter.relationship" /></label>
							    <label class="form-control-static"><#if contacter.relationship?exists>${contacter.relationship}</#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.contacter.phone" /></label>
								<label class="form-control-static"><#if contacter.phone?exists>${contacter.phone}</#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.contacter.address" /></label>
								<label class="form-control-static"><#if contacter.address?exists>${contacter.address}</#if></label>
							</div>
						</form>
					</div>
				</div>
			</div>
		</#list>
		<div class="col-sm-6 col-md-6">
			<div class="thumbnail">
				<div class="caption add-block">
					<span><a href="#" onclick="addContacter()"><i class="fa fa-plus-circle" style="font-size:2em;"></i></a></span>
				</div>
			</div>
		</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
});
function addContacter(){
	var contacterId=null;
	$.ajax({
		url: '${app}/account/loadContacterDetail/'+contacterId,
		success:function(data) {
		   $("#contacterInfo").html(data); // 内容装入div中
		}
	})
}
function modifyContacter(contacterId){
	 $.ajax({
		url: '${app}/account/loadContacterDetail/'+contacterId,
		success:function(data) {
			 $("#contacterInfo").html(data); // 内容装入div中
		}
	})
}
//-->
</script>
	