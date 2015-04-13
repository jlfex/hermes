<div id="channelList" class="panel panel-default embed">
	<div class="panel-body">
		<ul class="nav nav-tabs">
			<#list channels as cmap>
			<li>
				<a href="#">${cmap.key}</a>
				<div class="channels clearfix">
					<#list cmap.value as c>
					<p class="channel pull-left" data-id="${c.id}"><img alt="${c.name}" src="${c.logo}" class="img-channel"></p>
					</#list>
				</div>
			</li>
			</#list>
		</ul>
	</div>
	
	<script type="text/javascript" charset="utf-8">
	<!--
	jQuery(function($) {
		// 初始化
		var _list = $('#channelList'),
			_body = _list.find('.panel-body'),
			_tabs = _list.find('.nav-tabs li');
		
		// 绑定选择支付方式分类事件
		_tabs.on('click', function() {
			_tabs.filter('.active').append(_body.find('.current').removeClass('current').hide()).removeClass('active');
			$(this).addClass('active').find('.channels').addClass('current').show().appendTo(_body);
		}).filter(':first').trigger('click');
		
		// 绑定选择支付方式事件
		_list.find('.channel').on('click', function() {
			// 初始化
			var _elem = $(this),
				_img = _elem.find('img'),
				_payment = $('#channel'),
				_selected = $('#selectedChannel'),
				_select = $('#selectChannel');
			
			// 属性设置
			_selected.show().find('img').prop({'alt': _img.prop('alt'), 'src': _img.prop('src')});
			_payment.val(_elem.data().id);
			_list.hide('fast', function() { _list.remove(); _select.show(); });
			$('#addChargeBtn').prop('disabled', false).removeClass('btn-default').addClass('btn-primary');
		});
	});
	//-->
	</script>
</div>
