<meta charset="utf-8">
<div class="panel panel-primary">
		<div class="panel-heading">申请信息</div>
		<div class="panel-body">
		<form id="dataTopForm" method="post" action="#" class="form-horizontal">
			<div class="form-group">
				<label class="col-xs-1 control-label"><@messages key="model.product.name" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${product.name}</p>
				</div>
				<label class="col-xs-1 control-label"><@messages key="model.basic.account" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${user.account!''}</p>
				</div>
				<label class="col-xs-1 control-label"><@messages key="model.loan.amount" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${realLoanAmount!''}<@messages key="common.unit.cny" /></p>
				</div>
				<label class="col-xs-1 control-label"><@messages key="model.loan.purpose" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${purpose!''}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-1  control-label"><@messages key="model.loan.rate" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${loan.rateFormat!''}</p>
				</div>
				<label class="col-xs-1 control-label"><@messages key="model.loan.period" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${loan.period!''}<@messages key="common.unit.month" /></p>
				</div>
				<label class="col-xs-1 control-label"><@messages key="model.repay.name" /></label>
				<div class="col-xs-2">
					<p class="form-control-static">${repay.name!''}</p>
				</div>
			</div>
		</form>
	</div>
</div>
