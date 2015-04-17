<meta charset="utf-8">
<div class="panel panel-default">
	<div class="panel-body">
		<form id="dataTopForm" method="post" action="#" class="form-horizontal">
		<div class="form-group">
			<label class="col-xs-1 control-label"><@messages key="model.product.name" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${product.name}</p>
			</div>
			<label class="col-xs-1 control-label"><@messages key="model.basic.realName" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${userProperties.realName!''}</p>
			</div>
			<label class="col-xs-1 control-label"><@messages key="model.loan.loanNo" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loan.loanNo!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-1 control-label"><@messages key="model.loan.amount" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loan.amount!''}<@messages key="common.unit.cny" /></p>
			</div>
			<label class="col-xs-1 control-label"><@messages key="model.loan.rate" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${loan.rate *100}%</p>
			</div>
			<label class="col-xs-1 control-label"><@messages key="model.loan.period" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">
				  <#if (loan.loanKind)?? && loan.loanKind == '00'>
				       ${loan.period!''}<@messages key="common.unit.month" />
				    <#else>
				       ${loan.period}天
				  </#if>
				</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-1 control-label"><@messages key="model.repay.name" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${repay.name!''}</p>
			</div>
			<label class="col-xs-1 control-label"><@messages key="model.loan.purpose" /></label>
			<div class="col-xs-2">
				<p class="form-control-static">${purpose!''}</p>
			</div>
		</div>
	</form>
	</div>
</div>

<div class="panel panel-primary">
	<div class="panel-heading">审核记录</div>
	<div class="panel-body">
		<table class="table table-striped table-hover">
			<thead>
				<tr>
						<th>状态</th>
						<th>结果</th>
						<th>理由</th>
				</tr>
			</thead>
			<#if loanaudits?size == 0>
				<tr>
					<td colspan="3" class="align-center"><@messages key="common.table.empty" /></td>
				</tr>
			<#else>
				<#list loanaudits as l>
					<tr>
						<td>${l.statusName!''}</td>
						<td>${l.typeName!''}</td>
						<td>${l.remark}</td>
					</tr>
				</#list>
			</#if>
		</table>
	</div>
</div>
<div class="panel panel-primary">
	<div class="panel-heading">投标记录</div>
	<div class="panel-body">
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>投标人</th>
					<th><@messages key="model.invest.amount" />(元)</th>
					<th>投标时间</th>
				</tr>
			</thead>
			<#if invests?size == 0>
				<tr>
					<td colspan="3" class="align-center"><@messages key="common.table.empty" /></td>
				</tr>
			<#else>
				<#assign repayPlan = 1 />
				<#list invests as i>  
					<tr>
						<td>${repayPlan}</td>
						<td class="th_00"><#if (i.user.account)??>${i.user.account}</#if></td>
						<td>${i.amount?string('#,##0.00')}</td>
						<td>${i.datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
					</tr>
					 <#assign repayPlan = repayPlan + 1 /> 
				</#list>
			</#if>
		</table>
	</div>
</div>
<div class="panel panel-primary">
	<div class="panel-heading">还款记录</div>
	<div class="panel-body">
			<table class="table table-hover">
			<thead>
				<tr>
					<th>第几期</th>
					<th>预计还款日</th>
					<th>应还本息</th>
					<th>管理费</th>
					<th>逾期管理费</th>
					<th>罚息</th>
					<th>逾期天数</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<#if loanRepays.numberOfElements == 0>
					<tr>
						<td colspan="8" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
				<#else>
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
				        </tr>
				    </#list>
                </#if>
			</tbody>
	</div>
</div>
