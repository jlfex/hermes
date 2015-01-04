<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<div class="tab-content">
<form class="form-horizontal"role="form" id="userBasic" name="userBasic">
  <div class="form-group">
    <label for="account" class="col-sm-2 control-label">*<@messages key="model.basic.account"/></label>
    <input type="hidden" class="form-control" id="authEmail" name="authEmail" value="<#if userBasic?exists>${userBasic.authEmail!''}</#if>"  >
    <input type="hidden" class="form-control" id="authName" name="authName" value="<#if userBasic?exists>${userBasic.authName!''}</#if>"  >
    <input type="hidden" class="form-control" id="authCellphone" name="authCellphone" value="<#if userBasic?exists>${userBasic.authCellphone!''}</#if>"  >
    <label for="account" class="form-control-static">${userBasic.account!''}</label>
  	 <div class="col-xs-4 u-col eidt-group edit-input" >
		<input type="text" class="form-control" id="account" name="account" value="<#if userBasic?exists>${userBasic.account!''}</#if>" disabled >
		<label for="account" generated="true" class="error valid"></label>
	</div>
  </div>
  <div class="form-group">
    <label for="realName" class="col-sm-2 control-label">*<@messages key="model.basic.realName"/></label>
    <label for="realName" class="form-control-static">${userBasic.realName!''}</label>
 	 <div  class="col-xs-4 u-col eidt-group edit-input">
		<input type="text" class="form-control " id="realName" name="realName" value="<#if userBasic?exists>${userBasic.realName!''}</#if>"  data-auth="${userBasic.authName!''}">
		<label for="realName" generated="true" class="error valid"></label>
	</div>
  </div>
  <div class="form-group">
    <label for="idType" class="col-sm-2 control-label">*<@messages key="model.basic.idType"/></label>
    <label for="idType" class="form-control-static">
   		 <#list idTypeMap?keys as key> 
				<#if userBasic.idType?exists&&userBasic.idType==key>
						${idTypeMap[key]}
				</#if>
		</#list>
    </label>
    <div class="col-xs-4 u-col eidt-group edit-input">
		<select id="idType" name="idType"  class="form-control" data-auth="${userBasic.authName!''}" >
			<#list idTypeMap?keys as key> 
				<option value="${key}" <#if userBasic?exists><#if userBasic.idType?exists&&userBasic.idType==key> selected</#if></#if>>${idTypeMap[key]}</option> 
			</#list>
		</select>
		<label for="idType" ></label>
	</div>
  </div>
  <div class="form-group">
    <label for="idNumber" class="col-sm-2 control-label">*<@messages key="model.basic.idNumber"/></label>
    <label for="idNumber"class="form-control-static">${userBasic.idNumber!''}</label>
  	 <div class="col-xs-4 u-col eidt-group edit-input">
		<input type="text" class="form-control " id="idNumber" name="idNumber" value="<#if userBasic?exists><#if userBasic.idNumber?exists>${userBasic.idNumber}</#if></#if>" data-auth="${userBasic.authName!''}" >
		<label for="idNumber" generated="true" class="error valid"></label>
	</div>
  </div>
  <div class="form-group">
     <label for="cellphone" class="col-sm-2 control-label">*<@messages key="model.basic.cellphone"/></label>
     <label for="cellphone"class="form-control-static">${userBasic.cellphone!''}</label>
	<div  class="col-xs-4 u-col eidt-group edit-input">
		<input type="text" class="form-control " id="cellphone" name="cellphone" value="<#if userBasic?exists>${userBasic.cellphone!''}</#if>" data-auth="${userBasic.authCellphone!''}" >
		<label for="cellphone" generated="true" class="error valid"></label>
	</div>
  </div>
  <div class="form-group">
    <label for="gender" class="col-sm-2 control-label">*<@messages key="model.basic.gender"/></label>
    <label for="gender" class="form-control-static">
     	<#list genterMap?keys as key> 
				<#if userBasic.gender?exists&&userBasic.gender==key>
						${genterMap[key]}
				</#if>
		</#list>
    </label>
    <div  class="col-xs-4 u-col eidt-group edit-input">
		<select id="gender" name="gender"  class="form-control" >
			<#list genterMap?keys as key> 
				<option value="${key}" <#if userBasic?exists><#if userBasic.gender?exists&&userBasic.gender==key> selected</#if></#if>>${genterMap[key]}</option> 
			</#list>
		</select>
		<label for="gender" ></label>
	</div>
  </div>
  <div class="form-group">
    <label for="age" class="col-sm-2 control-label">*<@messages key="model.basic.age"/></label>
    <label for="age" class="form-control-static">${userBasic.age!''}</label>
	<div  class="col-xs-4 u-col eidt-group edit-input">
		<input type="text" class="form-control " id="age" name="age" value="<#if userBasic?exists>${userBasic.age!''}</#if>"  >
		<label for="age" generated="true" class="error valid"></label>
	</div>
  </div>
  <div class="form-group">
    <label for="married" class="col-sm-2 control-label">*<@messages key="model.basic.married"/></label>
    <label for="married" class="form-control-static">
    	 <#list marriedMap?keys as key> 
				<#if userBasic.married?exists&&userBasic.married==key>
						${marriedMap[key]}
				</#if>
		</#list>
    </label>
    <div  class="col-xs-4 u-col eidt-group edit-input">
		<select id="married" name="married"  class="form-control" >
			<#list marriedMap?keys as key> 
				<option value="${key}" <#if userBasic?exists><#if userBasic.married?exists&&userBasic.married==key> selected</#if></#if>>${marriedMap[key]}</option> 
			</#list>
		</select>
		<label for="married" ></label>
	</div>
  </div>
  <div class="form-group">
    <label for="address" class="col-sm-2 control-label">*<@messages key="model.basic.address"/></label>
    <label for="address" class="form-control-static">
    	
		<#if addressPerson?exists>${addressPerson}</#if>   
    </label>
    	<div  class="col-xs-2 u-col eidt-group edit-input">
			<select id="province" name="province"  class="form-control" data-val="${userBasic.province!''}">
			</select>
		</div>
		<div  class="col-xs-2 u-col eidt-group edit-input">
			<select id="city" name="city"  class="form-control" data-val="${userBasic.city!''}">
			</select>
		</div>
		<div  class="col-xs-2 u-col eidt-group edit-input">
			<select id="county" name="county"  class="form-control" data-val="${userBasic.county!''}" >
			</select>
		</div>
		
  </div>
  <div class="form-group">
   	<label for="account" class="col-sm-2 control-label"></label>
	  <div  class="col-xs-4 u-col eidt-group edit-input">
				<input type="text" class="form-control " id="address" name="address" value="<#if userBasic?exists>${userBasic.address!''}</#if>"  >
				<label for="address" generated="true" class="error valid"></label>
			</div>
	  </div>
  <div class="form-group">
    <label for="degree" class="col-sm-2 control-label"><@messages key="model.basic.degree"/></label>
    <label for="degree" class="form-control-static">
    	 <#list eduMap?keys as key> 
				<#if userBasic.degree?exists&&userBasic.degree==key>
						${eduMap[key]}
				</#if>
		</#list>
    </label>
    <div  class="col-xs-4 u-col eidt-group edit-input">
		<select id="degree" name="degree"  class="form-control" >
			<#list eduMap?keys as key> 
				<option value="${key}" <#if userBasic?exists><#if userBasic.degree?exists&&userBasic.degree==key> selected</#if></#if>>${eduMap[key]}</option> 
			</#list>
		</select>
		<label for="degree" ></label>
	</div>
  </div>
  <div class="form-group">
    <label for="school" class="col-sm-2 control-label"><@messages key="model.basic.school"/></label>
    <label for="school"class="form-control-static">${userBasic.school!''}</label>
 	 <div  class="col-xs-4 u-col eidt-group edit-input">
		<input type="text" class="form-control " id="school" name="school" value="<#if userBasic?exists>${userBasic.school!''}</#if>"  >
		<label for="school" ></label>
	</div>
  </div>
  <div class="form-group">
    <label for="year" class="col-sm-2 control-label"><@messages key="model.basic.year"/></label>
    <label for="year" class="form-control-static">${userBasic.year!''}</label>
 	<div  class="col-xs-4 u-col eidt-group edit-input">
 		<select id="year" name="year"  class="form-control"  data-auth="${userBasic.year!''}">
		</select>
		<label for="year" ></label>
	</div>
  </div>
  <div class="form-group">
	 	<div class="col-sm-offset-2 col-sm-10">
			<button type="button" class="btn btn-primary" id="modifyBasic" ><@messages key="common.op.edit"/></button>
			<button type="submit" class="btn btn-primary eidt-group edit-input" id="saveBasic" ><@messages key="common.op.save"/></button>
			<button type="button" class="btn btn-primary eidt-group edit-input" id="cancel" ><@messages key="common.op.cancel"/></button>
		</div>
	</div>
</form>
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$("#userBasic").find('input').each(function() {
		if ($(this).data().auth == '10') {
		  $(this).attr("disabled",true);
		}
	});
	$("#userBasic").find('select').each(function() {
		if ($(this).data().auth == '10') {
		  $(this).attr("disabled",true);
		}
	});
	$.area({ data: ${area}, bind: [$('#province'), $('#city'), $('#county')] });
	$("#modifyBasic").on('click', function(){
		$("#navTab").find('li').each(function() {
			if($(this).attr("class")=="active"){
				$(".form-control-static").addClass("form-label");
				$(".eidt-group").removeClass("edit-input");
				$("#modifyBasic").addClass("edit-input");
			}
		});
	});
	$("#cancel").on('click',function(){
		$(".eidt-group").addClass("edit-input");
		$(".form-control-static").removeClass("form-label");
		$("#modifyBasic").removeClass("edit-input");
	});
	initSelect();
});
function saveBasic(){
	$.ajax({
					data: $("#userBasic").serialize(),
			        url: "${app}/account/saveBasic",
			        type: "POST",
			        dataType: 'json',
			        cache:false,
			        timeout: 10000,
			        success: function(data) {
			            if(data.type=="FAILURE"){
								alert(data.firstMessage);
						}else{
						  $.ajax({
					             url: '${app}/account/getUserBasic',
					           	 success:function(data) {
					                $("#basicInfo").html(data) // 内容装入div中
					             }
		         			})
						}
			        }
		    	});
}
function initSelect(){
	var nowDate = new Date();
	var year =nowDate.getFullYear();
	for(var i=0;i<50;i++){
		var value=year-i;
		if($("#year").data().auth==value){
			$("#year").append("<option value='"+value+"' selected>"+value+"</option>");
		}else{
			$("#year").append("<option value='"+value+"'>"+value+"</option>");
		}
	}
}

//-->
</script>