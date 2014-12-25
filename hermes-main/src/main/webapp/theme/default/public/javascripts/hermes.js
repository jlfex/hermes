/**
 * 
 */
(function($) {
	$.extend({
		// 顶端导航栏选中处理
		homeNav: function(className) {
			$('#homeNav .' + className).addClass('active');
		},
		
		// 地区级联处理
		area: function(opts, indx) {
			// 初始化当前控件
			var _indx = indx || 0,
				_elem = opts.bind[_indx];
			
			// 处理控件
			// 绑定事件
			_elem.empty().each(function() {
				// 遍历数据生成选项
				$.each(opts.data, function() {
					_elem.append($('<option />').val(this.id).text(this.name).data(this.children).prop('selected', (this.id === _elem.data().val)));
				});
			}).on('change', function() {
				// 仅当尚存在下级级联时响应事件
				if (opts.bind.length > _indx + 1) {
					$.area({ data: _elem.find(':selected').data(), bind: opts.bind }, _indx + 1);
				}
			}).trigger('change');
		}
	});
	
	/**
	 * 
	 */
	$.fn.extend({
		// 重复向上滚动
		// 应用于公告滚动
		roll: function() {
			return this.each(function() {
				// 初始化
				var _elem = $(this),
					_data = _elem.data();
				
				// 判断是否需要滚动
				// 当超过一条内容时进行滚动
				if (_elem.find('li').size() > 1) {
					window.setInterval(function() {
						_elem.find('li:first').slideUp('slow', function() {
							$(this).appendTo(_elem).show();
						});
					}, _data.speed);
				}
			});
		}, 
		
		// 借款列表的处理
		// 主要处理进度的效果以及数字的格式化
		loan: function() {
			return this.each(function(i, elem) {
				// 遍历所有数据行并进行处理
				$(elem).find('tbody tr').each(function() {
					// 初始化
					var _tr = $(this),
						_tr_offset = _tr.offset(),
						_tr_height = _tr.outerHeight(),
						_data = _tr.data(),
						_top = _tr_offset.top + _tr_height - 2,
						_width = 980 * _data.progress / 100;
					
					// 格式化数字格式
					_tr.find('td').formatNumber();
					
					// 生成进度条
					$('<div />').addClass('progress').addClass(function() {
						// 根据不同进度添加不同样式
						// 同时对满标借款实行无效处理
						if (_data.progress < 100) {
							return 'rank-w';
						} else {
							_tr.find('button').attr('disabled', true).addClass('btn-default').removeClass('btn-primary');
							return 'rank-s';
						}
					}).css({'position': 'absolute', 'top': _top}).appendTo($(elem)).animate({
						width: _width
					}, 3000, function() {
						// 回调时显示百分比
						$(this).popover({
							animation: 'true',
							html: 'true',
							placement: 'left',
							content: [_data.progress, '<span class="weak">%</span>'].join('')
						}).popover('show');
					});
				});
			});
		}, 
		
		// 格式化数字
		// 对小数点后或单位符号进行弱化处理
		formatNumber: function() {
			return this.html(function() {
				// 初始化
				var _elem = $(this),
					_text = _elem.text();
				
				// 判断并进行处理
				if (_text.indexOf('.') > 0) {
					var _tmp = _text.split('.');
					return [_tmp[0], '.<span class="weak">', _tmp[1], '</span>'].join('');
				} else if (_text.indexOf(' ') > 0) {
					var _tmp = _text.split(' ');
					return [_tmp[0], ' <span class="weak">', _tmp[1], '</span>'].join('');
				}
			});
		},
		
		// 链接处理
		// 对元素绑定点击事件并异步加载内容
		link: function() {
			return this.on('click', function() {
				// 初始化
				var _elem = $(this),
					_data = _elem.data();
				
				// 加载数据
				$.ajax(_data.url, {
					data: _data.data,
					type: 'post',
					dataType: 'html',
					timeout: 10000,
					success: function(data, textStatus, xhr) {
						$('#' + _data.target).fadeOut('fast', function() {
							$(this).html(data).fadeIn('fast');
						});
					}
				});
			});
		},
		
		// 设置单一样式
		// 在容器内定义唯一的样式
		singleClass: function(className, opts) {
			return this.each(function() {
				// 初始化
				var _elem = $(this),
					_opts = $.extend({}, opts);
					_container = _opts.container || _elem.parent(),
					_replace = _opts.replace || '';
				
				// 设置样式
				_container.find('.' + className).addClass(_replace).removeClass(className);
				_elem.addClass(className).removeClass(_replace);
			});
		}
	});
})(jQuery);
