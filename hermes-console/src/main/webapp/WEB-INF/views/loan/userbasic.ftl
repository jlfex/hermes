<meta charset="utf-8">
<div class="tab-content">
	<form id="dataForm" method="post" action="#" class="form-horizontal">
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.account"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.account!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.realName"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.realName!''}</p>
			</div>
		</div>
	
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.idType"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.idTypeName!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.idNumber"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.realName!''}
				<#if loanUserBasic.realName?exists>
		    		<#if loanUserBasic.authName?exists>
		    			<#if loanUserBasic.authName=='10'><img src="${app.img}/auth.png"/>
		    			<#else>
		    				<img src="${app.img}/unauth.png"/>
		    			</#if>
		    		</#if>
	    		</#if>
	    		</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="user.cellphone"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.cellphone!''}
				<#if loanUserBasic.cellphone?exists>
		    		<#if loanUserBasic.authCellphone?exists>
		    			<#if loanUserBasic.authCellphone=='10'><img src="${app.img}/auth.png"/>
		    			<#else>
		    				<img src="${app.img}/unauth.png"/>
		    			</#if>
		    		</#if>
	    		</#if>
	    		</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="user.basic.email"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.email!''}
				<#if loanUserBasic.email?exists>
		    		<#if loanUserBasic.authEmail?exists>
		    			<#if loanUserBasic.authEmail=='10'><img src="${app.img}/auth.png"/>
		    			<#else>
		    				<img src="${app.img}/unauth.png"/>
		    			</#if>
		    		</#if>
	    		</#if>
	    		</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.address"/></label>
			<div class="col-xs-10">
				<p class="form-control-static">${loanUserBasic.address!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.age"/></label>
			<div class="col-xs-1">
				<p class="form-control-static">${loanUserBasic.age!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.married"/></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loanUserBasic.marriedName!''}</p>
			</div>
			<label class="col-xs-2 control-label"><@messages key="model.basic.gender"/></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loanUserBasic.genderName!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-2 control-label"><@messages key="model.basic.degree"/></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loanUserBasic.degreeName!''}</p>
			</div>
			<label class="col-xs-2 control-label"><@messages key="model.basic.year"/></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loanUserBasic.year!''}</p>
			</div>
			<label class="col-xs-2 control-label"><@messages key="model.basic.school"/></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loanUserBasic.school!''}</p>
			</div>
		</div>
	</form>
</div>
