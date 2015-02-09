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
        <div class="panel-heading">回款明细</div>  </div>
 <table id="creditRepayPlanDetail" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th class="align-center">期数</th>
                                <th class="align-center">计划还款日期</th>
                                <th class="align-center">应还本金</th>
                                <th class="align-center">应还利息</th>
                                <th class="align-center">应还总额</th>
                                <th class="align-center">剩余本金</th>
                                <th class="align-center">状态</th>
                                <th class="align-center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                           <#if repayPlanDetailList??>
                             <#list repayPlanDetailList as l>
                                <tr> 
                                <td class="align-center">${l.period!''}</td>
                                <td class="align-center">${l.repayPlanTime?string('yyyy-MM-dd')}</td> 
                                <td class="align-center">${l.repayPrincipal!''}</td> 
                                <td class="align-center">${l.repayInterest!''}</td> 
                                <td class="align-center">${l.remainPrincipal!''}</td>
                                <td class="align-center">${l.repayAllmount!''}</td>
                                <td class="align-center">${l.statusName!''}</td> 
                                <td class="align-center">
                                <#if l.status?? && l.status == '00' >
                                  <button type="button" class="btn btn-primary" data-id="${l.id}">还款</button>
                                <#elseif l.status?? && l.status == '01'>
                                  <button type="button" disabled ="true"  class="btn btn-Default" data-id="${l.id}">还款</button>
                                <#else></#if>
                                </td>  
                            </tr>
                           </#list>
                           <#else>
                                <tr>
									<td colspan="9" class="align-center"><@messages key="common.table.empty" /></td>
								</tr>
                           </#if>
                        </tbody>
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
  <script type="text/javascript" charset="utf-8">
   $('#creditRepayPlanDetail .btn-primary').click(function() {
   	    $.link.html(null, {
			url: '${app}/loan/repayment/'+$(this).data().id,
			target: 'main'
		});
     });
     	     
 </script>        
                    