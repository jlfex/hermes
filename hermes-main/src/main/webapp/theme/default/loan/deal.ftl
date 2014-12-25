<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>借款协议</title>

<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<script type="text/javascript" src="${app.js}/jquery.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$('.j_zebra').find('tr:odd').addClass('j_even');
	});
</script>
</head>

<body>

<!-- middle start-->
<div class="m_con m_jkfa">
	<h2 class="m">借款协议</h2>
	<div class="men_info clearfix"><p class="men_sp">甲方(理财人):</p><p class="men_code">编号：	<#if loan?exists>${loan.loanNo!''}</#if></p></div>
	<div class="section_j">
		<table class="j_zebra">
			<tr>
				<th>用户名</th>
				<th>姓名</th>
				<th>借出金额(元)</th>
				<th>借款期限(个月)</th>
			</tr>
			<#if invests?exists>
				 <#list invests as i>  
					<tr>
						<th>${i.account!''}</th>
						<th>${i.realName!''}</th>
						<th>${i.amount!''}</th>
						<th>${i.period!''}</th>
					</tr>
				</#list>
			</#if>
			<tr class="b_line">
				<td>总计</td>
				<td colspan="3">${sumInvestAmount!''}(元)</td>
			</tr>
		</table>
		<p class="w_notice">注：因计算中存在四舍五入，最后一期应收本息与之前略有不同</p>
	</div>
	<div class="dl_ct">
		<ul>
			<li class="li_title">乙方（借款人）：</li>
			<li>真实姓名：<#if loanUserProperties?exists>${loanUserProperties.realName!''}</#if></li>
			<li>身份证号：<#if loanUserProperties?exists>${loanUserProperties.idNumber!''}</#if></li>
			<li>用户名：<#if user?exists>${user.account!''}</#if></li>
		</ul>

		<ul>
			<li class="li_title">丙方（见证人）：</li>
			<li>公司：${companyName}</li>
			<li>联系方式：${companyAddress}</li>
		</ul>

		<ul>
			<li class="li_title">鉴于：</li>
			<li>1、丙方是一家在××市××区合法成立并有效存续的有限责任公司，拥有www. ××××.com 网站 （以下简称“该网站”）的经营权，提供信用咨询,　  为交易提供信息服务；</li>
			<li>2、乙方已在该网站注册，并承诺其提供给丙方的信息是完全真实的；</li>
			<li>3、甲方承诺对本协议涉及的借款具有完全的支配能力，是其自有闲散资金，为其合法所得；并承 诺其提供给丙方的信息是完全真实的；</li>
			<li>4、乙方有借款需求，甲方亦同意借款，双方有意成立借贷关系；</li>
			<li>5、各方经协商一致，签订如下协议，共同遵照履行： </li>
		</ul>
		<ul>
			<li class="li_title">第一条 借款基本信息</li>
		</ul>
		<div class="section_k">
			<table class="j_zebra">
				<tr>
					<th>借款用途</th>
					<th colspan="3">${purpose!''}</th>
				</tr>
				<tr>
					<td>借款本金数额</td>
					<td colspan="3"><#if loan?exists>${loan.amount}</#if> 元（各出借人借款本金数额详见本协议文首表格）</td>		
				</tr>
				<tr>
					<td>借款年利率  </td>
					<td colspan="3"><#if loan?exists>${loan.rate*100}%</#if></td>
				</tr>
				<tr>
					<td>借款期限</td>
					<td colspan="3"><#if loan?exists>${loan.period}</#if>个月</td>
				</tr>
				<tr>
					<td>月偿还本息数额</td>
					<td colspan="3"><#if loanRepay?exists>${loanRepay.amount}</#if> 元</td>
				</tr>
				<tr>
					<td>还款分期月数</td>
					<td colspan="3"><#if loan?exists>${loan.period}</#if></td>
				</tr>
				<tr>
					<td>放款日期</td>
					<td colspan="3"><#if loantime?exists>${loantime}</#if></td>
				</tr>
				<tr class="b_line">
					<td>还款日</td>
					<td colspan="3"><#if loanRepay?exists>${loanRepay.planDatetime}</#if></td>
				</tr>
				
			</table>
		</div>
		<p>协议正文</p>
		<p class="dl_da">${now}</p>		
	</div>
</div>

</body>
</html>
