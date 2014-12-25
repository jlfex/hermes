<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div  class="panel panel-default">
	<div class="panel-heading"><@messages key="model.house.info"/></div>
 	<div class="panel-body">
		<form class="form-horizontal" role="form"  id="houseForm">
			<div class="form">
				<input type="hidden" class="form-control " id="id" name="id" value="<#if house?exists><#if house.id?exists>${house.id}</#if></#if>"  >
				<input type="hidden" class="form-control " id="status" name="status" value="<#if house?exists><#if house.status?exists>${house.status}</#if></#if>"  >
				<div class="form-group">
					 <label  class="col-sm-2 control-label">*<@messages key="model.house.address" /></label>
						<div  class="col-xs-2 u-col">
							<select id="province_h" name="province"  class="form-control" data-val="<#if house.province?exists>${house.province}</#if>">
							</select>
						</div>
						<div  class="col-xs-2 u-col">
							<select id="city_h" name="city"  class="form-control" data-val="<#if house.city?exists>${house.city}</#if>">
							</select>
						</div>
						<div  class="col-xs-2 u-col">
							<select id="county_h" name="county"  class="form-control" data-val="<#if house.county?exists>${house.county}</#if>" >
							</select>
						</div>
				</div>
				<div class="form-group">
					 <label  class="col-sm-2 control-label"></label>
					<div  class="col-xs-4 u-col">
						<input type="text" class="form-control " id="address" name="address" value="<#if house?exists><#if house.address?exists>${house.address}</#if></#if>"  >
						<label for="address" ></label>
					</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.house.certificate" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="certificate" name="certificate" value="<#if house?exists><#if house.certificate?exists>${house.certificate}</#if></#if>"   >
				      <label for="certificate" generated="true" class="error valid"></label>
				   	</div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.house.area" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="area" name="area" value="<#if house?exists><#if house.area?exists>${house.area}</#if></#if>"   >
				      <label for="area" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.house.year" /></label>
				    <div class="col-xs-4 u-col">
				      <input type="text" class="form-control " id="year" name="year" value="<#if house?exists><#if house.year?exists>${house.year}</#if></#if>"   >
				      <label for="year" generated="true" class="error valid"></label>
				    </div>
				</div>
				<div class="form-group">
					<label  class="col-sm-2 control-label">*<@messages key="model.house.mortgage" /></label>
				     <div class="col-xs-4 u-col">
					  <select id="mortgage" name="mortgage"  class="form-control" >
					  		<#list mortgageMap?keys as key> 
								 <option value="${key}" <#if house?exists><#if house.mortgage?exists&&house.mortgage==key> selected</#if></#if>>${mortgageMap[key]}</option> 
							</#list>
					  </select>
					  <label for="mortgage"></label>
				    </div>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <button type="submit" class="btn btn-primary" id="saveHouse" ><@messages key="common.op.save"/></button>
				       <button type="button" class="btn btn-primary" id="cancelHouse"><@messages key="common.op.cancel"/></button>
				    </div>
				  </div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$.area({ data: ${area}, bind: [$('#province_h'), $('#city_h'), $('#county_h')] });
	$("#cancelHouse").on('click',function(){
		$.ajax({
		      url: '${app}/account/getUserHouse',
		      success:function(data) {
		           $("#houseInfo").html(data); // 内容装入div中
		      }
	    })
	});
});
function saveHouse(){
	$.ajax({
				data: $("#houseForm").serialize(),
		        url: "${app}/account/saveHouse",
		        type: "POST",
		        dataType: 'html',
		        timeout: 10000,
		        success: function(data) {
		        	 $("#houseInfo").html(data) // 内容装入div中
		        }
		    })
}
//-->
</script>