<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
		                <th class="align-left">参数编码</th>
				        <th class="align-left">参数名称</th>
                        <th class="align-left">参数值</th>
                        <th class="align-left">参数描述</th>
                        <th class="align-center">创建时间</th>
                        <!--<th class="align-center">参数状态</th>-->
                        <th class="align-center">参数类型</th>
                        <th class="align-center">操作</th>
		</tr>
	</thead>
	 <tbody>
                   <#if propertiesList.numberOfElements == 0>
					<tr>
						<td colspan="8" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (propertiesList.content)?sort_by("createTime")?reverse as l>  
							<tr>
							    <td class="align-left">${(l.code)!''}</td> 
							    <td class="align-left">${(l.name)!''}</td> 
		                        <td class="align-left">${(l.value)!''}</td>
		                        <td class="align-left" width="300px">${(l.remark)!''}</td> 
		                        <td class="align-center">${(l.createTime)!''}</td> 
		                        <!--<td class="align-center">${(l.statusName)!''}</td>--> 
		                        <td class="align-center">${(l.type.name)!''}</td> 		                         		                        		                         
								<td class="align-center">
									<button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-link hm-col deleteBtn"  cid="${l.id}">删除</button>									
								</td>
							</tr>
						</#list>
					</#if>
       </tbody>
</table>
 <#if propertiesList??>
<ul class="pagination" data-number="${propertiesList.number!''}" data-total-pages="${propertiesList.totalPages!''}"></ul>
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
			url: '${app}/properties/updateProperties?id='+pid,
			target: 'main'
		});
	});
	$(".deleteBtn").on("click",function(){
		var cid = $(this).attr("cid");
		$.link.html(null, {
			url: '${app}/properties/delProperties?id='+cid,
			target: 'main'
		});
     });				
});
</script>
