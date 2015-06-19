<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">用户名称</th>
		                <th class="align-center">角色名称</th>
				        <th class="align-center">备注</th>
				        <th class="align-center">创建时间</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if userList??>
                   <#if userList.numberOfElements == 0>
					<tr>
						<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (userList.content)?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.account)!''}</td> 
							    <td class="align-center">
							      <#if l.roles?exists>
									<#list l.roles as r> ${r.name!''} </#list>
								  <#else>
									  暂无角色
								  </#if>
							    </td> 
		                        <td class="align-center">${(l.remark)!''}</td> 	
		                        <td class="align-center">${(l.createTime)!''}</td> 		                         
								<td class="align-center">
								    <#if backRoleResourceList?seq_contains("back_privi_user_impower")>
									<button type="button" class="btn btn-link addRole"  pid="${l.id}">角色分配</button>&nbsp;&nbsp;
									</#if>
									<#if backRoleResourceList?seq_contains("back_privi_user_edit")>
									<button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>&nbsp;&nbsp;
									</#if>
									<#if backRoleResourceList?seq_contains("back_privi_user_del")>
                                    <button type="button" class="btn btn-link hm-col deleteBtn"  pid="${l.id}">删除</button>		
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
 <#if userList??>
   <ul class="pagination" data-number="${userList.number!''}" data-total-pages="${userList.totalPages!''}"></ul>
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
			url: '${app}/privilege/goModifyUser',
			data: 'id='+pid,
			target: 'main'
		});
	});
	$(".addRole").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/privilege/goRoleImpower',
			data: 'id='+pid,
			target: 'main'
		});
	});
	$(".deleteBtn").on("click",function(){ 
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/privilege/delUser',
			data: 'id='+pid,
			target: 'main'
		});
     });				
});
</script>
