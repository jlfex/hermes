<div class="block">
	<div class="body-sm">
		<h4>投标记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr>
				    <th>借款编号</th>
					<!-- <th><@messages key="model.loan.purpose" /></th>  --> 
					<th><@messages key="model.invest.amount" />/<@messages key="common.unit.cny" /></th>
					<th><@messages key="model.loan.rate" /></th>
					<th><@messages key="model.loan.period" /></th>
					<th><@messages key="invest.should.receive.pi" />/<@messages key="common.unit.cny" /></th>
					<th><@messages key="invest.received.pi" />/<@messages key="common.unit.cny" /></th>
					<th><@messages key="invest.wait.receive.pi" />/<@messages key="common.unit.cny" /></th>
					<th><@messages key="common.status" /></th>
					<th>协议</th>
				</tr>
			</thead>
			<tbody>
			  <#list invests.content as i>  
				<tr>
				    <td><a href="#" class="icon loan info" data-id="${i.id}">${i.loanNo!''}</a></td>
					<!-- <td>${i.purpose!''}</td>  -->
					<td class="right">${i.amount}</td>
					<td class="right">${i.rate}</td>
					<td class="right">${i.period}<@messages key="common.unit.month" /></td>
					<td class="right">${i.shouldReceivePI}</td>
					<td class="right">${i.receivedPI}</td>
					<td class="right">${i.waitReceivePI}</td>
					<td class="right">${i.investStatusName}</td>
					<td><span class="view" data-val="${i.id}"><a href="#" class="m_a1">协议</a></span></td> 
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
		$(".info").on("click",function(){
		    var infoId = $(this).attr("data-id");
			window.location.href="${app}/account/index?type=info&infoId="+infoId;				
		});

	// 处理分页
	$('.pagination').each(function() {
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
		
		//协议查看
		$('.view').click(function(){
		    var id = $(this).attr("data-val");
			openwindow("${app}/invest/investAgree?investId="+id,"",1000,800);
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
});
//-->
</script>
