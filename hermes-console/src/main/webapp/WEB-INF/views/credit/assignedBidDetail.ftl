<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@messages key="app.title" /></title>
<link rel="shortcut icon" type="image/png" href="${app.img}/favicon.png">
<link rel="stylesheet" href="${app.css}/style.css">
<link rel="stylesheet" href="${app.css}/jquery-ui.css">
</head>
<body>
<div id="main" style="margin-left:50px;margin-right:50px;margin-top:5px;">
<div class="panel panel-primary">
        <div class="panel-heading">投标明细</div>     </div>
<table id="table" class="table table-striped table-hover">
	<thead>
		              <tr>
                        <th class="align-center">序号</th>
                        <th class="align-center">投标人</th>
                        <th class="align-center">投标金额</th>
                        <th class="align-center">投标时间</th>
                    </tr>
	</thead>
	      <#if bidLogList??>
             <#list bidLogList as l>
				 <tr>
				   <td class="align-center">${l_index +1}</td>
	               <td class="align-center">${(l.user)!''}</td>  
	               <td class="align-center">${l.amount!''}</td> 
	               <td class="align-center">${l.datetime!''}</td> 
	             </tr>
			</#list>
		<#else>
		 <tr>
			<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
		 </tr>
		</#if>
	<tbody>
</table>

</div>

<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap3-validation.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery-ui.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/hermes.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/hermes-page.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/sco.modal.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/sco.confirm.js"></script>


<body>
</html>
