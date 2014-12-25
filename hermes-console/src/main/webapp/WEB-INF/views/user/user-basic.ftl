<div id="userBasic">
	<div class="row show-grid">
	  <div class="col-md-4">
		<label class="col-xs-6"><@messages key="model.basic.account"/></label>	
		<label class="col-xs-6"><span><#if userBasic?exists>${userBasic.account!''}</#if></span></label>    
	  </div>
	  <div class="col-md-4">
	    <label class="col-xs-6"><@messages key="model.basic.realName"/></label>	
	    <label class="col-xs-6"><span>${userBasic.realName!''}</span></label>  
	  </div>
	    <div class="col-md-4">
	    <label class="col-xs-6"><@messages key="user.basic.email"/></label>	
	    <label class="col-xs-6">
	    	<span>
	    		${userBasic.email!''}
	    		<#if userBasic.email?exists>
					<img  data-auth="${userBasic.authEmail}" src=""/>
	   			</#if>
	    	</span>
	    </label>  
	  </div>
	</div>
	<div class="row show-grid">
	  <div class="col-md-4">
		<label class="col-xs-6"><@messages key="model.basic.age"/></label>	
		<label class="col-xs-6"><span>${userBasic.age!''}</span></label>    
	  </div>
	  <div class="col-md-4">
	    <label class="col-xs-6"><@messages key="user.cellphone"/></label>	
	    <label class="col-xs-6">
	    	<span>
	   			${userBasic.cellphone!''}
	   			<#if userBasic.cellphone?exists>
					<img  data-auth="${userBasic.authCellphone}" src=""/></a>
	   			</#if>
	    	</span>
	    </label>  
	  </div>
	    <div class="col-md-4">
	    <label class="col-xs-6"><@messages key="model.basic.status"/></label>	
	    <label class="col-xs-6"><span>
	   	   <#list statusMap?keys as key> 
				<#if userBasic.status?exists&&userBasic.status==key>
						${statusMap[key]}
				</#if>
			</#list>
	  </div>
	</div>
	<div class="row show-grid">
	  <div class="col-md-4">
		<label class="col-xs-6"><@messages key="model.basic.idType"/></label>	
		<label class="col-xs-6"><span>
			<#list idTypeMap?keys as key> 
				<#if userBasic.idType?exists&&userBasic.idType==key>
						${idTypeMap[key]}
				</#if>
			</#list>
		</span></label>    
	  </div>
	  <div class="col-md-4">
	    <label class="col-xs-6"><@messages key="model.basic.idNumber"/></label>	
	    <label class="col-xs-6">
	    	<span>${userBasic.idNumber!''}</span>
	    		<#if userBasic.idNumber?exists>
					<img  data-auth="${userBasic.authName}" src=""/>
	   			</#if>
	    </label>  
	  </div>
	    <div class="col-md-4">
	    <label class="col-xs-6"><@messages key="model.basic.gender"/></label>	
	    <label class="col-xs-6"><span>
	    	<#list genterMap?keys as key> 
				<#if userBasic.gender?exists&&userBasic.gender==key>
						${genterMap[key]}
				</#if>
			</#list>
	    </span></label>  
	  </div>
	</div>
	<#if type=="loan">
		<div class="row show-grid">
		  <div class="col-md-4">
			<label class="col-xs-6"><@messages key="model.basic.married"/></label>	
			<label class="col-xs-6"><span>
				<#list marriedMap?keys as key> 
					<#if userBasic.married?exists&&userBasic.married==key>
							${marriedMap[key]}
					</#if>
				</#list>
			</span></label>    
		  </div>
		   <div class="col-md-4">
			<label class="col-xs-6"><@messages key="model.basic.address"/></label>	
			<label class="col-xs-6"><span>${addressPerson!''}   </span></label>    
		  </div>
		</div>
		<div class="row show-grid">
		  <div class="col-md-4">
			<label class="col-xs-6"><@messages key="model.basic.degree"/></label>	
			<label class="col-xs-6"><span>
				<#list eduMap?keys as key> 
					<#if userBasic.degree?exists&&userBasic.degree==key>
							${eduMap[key]}
					</#if>
				</#list>
			</span></label>    
		  </div>
		   <div class="col-md-4">
			<label class="col-xs-6"><@messages key="model.basic.school"/></label>	
			<label class="col-xs-6"><span>${userBasic.school!''}</span></label>    
		  </div>
		   <div class="col-md-4">
			<label class="col-xs-6"><@messages key="model.basic.year"/></label>	
			<label class="col-xs-6"><span>${userBasic.year!''}</span></label>    
		  </div>
		</div>
	</#if>
	
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
$("#userBasic").find('img').each(function() {
	if ($(this).data().auth == 10) 
		{
			$(this).attr('src','${app.img}/auth.png');
			$(this).popover({
				html: true,
				placement: 'right',
				trigger: 'hover', 
				content: '<span style="white-space:nowrap;">已认证</span>'
			});
		}else{
			$(this).attr('src','${app.img}/unauth.png');
			$(this).popover({
				html: true,
				placement: 'right',
				trigger: 'hover', 
				content: '<span style="white-space:nowrap;">未认证</span>'
			});
		}
	});
});

//-->
</script>