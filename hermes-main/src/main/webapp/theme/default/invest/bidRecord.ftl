<style>
ul#paged li{width:34px; height:34px; border:none; line-height:34px; text-align:center;}
ul#paged li.active{background-color: #428bca;border-color: #428bca;}
    ul#paged li.active a{ color:#ffffff;}
</style>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">

<div class="m_tda table_mar">
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
        <th class="th_00"><@messages key="invest.user" /></th>
        <th><@messages key="model.invest.amount" />(<@messages key="common.unit.cny" />)</th>
        <th>投标状态</th>
        <th>投标时间</th></tr>
        <#list invests.content as i>  
		<tr>
			<td class="th_00"><#if (i.user.account)??>${i.user.account}</#if></td>
			<td>${i.amount?string('#,##0.00')}</td>
			<td>${i.statusName!''}</td>
			<td>${i.datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
		</tr>
		</#list>
    </table>
   	<ul class="pagination" id="paged" data-number="${invests.number}" data-total-pages="${invests.totalPages}"></ul>					                   
</div>	
<script type="text/javascript">
<!--
jQuery(function($) {
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
			$('#bidRecordForm').trigger('submit');
		});
	});
});
//-->
</script>