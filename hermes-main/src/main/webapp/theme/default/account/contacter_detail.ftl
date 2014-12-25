<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div  class="panel panel-default">
	<div class="panel-heading"><@messages key="model.contacter.info" /> </div>
 	<div class="panel-body">
		<form class="form-horizontal" role="form"  id="contacterForm">
			<div class="form">
				<input type="hidden" class="form-control " id="id" name="id" value="<#if contacter?exists><#if contacter.id?exists>${contacter.id}</#if></#if>"  >
				<input type="hidden" class="form-control " id="status" name="status" value="<#if contacter?exists><#if contacter.status?exists>${contacter.status}</#if></#if>"  >
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.contacter.name" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="name" name="name" value="<#if contacter?exists><#if contacter.name?exists>${contacter.name}</#if></#if>"  >
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.contacter.relationship" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="relationship" name="relationship" value="<#if contacter?exists><#if contacter.relationship?exists>${contacter.relationship}</#if></#if>"   >
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.contacter.phone" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="phone" name="phone"  value="<#if contacter?exists><#if contacter.phone?exists>${contacter.phone}</#if></#if>"  >
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.contacter.address" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="address" name="address" value="<#if contacter?exists><#if contacter.address?exists>${contacter.address}</#if></#if>"   >
				    </div>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <button type="submit" class="btn btn-primary" id="saveContacter" ><@messages key="common.op.save"/></button>
				       <button type="button" class="btn btn-primary" id="cancelContacter"><@messages key="common.op.cancel"/></button>
				    </div>
				  </div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$("#cancelContacter").on('click', function(){
			$.ajax({
		             url: '${app}/account/getUserContacter',
		           	 success:function(data) {
		               $("#contacterInfo").html(data); // 内容装入div中
		             }
	         	})
	});
});
function saveContacter(){
	$.ajax({
				data: $("#contacterForm").serialize(),
		        url: "${app}/account/saveContacter",
		        type: "POST",
		        dataType: 'html',
		        timeout: 10000,
		        success: function(data) {
		        	   $("#contacterInfo").html(data); // 内容装入div中
		        }
		    });
}
//-->
</script>