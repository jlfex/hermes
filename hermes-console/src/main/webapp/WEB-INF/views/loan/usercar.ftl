<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th>车辆品牌</th>
			<th>购入年份 </th>
			<th>购入价格</th>
			<th>拍照</th>
			<th>按揭</th>
		
		</tr>
	</thead>
	<tbody>
		<#if userCars?size == 0>
			<tr>
				<td colspan="9" class="align-center"><@messages key="common.table.empty" /></td>
			</tr>
		<#else>
			<#list userCars as car>
			<tr>
				<td>${car.brand!''}</td>
				<td>${car.purchaseYear!''}</td>
				<td>${car.purchaseAmount!''}</td>
				<td>${car.licencePlate!''}</td>
				<td>${car.mortageName!''}</td>
			</tr>
			</#list>
		</#if>
		
		
	</tbody>
</table>