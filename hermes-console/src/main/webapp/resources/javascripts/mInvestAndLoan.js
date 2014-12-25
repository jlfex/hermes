// progress

;(function($) {   
  $.fn.mProgress = function(options) { 
    var opts = $.extend({}, $.fn.mProgress.defaults, options);   
    return this.each(function() {
	  	var progress = $(this); // 进度条
	  	var startNum = parseFloat(progress.find('.m_progress_start').children('span').html()); // 起始值
	  	var endNum = parseFloat(progress.find('.m_progress_end').children('span').html()); // 结束值
			var cursor = progress.find('.m_progress_end'); // 游标
			var range = progress.find('.m_progress_range'); // 进度条范围
			var cursor = progress.find('.m_progress_cursor'); // 游标
			var percent = progress.find('.m_progress_percent'); // 百分比
			var title = progress.find('.m_progress_cursor_title'); // 游标标题
			var titleCon = title.find('span'); // 游标标题内容
			var accuracy = progress.data('accuracy') || 1; // 精确度
			var isClick = false; // 记录鼠标是否按下
			var defaultX = cursor.offset().left; // 按下鼠标时候的X坐标
			var imgLeft = parseFloat(cursor.css("left")); // 游标离起始点的距离
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
			 
			// 初始化游标位置
			cssPosition(500 * (titleCon.html() -startNum) / (endNum - startNum));
			
			// 作用于进度条
			range.mousemove(function(e) { // 鼠标移动
				if(isClick) { 
					move(e.pageX)
				};
				return false;
			}).mouseleave(function(){ // 鼠标离开
				isClick = false; 
			}).click(function(e) { // 鼠标点击
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
			  		mv_memo: {
			  			initText: opts.memoInitText,  // 
			  			initMsg: '输入驳回原因，通过理由等。必填项',  // 获取焦点时提示信息
			  			methods: [{  // 验证方法
			    			errorMsg: '请输入备注',  // 错误信息
			    			rule: function(val) {	return  !/^\s*$/.test(val) }  // 验证规则
			    		}]
			  		},
			  		mv_fixamount: {
			  			initText: opts.fixamountInitText,  // 
			  			initMsg: '输入调整金额≤借款金额',  // 获取焦点时提示信息
			  			methods: [{  // 验证方法
			    			errorMsg: '金额必须为整数或小数，小数点后不超过2位',  // 错误信息
			    			rule: function(val) {	
			    					var result =   /^\s*$/.test(val) ;  //判断是否有值
			    					if(!result)
		    						{
			    						return /^([1-9]\d*)(\.\d{1,2})?$/.test(val)   //有值的话判断是否是数字且精确到2位小数 
		    						}
			    					else          //没有值直接返回正确
			    					{
			    						return true;
			    					}	
			    				}  // 验证规则
			    		},{
			    			errorMsg: '输入调整金额≤借款金额',
			  				ajax: opts.moneyMoreAjax
			  			},{
			  				errorMsg: '输入数字应为50的整倍',
			  				rule: function(val) {
			  					var result =   /^\s*$/.test(val) ;  //判断是否有值
		    					if(!result)
	    						{
		    						return /^([1-9]\d*)(\.0+)?$/.test(val) && val%50 === 0  //有值的话判断是否是数字是50的倍数
	    						}
		    					else          //没有值直接返回正确
		    					{
		    						return true;
		    					}	
			  				
			  				}
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
		$this.find('.mv_inputsubmit').on('click', function() {  // 提交按钮

    		$.each(info, function(key) {  // 提交时遍历所有输入框
    			$this.find('.' + key).focus().blur();
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
    pwdInitText: '密码',
    memoInitText: '',
    memoInitText: ''
  };      
})(jQuery);

//tab

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


;$(function(){
	var _area=$('textarea#remark');
	 var _info=$('p#jp_text');
	 // var _submit=_info.find(':submit');
	 var _max=_area.attr('maxlength');
	 var _val,_cur,_count,_warn;
	 //_submit.attr('disabled',true);
	 _area.bind('keyup change',function(){ //绑定keyup和change事件
	 // _submit.attr('disabled',false);
	  if(_info.find('span').size()<1){//避免每次弹起都会插入一条提示信息
	   _info.append('<span>还可输入<em>'+ _max +'</em>个字</span>');
	  }
	  _val=$(this).val();
	  _cur=_val.length;
	  _count=_info.find('em');
	  //_warn=_info.find('font');
	  
	  if(_cur==0){//当默认值长度为0时,可输入数为默认maxlength值,此时不可提交
	   _count.text(_max);
	  // _submit.attr('disabled',true);
	  }else if(_cur<_max){//当默认值小于限制数时,可输入数为max-cur
	   _count.text(_max-_cur);
	  // _warn.text('不区分中英文字符数');
	  }else{//当默认值大于等于限制数时,插入一条提示信息并截取限制数内的值
	   _count.text(0);
	   //_warn.text('不可再输入!');
	   $(this).val(_val.substring(0,_max));
	  }
	 });
	 
	 $('.m_progress').mProgress();
// 所有 tab
    $('#tab1').mTab()
    $('#tab2').mTab()
	$('#tab3').mTab()
	    
    $('#loanAudit').mValidator({
        isInitText: true,  // 输入框初始化内容
        isInitMsg: true
    });   
   
    // checked验证
    $('.commonChecked').mValidator();


	

});







