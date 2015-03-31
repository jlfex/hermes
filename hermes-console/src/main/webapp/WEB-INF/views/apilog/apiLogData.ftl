<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">平台编码</th>
				        <th class="align-center">请求流水号</th>
                        <th class="align-center">接口名称</th>
                        <th class="align-center">请求时间</th>
                        <th class="align-center">处理标识</th>
                        <th class="align-center">创建时间</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if apiLogList??>
                   <#if apiLogList.numberOfElements == 0>
					<tr>
						<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (apiLogList.content)?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.apiConfig.platCode)!''}</td> 
							    <td class="align-center">${(l.serialNo)!''}</td> 
		                        <td class="align-center">${(l.interfaceName)!''}</td> 
		                        <td class="align-center">${(l.requestTime)!''}</td> 
		                        <td class="align-center">${(l.dealResultName)!''}</td> 
		                        <td class="align-center">${(l.createTime)!''}</td> 
								<td class="align-center">
									<a href="#" data-url="${app}/apiLog/apiLogDetail/${l.id}" data-target="main">查看详细</a>
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
 <#if apiLogList??>
<ul class="pagination" data-number="${apiLogList.number!''}" data-total-pages="${apiLogList.totalPages!''}"></ul>
 </#if>
<script type="text/javascript">
<!--
jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});


//-->
</script>
