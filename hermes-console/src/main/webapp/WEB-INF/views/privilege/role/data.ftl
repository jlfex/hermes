<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th class="align-center">代码</th>
				<th class="align-center">名称</th>
				<th class="align-center">创建者</th>
				<th class="align-center">创建时间</th>
				<th class="align-center">操作</th>
		</tr>
	</thead>
		<#if lists.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list lists.content?reverse as l>      
			<tr>
			    <td class="align-center">${l.code!''}</td>
				<td class="align-center">${l.name!''}</td>
				<td class="align-center">${l.creatorName!''}</td>
				<td class="align-center">${l.createTime!''}</td>
				<td class="align-center">
					<#if backRoleResourceList?seq_contains("back_role_user_setprivi")>
						<button type="button" class="btn btn-link setPrivilege"  pid="${l.id}">设置权限</button>
					</#if>
					<#if backRoleResourceList?seq_contains("back_role_user_edit")>
						<button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>
					</#if>
					<#if backRoleResourceList?seq_contains("back_role_user_del")>
					 	<button type="button" onclick="deleteRole('${l.id}')" class="btn btn-link hm-col deleteBtn"  pid="${l.id}">删除</button>	
					</#if>
				</td>
			</tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${(lists.number)!''}" data-total-pages="${(lists.totalPages)!''}"></ul>

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
	
	$(".setPrivilege").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/privilege/goPrivilege',
			data: 'id='+pid,
			target: 'main'
		});
	});
	
	$(".editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/privilege/addOrEdit',
			data: 'id='+pid,
			target: 'main'
		});
	});
	
});
	// 删除角色
	function deleteRole(id) {
		$.confirm({
			confirmButton: '确定',
			cancelButton: '取消',
			cancelButtonClass: 'btn-danger',
		    title: '提示',
		    content: '您确定要删除吗？',
		    confirm: function(){
		       $.ajax({
     				url:"${app}/privilege/delete/"+id,
     				type:"POST",
     				dataType:'json',
     				success:function(data) {
     					if(data.type == "SUCCESS") {
     						$.alert({
							    title: '结果',
							    content: '删除成功',
							    icon: 'glyphicon glyphicon-heart',
							    confirm: function(){
							     		$.link.html(null, {
											url: '${app}/privilege/roleIndex',
											target: 'main'
										});
							    }
							});
     					} else {
     						$.alert({
							    title: '结果',
							    icon: 'fa fa-warning',
							    content: data.firstMessage
							});
     					}
     				}
		       });
		    },
		    cancel: function(){
		}
	});
}
//-->
</script>