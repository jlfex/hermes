<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		        <th width="10%" class="align-center">类型编码</th>
				<th width="10%" class="align-center">类型名称</th>
				<th width="10%" class="align-center">字典项名称</th>
				<th width="10%" class="align-center">字典项编码</th>				
				<th width="10%" class="align-center">状态</th>
				<th width="10%" class="align-center">操作</th>
		</tr>
	</thead>
	<#if parameterSet.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list parameterSet.content as p>
		<tr>
			<td class="align-center">${(p.typeCode)!''}</td>		
			<td class="align-center">${(p.parameterType)!''}</td>
			<td class="align-center">${(p.parameterValue)!''}</td>
			<td class="align-center">${(p.code)!''}</td>			
			<#if p.status == "00">
			  <td class="align-center">启用</td>			  
			<#elseif p.status == "09">
			  <td class="align-center">禁用</td>
			<#elseif p.typeStatus == "00">
			  <td class="align-center">启用</td>
			<#elseif p.typeStatus == "09">
			  <td class="align-center">禁用</td>
			<#else>
			  <td class="align-center">禁用</td>
			</#if>
			<td class="align-center">	
			    <#if p.parameterValue??>		  
                   <button type="button" class="btn btn-link hm-col editBtn"  pid="${(p.dicId)!''}">编辑</button>
                <#else>
                   <button type="button" class="btn btn-link hm-col editTypeBtn"  oid="${(p.id)!''}">编辑</button>
                   <button type="button" class="btn btn-link delTypeBtn"  tid="${(p.id)!''}">删除</button>                   
                </#if>
                <#if p.status == "00" && p.parameterValue?exists>
                  <button type="button" class="btn btn-link forbiddenBtn"  fid="${(p.dicId)!''}">禁用</button>
                <#elseif p.status == "09" && p.parameterValue?exists>
                  <button type="button" class="btn btn-link enabledBtn"  eid="${(p.dicId)!''}">启用</button>
                </#if>	
                  <button type="button" class="btn btn-link addBtn"  aid="${(p.id)!''}">新增字典项</button>                                    			                                 			  
			</td>
		</tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${parameterSet.number}" data-total-pages="${parameterSet.totalPages}"></ul>
<script type="text/javascript">
	$('.pagination').pagination({
			handler: function(elem) {
				$('#page').val(elem.data().page);
				$('#searchForm').trigger('submit');
			}
		});		
	//点击修改按钮
	$(".editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/parameter/editParameter?id='+pid,
			target: 'main'
		});
	});	

	$(".addBtn").on("click",function(){
		var aid = $(this).attr("aid");
		$.link.html(null, {
			url: '${app}/parameter/addParameter?id='+aid,
			target: 'main'
		});
	});

	$(".delTypeBtn").on("click",function(){
		var tid = $(this).attr("tid");
		$.link.html(null, {
			url: '${app}/parameter/delTypeParameter?id='+tid,
			target: 'main'
		});
	});

	$(".editTypeBtn").on("click",function(){
		var oid = $(this).attr("oid");
		$.link.html(null, {
			url: '${app}/parameter/editParameterType?id='+oid,
			target: 'main'
		});
	});	
	
	//点击禁用按钮
	$(".forbiddenBtn").on("click",function(){
		var fid = $(this).attr("fid");
		$.link.html(null, {
			url: '${app}/parameter/switch?id='+fid,
			target: 'main'
		});
	});	
	//点击启用按钮
	$(".enabledBtn").on("click",function(){
		var eid = $(this).attr("eid");
		$.link.html(null, {
			url: '${app}/parameter/switch?id='+eid,
			target: 'main'
		});
	});	

</script> 