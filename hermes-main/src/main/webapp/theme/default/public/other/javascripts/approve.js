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
			$(this).css('background','url(../theme/default/public/other/images/icon2/bg3_01.png) no-repeat center');	
			}else{
			$content.show();
			$(this).css('background','url(../theme/default/public/other/images/icon2/bg3_03.png) no-repeat center');
			$(this).parent("h5").parent(".papers_01").siblings().children("div.content").hide();	
			}
	});
});
