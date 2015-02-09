<table id="table" class="table table-striped table-hover">
	<thead>
		              <tr>
		                <th class="align-center">债权人名称</th>
                        <th class="align-center">债权编号</th>
                        <th class="align-center">原始借款金额</th>
                        <th class="align-center">年利率</th>
                        <th class="align-center">期限(月)</th>
                        <th class="align-center">产品名称</th>
                        <th class="align-center">转让价格</th>
                        <th class="align-center">转让年利率</th>
                        <th class="align-center">转让期限(天)</th>
                        <th class="align-center">转让日期</th>
                        <th class="align-center">还款方式</th>
                        <th class="align-center">状态</th>
                        <th class="align-center">投标管理</th>
                        <th class="align-center">操作</th>
                    </tr>
	</thead>
		<#if assignedList.numberOfElements == 0>
		<tr>
			<td colspan="13" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list (assignedList.content)?sort_by("createTime")?reverse as l>  
			 <tr>
			   <td class="align-center">${(l.creditor.creditorName)!''}</td> 
			   <td class="align-center">${l.crediteCode!''}</td> 
               <td class="align-center">${(l.amount)!''}</td>  
               <td class="align-center">${(l.rate!0)?string.percent}</td>
               <td class="align-center">${l.period!''}</td>
               <td class="align-center">${l.purpose!''}</td> 
               <td class="align-center">${l.sellAmount!''}</td> 
               <td class="align-center">${(l.rate!0)?string.percent}</td> 
               <td class="align-center">${(l.deadLine)!''}</td>
               <td class="align-center">${l.assignTime!''}</td>
               <td class="align-center">${l.payType}</td> 
               <td class="align-center">${l.statusName}</td>
               <td class="align-center">
	                <span class="bidDetailView" data-val="${app}/credit/bidDetail/${l.id}">
				      <span class="btn-link" style="color:#428bca;">投标明细</span>
			        </span>
                </td>
               <td class="align-center">
                    <span class="repayDetailView" data-val="${app}/credit/repayDetail/${l.id}">
				      <span class="btn-link" style="color:#428bca;">查看回款详情</span>
			        </span>
               </td>    
             </tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${(assignedList.number)!''}" data-total-pages="${(assignedList.totalPages)!''}"></ul>

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
	
	$('.bidDetailView').click(function(){
	    var url = $(this).attr("data-val"); 
		var win = openwindow(url,"",800,500);
	});
	$('.repayDetailView').click(function(){
	    var url = $(this).attr("data-val"); 
		var win = openwindow(url,"",800,500);
	});
    function openwindow(url,name,iWidth,iHeight)
	{
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		return window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}  
	
	
	
    
});
//-->
</script>