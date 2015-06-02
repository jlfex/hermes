<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/approve.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>

<body class="index">
<div class="_container">
<!-- function nav start-->
<#include "/header.ftl" />
<div class="sub_main">
	<div class="account_center">
		<div class="account_nav_left">
			<div class="title"></div>
			<div class="left_nav">

				<!--信息管理-->
				<div class="information">
					<h2>信息管理</h2>
					<ul>
						<li class="liactivebg"><a href="${app}/account/getUserInfo" class="a_010">个人信息</a></li>
						<li><a href="${app}/auth/approve" class="a_02">认证中心</a></li>
					</ul>
				</div>
				<div class="navline"></div>
				<div class="information">
					<h2>理财信息</h2>
					<ul>
						<li><a href="accountMyLoan.html" class="a_03">我的借款</a></li>
						<li><a href="accountMyInvest.html" class="a_04">我的理财</a></li>
					</ul>
				</div>
				<div class="navline"></div>

				<div class="information">
					<h2>资金管理</h2>
					<ul>
						<li><a href="accountList.html" class="a_05">资金明细</a></li>
						<li><a href="accountFilled.html" class="a_06">账户充值</a></li>
						<li><a href="accountMoney.html" class="a_07">账户提现</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="account_content_right">
			<div class="account_right">
				<div class="account_right_part01 clearfix">
					<div class="part01_left">
						<div class="part01_left_line clearfix">
							<#if avatar?exists>
								<img  src="${avatar.image!''}" class="avatar">
							<#else>
								<img class="headimg" src="${app.theme}/public/other/images/icon1/acdount_head_img02.png"/>
							</#if>
							<div class="peopleinfo">
								<p class="num">${email}</p>
								<p><a class="modifyps" href="${app}/account/showModify">修改密码</a></p>
								<p class="clicklink">
									<#if userPro.authEmail=='10'>
										<a href="javascript:value(0);" class="hover"><img src="${app.theme}/public/other/images/icon1/info_iconemail.png"/></a>
									<#else>
										<a href="javascript:value(0);" ><img src="${app.theme}/public/other/images/icon1/info_iconemail.png"/></a>
									</#if>
									<#if userPro.authCellphone=='10'>
										<a href="javascript:value(0);" class="hover"><img src="${app.theme}/public/other/images/icon1/info_iconephone01.png"/></a>
									<#else>
										<a href="javascript:value(0);"><img src="${app.theme}/public/other/images/icon1/info_iconephone01.png"/></a>
									</#if>
									<#if userPro.authName=='10'>
										<a href="javascript:value(0);" class="hover"><img src="${app.theme}/public/other/images/icon1/info_iconepeople01.png"/></a>
									<#else>
										<a href="javascript:value(0);"><img src="${app.theme}/public/other/images/icon1/info_iconepeople01.png"/></a>
									</#if>
								</p>	
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="part01_right">
						<div class="part01_data">
							<p class="data01">可用余额<span class="data">${cashBalance}</span>元</p>
							<p class="data02">账户总额 <span class="lightyellow">${allBalance} 元</span>&nbsp;&nbsp;|&nbsp;&nbsp;冻结总额 <span class="lightyellow">${freezeBalance} 元</span></p>
						</div>
					</div>
				</div>
				<!-- tab  -->
				<div id="tab2" class="account_right_part02">
					<ul id="tabAjax" class="all_information clearfix m_tab_t">
						<li class="active">基本信息</li>
						<li>工作信息</li>
						<li>房产信息</li>
						<li>车辆信息</li>
						<li>联系人信息</li>
						<li class="lastnone" >图片信息</li>
					</ul>
					<div class="m_tab_c ad_border clearfix ad_height">
						<div style="display: block;">
							<div class="poscenter" id="poscenter01">
								<table id="baseinfor" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="td_left">*昵称</td><td  class="td_right"><#if userBasic.account?exists>${userBasic.account}</#if></td>
									</tr>
									<tr>
										<td class="td_left">*真实姓名</td><td class="td_right">${userBasic.realName}</td>
									</tr>
									<tr>
										<td class="td_left">*证件类型</td>
										<td class="td_right">
												<#list idTypeMap?keys as key> 
													<#if userBasic.idType?exists&&userBasic.idType==key>
														${idTypeMap[key]}
													</#if>
									            </#list>
										</td>
									</tr>
									<tr>
										<td class="td_left">*证件号码</td><td class="td_right"><#if userBasic.idNumber?exists>${userBasic.idNumber}</#if></td>
									</tr>
									<tr>
										<td class="td_left">*手机号码</td><td class="td_right"><#if userBasic.cellphone?exists>${userBasic.cellphone}</#if></td>
									</tr>
									<tr>
										<td class="td_left">*性别</td>
										<td class="td_right">
											<#list genterMap?keys as key> 
												<#if userBasic.gender?exists&&userBasic.gender==key>
													${genterMap[key]}
												</#if>
									        </#list>
										</td>
									</tr>
									<tr>
										<td class="td_left">*年龄</td><td class="td_right"><#if userBasic.age?exists>${userBasic.age}</#if></td>
									</tr>
									<tr>
										<td class="td_left">*婚姻状况</td>
										<td class="td_right">
											<#list marriedMap?keys as key> 
												<#if userBasic.married?exists&&userBasic.married==key>
															  ${marriedMap[key]}
												</#if>
									        </#list>
										</td>
									</tr>
									<tr>
										<td class="td_left">*居住地址</td>
										<td class="td_right">
											<#list areas as area> 
											     <#if userBasic.province?exists&&userBasic.province==area.id>${area.name} </#if>
											</#list>
											<#list areaCitys as areaCity> 
											     <#if userBasic.city?exists&&userBasic.city==areaCity.id>${areaCity.name}市 </#if>
											</#list>
											<#list areaCoutrys as areaCoutry> 
											     <#if userBasic.county?exists&&userBasic.county==areaCoutry.id>${areaCoutry.name}</#if>
											</#list>
										<#if userBasic.address?exists>${userBasic.address}</#if>
										</td>
									</tr>
									<tr>
										<td class="td_left">最高学历</td>
										<td class="td_right">
											<#list eduMap?keys as key>
												<#if userBasic.degree?exists&&userBasic.degree==key>
													${eduMap[key]}
												</#if>
											</#list>
										</td>
									</tr>
									<tr>
										<td class="td_left">毕业院校</td><td class="td_right"><#if userBasic.school?exists>${userBasic.school}</#if></td>
									</tr>
									<tr>
										<td class="td_left">毕业年份</td><td class="td_right"><#if userBasic.year?exists>${userBasic.year}</#if></td>
									</tr>
									<tr>
										<td class="td_left">&nbsp;</td><td class="td_right"><a href="#" class="m_btn1 m_bg1" id="edit_bt">修改信息</a></td>
									</tr>
								</table>
							</div>

							<!--可编辑部分-->

							<div class="poscenter commonForm"  id="poscenter02">
								<form id="basicForm" name="basicForm">
									<table id="baseinfor" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td class="td_left">*昵称</td><td  class="td_right"><input  id="school" name="school" <#if userBasic.account?exists>value="${userBasic.account}"</#if>  class="info_edit" type="text"></td>
										</tr>
										<tr>
											<td class="td_left">*真实姓名</td>
											<td class="td_right">
												<#if userPro.authName=='10'>
														${userBasic.realName}
														<input  name="realName" type="hidden" value="${userBasic.realName}" />
												<#else>
														<input id="realName" name="realName" type="text" value="${userBasic.realName}" class="info_edit mv_realname"/>
														 <span class="mv_msg"></span>
												</#if>
											</td>
										</tr>
										<tr>
											<td class="td_left">*证件类型</td>
											<td class="td_right">
												<#if userPro.authName=='10'>
												 	<#list idTypeMap?keys as key> 
														<#if userBasic.idType?exists&&userBasic.idType==key>
															${idTypeMap[key]}
															<input  name="idType" type="hidden" value="${userBasic.idType}" />
														</#if>
									           		 </#list>
									           	<#else>
									           		<select id="idType" name="idType" class="info_edit_choose">
														<#list idTypeMap?keys as key> 
											                 <option value="${key}" <#if userBasic.idType?exists&&userBasic.idType==key>selected</#if> >${idTypeMap[key]}</option> 
											            </#list>
													</select>
												</#if>
											</td>
										</tr>
										<tr>
											<td class="td_left">*证件号码</td>
											<td class="td_right">
												<#if userPro.authName=='10'>
												 	${userBasic.idNumber}
												 	<input  name="idNumber" value="${userBasic.idNumber}" type="hidden">
									           	<#else>
									           		<input  id="idNumber" name="idNumber" <#if userBasic.idNumber?exists>value="${userBasic.idNumber}"</#if> class="info_edit" type="text">
												</#if>
											</td>
										</tr>
										<tr>
											<td class="td_left">*手机号码</td>
											<td class="td_right">
												<#if userPro.authName=='10'>
												 	${userBasic.cellphone}
												 	<input  id="cellphone" name="cellphone" value="${userBasic.cellphone}" type="hidden">
									           	<#else>
									           		<input  id="cellphone" name="cellphone" <#if userBasic.cellphone?exists>value="${userBasic.cellphone}"</#if> class="info_edit mv_mobile" type="text">
									           		 <span class="mv_msg"></span>
												</#if>
											</td>
										</tr>
										<tr>
											<td class="td_left">*性别</td>
											<td class="td_right">
												<select id="gender" name="gender" class="info_edit_choose">
													 <#list genterMap?keys as key> 
										                 <option value="${key}" <#if userBasic.gender?exists&&userBasic.gender==key> selected</#if>>${genterMap[key]}</option> 
										               </#list>
												</select>
											</td>
										</tr>
										<tr>
											<td class="td_left">*年龄</td><td class="td_right"><input  id="age" name="age" value=""  class="info_edit" type="text"></td>
										</tr>
										<tr>
											<td class="td_left">*婚姻状况</td>
											<td class="td_right">
												<select id="married" name="married" class="info_edit_choose">
													<#list marriedMap?keys as key> 
										                 <option value="${key}" <#if userBasic.married?exists&&userBasic.married==key> selected</#if>>${marriedMap[key]}</option> 
										             </#list>
												</select>
											</td>
										</tr>
										<tr>
											<td class="td_left">*居住地址</td>
											<td class="td_right">
												<select id="province" name="province" class="info_edit_city">
													<#list areas as area> 
										                 <option value="${area.id}" <#if userBasic.province?exists&&userBasic.province==area.id> selected</#if>>${area.name}</option> 
										             </#list>
												</select> 省
												<select id="city" name="city" class="info_edit_city">
													<#list areaCitys as areaCity> 
										                 <option value="${areaCity.id}" <#if userBasic.city?exists&&userBasic.city==areaCity.id> selected</#if>>${areaCity.name}</option> 
										             </#list>
												</select> 市
												<select id="county" name="county" class="info_edit_city">
													<#list areaCoutrys as areaCoutry> 
										                 <option value="${areaCoutry.id}" <#if userBasic.city?exists&&userBasic.city==areaCoutry.id> selected</#if>>${areaCoutry.name}</option> 
										             </#list>
												</select> 区
												<input id="address" name="address" type="text"  class="info_edit_street"> 街道
											</td>
										</tr>
										<tr>
											<td class="td_left">最高学历</td>
											<td class="td_right">
												<select id="degree" name="degree" class="info_edit_choose">
													<#list eduMap?keys as key>
															<option value="${key}"<#if userBasic.degree?exists&&userBasic.degree==key> selected</#if> >${eduMap[key]}</option> 
													</#list>
												</select>
											</td>
										</tr>
										<tr>
											<td class="td_left">毕业院校</td>
											<td class="td_right"><input  id="school" name="school" <#if userBasic.school?exists>value="${userBasic.school}"</#if>  class="info_edit" type="text"></td>
										</tr>
										<tr>
											<td class="td_left">毕业年份</td>
											<td class="td_right"><input  id="year" name="year" <#if userBasic.year?exists>value="${userBasic.year}"</#if>  class="info_edit" type="text"></td>
										</tr>
										<tr>
											<td class="td_left">&nbsp;</td><td class="td_right"><a href="#" id="save_bt01" class="q_btn1 m_bg1 mv_submit">保存</a>&nbsp;&nbsp;<a href="#" id="cancel_bt01" class="q_btn1 m_bg2">取消</a></td>
										</tr>
									</table>
								</form>
							</div>
						</div>
						<div style="display: none;">
						</div>
						<div style="display: none;">
						</div>
						<div style="display: none;">
						</div>
						<div style="display: none;">
						</div>
						<div style="display: none;">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>






<!-- foot start-->
<div class="push"><!-- not put anything here --></div>
</div>
<#include "/footer.ftl" />
<script type="text/javascript">
<!--
jQuery(function($) {
	$("#save_bt01").click(function(){
			 $.ajax({
				data: $("#basicForm").serialize(),
		        url: "${app}/account/saveBasic",
		        type: "POST",
		        dataType: 'json',
		        timeout: 10000,
		        success: function(data) {
		            if(data.type=="FAILURE"){
							alert(data.firstMessage);
					}else{
					  window.location.href="${app}/account/getUserInfo";
					}
		        }
		    });
				$("#poscenter02").hide();
				$("#poscenter01").show();
		});
			$("#cancel_bt01").click(function(){
				$("#poscenter02").hide();
				$("#poscenter01").show();
				});
		// 某个 tab 如果需要ajax请求内容，请写给 class= 'm_tab_t' 的标签中写id然后调用写ajax，如下例：
	     $('#tabAjax').find('li').click(function(){
	         var index = $(this).index();
	        var content = $(this).parent().next('.m_tab_c').children().eq(index);
	        if(index==1){
	        	$.ajax({
		             url: '${app}/account/getUserJob',
		           	 success:function(data) {
		               content.html(data) // 内容装入div中
		             }
	         	})
	        }else if(index==2){
	        	$.ajax({
		             url: '${app}/account/getUserHouse',
		           	 success:function(data) {
		               content.html(data) // 内容装入div中
		             }
	         	})
	       	 }else if(index==3){
	        	$.ajax({
		             url: '${app}/account/getUserCar',
		           	 success:function(data) {
		               content.html(data) // 内容装入div中
		             }
	         	})
	       	 }else if(index==4){
	        	$.ajax({
		             url: '${app}/account/getUserContacter',
		           	 success:function(data) {
		               content.html(data) // 内容装入div中
		             }
	         	})
	       	 }else if(index==5){
	        	$.ajax({
		             url: '${app}/account/getUserPicture',
		           	 success:function(data) {
		               content.html(data) // 内容装入div中
		             }
	         	})
	       	 }
	         
	     });
});
	
//-->
</script>

</body>
</html>
