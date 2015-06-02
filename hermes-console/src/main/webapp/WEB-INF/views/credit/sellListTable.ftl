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
                        <th class="align-center">债权到期日</th>
                        <th class="align-center">状态</th>
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
		                        <td class="align-center">${(l.deadTime)?string('yyyy-MM-dd')}</td>
		                        <td class="align-center">${(l.statusName)!''}</td> 
								<td class="align-center">
	                                 <a href="#"  data-url="${app}/credit/sellDetail/${l.id}" data-target="main" >查看</a> &nbsp;&nbsp;
	                         	<#if l.creditKind?? && l.creditKind =='01'>
	                                   <span style="color:#CDCDCD">发售</a>
	                            <#else>
	                                   <#if l.status == '00' &&  !l.outOfDate>
	                                   <a href="#" data-url="${app}/credit/goSell/${l.id}"  data-target="main">发售</a>
	                                   <#else>
	                                   <span style="color:#CDCDCD">发售</span>
	                                   </#if>
	                            </#if>
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
