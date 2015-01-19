<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<style type="text/css">

.content_template{width: 750px;
border: 2px solid #e2e2e2;
height: auto;
background: #FFF;
-moz-border-radius: 5px;
-webkit-border-radius: 5px;
border-radius: 5px;}
.content_template h2.title{ text-align: center; padding: 20px 0; color: #323232; font-size: 22px; border-bottom: 1px dashed #eeeeee; font-weight: 500;}
.content_index .fl_right{ float: right;}
.content_index p{ margin: 20px;}
.tu_images{ text-align: center; margin: 20px 0;}
*{
-webkit-box-sizing: content-box;
-moz-box-sizing: content-box;
box-sizing: content-box;
}
</style>
</head>

<body>

<#include "/header.ftl" />

<div class="sub_main" style="margin-top:133px;">
	<div class="account_center">
		<div class="account_nav_left">
			<div class="titleuser"></div>
			<div class="left_nav">
				<#list nav.children as l>
				<!--信息管理-->
				<div class="information">
					<h2>${l.name}</h2>
					<ul>
						<#list l.children as m>
							<li class="liactivebg"><a href="javascript:value(0);" class="a_01">平台原理</a></li>
							<li><a href="javascript:value(0);" class="a_02">如何理财</a></li>
							<li><a href="javascript:value(0);" class="a_02">如何借款</a></li>
							<li><a href="javascript:value(0);" class="a_02">常见问题</a></li>
						</#list>
					</ul>
				</div>
				<div class="navline"></div>
				</#list>
			</div>
			<div class="leftbottom"></div>
		</div>
		<div class="account_content_right">
			<div class="content_template">
				<h2 class="title">易联天下服务公告</h2>	
				<div class="content_index">
					<div class="clearfix"><p class="fl_right ">【发布时间：2015-01-09】</p>	</div>
					<p>据21CN：互联网金融交易平台焦点战场众星云集，易联天下的创新与差异化相结合拥有成为行业新领军的强大潜质。Sina.com.cn则表示：国务院总理李克强在“两会”政府工作报告中提出：“促进民间金融健康发展，让金融成为一池活水，更好地浇灌小微企业、三农等实体经济之树”。</p>
					<p>易联天下在探讨互联网金融如何服务中小微企业和文化影视艺术企业，实现共融性发展，进而提升上海乃至全国金融业的总体竞争力方面，已经走出有益的一步。积极参与评论报道的还有搜狐、股市360、搜狐、腾讯、新浪、财经网、中证网、东方财富网、财经中国、看看新闻网、百家财富网、好买基金网、股城网、汉丰网、金融界、凤凰网、北国网、微e贷等诸多国内金融行业知名媒体。</p>	
					<p>对于媒介、行业的热情，易联天下颇为冷静、自信：这条路必定颇为艰辛，需要易联天下的同仁全身心的付出。但同时，我们的愿景绝对值得我们为之付出，并以此谱写我们的华彩乐章。</p>	 
					<p>在成功分散投资10笔以上: 从第一笔投资起，如果三个月内成功投资10笔以上，则本金保护从第一笔投资开始生效；如果三个月内投资少于10笔，则本金保护从第10笔成功投资开始生效。</p>	
					<p>满足参与本金保障条件的所有非公益借款项目。</p>	
					<div class="tu_images"><img src="images/notice_img_01.png" width="199" height="140"><br>易联天下公告</div>
					<p>易联天下在探讨互联网金融如何服务中小微企业和文化影视艺术企业，实现共融性发展，进而提升上海乃至全国金融业的总体竞争力方面，已经走出有益的一步。</p>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>

<#include "/footer.ftl" />

</body>
</html>