<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div  class="panel panel-default">
	<div class="panel-heading">工作信息</div>
 	<div class="panel-body">
		<form class="form-horizontal" role="form"  id="jobForm" name="jobForm" >
			<div class="form">
				<input type="hidden" class="form-control " id="id" name="id" value="<#if job?exists>${job.id!''}</#if>"  >
				<input type="hidden" class="form-control " id="status" name="status" value="<#if job?exists>${job.status!''}</#if>"  >
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.type" /></label>
					<div class="col-xs-4 u-col">
					    <select id="type" name="type"  class="form-control" >
					   		 <#list typeMap?keys as key> 
								<option value="${key}" <#if job?exists><#if job.type?exists&&job.type==key> selected</#if></#if>>${typeMap[key]}</option> 
							 </#list>
					    </select>
					    <label for="type" ></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.company.name" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="name" name="name" value="<#if job?exists>${job.name!''}</#if>"  >
				      <label for="name" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.company.properties" /></label>
				    <div class="col-xs-4 u-col">
				    	<select id="properties" name="properties"  class="form-control" >
				    		<#list enterpriseMap?keys as key> 
								<option value="${key}" <#if job?exists><#if job.properties?exists&&job.properties==key> selected</#if></#if>>${enterpriseMap[key]}</option> 
							</#list>
				    	</select>
				    	<label for="properties" ></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.company.scale" /></label>
				    <div class="col-xs-4 u-col">
					  <select id="scale" name="scale"  class="form-control" >
					  		<#list scaleMap?keys as key> 
								 <option value="${key}" <#if job?exists><#if job.scale?exists&&job.scale==key> selected</#if></#if>>${scaleMap[key]}</option> 
							</#list>
					  </select>
					  <label for="scale" ></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.company.address" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="address" name="address" value="<#if job?exists>${job.address!''}</#if>"   >
				      <label for="address" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.company.phone" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="phone" name="phone" value="<#if job?exists>${job.phone!''}</#if>"   >
				      <label for="phone" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.position" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="position" name="position"  value="<#if job?exists>${job.position!''}</#if>"  >
				      <label for="position" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.job.annualSalary" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="annualSalary" name="annualSalary" value="<#if job?exists><#if job.annualSalary?exists>${job.annualSalary?string('#0.00')}</#if></#if>">
				      <label for="annualSalary" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label"><@messages key="model.job.company.registeredCapital" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="registeredCapital" name="registeredCapital"  value="<#if job?exists>${job.registeredCapital!''}</#if>"  >
				       <label for="registeredCapital" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label"><@messages key="model.job.company.license"  /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="license" name="license"  value="<#if job?exists>${job.license!''}</#if>" >
				    </div>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-4 col-sm-10">
				      <button type="submit" class="btn btn-primary" id="saveJob" ><@messages key="common.op.save"/></button>
				       <button type="button" class="btn btn-primary" id="cancelJob"><@messages key="common.op.cancel"/></button>
				    </div>
				  </div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$("#cancelJob").on('click', function(){
			$.ajax({
		             url: '${app}/account/getUserJob',
		           	 success:function(data) {
		                $("#jobInfo").html(data); // 内容装入div中
		             }
	         	})
	})
});
function saveJob(){
	$.ajax('${app}/account/saveJob', {
			data: $('#jobForm').serialize(),
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data) {
				 $("#jobInfo").html(data); // 内容装入div中
			}
		});
}
//-->
</script>