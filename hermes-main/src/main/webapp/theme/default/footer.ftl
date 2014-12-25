<!-- footer -->
<div id="footer" class="footer clearfix">
	<div class="u-container clearfix">
		<div class="row">
			<div class="col-xs-9 footer-left">
				<p class="footer-nav">
					<a href="#"><@messages key="index.bottom.crop" /></a><#t>
					&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<#t>
					<a href="#"><@messages key="index.bottom.platform" /></a><#t>
					&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<#t>
					<a href="#"><@messages key="index.bottom.security" /></a><#t>
					&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<#t>
					<a href="#"><@messages key="index.bottom.faq" /></a><#t>
					&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<#t>
					<a href="#"><@messages key="index.bottom.contact" /></a><#t>
					&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<#t>
					<a href="#"><@messages key="index.bottom.service" /></a><#t>
				</p>
				<p><@config key="app.copyright" /></p>
				<p><@config key="app.icp" /></p>
			</div>
			<div class="col-xs-3 footer-right">
				<div class="wrap clearfix">
					<h4><@messages key="index.service.tel" /></h4>
					<p class="phone"><i class="fa fa-phone"></i> <@config key="site.service.tel" /></p>
					<p><@messages key="index.service.time" /><@config key="site.service.time" /></p>
				</div>
			</div>
		</div>
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
