

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mInvestAndLoan.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript">
jQuery(function($) {
    $('#funanceProtocol').click(function(){
			openwindow("${app}/loan/funanceProtocol","",1000,800);
			
	});
	$('.deal').click(function(){
			openwindow("${app}/loan/deal/${loan.id}","",1000,800);
			
	});
	function openwindow(url,name,iWidth,iHeight)
	{
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}
	$('.confirm').click(function(){
	 	$.ajax({
				data: $("#loanDetail").serialize(),
			     url: "${app}/invest/bid",
			    type: "POST",
			    dataType: 'json',
				success: function(data){
					if(data.type=="SUCCESS"){
						var investamount =$("#investamount").val();
						var loanid =$("#loanid").val();
						window.location.href="${app}/invest/bidsuccess?investamount="+investamount+"&loanid="+loanid;
					}
					else if(data.type=="WARNING"){
						window.location.href="${app}/userIndex/skipSignIn";
					}else{
						window.location.href="${app}/invest/bidfull";
					}
				}
			});
	 });
		var remaintime =$("#remaintime").val();
		setTime();
		function setTime() 
		{
			var day=24*60*60*1000;
			var hour=60*60*1000;
			var minute=60*1000;
			var second=1000;
	    	var str=0;
	    	if(remaintime>0)
	    	{
		    	var  _day=parseInt(remaintime/day);
		    	var _hour=parseInt(remaintime%day/hour);
		    	var _minute=parseInt(remaintime%day%hour/minute);
		    	var _seconds=parseInt(remaintime%day%hour%minute/second);
		    	str= _day+'天'+_hour+'时'+_minute+'分'+_seconds+'秒';
			}
	   		$("#timeleft").html(str);
	   		remaintime=remaintime-1000;
	    	setTimeout(function(){setTime()},1000);
		}  
		$('#investamount').blur(function()
		{
			var investamount =$("#investamount").val();
			var loanid =$("#loanid").val();
			var msg =$('#investamount').siblings("span").text();
		//	console.log(msg);
			if(msg.length==1)
			{
				//console.log(investamount);
			  	htmlobj=$.ajax({
			  			url:'${app}/invest/calmaturegain',
			  			data: {"investamount":investamount,"loanid":loanid},
			  			dataType: "json",
			  			async:false});
				//console.log(htmlobj.responseText);
				$("#maturegain").html(htmlobj.responseText);
			}
			else
			{
				$("#maturegain").html('');
			}
	  });
	
});

</script>
</head>

<body>
<#include "/header.ftl" />


<!-- middle start-->

<div id="content" class="content">	
<form id="loanDetail" name="loanDetail">
<input id="investBidMultiple" name="investBidMultiple" type="hidden"  value="${investBidMultiple}" ></input>
<input id="otherrepayselect" name="otherrepayselect" type="hidden" ></input>
<input id="loanid" name="loanid" type="hidden" value="${loan.id}" ></input>	
<input id="remaintime" name="remaintime" type="hidden" value="${remaintime}" ></input>	
<div class="sub_main" id="invest">
	<div class="account_center">
		<div class="account_nav_left">
			<div class="account_center_list">
				<div class="head_part">
					<#if loanUserInfo?exists><#if loanUserInfo.lgAvatar?exists><img alt="" src="${loanUserInfo.lgAvatar!''}" class="head_img"> <#else> 	<img class="head_img" src="${app.theme}/public/other/images/icon1/acdount_head_img.png"></#if></#if>
			
				</div>
				<p class="clicklink">
				 	 <#if loanUserInfo?exists && loanUserInfo.authEmail?exists && loanUserInfo.authEmail=='10'>
						 <a href="javascript:value(0);" class="hover">
						 	<img src="${app.theme}/public/other/images/icon1/info_iconemail.png"/>
						 </a>
					 	<#else> 
						    <a href="javascript:value(0);"><img src="${app.theme}/public/other/images/icon1/info_iconemail01.png"/></a>
					 </#if>
					 
					 <#if loanUserInfo?exists && loanUserInfo.authCellphone?exists &&loanUserInfo.authCellphone=='10'>
						 <a href="javascript:value(0);" class="hover">
						 	<img src="${app.theme}/public/other/images/icon1/info_iconephone.png"/>
						 </a>
					 <#else> 
					  	 <a href="javascript:value(0);"><img src="${app.theme}/public/other/images/icon1/info_iconephone01.png"/></a>
					 </#if>
					 
					 <#if  loanUserInfo?exists && loanUserInfo.authName?exists &&loanUserInfo.authName=='10'>
						 <a href="javascript:value(0);" class="hover">
						 	<img src="${app.theme}/public/other/images/icon1/info_iconepeople.png"/>
						 </a>
					 <#else> 
					 	<a href="javascript:value(0);"><img src="${app.theme}/public/other/images/icon1/info_iconepeople01.png"/></a>
					 </#if>
                   
                </p>    
			</div>
			<div class="left_nav">
				<!--审核记录-->
				<div class="record_info">
					<h2 class="record_title">审核记录</h2>
					<div class="record_list">
						 <#list loanauths as l>  
							<p class="linkbt">
								<a class="li_left" href="javascript:value(0);">${l.label.name!''}</a>
								 <#if l.status=='01'>
									<img src="${app.theme}/public/other/images/icon1/loan_postmarkbg.png">
								</#if>
							</p>
						</#list>
					
					</div>
				</div>
			</div>
			<div class="leftbottom"></div>
		</div>
		<div class="account_content_right">
			<div class="account_right">
				<div class="my_loan_sub bgnone">
                    <div class="use_type">
                        <div class="use_type_name">
                           <#if loan.loanKind=='00'>
                              <span class="title">${purpose}</span>&nbsp;<a href="#" class="bg">${product.name}</a>
                           <#else>
                              <span class="title">${purpose}</span>&nbsp;<a href="#" class="bg">${product.name}</a>
                           </#if>
                         </div>
                        <div class="dash_line"></div>
                        <div class="use_type_code lightgray">
                            <#if loan.loanKind=='00'> 协议编号<#else>债权转让编号 </#if>
                           <span class="deal">	<a href="#" class="m_a1">：${loan.loanNo}</a></span></div>
                        <div class="clearfix"></div>   
                    </div>
                    <table class="tab_invest_gap" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                            <td class="td_height th_06"><@messages key="model.loan.amount" />：<span>${loan.amount}元</span></td>
                            <td class="th_06"><@messages key="model.loan.rate" />：<span>${loan.rate *100}%</span></td>
                            <td class="th_06"><@messages key="model.loan.period" />：<span>${loan.period}<@messages key="common.unit.month" /></span></td>
                        </tr>
                        <tr>
                            <td class="td_height th_06"><@messages key="model.repay.name" />：<span>${repay.name}</span></td>
                            <td colspan="2" class="th_06"></td>
                        </tr>
                    </table>
                </div>
                 <#if loan.status=='10'>
            	<!--投标-->
                <div class="bid_part" id="loan">
                	<table>
                		<tr><td class="td_height th_06"><@messages key="model.loan.remain" />：${loan.remain}<@messages key="common.unit.cny" /></td><td class="th_06"><@messages key="model.loan.progress" />：${loan.progress}%</td><td><@messages key="invest.remain.time" />：<span id="timeleft"></span></td></tr>
                	</table>
                	<table>
                		<tr>
                			<td  colspan="3" class="tl_tip"><@messages key="model.invest.amount" />：<input id="investamount" name="investamount" type="text" class="inputstyle mv_money50">&nbsp;&nbsp;
							<span class="mv_msg"></span></td>
                		<tr>
                		<td colspan="3" class="td_ht1"><span class="lighrgray"><@messages key="account.info.user.cash" />：</span><span class="yellow">
                			<#if (loanUserInfo.balance)??>
								${loanUserInfo.balance}
							<#else> 
								0
							</#if>
							<@messages key="common.unit.cny" /></span><a href="${app}/account/index?type=charge" class="m_btn3 m_bg1 a_middle"><@messages key="common.op.charge" /></a></td>
                		</tr>
                		<tr><td class="td_height"><@messages key="invest.yield.to.maturity" />：<span id="maturegain"></span></td><td colspan="2">&nbsp;</td></tr>
                		<tr class="mv_checked"><td colspan="3" class="td_height"><@messages key="invest.occurred.late" />：<input type="radio"name="otherrepay" value="00" />  <@messages key="invest.occurred.late.advance" />&nbsp;&nbsp;<input type="radio" name="otherrepay"  value="01" checked="true"> <@messages key="invest.occurred.late.noadvance" /><span class="mv_msg" data-msg='请选择逾期垫付方式！'></span></td></tr>
                		<tr><td colspan="3" class="td_deal">
                            <div class="commonChecked">
                            <p class="mv_checked">
                            	<input type="checkbox" id="cr"/>  <label for="cr">我已阅读并同意 <a href="#" id="funanceProtocol" class="blue">《理财协议》</a><span class="mv_msg" data-msg='勾选后方可点击确认！'></label>
                            </p>
                            </div>
                            </td>
                        </tr>
                		<tr>
                			<td colspan="3" class="td_ht1 btn_list">
	                		<span class="confirm">
								<a href="#"  class="m_btn1 bt_red a_middle mv_submit"><@messages key="invest.loan.bid" /></a>
							</span>
							</td>
						</tr>
                	</table>
                </div>
				<!-- tab  -->
				<#elseif loan.status=='11'>
				<!--满标-->
                <div class="bid_part">
                	<table>
                		<tr><td class="td_height th_06"><@messages key="model.loan.progress" />：100%</td><td class="th_06"><@messages key="invest.remain.time" />：0</td><td></td></tr>
                	</table>
                	<table>
                		<td colspan="3" class="td_ht1"><span class="lighrgray"><@messages key="account.info.user.cash" />：</span><span class="yellow">
                			<#if (loanUserInfo.balance)??>
								${loanUserInfo.balance}
							<#else> 
								0
							</#if>
							<@messages key="common.unit.cny" /></span><a href="${app}/account/index?type=charge" class="m_btn3 m_bg1 a_middle"><@messages key="common.op.charge" /></a></td>
                		</tr>
                	</table>
                	<div class="tw_condition"><img src="${app.theme}/public/other/images/icon1/bid_postmarkbg.png"/></div>
                </div>
                <#elseif loan.status=='12'>
                <!--还款中-->
                <div class="bid_part">
                	<table>
                		<tr><td class="td_height th_06"><@messages key="model.loan.progress" />：100%</td><td class="th_06"><@messages key="invest.remain.time" />：0</td><td></td></tr>
                	</table>
                	<table>
                		<td colspan="3" class="td_ht1"><span class="lighrgray"><@messages key="account.info.user.cash" />：</span><span class="yellow">
                			<#if (loanUserInfo.balance)??>
								${loanUserInfo.balance}
							<#else> 
								0
							</#if>
							<@messages key="common.unit.cny" /></span><a href="${app}/account/index?type=charge" class="m_btn3 m_bg1 a_middle"><@messages key="common.op.charge" /></a></td>
                		</tr>
                	</table>
                	<div class="tw_condition"><img src="${app.theme}/public/other/images/icon1/bid_postmarkbg01.png"/></div>
                </div>
                <#else> 
				</#if>
                
				<div id="tab3" class="account_right_part02 loan_myloan_sub">
					<ul class="all_information m_tab_t">
						<li class="active"><@messages key="invest.loanuser.detail" /></li>
						<li><@messages key="invest.record" /></li>
						<li class="lastnone" ><@messages key="model.loan.description" /></li>
					</ul>
					<div class="m_tab_c ad_border">
						<div style="display: block;">
							<div class="m_tda table_mar">
			                    <table cellpadding="0" cellspacing="0" border="0">
			                    	<tr>
			                            <td class="tdalign"><@messages key="model.basic.gender" /></td>
			                            <td class="th_00 th_06 black">${loanUserInfo.genderName!''}</td>
			                            <td class="tdalign"><@messages key="model.basic.married" /></td>
			                            <td class="th_00 black">${loanUserInfo.marriedName!''}</td>
			                            <td class="tdalign"><@messages key="model.basic.age" /></td>
			                            <td class="th_00 black">${loanUserInfo.age!''}</td>
			                        </tr>
			                       <tr>
			                            <td class="tdalign"><@messages key="model.job.annualSalary" /></td>
			                            <td class="th_00 th_06 black">	<#if (loanUserInfo.annualSalary)??>${loanUserInfo.annualSalary!''}</#if></td>
			                            <td class="tdalign"><@messages key="model.job.position" /></td>
			                            <td class="th_00 black">${loanUserInfo.position!''}</td>
			                            <td class="tdalign"><@messages key="model.basic.degree" /></td>
			                            <td class="th_00 black">${loanUserInfo.degreeName!''}</td>
			                        </tr>
			                         <tr>
			                            <td class="tdalign"><@messages key="model.job.company.scale" /></td>
			                            <td class="th_00 th_06 black">${loanUserInfo.scaleName!''}</td>
			                            <td class="tdalign"><@messages key="model.job.company.properties" /></td>
			                            <td class="th_00 black">${loanUserInfo.propertiesName!''}</td>
			                            <td class="tdalign"></td>
			                            <td class="th_00 black"></td>
			                        </tr>
			                        <tr>
			                            <td class="tdalign"><@messages key="invest.hourse.info" /></td>
			                            <td class="th_00 black">${loanUserInfo.hasHouse!''}</td>
			                            <td class="tdalign"><@messages key="invest.hourse。mortgage.info" /></td>
			                            <td colspan="3" class="th_00 black">${loanUserInfo.hasHouseMortgage!''}</td>
			                        </tr>
			                        <tr>
			                            <td class="tdalign"><@messages key="invest.car.info" /></td>
			                            <td class="th_00 black">${loanUserInfo.hasCar!''}</td>
			                            <td class="tdalign"><@messages key="invest.car.mortgage.info" /></td>
			                            <td colspan="3" class="th_00 black">${loanUserInfo.hasCarMortgage!''}</td>
			                        </tr>
			                    </table>
                			</div>
						</div>
						<div style="display: none;">
							<div class="m_tda table_mar">
			                    <table cellpadding="0" cellspacing="0" border="0">
			                        <tr><th class="th_00"><@messages key="invest.user" /></th><th><@messages key="model.invest.amount" />(<@messages key="common.unit.cny" />)</th><th>投标时间</th></tr>
			                          <#list invests as i>  
									<tr>
										<td class="th_00"><#if (i.user.account)??>${i.user.account}</#if></td>
										<td>${i.amount?string('#,##0.00')}</td>
										<td>${i.datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
									</tr>
									</#list>
			                    </table>
                			</div>	
						</div>
						<div style="display: none;">
							<p class="new_year">${loan.description!''}</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>
</form>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
</body>
</html>
