<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th>公司性质</th>
			<th>年收入 </th>
			<th>职位</th>
			<th>公司名称</th>
			<th>公司规模</th>
			<th>公司电话</th>
			<th>注册资金</th>
			<th>营业执照</th>
			<th>公司地址</th>
		</tr>
	</thead>
	<tbody>
		<#if userJobs?size == 0>
			<tr>
				<td colspan="9" class="align-center"><@messages key="common.table.empty" /></td>
			</tr>
		<#else>
			<#list userJobs as job>
			<tr>
				<td>${job.properties!''}</td>
				<td>${job.annualSalary!''}</td>
				<td>${job.position!''}</td>
				<td>${job.name!''}</td>
				<td>${job.scaleName!''}</td>
				<td>${job.phone!''}</td>
				<td>${job.registeredCapital!''}</td>
				<td>${job.license!''}</td>
				<td>${job.address!''}</td>
			</tr>
			</#list>
		</#if>
		
		
	</tbody>
</table>