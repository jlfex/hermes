<!-- foot start-->
<div class="footer">
    <div class="footcenter">
        <div class="companyinfo clearfix">
            <h4>公司介绍</h4>
            <ul>
                <li><a href="javascript:value(0);">平台原理</a></li>
                <li><a href="javascript:value(0);">安全保障</a></li>
                <li><a href="javascript:value(0);">媒体报道</a></li>
                <li><a href="javascript:value(0);">联系我们</a></li>
            </ul>

        </div>
		<img src="${app.theme}/public/images/footerline.png" alt="" />
        <div class="links clearfix">
            <h4>友情链接</h4>
            <ul>
            	<#list friendlinkData as m>
               	 <li><a href="http://${(m.link)!}" target="_blank">${(m.name)!}</a></li>
                </#list>
            </ul>
        </div>
        <!-- <div class="serves clearfix">
            <h4>客户服务</h4>
            <ul>
                <li><a href="javascript:value(0);"><img src="images/icon1/foot_xinlang.png"><span>新浪微博</span></a></li>
                <li><a href="javascript:value(0);"><img src="images/icon1/foot_tengxun.png">腾讯微博</a></li>
                <li><a href="javascript:value(0);"><img src="images/icon1/foot_wechat.png">微信</a></li>
            </ul>
        </div> -->
		<img src="${app.theme}/public/images/footerline.png" alt="" />
        <div class="telephone clearfix">
            <h4>客服电话</h4>
			<ul>
				<li><p><@config key="site.service.tel"/></p></li>
				<li><span class="worktime">工作时间：<@config key="site.service.time"/></span></li>
			</ul>
        </div>
		<div class="copyright">版权所有 © <@config key="app.company.name"/> <@config key="app.icp"/></div>
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
		_content.css('min-height', _win_height - _footer_height - _content_offset.top - 40);
	}).trigger('resize');
});
//-->
</script>
<!-- /footer -->
