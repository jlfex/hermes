<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				        <th class="align-left">债权来源</th>
                        <th class="align-center">第三方编号</th>
                        <th class="align-center">债权编号</th>
                        <th class="align-center">借款人</th>
                        <th class="align-right">证件号码</th>
                        <th class="align-right">金额</th>
                        <th class="align-center">年利率</th>
                        <th class="align-center">期限</th>
                        <th class="align-center">导入日期</th>
                        <th class="align-center">借款类型</th>
                        <th class="align-center">借款用途</th>
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
							    <td class="align-left">${(l.creditor.source)!''}</td>
								<td class="align-center">${(l.creditor.creditorNo)!''}</td>
								<td class="align-center">${l.crediteCode!''}</td>
								<td class="align-center">${l.borrower!''}</td>
								<td class="align-right">${l.certificateNo!''}</td>
								<td class="align-right">${l.amount!''}</td>
								<td class="align-center">${l.rate!''}</td>
								<td class="align-center">${l.period!''}</td>
								<td class="align-center">${l.createTime!''}</td>
								<td class="align-center">${l.crediteType!''}</td>
								<td class="align-center">${l.purpose!''}</td>
								<td class="align-center">
									<a href="#" id="creditDetailView" data-url="${app}/credit/repayPlanDetail/${l.id}" >查看</a>
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

<ul class="pagination" data-number="${infoList.number}" data-total-pages="${infoList.totalPages}"></ul>

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

$("table a").on("click",function(){
		$.link.html(null, {
			url: $(this).attr("data-url"),
			target: 'main'
		});
});
//-->
</script>
