<div class="panel panel-primary"><div class="panel-heading">角色列表</div></div>
<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">角色名称</th>
				        <th class="align-center">状态</th>
				        <th class="align-center">类型</th>
				        <th class="align-center">创建者</th>
				        <th class="align-center">创建时间</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if roles??>
                   <#if roles.numberOfElements == 0>
					<tr>
						<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (roles.content)?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.name)!''}</td> 
							    <td class="align-center">${(l.statusName)!''}</td> 
		                        <td class="align-center">${(l.typeName)!''}</td> 
		                        <td class="align-center">${(l.creatorName)!''}</td> 		
		                        <td class="align-center">${(l.createTime)!''}</td> 		                         
								<td class="align-center">
									<button type="button" class="btn btn-link impower"  pid="${l.id}">授权</button>
								</td>
							</tr>
						</#list>
					</#if>
					<#else>
					    <tr>
						<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					</#if>
       </tbody>
</table>
 <#if roles??>
   <ul class="pagination" data-number="${roles.number!''}" data-total-pages="${roles.totalPages!''}"></ul>
 </#if>
 <div class="panel panel-primary"><div class="panel-heading">当前角色</div></div>
<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-center">角色名称</th>
				        <th class="align-center">状态</th>
				        <th class="align-center">类型</th>
				        <th class="align-center">创建者</th>
				        <th class="align-center">创建时间</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if ownedRoles??>
					  <#list ownedRoles?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-center">${(l.name)!''}</td> 
							    <td class="align-center">${(l.statusName)!''}</td> 
		                        <td class="align-center">${(l.typeName)!''}</td> 
		                        <td class="align-center">${(l.creatorName)!''}</td> 		
		                        <td class="align-center">${(l.createTime)!''}</td> 		                         
								<td class="align-center">
									<button type="button" class="btn btn-link removeBtn"  pid="${l.belongUserRoleId!''}">删除</button>
								</td>
							</tr>
						</#list>
					<#else>
					    <tr>
						<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
					    </tr>
					</#if>
       </tbody>
</table>
<script type="text/javascript">

jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
	//授权
	$(".impower").on("click",function(){
		var pid = $(this).attr("pid");
		var userId='${userId!''}';
		$.link.html(null, {
			url: '${app}/privilege/impowerUser',
			data: 'id='+pid+'&userId='+userId,
			target: 'main'
		});
	});
	
     //删除当前角色
     $(".removeBtn").on("click",function(){
		var pid = $(this).attr("pid");
		var userId='${userId!''}';
		$.link.html(null, {
			url: '${app}/privilege/delRole',
			data: 'id='+pid+'&userId='+userId,
			target: 'main'
		});
	});
     			
});
</script>
