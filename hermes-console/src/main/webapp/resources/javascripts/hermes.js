/* $Id
 * 
 */

/**
 * 
 */
(function($) {
	'use strict';
	$.extend({
		// 链接处理
		link: {
			defaults: {
				name: 'hermes_request_type',
				html: 'html',
				json: 'json',
				type: 'post',
				timeout: 5000,
				_self: function(elem, opts) { window.location.href = opts.url; }, 
				_blank: function(elem, opts) { window.open(opts.url); }, 
				_default: function(elem, opts) { $.link.html(elem, opts); }
			}, init: function(elem, opts) {
				if (elem.is('[data-url]') && !elem.hasClass('hm-link')) {
					var _opts = $.extend({}, this.defaults, opts, elem.data()),
						_target = _opts.target,
						_method = _opts[_target] || _opts._default;
					elem.on('click', function() { _method(elem, _opts); }).addClass('hm-link');
				}
			}, html: function(elem, opts) {
				var _opts = $.extend({}, this.defaults, opts);
				$.ajax(_opts.url, {
					data: $.link._data(_opts.data, _opts.name, _opts.html),
					type: _opts.type,
					dataType: 'html',
					timeout: _opts.timeout,
					success: function(data, textStatus, xhr) {
						var _target = ($.type(_opts.target) === 'string') ? $('#' + _opts.target) : _opts.target;
						_target.fadeOut('fast', function() {
							_target.html(data).fadeIn('fast');
						});
					}
				});
			}, json: function(elem, opts) {
				var _opts = $.extend({}, this.defaults, opts);
				$.ajax(_opts.url, {
					data: $.link._data(_opts.data, _opts.name, _opts.json),
					type: _opts.type,
					dataType: 'json',
					timeout: _opts.timeout,
					success: function(data, textStatus, xhr) { _opts.success(elem, data, textStatus, xhr); }
				});
			}, _data: function(data, name, value) {
				switch ($.type(data)) {
					case 'object':
						data[name] = value;
						break;
					case 'array':
						data.push({name: name, value: value});
						break;
					case 'string':
						if (data.length > 0) {
							data += ('&' + name + '=' + value);
							break;
						}
					case 'undefined':
						data = (name + '=' + value);
						break;
					default:
						console.error('cannot deal with type.', $.type(data));
				}
				return data;
			}
		}
	});
	
	/**
	 * 
	 */
	$.fn.extend({
		// 添加唯一样式
		singleClass: function(className, defaultName, container) {
			return this.each(function() {
				// 初始化
				var _elem = $(this), 
					_class = className || 'active',
					_default = defaultName || '', 
					_container = container || _this.parent();
				
				// 设置样式
				_container.find('.' + _class).removeClass(_class).addClass(_default);
				_elem.removeClass(_default).addClass(_class);
			});
		},
		
		// 初始化链接处理
		link: function(opts) {
			return this.each(function() {
				$.link.init($(this), opts);
			});
		},
		
		// 分页处理
		pagination: function(opts) {
			return this.each(function() {
				// 初始化
				var _elem = $(this).empty(),
					_opts = $.extend({}, _elem.data(), opts),
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
					_opts.handler($(this));
				});
			});
		}
	});
})(jQuery);
