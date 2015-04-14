<!-- invest info -->
<div class="block">
	<div class="body-xs">
		<div class="row u-row">
			<div class="col-xs-12 u-col">
				<div class="body">
					<p class="account">
						<div class="row hm-row">
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.purpose" />：&nbsp;${purpose}&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.product.name" />：&nbsp;${product.name}&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.amount" />：&nbsp;<span class="currency">${loan.amount}</span>元&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.basic.account" />：&nbsp;${user.account}&nbsp;<#t>
							</div>
						</div>
					</p>
					<p class="account">
						<div class="row hm-row">
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.rate" />：&nbsp;${loan.rate*100}%&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.repay" />：&nbsp;${repay.name}&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.period" />：&nbsp;
								${loan.period}<#if loan.loanKind=='00'>个月 <#else>天</#if>&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
							       编号：&nbsp;${loan.loanNo}&nbsp;<#t>
							</div>
						</div>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="block">
	<div class="body-sm">
		<h4>回款记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>当期/总期</th>
					<th>应收日期</th>
					<th>待收本金(<@messages key="common.unit.cny" />)</th>
					<th>待收利息(<@messages key="common.unit.cny" />)</th>
					<th>待收总额(<@messages key="common.unit.cny" />)</th>
					<th>罚息(<@messages key="common.unit.cny" />)</th>
					<th>逾期天数</th>
					<th>回款状态</th>
				</tr>
			</thead>
			<tbody>
			  <#list investprofitinfos.content as i>  
	                <tr>
	                    <#if i.loanKind == '03'>
	                       <td class="th_00">${i.sequence}/1</td>
	                    <#else>
	                       <td class="th_00">${i.sequence}/${i.period}</td>
	                    </#if>
                        <td>${i.planDatetime?string('yyyy-MM-dd')}</td>
                    	 <td class="right">${i.principal}</td>
                    	 <td class="right">${i.interest}</td>
                    	 <td class="right">${i.amount}</td>
                    	 <td class="right">${i.overdueInterest}</td>
                    	 <td class="right">${i.overdueDays}</td>
                    	 <td>${i.statusName}</td>
	                </tr>
                </#list>
			</tbody>
		</table>
	</div>
</div>