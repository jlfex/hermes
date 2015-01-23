<div class="panel panel-primary">
        <div class="panel-heading">投标明细</div>     </div>
<table id="table" class="table table-striped table-hover">
	<thead>
		              <tr>
                        <th class="align-center">序号</th>
                        <th class="align-center">投标人</th>
                        <th class="align-center">投标金额</th>
                        <th class="align-center">投标时间</th>
                    </tr>
	</thead>
	      <#if bidLogList??>
             <#list bidLogList as l>
				 <tr>
				   <td class="align-center">${l_index +1}</td>
	               <td class="align-center">${(l.user)!''}</td>  
	               <td class="align-center">${l.amount!''}</td> 
	               <td class="align-center">${l.datetime!''}</td> 
	             </tr>
			</#list>
		<#else>
		 <tr>
			<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
		 </tr>
		</#if>
	<tbody>
</table>
 <div class="col-xs-1 hm-col form-group" style="margin-right:100px;"><button type="button" class="btn btn-default btn-block" id="cancelBtn">关闭</button></div>
<script type="text/javascript">

jQuery(function($) {
	//点击取消按钮
	$("#cancelBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/credit/assigned',
			target: 'main'
		});
	});
});
</script>  