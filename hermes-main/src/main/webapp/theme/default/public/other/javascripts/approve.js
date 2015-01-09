// JavaScript Document


$(function(){
	  
	
	// 认证中心
    $(".phone_aprove, .name_aprove").mouseenter(function(){
        $(this).addClass("mousebg");
    });
    $(".phone_aprove, .name_aprove").mouseleave(function(){
        $(this).removeClass("mousebg");
    });
      $("p.p3").click(function(){
        $(".formapprove3").show();
    });

      $(".phone_aprove").mouseleave(function(){
        $(".formapprove3").hide();
    });
        $("p.p4").click(function(){
                $(".formapprove4").show();
            });
        $(".name_aprove").mouseleave(function(){
        $(".formapprove4").hide();
    });
		$("a.ad_bankcart").click(function(){
			$(".bankinfo").show();
			});
		$("a.closex").click(function(){
			$(".bankinfo").hide();
			});
		$("#edit_bt").click(function(){
			$("#poscenter01").hide();
			$("#poscenter02").show();
			});
		$("#save_bt01").click(function(){
				$("#poscenter02").hide();
				$("#poscenter01").show();
				});

	//借款详情投标按钮   理财协议
		
/*		var $cr = $("#cr"); 
		$cr.click(function(){
			if($cr.is(":checked")){ 
				$("#a_bg").addClass("m_bg1");
			}else{
				$("#a_bg").removeClass("m_bg1");
				}
		});	*/
		
   //个人信息		
					
			$("#w_info_tb_01").mouseenter(function(){
			$("#w_lay_ps_01").show();
			});
			$("#w_lay_ps_01").mouseleave(function(){
				$("#w_info_tb_01").show();
				$(this).hide();
			});		
			// $(".peopel_add").click(function(){
			// 	$(".et_ad").show();
			// 	$(this).hide();
			// 	});	
			

		
});


$(function(){


	// var	addBox = $('.m_addbox').clone(true);

	// $('body').on('click', '.m_addbox_add', function(){
	// 	$(this).next('.m_addbox_con').show();
	// 	$(this).hide();
	// });
	// $('body').on('click', '.m_addbox_save', function(){
	// 	var box = $(this).closest('.m_addbox');
	// 	box.find('.m_addbox_input').hide().nextAll('.m_addbox_result').show();
	// 	box.find('.m_addbox_list').hide();
	// 	box.after(addBox.clone(true))
	// });
	// $('body').on('click', '.m_addbox_cancel', function(){
	// 	var box = $(this).closest('.m_addbox');
	// 	box.find('.m_addbox_con').hide();
	// 	box.find('.m_addbox_add').show();
	// });
	// $('body').on('click', '.m_addbox_edit', function(){
	// 	var box = $(this).closest('.m_addbox');
	// 	box.find('.m_addbox_result').hide().prevAll('.m_addbox_input').show();
	// 	box.find('.m_addbox_list').show();
	// });

	var	addBoxTemp = [];

	$('.m_addbox').each(function(){
		addBoxTemp[$(this).data('addbox')] = $(this).clone(true);
	})


	$('body').on('click', '.m_addbox_add', function(){
		$(this).next('.m_addbox_con').show();
		$(this).hide();
	});
	$('body').on('click', '.m_addbox_save', function(){
		var box = $(this).closest('.m_addbox');
		box.find('.m_addbox_input').hide().nextAll('.m_addbox_result').show();
		box.find('.m_addbox_list').hide();
		box.after(addBoxTemp[box.data('addbox')].clone(true));
	});
	$('body').on('click', '.m_addbox_cancel', function(){
		var box = $(this).closest('.m_addbox');
		box.find('.m_addbox_con').hide();
		box.find('.m_addbox_add').show();
	});
	$('body').on('click', '.m_addbox_edit', function(){
		var box = $(this).closest('.m_addbox');
		box.find('.m_addbox_result').hide().prevAll('.m_addbox_input').show();
		box.find('.m_addbox_list').show();
	});

});
$(function(){
	$("a.slide_atuo").bind('click',function(){
		var $content = $("div.content");
		if($content.is(":visible")){
			$content.hide();
			$(this).css('background','url(images/icon2/bg3_01.png) no-repeat center');	
			}else{
			$content.show();
			$(this).css('background','url(images/icon2/bg3_03.png) no-repeat center');
			$(this).parent("h5").parent(".papers_01").siblings().children("div.content").hide();	
			}
	});
});

$(function(){
 var _area=$('textarea#jq');
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
});

$(function(){
	$(".headimg img").mouseenter(function(){
		$("a.replace_hd_img").show();
		});
	$(".headimg").mouseleave(function(){
		$("a.replace_hd_img").hide();
		});
	});

$(function(){
	$("#login_bt, #a_bg").click(function(){
		$(".login_cover").css({ "position": "absolute", "text-align": "center", "top": "0px", "left": "0px", "right": "0px", "bottom": "0px", "background": "#000000", "visibility": "visible", "filter": "Alpha(opacity=60)"
                }).show();		
		 $(".login_cover").css({
                    height: function () {
                        return $(document).height();
                    },
                    width:"100%"
          });
		});
		var width = document.documentElement.clientWidth;
      	var height = document.documentElement.clientHeight;
      	var width1 = $(".login_pt").width();
      	var height1 = $(".login_pt").height();
      	var temp_width = (width - width1)/2 + "px";
      	var temp_top = (height - height1)/2 + "px";
      	$(".login_pt").css("margin-left",temp_width);
      	$(".login_pt").css("margin-top",temp_top);
	$(".login_pt .close").click(function(){
		$(".login_cover").hide();
		});
		
		
	});





















