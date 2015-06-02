<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">债权人名称</th>
				        <th class="align-center">债权人编号</th>
                        <th class="align-center">债权编号</th>
                        <th class="align-center">借款人</th>
                        <th class="align-center">借款金额</th>
                        <th class="align-center">年利率</th>
                        <th class="align-center">期限</th>
                        <th class="align-center">借款用途</th>
                        <th class="align-center">还款方式</th>
                        <th class="align-center">导入日期</th>
                        <th class="align-center">操作人</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if infoList??>
                   <#if infoList.numberOfElements == 0>
					<tr>
						<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (infoList.content)?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.creditor.creditorName)!''}</td> 
							    <td class="align-center">${(l.creditor.creditorNo)!''}</td> 
		                        <td class="align-center">${(l.crediteCode)!''}</td> 
		                        <td class="align-center">${(l.borrower)!''}</td> 
		                        <td class="align-center">${(l.amount)!''}</td> 
		                        <td class="align-center">${l.rateFormat!0}</td> 
		                        <td class="align-center">${(l.period)!''} 
		                          <#if (l.creditKind)?? && l.creditKind == '01'  >
								              天 
								   <#else>
								        <@messages key="common.unit.month" />
								  </#if>
		                        </td>
		                        <td class="align-center">${(l.purpose)!''}</td>
		                        <td class="align-center">${(l.payType)!''}</td> 
		                        <td class="align-center">${(l.createTime)!''}</td> 
		                        <td class="align-center">${(l.currentUserName!'')}</td> 
								<td class="align-center">
									<a href="#" data-url="${app}/credit/repayPlanDetail/${l.id}" data-target="main">查看详细</a>
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
 <#if infoList??>
<ul class="pagination" data-number="${infoList.number!''}" data-total-pages="${infoList.totalPages!''}"></ul>
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
