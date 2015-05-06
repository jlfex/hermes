<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		        <th width="10%" class="align-center">所属字典类型</th>
		        <th width="10%" class="align-center">字典项编码</th>
				<th width="10%" class="align-center">字典项名称</th>
				<th width="10%" class="align-center">创建时间</th>
				<th width="10%" class="align-center">状态</th>
				<th width="10%" class="align-center">操作</th>
		</tr>
	</thead>
	<#if dictList.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list dictList.content as d>
		<tr>
		    <td class="align-center">${(d.type.name)!''}</td>
			<td class="align-center">${(d.code)!''}</td>		
			<td class="align-center">${(d.name)!''}</td>
			<td class="align-center">${(d.createTime)!''}</td>
		    <#if d.status == "00">
			  <td class="align-center">启用</td>			  
			<#elseif d.status == "09">
			  <td class="align-center">禁用</td>
			</#if>			
			<td class="align-center">	
                   <button type="button" class="btn btn-link hm-col delBtn"     oid="${(d.id)!''}">删除</button>
                   <button type="button" class="btn btn-link hm-col editBtn"    tid="${(d.id)!''}">编辑</button> 
                   <#if d.status == "00">
                      <button type="button" class="btn btn-link forbiddenBtn"  fid="${(d.id)!''}">禁用</button>
                   <#elseif d.status == "09">
                      <button type="button" class="btn btn-link enabledBtn"    eid="${(d.id)!''}">启用</button>
                   </#if>	                                     
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
	$(".editBtn").on("click",function(){
		var tid = $(this).attr("tid");
		$.link.html(null, {
			url: '${app}/dictionary/updateDictionary?id='+tid,
			target: 'main'
		});
	});	

	$(".delBtn").on("click",function(){
		var oid = $(this).attr("oid");
		$.link.html(null, {
			url: '${app}/dictionary/delDictionary?id='+oid,
			target: 'main'
		});
	});
	
		//点击禁用按钮
	$(".forbiddenBtn").on("click",function(){
		var fid = $(this).attr("fid");
		$.link.html(null, {
			url: '${app}/dictionary/switch?id='+fid,
			target: 'main'
		});
	});	
	//点击启用按钮
	$(".enabledBtn").on("click",function(){
		var eid = $(this).attr("eid");
		$.link.html(null, {
			url: '${app}/dictionary/switch?id='+eid,
			target: 'main'
		});
	});	
</script> 