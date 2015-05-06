<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		        <th width="10%" class="align-center">类型编码</th>
				<th width="10%" class="align-center">类型名称</th>
				<th width="10%" class="align-center">创建时间</th>
				<th width="10%" class="align-center">操作</th>
		</tr>
	</thead>
	<#if dictList.numberOfElements == 0>
		<tr>
			<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list dictList.content as d>
		<tr>
			<td class="align-center">${(d.code)!''}</td>		
			<td class="align-center">${(d.name)!''}</td>
			<td class="align-center">${(d.createTime)!''}</td>
			<td class="align-center">	
                   <button type="button" class="btn btn-link hm-col detailBtn"  pid="${(d.id)!''}">查看详情</button>
                   <button type="button" class="btn btn-link hm-col delBtn"     oid="${(d.id)!''}">删除</button>
                   <button type="button" class="btn btn-link hm-col editBtn"    tid="${(d.id)!''}">编辑</button>                   
			</td>
		</tr>
		</#list>
		</#if>
	<tbody>
</table>
<ul class="pagination" data-number="${dictList.number}" data-total-pages="${dictList.totalPages}"></ul>

<script type="text/javascript">
	$('.pagination').pagination({
			handler: function(elem) {
				$('#page').val(elem.data().page);
				$('#searchForm').trigger('submit');
			}
		});	
			
	$(".detailBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/dictionary/index?id='+pid,
			target: 'main'
		});
	});	

	$(".editBtn").on("click",function(){
		var tid = $(this).attr("tid");
		$.link.html(null, {
			url: '${app}/dictionaryType/updateDictionaryType?id='+tid,
			target: 'main'
		});
	});	

	$(".delBtn").on("click",function(){
		var oid = $(this).attr("oid");
		$.link.html(null, {
			url: '${app}/dictionaryType/delDictionaryType?id='+oid,
			target: 'main'
		});
	});

</script> 