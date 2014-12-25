<div class="row">
		<#list jobs as job>
			<div class="col-sm-8 col-md-6">
				<div class="panel panel-info ">
					<div class="panel-heading clearfix">
						<h6 class="pull-left"><@messages key="model.job.info"/></h6>
						<div class="pull-right" ><a href="#" onclick="modifyJob('${job.id}')"><i class="fa fa-pencil-square-o" style="font-size:2em;"></i></a></div>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" role="form"  id="jobForm${job_index}">
							<div class="form-group">
									<label  class="col-sm-6 control-label"><@messages key="model.job.type" /></label>
								     <label class="form-control-static">
									  	<#list typeMap?keys as key> 
											<#if job.type?exists&&job.type==key> ${typeMap[key]} </#if>
										</#list>
								    </label>
								</div>
							<div class="form-group">
								 <label  class="col-sm-6 control-label"><@messages key="model.job.company.name" /></label>
								 <label class="form-control-static"><#if job.name?exists>${job.name}</#if></label>
				   			</div>
							<div class="form-group">
								 <label  class="col-sm-6 control-label"><@messages key="model.job.company.properties" /></label>
								 <label class="form-control-static">
								 <#list enterpriseMap?keys as key> 
									 <#if job.properties?exists&&job.properties==key> ${enterpriseMap[key]}</#if>
								 </#list>
				   			</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.job.company.scale" /></label>
							     <label class="form-control-static">
								  	<#list scaleMap?keys as key> 
										<#if job.scale?exists&&job.scale==key> ${scaleMap[key]} </#if>
									</#list>
							    </label>
							</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.job.company.address" /></label>
							    	<label class="form-control-static"><#if job.address?exists>${job.address}</#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.job.company.phone" /></label>
							    	<label class="form-control-static"><#if job.phone?exists>${job.phone}</#if></label>
							</div>
							<div id="jobBlock" >
								<div class="form-group">
									 <label  class="col-sm-6 control-label"><@messages key="model.job.position" /></label>
									 <label class="form-control-static"><#if job.position?exists>${job.position}</#if></label>
					   			</div>
					   			<div class="form-group">
									 <label  class="col-sm-6 control-label"><@messages key="model.job.annualSalary" /></label>
									 <label class="form-control-static"><#if job.annualSalary?exists>${job.annualSalary}</#if></label>
					   			</div>
					   			<div class="form-group">
									 <label  class="col-sm-6 control-label"><@messages key="model.job.company.registeredCapital" /></label>
									 <label class="form-control-static"><#if job.registeredCapital?exists>${job.registeredCapital}</#if></label>
					   			</div>
					   			<div class="form-group">
									 <label  class="col-sm-6 control-label"><@messages key="model.job.company.license" /></label>
									 <label class="form-control-static"><#if job.license?exists>${job.license}</#if></label>
					   			</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</#list>
		<div class="col-sm-6 col-md-6">
			<div class="thumbnail">
				<div class="caption add-block">
					<span><a href="#" onclick="addJob()"><i class="fa fa-plus-circle" style="font-size:2em;"></i></a></span>
				</div>
			</div>
		</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
});
function addJob(){
	var jobid=null;
	$.ajax({
		url: '${app}/account/loadJobDetail/'+jobid,
		success:function(data) {
		   $("#jobInfo").html(data); // 内容装入div中
		}
	})
}
function modifyJob(jobid){
	 $.ajax({
		url: '${app}/account/loadJobDetail/'+jobid,
		success:function(data) {
			 $("#jobInfo").html(data); // 内容装入div中
		}
	})
}
//-->
</script>