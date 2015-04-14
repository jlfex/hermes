<script type="text/javascript">
<!--
jQuery(function($) {

	$('#loan .btn-primary').click(function() {
		$.ajax({
			url: '${app}/loan/repayment/'+$(this).data().id,
			dataType: 'json',
			type: "POST",
			success: function(data){
						if(data.type=="SUCCESS"){
							window.location.href="${app}/loan/repaymentsuccess";
						}else{
							window.location.href="${app}/loan/repaymentfailure";
						}
					}
		});
 });
});
//-->
</script>
<!-- loan info -->
<div class="block">
	<div class="body-xs">
		<div class="row u-row">
			<div class="col-xs-12 u-col">
				<div class="body">
					<p class="account">
						
						<div class="row hm-row">
							<div class="col-xs-4 hm-col form-group">
								<@messages key="model.loan.purpose" />：&nbsp;${purpose}&nbsp;<#t>
							</div>
							<div class="col-xs-4 hm-col form-group">
								<@messages key="model.loan.loanNo" />：&nbsp;${loan.loanNo}&nbsp;<#t>
							</div>
							<div class="col-xs-4 hm-col form-group">
								<@messages key="model.loan.repay" />：&nbsp;${repay.name}&nbsp;<#t>
							</div>
							
						</div>
						
					</p>
					<p class="account">
						<div class="row hm-row">
						    <div class="col-xs-4 hm-col form-group">
								<@messages key="model.loan.amount" />：&nbsp;<span class="currency">${loan.amount}</span>元&nbsp;<#t>
							</div>
							<div class="col-xs-4 hm-col form-group">
								<@messages key="model.loan.rate" />：&nbsp;${loan.rate*100}%&nbsp;<#t>
							</div>
							<div class="col-xs-4 hm-col form-group">
								<@messages key="model.loan.period" />：&nbsp;${loan.period}个月&nbsp;<#t>
							</div>
							
						</div>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="block" id="loan">
	<div class="body-sm">
		<h4>还款记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>期数</th>
					<th>预计还款日</th>
					<th>应还本息(<@messages key="common.unit.cny" />)</th>
					<th>管理费(<@messages key="common.unit.cny" />)</th>
					<th>逾期管理费(<@messages key="common.unit.cny" />)</th>
					<th>罚息(<@messages key="common.unit.cny" />)</th>
					<th>逾期天数</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			   <#list loanRepays.content as l>  
	                <tr>
	                    <td class="center">${l.sequence}</td>
	                    <td>${l.planDatetime?string('yyyy-MM-dd')}</td>
	                    <td  class="right">${l.amount}</td>
	                    <td  class="right">${l.otherAmount}</td>
	                    <td  class="right">${l.overduePenalty}</td>
	                    <td  class="right">${l.overdueInterest}</td>
	                    <td  class="right">${l.overdueDays}</td>
	                    <td>${l.statusName}</td>
	                    <td>
	                     <#if l.status=='00' || l.status=='20'>
	                     	<button type="button" class="btn btn-primary" data-id="${l.id}">还款</button>
	                    </#if>	                    
	                    </td>
	                </tr>
                </#list>
			</tbody>
		</table>
	</div>
</div>

