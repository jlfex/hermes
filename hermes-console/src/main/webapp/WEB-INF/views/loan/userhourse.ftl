<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th>产证面积</th>
			<th>产证编号 </th>
			<th>建成年份</th>
			<th>房产地址</th>
			<th>按揭</th>
		
		</tr>
	</thead>
	<tbody>
		<#if userHouses?size == 0>
			<tr>
				<td colspan="9" class="align-center"><@messages key="common.table.empty" /></td>
			</tr>
		<#else>
			<#list userHouses as hourse>
			<tr>
				<td>${hourse.area!''}</td>
				<td>${hourse.certificate!''}</td>
				<td>${hourse.year!''}</td>
				<td>${hourse.address!''}</td>
				<td>${hourse.mortageName!''}</td>
			</tr>
			</#list>
		</#if>
		
		
	</tbody>
</table>