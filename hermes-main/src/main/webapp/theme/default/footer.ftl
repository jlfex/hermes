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

<!-- /footer -->
