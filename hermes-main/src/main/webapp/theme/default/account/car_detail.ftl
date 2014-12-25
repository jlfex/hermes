<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div  class="panel panel-default">
	<div class="panel-heading"><@messages key="model.car.info"/></div>
 	<div class="panel-body">
		<form class="form-horizontal" role="form"  id="carForm">
			<div class="form">
				<input type="hidden" class="form-control " id="id" name="id" value="<#if car?exists><#if car.id?exists>${car.id}</#if></#if>"  >
				<input type="hidden" class="form-control " id="status" name="status" value="<#if car?exists><#if car.status?exists>${car.status}</#if></#if>"  >
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.car.brand" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="brand" name="brand" value="<#if car?exists><#if car.brand?exists>${car.brand}</#if></#if>"  >
				       <label for="brand" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.car.purchaseYear" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="purchaseYear" name="purchaseYear" value="<#if car?exists><#if car.purchaseYear?exists>${car.purchaseYear}</#if></#if>"   >
				       <label for="purchaseYear" generated="true" class="error valid"></label>
				   	</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.car.purchaseAmount" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="purchaseAmount" name="purchaseAmount" value="<#if car?exists><#if car.purchaseAmount?exists>${car.purchaseAmount?string('#0.00')}</#if></#if>"   >
				       <label for="purchaseAmount" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.car.licencePlate" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="licencePlate" name="licencePlate" value="<#if car?exists><#if car.licencePlate?exists>${car.licencePlate}</#if></#if>"   >
				       <label for="licencePlate" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">*<@messages key="model.car.mortgage" /></label>
				     <div class="col-xs-4 u-col">
					  <select id="mortgage" name="mortgage"  class="form-control" >
					  		<#list mortgageMap?keys as key> 
								 <option value="${key}" <#if car?exists><#if car.mortgage?exists&&car.mortgage==key> selected</#if></#if>>${mortgageMap[key]}</option> 
							</#list>
					  </select>
					  <label for="mortgage" ></label>
				    </div>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-4 col-sm-10">
				      <button type="submit" class="btn btn-primary" id="saveCar" ><@messages key="common.op.save"/></button>
				       <button type="button" class="btn btn-primary" id="cancelCar"><@messages key="common.op.cancel"/></button>
				    </div>
				  </div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	
	$("#cancelCar").on('click', function(){
			$.ajax({
		             url: '${app}/account/getUserCar',
		           	 success:function(data) {
		              $("#carInfo").html(data) // 内容装入div中
		             }
	         	})
	})
});
function saveCar(){
		$.ajax({
				data: $("#carForm").serialize(),
		        url: "${app}/account/saveCar",
		        type: "POST",
		        dataType: 'html',
		        timeout: 10000,
		        success: function(data) {
		      	   $("#carInfo").html(data) // 内容装入div中
		        }
		    });
}
//-->
</script>