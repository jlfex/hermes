<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<link rel="shortcut icon" href="${app.theme}/public/images/smallIcon.png">
<link rel="apple-touch-icon-precomposed" href="${app.theme}/public/images/bigIcon.png">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/email.js"></script>
<style type="text/css">
table th{text-align:center;}
</style>
<script type="text/javascript">
$(function(){
	$(".content .banner").css("background-image", "url(${(bannerPicture.image)!})");
});
</script>
</head>
<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="_content">
<div id="content" class="content">
	<!-- banner & signin -->
	<div class="banner">	
		<div class="u-container">
			<#if cuser??>
			   <input id="email" name="email" type="hidden" >
			<#else>
			<form id="signInForm" method="post" action="#">
				<div class="sign pull-right">
					<h3><@messages key="index.sign.in.quick" /></h3>
					<div class="form-group">
						<label for="email" class="sr-only">email</label>
						<input id="email" name="email" type="email" placeholder="<@messages key="index.sign.in.account.hint" />" class="form-control">
					</div>
					<div class="form-group">
						<label for="signPassword" class="sr-only">password</label>
						<input id="signPassword" name="signPassword" type="password" autocomplete = "off" placeholder="<@messages key="index.sign.in.password.hint" />" class="form-control">
					</div>
					<div class="form-group"><button class="btn btn-primary btn-block"><@messages key="common.op.sign.in" /></button></div>
					<p>
						<a href="${app}/userIndex/retrivePwd"><@messages key="index.sign.in.forget" /></a><#t>
						&nbsp;&nbsp;|&nbsp;&nbsp;<#t>
						<a href="${app}/userIndex/regNow"><@messages key="index.sign.in.signup" /></a><#t>
					</p>
				</div>
			</form>
			</#if>
		</div>
	</div>
	<!-- /banner & signin -->
	<!-- notice -->
	<div class="notice clearfix">
		<div class="u-container">
			<div class="bullhorn pull-left"><i class="fa fa-bullhorn"></i></div>
			<ul id="notice" class="notices pull-left" data-speed="5000">
			<#if notices??>
			  <#list notices as l> 
				<li data-id="${(l.id)!}"><span>${(l.updateTime)!}</span>&nbsp;<a href="${app}/notice/${articleCategoryId}/${(l.id)!}">${(l.articleTitle)!}</a></li>
			  </#list> 
			  <#else>
			    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;暂无公告</li>			    
			</#if>			  
			</ul>
			<div class="pull-right"><a href="${app}/notice/${articleCategoryId}"><@messages key="index.notice.more" />&nbsp;<i class="fa fa-angle-double-right"></i></a></div>			
		</div>
	</div>
	<!-- /notice -->
	
	<div class="u-container">
		<#include "/index-shortcut.ftl" />
			
		<!-- loan -->
		<div class="slide">
			<div style="clear:both">
            <h2 class="tabtitle">普通标</h2>
			<div class="click_more">
               <a href="${app}/invest/index"  class="colorblue">查看更多 &gt;&gt;</a>
            </div>
            </div>
            <div class="data">
                <table cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr style="background:#e5f9ff;">
						    <th class="th_04">出借编号</th>
							<th class="th_04">借款用途</th>
							<th class="th_04">金额（元）</th>
							<th class="th_04">年利率</th>
							<th class="th_04">期限</th>
							<th class="th_04">进度</th>
							<th class="th_04">剩余金额（元）</th>
							<th class="th_04">操作</th>
						</tr>
					</thead>
					<#list loans as l>
					<tr data-progress="${l.progress}">
						<td class="td_04">${l.applicationNo!'-'}</td>					
						<td class="td_04"><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
                        <td class="td_04">${l.amount} <@messages key="common.unit.cny" /></td>
                        <td class="td_04">${l.rate}</td>
                        <td class="td_04">${l.period} <@messages key="common.unit.month" /></td>
                        <td class="td_04">
                            <div class="layer_box"> 
                                <div class="layer1"><@percent total="${l.amount!'0'}" remain="${l.remain!'0'}"/></div>
                                <div class="layer2" style="height:<@percent total="${l.amount!'0'}" remain="${l.remain!'0'}"/>"></div>
                            </div>
                        </td>
                        <td class="td_04">${l.remain} <@messages key="common.unit.cny" /></td>
                        <td class="td_04"><a class="i_btn1 i_bg1"  data-id="${l.id}" href="#">${l.statusName}</a> </td>
					</tr>
					</#list>
                </table>
            </div>
        </div>
		<div class="slide">
			<div style="clear:both">
            <h2 class="tabtitle">债权转让区</h2>
			<div class="click_more">
                <a href="${app}/invest/index"  class="colorblue">查看更多 &gt;&gt;</a>
            </div>
            </div>
            <div class="data">
                <table cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr style="background:#fff9f9;">
						    <th class="th_04">出借编号</th>
							<th class="th_04">债权名称</th>
							<th class="th_04">年利率</th>
							<th class="th_04">期限</th>
							<th class="th_04">进度</th>
							<th class="th_04">剩余金额（元）</th>
							<th class="th_04">操作</th>
						</tr>
					</thead>
                    <#list assignLoans as l>
					<tr data-progress="${l.progress}">
					    <td class="td_04">${l.applicationNo!'-'}</td>
						<td class="td_04"><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
                        <td class="td_04">${l.rate}</td>
                        <td class="td_04">${l.period}天</td>
                        <#if l.loanKind == '03' && l.outOfDate >
	                         <td class="td_04">
	                            <div class="layer_box">
	                                <div class="layer1">100</div>
	                                <div class="layer2" style="height:100"></div>
	                            </div>
	                        </td>
		                    <td class="td_04">0<@messages key="common.unit.cny" /></td>
		                    <td class="td_04"><a class="i_btn1 i_bg2" >${l.statusName}</a></td>
                        <#else>
	                         <td class="td_04">
	                            <div class="layer_box">
	                                <div class="layer1"><@percent total="${l.amount!'0'}" remain="${l.remain!'0'}"/></div>
	                                <div class="layer2" style="height:<@percent total="${l.amount!'0'}" remain="${l.remain!'0'}"/>"></div>
	                            </div>
	                        </td>
		                    <td class="td_04">${l.remain} <@messages key="common.unit.cny" /></td>
		                    <td class="td_04"><a class="i_btn1 i_bg1"  data-id="${l.id}" href="#">${l.statusName}</a> </td>
                        </#if>
					</tr>
					</#list>
                </table>
            </div>
        </div>
		<!-- /loan -->
	</div>
</div>
</div>
<#include "/footer.ftl" />
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
 	if (navigator.userAgent.indexOf("MSIE") > 0) { //IE
	   		if(/msie 7.0/.test(navigator.userAgent.toLowerCase()) || /msie 8.0/.test(navigator.userAgent.toLowerCase())) {
	        	alert("请使用IE8以上版本浏览器或其他浏览器");
	        	return;
	        }
	}
    //邮箱补全
	var inputSuggest = new InputSuggest({
		input: document.getElementById('email'),
		data: ['sina.com','sina.cn','163.com','qq.com','126.com','sohu.com','hotmail.com','gmail.com','139.com','189.com']
	});
	$('#email').keyup(function(){
			var obj = $("#email").val();
			if(obj.indexOf('@')!=-1){
				//TODO 补全email
			}
	}); 
	
	$('.slide .i_btn1.i_bg1').click(function() { window.location.href = '${app}/invest/info?loanid=' + $(this).data().id; });
	// 提交登录
	$('#signInForm').submit(function() {
		// 初始化
		var _elem = $(this);
		
		// 提交异步请求
		$.ajax('${app}/userIndex/signIn', {
			data: _elem.serialize(),
			type: 'post',
			dataType: 'json',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				if (data.typeName === 'success') {
					window.location.href = '${app}';
				}else if(data.typeName == 'cellphone_notauth'){   //手机未认证
				    window.location.href = '${app}/userIndex/authCellPhone?email=' + $('#email').val();
				}else if(data.typeName == 'name_notauth'){   //实名未认证											
					window.location.href = '${app}/userIndex/authName?email=' + $('#email').val();								
				}else if(data.typeName == 'bankcard_notauth'){   //银行卡未认证
					window.location.href = '${app}/userIndex/authBankCard?email=' + $('#email').val();			
				}else if (data.typeName === 'warning') {
					window.location.href = '${app}/userIndex/resendMail?email=' + $('#email').val();
				} else if (data.typeName === 'failure') {
					$('#signPassword').val('').attr('placeholder', data.firstMessage).parent().addClass('has-error');
					$('#email').parent().addClass('has-error');
				}
			}
		});
		
		// 中断事件
		return false;
	});
});
//-->
</script>
</body>
</html>
