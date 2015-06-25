<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th >代码</th>
				<th >名称</th>
				<th >权限</th>
				<th >操作</th>
		</tr>
	</thead>
		<#if lists.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list lists.content?reverse as l>      
			<tr>
			    <td>${l.code!''}</td>
				<td>${l.name!''}</td>
				<td >${l.cellphone!''}</td>
				<td>
					<a href="#" data-url="${app}/privilege/goPrivilege/${l.id}" data-target="main">设置权限</a>
					<a href="#" data-url="${app}/privilege/addOrEdit/${l.id}" data-target="main">编辑</a>
					<a href="#" onclick="deleteRole('${l.id}')">删除</a>
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