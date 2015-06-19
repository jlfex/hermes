<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">平台名称</th>
		                <th class="align-center">平台编码</th>
				        <th class="align-center">本地证书</th>
                        <th class="align-center">本地证书保护密码</th>
                        <th class="align-center">服务端证书</th>
                        <th class="align-center">服务端证书密码</th>
                        <th class="align-center">服务端URL</th>
                        <th class="align-center">接口状态</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if apiConfigList??>
                   <#if apiConfigList.numberOfElements == 0>
					<tr>
						<td colspan="8" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (apiConfigList.content)?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.platName)!''}</td> 
							    <td class="align-center">${(l.platCode)!''}</td> 
							    <td class="align-center">${(l.clientStoreName)!''}</td> 
		                        <td class="align-center">${(l.clientStorePwd)!''}</td> 
		                        <td class="align-center">${(l.truestStoreName)!''}</td> 
		                        <td class="align-center">${(l.truststorePwd)!''}</td> 
		                        <td class="align-center">${(l.apiUrl)!''}</td>
		                        <td class="align-center">${(l.statusName)!''}</td> 		                         
								<td class="align-center">
								    <#if backRoleResourceList?seq_contains("back_api_edit")>
									<button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>&nbsp;&nbsp;&nbsp;
									</#if>
									<#if backRoleResourceList?seq_contains("back_api_del")>
                                    <button type="button" class="btn btn-link hm-col deleteBtn"  cid="${l.id}">删除</button>		
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
 <#if apiConfigList??>
<ul class="pagination" data-number="${apiConfigList.number!''}" data-total-pages="${apiConfigList.totalPages!''}"></ul>
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
	
	$(".editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/apiConfig/updateApiConfig?id='+pid,
			target: 'main'
		});
	});
	$(".deleteBtn").on("click",function(){
		var cid = $(this).attr("cid");
		$.link.html(null, {
			url: '${app}/apiConfig/delApiConfig?id='+cid,
			target: 'main'
		});
     });				
});
</script>
