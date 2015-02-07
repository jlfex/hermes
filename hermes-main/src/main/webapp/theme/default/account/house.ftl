<div>
	<div class="row">
		<#list houses as house>
			<div class="col-sm-6 col-md-6" id="${house.id}">
				<div class="panel panel-info ">
					<div class="panel-heading clearfix">
						<h6 class="pull-left"><@messages key="model.house.info"/></h6>
						<div class="pull-right" >
						<a href="#" onclick="modifyHouse('${house.id}')"><i class="fa fa-pencil-square-o" style="font-size:2em;"></i></a>
						<a href="#" onclick="reMoveHouse('${house.id}',this)"><i class="fa fa-times-circle" style="font-size:2em;"></i></a>
						</div>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" role="form"  id="houseForm">
							<div class="form-group">
								 <label  class="col-sm-4 control-label"><@messages key="model.house.address" /></label>
									 <label class="form-control-static">
										<#if house.addressDetail?exists>${house.addressDetail}</#if>  
				   				 </label>
				   			</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.house.certificate" /></label>
							    <label class="form-control-static"><#if house?exists><#if house.certificate?exists>${house.certificate}</#if></#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.house.area" /></label>
								<label class="form-control-static"><#if house?exists><#if house.area?exists>${house.area}</#if></#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.house.year" /></label>
								<label class="form-control-static"><#if house?exists><#if house.year?exists>${house.year}</#if></#if></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label"><@messages key="model.house.mortgage" /></label>
							     <label class="form-control-static">
								  	<#list mortgageMap?keys as key> 
										<#if house?exists><#if house.mortgage?exists&&house.mortgage==key> ${mortgageMap[key]} </#if></#if>
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
					<span><a href="#" onclick="addHouse()"><i class="fa fa-plus-circle" style="font-size:2em;"></i></a></span>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
});
function addHouse(){
	var houseId=null;
	$.ajax({
		url: '${app}/account/loadHouseDetail/'+houseId,
		success:function(data) {
		   $("#houseInfo").html(data); // 内容装入div中
		}
	})
}
function modifyHouse(houseId){
	 $.ajax({
		url: '${app}/account/loadHouseDetail/'+houseId,
		success:function(data) {
			 $("#houseInfo").html(data); // 内容装入div中
		}
	})
}

function reMoveHouse(houseId,obj){
   $.ajax({
		url: '${app}/account/delHouseDetail/'+houseId,
		success:function(data) {
			 if(data == '00'){
			     $(obj).parent().parent().parent().parent().hide(); 
			 }
		}
	});
}

//-->
</script>