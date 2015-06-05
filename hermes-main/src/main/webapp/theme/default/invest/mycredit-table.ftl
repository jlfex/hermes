<div class="block">
	<div class="body-sm">
		<h4>投标记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr style="padding:0px;border:0px;margin:0px;">
					<th class="center" style="width:10%;">债权名称</th>
					<th class="center" style="width:10%;">债权编号</th>
					<th class="center" style="width:12%;">投标金额/<@messages key="common.unit.cny" /></th>
					<th class="center" style="width:10%;">年利率</th>
					<th class="center" style="width:10%;">期限/天</th>
					<th class="center" style="width:10%;">应收本息/<@messages key="common.unit.cny" /></th>
					<th class="center" style="width:10%;">已收本息/<@messages key="common.unit.cny" /></th>
					<th class="center" style="width:10%;">待收本息/<@messages key="common.unit.cny" /></th>
					<th class="center" style="width:10%;">状态</th>
					<th class="center" style="width:10%;">协议</th>
				</tr>
			</thead>
			<tbody>
			  <#list invests.content as i>  
				<tr>
					<td class="center">
					    <a href="#" class="icon loan investinfo" data-id="${i.id}">${i.purpose}</a>
					</td>
					<td class="center">${i.loanNo!''}</td>
					<td class="center">${i.amount}</td>
					<td class="center">${(i.rate)!}</td>
					<td class="center">${(i.period)!}</td>
					<td class="center">${(i.shouldReceivePI)!}</td>
					<td class="center">${(i.receivedPI)!}</td>
					<td class="center">${(i.waitReceivePI)!}</td>
					<td class="center">${(i.investStatusName)!}</td>
					<#if i.loanKind == '03' && i.loanPdfId?? && i.guaranteePdfId??>									
				        <td class="center">
                            <span class="protocol" data-val="${app}/invest/queryFile/${i.loanPdfId!''}"><a href="#" class="m_a1">协议</a></span>
                            <span class="protocol" data-val="${app}/invest/queryFile/${i.guaranteePdfId!''}"><a href="#" class="m_a1">担保函</a></span>
				        </td>
				    <#elseif i.loanKind == '01'>		
				    	<td><span class="view" data-val="${i.id}"><a href="#" class="m_a1">协议</a></span></td> 
				    <#else>
				    	<td class="center"></td>				    
				    </#if>	
				</tr>
				</#list>
			</tbody>
		</table>
    <ul class="pagination" data-number="${invests.number}" data-total-pages="${invests.totalPages}"></ul>	
	</div>
</div>
<script type="text/javascript">
<!--
jQuery(function($) {
		$(".investinfo").on("click",function(){
		    var investId = $(this).attr("data-id");
			window.location.href="${app}/account/index?type=investinfo&id="+investId;				
		});
	// 处理分页
	$('.pagination').each(function(){                
		// 初始化
		var _elem = $(this).empty(),
			_opts = $.extend({}, _elem.data()),
			_number = _opts.number,
			_pages = _opts.totalPages - 1,
			_begin = ((_number - 3) < 0) ? 0 : (_number - 3),
			_end = ((_number + 3) > _pages) ? _pages : (_number + 3),
			_tag = $('<li />').append($('<a />').attr('href', '#'));
			
		// 当开始页码大于首页时补充首页页码
		if (_begin > 0) {
			_tag.clone().appendTo(_elem).find('a').attr('data-page', 0).text(1);
			_tag.clone().appendTo(_elem).addClass('disabled').find('a').text('...');
		}
		
		// 正常循环生成页码
		for (var _idx = _begin; _idx <= _end; _idx++) {
			if (_idx === _number) {
				_tag.clone().appendTo(_elem).addClass('active').find('a').text(_idx + 1);
			} else {
				_tag.clone().appendTo(_elem).find('a').attr('data-page', _idx).text(_idx + 1);
			}
		}
		
		// 当结束页码小于总页数时补充尾页页码
		if (_end < _pages) {
			_tag.clone().appendTo(_elem).addClass('disabled').find('a').text('...');
			_tag.clone().appendTo(_elem).find('a').attr('data-page', _pages).text(_pages + 1);
		}
		
		// 绑定页码点击事件
		_elem.find('a').on('click', function() {
			$('#page').val($(this).data().page);
			$('#searchForm').trigger('submit');
		});
	});
	 // 外部债权协议
	 $('.view').click(function(){
	    var pid = $(this).attr("data-val");
		var win = openwindow("${app}/invest/assignProtocol?id="+pid,"",1000,800);
	 });
	 //易联协议
	 $('.protocol').click(function(){
	    var url = $(this).attr("data-val");
		var win = openwindow(url,"",1000,800);
	 });
	 
     function openwindow(url,name,iWidth,iHeight){
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
