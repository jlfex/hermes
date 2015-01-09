// progress

;(function($) {   
  $.fn.mProgress = function(options) { 
    var opts = $.extend({}, $.fn.mProgress.defaults, options);   
    return this.each(function() {
	  	var progress = $(this); // 进度条
	  	var startNum = parseFloat(progress.find('.m_progress_start').children('span').html()); // 起始值
	  	var endNum = parseFloat(progress.find('.m_progress_end').children('span').html()); // 结束值
			var range = progress.find('.m_progress_range'); // 进度条范围
			var cursor = progress.find('.m_progress_cursor'); // 游标
			var percent = progress.find('.m_progress_percent'); // 百分比
			var title = progress.find('.m_progress_cursor_title'); // 游标标题
			var titleCon = title.find('span'); // 游标标题内容
			var accuracy = progress.data('accuracy') || 1; // 精确度
			var isClick = false; // 记录鼠标是否按下
			var defaultX; // 按下鼠标时候的X坐标
			var imgLeft; // 游标离起始点的距离
			var cssPosition = function(value, num) {
				cursor.css({"left": value});
				percent.css({"width": value});
				title.css({"left": value});
				num && titleCon.html(num);
			}
			var move = function(mouseX) { // 运动
				var newLeft = parseFloat(mouseX-defaultX) + imgLeft;
				var num = Math.round(((endNum - startNum) * newLeft / 500 + startNum) * accuracy) / accuracy;
				if(newLeft > 0 && newLeft < 500) {
					cssPosition(newLeft, num);
				}else if(newLeft <= 0) {
					cssPosition(0, startNum);	
				}else {
					cssPosition(500, endNum);
				}
			};
			
			// 作用于进度条
			range.mousemove(function(e) { // 鼠标移动
				if(isClick) { 
					move(e.pageX)
				};
				return false;
			}).mouseleave(function(){ // 鼠标离开
				isClick = false; 
			}).click(function(e) { // 鼠标点击
				!defaultX && (defaultX = cursor.css("left", 0).offset().left + 10);
				!imgLeft && (imgLeft = 0);
				move(e.pageX)
				return false;
			});
			
			// 作用于游标
			cursor.mousedown(function(e){ // 鼠标按下
				isClick = true;
				defaultX = e.pageX;
				imgLeft = parseFloat(cursor.css("left"));
				return false;
			}).mouseup(function(){ // 鼠标松开
				isClick = false; 
			})

			// 初始化游标位置
			cssPosition(500 * (titleCon.html() -startNum) / (endNum - startNum));

    });    
  };       
  $.fn.mProgress.defaults = {
		
  };      
})(jQuery); 


// tab

;(function($) {   
  $.fn.mTab = function(options) { 
    var opts = $.extend({}, $.fn.mTab.defaults, options);
    return this.each(function() {
  		$(this).find('.m_tab_t').find('li').on(opts.action, function(){
        var index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $(this).parent().next('.m_tab_c').children().eq(index).show().siblings().hide();
    	})
    });    
  };       
  $.fn.mTab.defaults = {
		action: 'click'	
  };      
})(jQuery);


// validator

;(function($) {   
  $.fn.mValidator = function(options) { 
    var opts = $.extend({}, $.fn.mValidator.defaults, options);
    return this.each(function() {
    	var $this = $(this)
    		, mv_checked = $this.find('.mv_checked')
    		, info = {  // 对应关系
			  		mv_email: {
			  			initText: opts.emailInitText,  // 输入框初始化内容
			  			initMsg: '',  // 获取焦点时提示信息
			  			methods: [{  // 验证方法
			    			errorMsg: '邮箱输入有误！',  // 错误信息
			    			rule: function(val) { return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(val)}  // 验证规则
			    		},{
			  				errorMsg: '该邮箱已被使用，请重新输入。',
			    			ajax: opts.emailUniqueAjax
			  			},{
			  				errorMsg: '该邮箱没有注册。',
			    			ajax: opts.emailExistAjax
			  			}]
			  		},
			  		mv_pwd: {
			  			initText:  opts.pwdInitText,
			  			initMsg: '6-16个字符，建议使用字母加数字或符号的组合密码',
			  			methods: [{
			  				errorMsg: '长度6-16位',
			  				rule: function(val) { return /^[A-Za-z0-9_!@#$%^&*()]{6,16}$/.test(val)}
			    		}]
			  		},
			  		mv_pwdagain: {
			  			initText: '确认密码',
			  			initMsg: '',
			  			methods: [{
			  				errorMsg: '密码不一致！',
			  				rule:  function(val, valPwd) { return val === valPwd}
			    		}]
			  		},
			  		mv_captcha: {
			  			initText: '验证码',
			  			initMsg: '',
			  			methods: [{
			  				errorMsg: '验证码错误',
			  				ajax: opts.captchaAjax
			    		}]
			  		},
			  		mv_mobile: {
			  			initText: '手机号码',
			  			initMsg: '请输入11位手机号码',
			  			methods: [{
			  				errorMsg: '请输入正确的手机号码',
			  				rule: function(val) { return /^1[3458]\d{9}$/.test(val)}
			  			},{
			  				errorMsg: '当前手机号码已被绑定！',
			  				ajax: opts.mobileUniqueAjax
			  			}]
			  		},
			  		mv_realname: {
			  			initText: '姓名',
			  			initMsg: '',
			  			methods: [{
			  				errorMsg: '请输入正确的姓名',
			  				rule: function(val) { return /^[a-zA-Z0-9\u4e00-\u9fa5 •·]+$/.test(val)}
			    		}]
			  		},
			  		mv_name: {
			  			initText: '昵称',
			  			initMsg: '',
			  			methods: [{
			  				errorMsg: '请输入4-20个字符，支持中文,英文,数字, “_”,“-”( 最少2个汉字或4个字符/数字）',
			  				rule: function(val) {
			  					var len =  val.replace(/[\u4e00-\u9fa5]/g,"**").length;
			  					return /^[a-zA-Z0-9\u4e00-\u9fa5_-]+$/.test(val) && len >= 4
			  				}
			  			},{
			  				errorMsg: '该昵称已被使用，请重新输入',
			  				ajax: opts.nameUniqueAjax
			  			}]
			  		},
			  		mv_money50: {
			  			initText: '',
			  			initMsg: '最低投标金额为50元，并且是50的倍数。',
			  			methods: [{
			  				errorMsg: '输入数字应为50的整倍',
			  				rule: function(val) {
			  					return /^([1-9]\d*)(\.0+)?$/.test(val) && val%50 === 0
			  				}
			    		},{
			  				errorMsg: '投标金额必须≤当前可投金额',
			  				ajax: opts.moneyMoreAjax
			    		},{
			  				errorMsg: '余额不足，请先充值',
			  				ajax: opts.moneyLessAjax
			    		}]
			  		},
			  		mv_money: {
			  			initText: '',
			  			initMsg: '',
			  			methods: [{
			  				errorMsg: '金额必须为整数或小数，小数点后不超过2位',
			  				rule: function(val) { return /^([1-9]\d*)(\.\d{1,2})?$/.test(val)}
			    		}]
			  		}
			 }, errorOperate = function(el, msg) {
	    		el.nextAll('.mv_msg').removeClass('mv_right').addClass('mv_error').html(msg)
	     }, rightOperate = function(el) {
	    		el.nextAll('.mv_msg').removeClass('mv_error').addClass('mv_right').html('&nbsp;')
	     };

			$.each(info, function(key, value) {  // 遍历所有输入框
				opts.isInitText && $this.find('.' + key).val(value.initText)  // 初始化输入框内容
				$this.find('.' + key).on({
			  	focus: function() {
			  		opts.isInitText && $(this).val() === value.initText && $(this).val('');
			  		opts.isInitMsg && value.initMsg && $(this).nextAll('.mv_msg').removeClass('mv_error mv_right').html(value.initMsg)
	    		},
	    		blur: function() {
	    			var text = $(this).val()
	    				, flag = true
	    				, hasAjax = false;

	        	// $(this).hasClass('mv_require') && text === '' && errorOperate($(this), '必填项,不能为空！') && return;

	        	opts.isInitText && text === '' && $(this).val(value.initText);

		    		for(var i=0,len=value.methods.length;i<len;i++){
		    			if(!value.methods[i].ajax && value.methods[i].rule && !value.methods[i].rule(text, $this.find('.mv_pwd').val())){
			    			errorOperate($(this), value.methods[i].errorMsg)
			    			flag = false;
			    			return
			    		}else if(value.methods[i].ajax) {
			    			if(!$.isEmptyObject(value.methods[i].ajax)) {
			    				var elem = $(this)
			    				, hasAjax = true;
				    			(function (i) {
					    			$.ajax({
		                  url: value.methods[i].ajax.url,  
		                  dataType: value.methods[i].ajax.dataType,
		                  data: value.methods[i].ajax.key + '=' + text,
		                  async: false,
		                  success: function(data){
		                    if(data[value.methods[i].ajax.key]) {
		                    	i === len-1 && flag && rightOperate(elem);
		                    }else {
		                  		errorOperate(elem, value.methods[i].errorMsg)
		                      flag = false;
		                    }
		                  }
		                });
	                })(i)
			    			}else {
			    				i === len-1 && flag && rightOperate($(this));
			    			}
			    			if(!flag) {
			    				return
			    			}
			    		};
		    		};

		    		!hasAjax && flag && rightOperate($(this));
	    		}
	    	});
			});

			mv_checked.on('click', function(){
				var msg = $(this).find('.mv_msg');
				if($(this).find(':checked').length > 0) {
					msg.removeClass('mv_error').addClass('mv_right').html('&nbsp;')
				}else {
					msg.removeClass('mv_right').addClass('mv_error').html(msg.data('msg'))
				}
			})

    	$this.find('.mv_submit').on('click', function() {  // 提交按钮

    		$.each(info, function(key) {  // 提交时遍历所有输入框
    			$this.find('.' + key).focus().blur();
    		})

    		mv_checked.filter(':visible').each(function(){  // 提交时遍历所有checkbox、radio
    			var msg = $(this).find('.mv_msg');
					$(this).find(':checked').length <= 0 && msg.removeClass('mv_right').addClass('mv_error').html(msg.data('msg'));
				})

    		return $this.has('.mv_error:visible').length <= 0;
    	});

    });    
  };       
  $.fn.mValidator.defaults = {
  	isInitText: false,
  	isInitMsg: false,
		emailUniqueAjax: {},
		emailExistAjax: {},
    captchaAjax: {},
    mobileUniqueAjax: {},
    nameUniqueAjax: {},
    moneyMoreAjax: {},
    moneyLessAjax: {},
		emailInitText: '常用电子邮箱',
    pwdInitText: '密码'
  };      
})(jQuery);