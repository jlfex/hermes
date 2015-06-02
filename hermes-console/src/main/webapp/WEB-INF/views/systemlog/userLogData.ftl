<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">账户</th>
				        <th class="align-center">邮件</th>
                        <th class="align-center">类型</th>
                        <th class="align-center">备注</th>
                        <th class="align-center">创建时间</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if userLogList??>
                   <#if userLogList.numberOfElements == 0>
					<tr>
						<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (userLogList.content)?sort_by("datetime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.user.account)!''}</td> 
							    <td class="align-center">${(l.user.email)!''}</td> 
		                        <td class="align-center">${(l.typeName)!''}</td> 
		                        <td class="align-center">${(l.remark)!''}</td> 
		                        <td class="align-center">${(l.datetime)!''}</td> 
								<td class="align-center">
									<a href="#" data-url="${app}/userLog/${l.id}" data-target="main">查看详细</a>
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
 <#if userLogList??>
	<ul class="pagination" data-number="${userLogList.number!''}" data-total-pages="${userLogList.totalPages!''}"></ul>
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
