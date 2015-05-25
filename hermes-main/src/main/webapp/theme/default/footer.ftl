<div class="footer _footer">
    <div class="footcenter">
        <div class="companyinfo clearfix">
            <h4>公司介绍</h4>
            <ul>
            	<#list companyIntroductions as m>
               	 <li><a href="${app}/help-center/${m.id}">${(m.name)!}</a></li>
                </#list>
            </ul>

        </div>
        <div class="links clearfix">
            <h4>友情链接</h4>
            <ul>
            	<#list friendlinkData as m>
               	 <li><a href="${(m.link)!}" target="_blank">${(m.name)!}</a></li>
                </#list>
            </ul>
        </div>
        <div class="telephone clearfix">
            <h4>客服电话</h4>
			<ul>
				<li><p><@config key="site.service.tel"/></p></li>
				<li><span class="worktime">工作时间：<@config key="site.service.time"/></span></li>
				<li><span class="worktime">版权所有<@config key="app.copyright"/></span></li>				
			</ul>
        </div>
        <div class="clearfix"></div>
    </div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$(window).on('resize', function() {
		// 初始化
		var _win = $(this), 
			_content = $('#content'), 
			_footer = $('#footer'), 
			_win_height = _win.height(), 
			_content_offset = _content.offset(),
			_footer_height = _footer.outerHeight();
		
		// 设置最小高度
		//_content.css('min-height', _win_height - _footer_height - _content_offset.top - 40);
	}).trigger('resize');
	
	if(document.documentElement.clientHeight >= document.documentElement.offsetHeight - $(".footer").height()) {
		$(".footer").show();
	}
	
	$(window).scroll(function() {
		if($(document).scrollTop()>=$(document).height()-$(window).height()- $(".footer").height()) {
			$(".footer").show();
		} else {
			$(".footer").hide();
		}
	});

});
//-->
</script>
<!-- /footer -->
