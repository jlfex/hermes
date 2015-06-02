(function($) {
	$.extend({
		page: {
			// 首页处理
			index: function(opts) {
				// 初始化
				var _tag_menu = $('#menu'),
					_tag_main = $('#main');
				
				// 绑定首页侧栏处理
				$('#bar').on('click', function() {
					// 初始化
					var _elem = $(this);
						
					// 判断当前状态并处理
					if (_elem.hasClass('active')) {
						_elem.removeClass('active');
						_tag_menu.slideUp('fast', function() {
							_tag_main.animate({
								left: '-=200'
							}, 'fast');
						});
					} else {
						_elem.addClass('active');
						_tag_main.animate({
							left: '+=200'
						}, 'fast', function() {
							_tag_menu.slideDown('fast');
						});
					}
				});
				
				// 绑定菜单点击事件
				_tag_menu.find('a').link({
					// 加载页面处理
					_main: function(elem, opts) {
						elem.singleClass('active', '', _tag_menu);
						$.link.html(elem, $.extend({}, opts, { target: _tag_main }));
					}, 
					
					// 加载菜单处理
					_menu: function(elem, opts) {
						_tag_menu.find('dd').remove();
						elem.singleClass('active', '', _tag_menu);
						elem.find('.fa-caret-right').singleClass('fa-caret-down', 'fa-caret-right', _tag_menu);
						$.link.json(elem, $.extend({}, opts, {
							data: {id: opts.id},
							success: function(elem, data) {
								var p = $(elem.parent());
								var n = p.nextAll();
								n.remove();
								$.each(data, function() {
									var _span = $('<span />').text(this.name),
										_i = $('<i />').addClass('pull-right fa fa-caret-right'),
										_a = $('<a href="#" />').attr({ 'data-url': this.url, 'data-target': this.target, 'data-id': this.id }).append(_span).append(_i).link(opts),
										_dd = $('<dd />').append(_a);
									elem.parent().parent().append(_dd);
								});
							}
						}));
					}
				}).filter(':first').trigger('click');
				
				// 初始化日历控件
				$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
			},
			
			// 提现页面处理
			withdraw: function(opts) {
				// 初始化
				var _form_search = $('#searchForm'),
					_btn_search = $('#searchBtn'),
					_date_begin = $('#beginDate'),
					_date_end = $('#endDate'),
					_hide_page = $('#page');
				
				// 绑定表单提交事件
				_form_search.on('submit', function() {
					$.link.html(null, {
						url: opts.search,
						data: _form_search.serialize(),
						target: 'data'
					});
					return false;
				});
				
				// 绑定查询事件
				_btn_search.on('click', function() {
					_hide_page.val(0);
					_form_search.trigger('submit');
				}).trigger('click');
				
				// 日历控件处理
				_date_begin.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_end.datepicker('option', 'minDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
				
				_date_end.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_begin.datepicker('option', 'maxDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
			},
			
			// 用户日志管理
			userLogMng: function(opts) {
				// 初始化
				var _form_search = $('#searchForm'),
					_btn_search = $('#searchBtn'),
					_date_begin = $('#beginDate'),
					_date_end = $('#endDate'),
					_hide_page = $('#page');
				
				// 绑定表单提交事件
				_form_search.on('submit', function() {
					$.link.html(null, {
						url: opts.search,
						data: _form_search.serialize(),
						target: 'data'
					});
					return false;
				});
				
				// 绑定查询事件
				_btn_search.on('click', function() {
					_hide_page.val(0);
					_form_search.trigger('submit');
				}).trigger('click');
				
				// 日历控件处理
				_date_begin.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_end.datepicker('option', 'minDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
				
				_date_end.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_begin.datepicker('option', 'maxDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
			},
			// 借款日志管理
			loanLogMng: function(opts) {
				// 初始化
				var _form_search = $('#searchForm'),
					_btn_search = $('#searchBtn'),
					_date_begin = $('#beginDate'),
					_date_end = $('#endDate'),
					_hide_page = $('#page');
				
				// 绑定表单提交事件
				_form_search.on('submit', function() {
					$.link.html(null, {
						url: opts.search,
						data: _form_search.serialize(),
						target: 'data'
					});
					return false;
				});
				
				// 绑定查询事件
				_btn_search.on('click', function() {
					_hide_page.val(0);
					_form_search.trigger('submit');
				}).trigger('click');
				
				// 日历控件处理
				_date_begin.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_end.datepicker('option', 'minDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
				
				_date_end.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_begin.datepicker('option', 'maxDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
			},
			// 充值明细管理
			rechargeDetailMng: function(opts) {
				// 初始化
				var _form_search = $('#searchForm'),
					_btn_search = $('#searchBtn'),
					_date_begin = $('#beginDate'),
					_date_end = $('#endDate'),
					_hide_page = $('#page');
				
				// 绑定表单提交事件
				_form_search.on('submit', function() {
					$.link.html(null, {
						url: opts.search,
						data: _form_search.serialize(),
						target: 'data'
					});
					return false;
				});
				
				// 绑定查询事件
				_btn_search.on('click', function() {
					_hide_page.val(0);
					_form_search.trigger('submit');
				}).trigger('click');
				
				// 日历控件处理
				_date_begin.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_end.datepicker('option', 'minDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
				
				_date_end.prop('readonly', true).datepicker({
					numberOfMonths: 2,
					onClose: function(date) {
						_date_begin.datepicker('option', 'maxDate', date);
					}
				}).datepicker('option', 'maxDate', opts.today);
			}
		}
	});
})(jQuery);
