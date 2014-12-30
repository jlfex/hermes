$(function(){
    // 自定义进度条
    $('.m_progress').mProgress();
    
    // 所有 tab
    $('#tab1').mTab()
    $('#tab2').mTab()
	$('#tab3').mTab()
    
// 某个 tab 如果需要ajax请求内容，请写给 class= 'm_tab_t' 的标签中写id然后调用写ajax，如下例：
//     $('#tabAjax').find('li').click(function(){
//         var index = $(this).index();
//         var content = $(this).parent().next('.m_tab_c').children().eq(index);
//         $.ajax({
//             url: '',
//             ...各种参数..,
//             success:function(data) {
//                content.html(data) // 内容装入div中
//             }
//         })
//     })

    // allcategory
    $('.allcategory .select_list').find('a').click(function(){
        var that = $(this)
          , selectResult = that.closest('.allcategory').children('.select_result').find('ul')
          , selectIndex = that.closest('ul').index()
          , content = '<li class="' + selectIndex + '"><span>' + that.html() + '</span><a href="#"><img src="images/icon2/closeicon.jpg"/></a></li>';

        that.addClass('active').parent().siblings().find('a').removeClass('active');

        selectResult.find('li').each(function(){
            $(this).attr('class') == selectIndex && $(this).remove()
        })

        selectResult.append(content);
    })
    $('.allcategory .select_result').on('click', "li", function(){
        $(this).closest('li').remove();
    })

    // 注册
    $('#register').mValidator({
        isInitText: true,  // 输入框初始化内容
        isInitMsg: true,  // 获取焦点时提示信息
        pwdInitText: '',
        confPwdInitText:'',
        emailUniqueAjax: {
            url: "isExistentEmail",  // 测试邮箱 mo@mo.com
            cache:false,
            dataType: 'json',
            key: 'email'
        },
        captchaAjax: {
            url: "checkVerifiedCode",  
            cache:false,
            dataType: 'json',
            key: 'captcha'
        },
        mobileUniqueAjax: {
            url: "checkCellphone",  // 测试手机唯一性 13659297636
            cache:false,
            dataType: 'json',
            key: 'cellphone'
        },
        nameUniqueAjax: {
            url: "checkAccount",  // 测试用户名 momo
            cache:false,
            dataType: 'json',
            key: 'account'
        }
    });    

    // 登陆
    $('#login').mValidator({
        isInitText: true,
        emailInitText: '账户名',
        pwdInitText: ''
    });

    // 投标金额验证
    $('#loan').mValidator({
        isInitMsg: true,
        moneyMoreAjax: {
            url: "http://192.16.6.89:18080/demo/test",  // 测试投标金额  <=5000
            dataType: 'jsonp',
            key: 'moneyMore'
        },
        moneyLessAjax: {
            url: "http://192.16.6.89:18080/demo/test",  // 测试余额 >=500
            dataType: 'jsonp',
            key: 'moneyLess'
        }
    });

    // 邮箱找回密码
    $('#retrieve').mValidator({
         emailExistAjax: {
            url: "isActiveEmail",  // 测试邮箱 mo@mo.com
            cache:false,
            dataType: 'json',
            key: 'email'
        }
    });
     $('#sendEmail').mValidator({
        emailExistAjax: {
            url: "isActiveEmail",  // 测试邮箱 mo@mo.com
            cache:false,
            dataType: 'json',
            key: 'email'
        },emailUniqueAjax: {
            url: "isExistentEmail",  // 测试邮箱 mo@mo.com
            cache:false,
            dataType: 'json',
            key: 'email'
        }
    });

    // 通用表单验证
    $('.commonForm').mValidator();

    // checked验证
    $('.commonChecked').mValidator();



    // 表格斑马线
    $('.m_zebra').find('tr:even').addClass('m_even');
	
	// 表格升降排序图标切换
    $('.m_table_sort').find('th').click(function(){
		var img = $(this).find('img');
		if(img.attr('src') && img.attr('src').lastIndexOf('down') > 0) {
			img.attr('src','images/icon2/invest_arrowd_up.png');
		}else {
			img.attr('src','images/icon2/invest_arrowd_down.png');
		}
	});
	

});







