<div class="row">
		<#list cars as car>
			<div class="col-sm-8 col-md-6">
				<div class="panel panel-info ">
					<div class="panel-heading clearfix">
						<h6 class="pull-left"><@messages key="model.car.info"/></h6>
						<div class="pull-right" ><a href="#" onclick="modifyCar('${car.id}')"><i class="fa fa-pencil-square-o" style="font-size:2em;"></i></a></div>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" role="form"  id="carForm">
							<div class="form-group">
								 <label  class="col-sm-6 control-label"><@messages key="model.car.brand" /></label>
								 <label class="form-control-static">
							  		<#if car.brand?exists>${car.brand}</#if>
				   				 </label>
				   			</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.car.purchaseYear" /></label>
							    <label class="form-control-static"><#if car.purchaseYear?exists>${car.purchaseYear}</#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.car.purchaseAmount" /></label>
								<label class="form-control-static"><#if car.purchaseAmount?exists>${car.purchaseAmount}</#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.car.licencePlate" /></label>
								<label class="form-control-static"><#if car.licencePlate?exists>${car.licencePlate}</#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-6 control-label"><@messages key="model.car.mortgage" /></label>
							     <label class="form-control-static">
								  	<#list mortgageMap?keys as key> 
										<#if car.mortgage?exists&&car.mortgage==key> ${mortgageMap[key]} </#if>
									</#list>
							    </label>
							</div>
						</form>
					</div>
				</div>
			</div>
		</#list>
		<div class="col-sm-6 col-md-6">
			<div class="thumbnail">
				<div class="caption add-block">
					<span><a href="#" onclick="addCar()"><i class="fa fa-plus-circle" style="font-size:2em;"></i></a></span>
				</div>
			</div>
		</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
});
function addCar(){
	var carId=null;
	$.ajax({
		url: '${app}/account/loanCarDetail/'+carId,
		success:function(data) {
		   $("#carInfo").html(data); // 内容装入div中
		}
	})
}
function modifyCar(carId){
	 $.ajax({
		url: '${app}/account/loanCarDetail/'+carId,
		success:function(data) {
			 $("#carInfo").html(data); // 内容装入div中
		}
	})
}
//-->
</script>