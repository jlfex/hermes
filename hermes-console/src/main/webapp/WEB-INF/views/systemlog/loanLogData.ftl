<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">标编号</th>
				        <th class="align-center">用户账户</th>
                        <th class="align-center">类型</th>
                        <th class="align-center">创建时间</th>
                        <th class="align-center">金额</th>
                        <th class="align-center">备注</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if loanLogList??>
                   <#if loanLogList.numberOfElements == 0>
					<tr>
						<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (loanLogList.content)?sort_by("datetime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.loan.loanNo)!''}</td> 
							    <td class="align-center">${(l.userName)!''}</td> 
		                        <td class="align-center">${(l.typeName)!''}</td> 
		                       	<td class="align-center">${(l.datetime)!''}</td>
		                       	<td class="align-center">${(l.amount)!''}</td>
		                        <td class="align-center">${(l.remark)!''}</td> 
								<td class="align-center">
									<a href="#" data-url="${app}/loanLog/${l.id}" data-target="main">查看详细</a>
								</td>
							</tr>
						</#list>
					</#if>
					<#else>
					    <tr>
						<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					</#if>
       </tbody>
</table>
 <#if loanLogList??>
	<ul class="pagination" data-number="${loanLogList.number!''}" data-total-pages="${loanLogList.totalPages!''}"></ul>
 </#if>
<script type="text/javascript">

jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
</script>
