<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>出借与借款协议</title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">

<style type="text/css">
	.account_center{ font-size: 16px;}
	.account_center h2.title{ text-align: center; font-size: 24px; font-weight: 600; color: #323131;}
	.account_center p.time{ float: right;}
	.account_center p.time span.red{ color: #F00; font-size: 14px;}
	.xy_block{ margin: 20px 0;}
	h2.ct_title{font-size: 18px; font-weight: 600;text-align: left; color: #323131;}
	.xy_tab{ margin: 10px 0;}
	.xy_tab table { border-collapse: collapse;}
	.xy_tab table tr th,.xy_tab table tr td{ height: 25px; line-height: 25px; text-align:left; font-weight: 500; border: 1px solid #d8d8d8; padding: 2px 10px;}
	.xy_tab table tr td.txt_right{ text-align: right;}
	.xy_tab table tr td.txt_center{ text-align: center;}
	.xy_tab table tr th.txt_center{ text-align: center;}
	.cont_main p{ margin: 10px 0; line-height: 30px;}
	.cont_main p.lt2em{ text-indent: 2em;}
</style>
</head>
<body>
<div class="sub_main">
	<div class="account_center">
		<div class="xy_block">
			<h2 class="title">债权/收益权转让及回购协议</h2>
			<div class="clearfix"></div>
		</div>

		<div class="xy_block cont_main">
			<h2 class="ct_title"><strong>甲方（出让人）：（债权/收益权）</strong></h2>
			<p>用户名：   ${creditorName!''}</p>
			<p>身份证号/证件号：${creditorIDCard!''}</p>
			<h2 class="ct_title"><strong>受让人（“乙方”）及其基本信息及合同要素:</strong></h2>
			<br>
			<div class="xy_tab">
				<table width="100%" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th width="10%">真实姓名</th>
							<th width="10%">证件类型</th>
							<th width="15%">证件号码</th>
							<th width="25%">受让债权/收益权金额（本金）</th>
							<th width="20%">债权/收益权转让价款</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${investorName!''}</td>
							<td>${investorCertiType!''}</td>
							<td>${investorIDCard!''}</td>
							<td>${principalAmount!''}</td>
							<td>${assignAmount!''}</td>
						</tr>
					</tbody>
				</table>
				<br>
				<h2 class="ct_title"><strong>见证人（“丙方”）: ${platformName!''} </strong></h2>
				<p>公司地址：${companyAddr!''}</p>

			</div>
			<p>鉴于，</p>
			<p>1.	甲方拟转让相应债权/收益权（以下简称“债/收益权”）并同意在特定情况下回购债权/收益权；乙方愿意以自有合法资金受让债权/收益权。</p>
			<p>2.	${operator!''} (上海公司)是一家在中国上海市合法成立并有效存续的有限责任公司，拥有P2P网站（网址为：${platformNetAddr!''}）</p>
			<p>3.	现甲、乙双方经过友好协商，在平等、自愿的基础上就债权/收益权转让事宜达成如下协议，以兹共守：</p>
			<p>一、	债权/收益权</p>
			<p>1.1	甲方同意以本协议文首“受让人（“乙方”）及其基本信息及合同要素”载明的债权/收益权转让价款金额为转让对价向乙方转让相应债权/收益权；；乙方同意以本协议文首“受让人（“乙方”）及其基本信息及合同要素”载明的债权/收益权转让价款金额为转让对价该等债权/收益权。</p>
			<p>1.2	甲方应当承诺本协议项下债权/收益权上不存在质押、其他转让意向等任何可能使得债权/收益权存在瑕疵、负担或可能被任何第三方追索的情形。</p>
			<p>1.3	债权/收益权整体情况如下：</p>
			<div class="xy_tab">
				<table width="100%" >
					
					<tbody>
						<tr>
							<td>债权/收益权</td>
							<td>￥ 人民币${principalAmount!''}元整</td>
						</tr>
						<tr>
							<td>债权/收益权期限</td>
							<td> ${period!''}</td>
						</tr>
						<tr>
							<td>债权/收益权年化利率</td>
							<td>${rate!0}</td>
						</tr>
						<tr>
							<td>起息日</td>
							<td>${raiseDate!''}</td>
						</tr>
					</tbody>
				</table>
				<p>1.4	乙方各自受让的债权/收益权数额以本协议文首“受让人（“乙方”）及其基本信息及合同要素”载明的内容为准。</p>
			</div>
			<p>二、	债权/收益权转让</p>
			<p>2.1	乙方应当于本协议签订后的个工作日内将本协议第一条约定之受让债权/收益权的债权/收益权转让价款全额支付至甲方委托丙方或丙方指定的第三方在第三方支付机构或者监管银行开立的监管账户:</p>
			<p>户名：${accountName!''}</p>	
			<p>账号：${bankCardNo!''}</p>	
			<p>开户行：${bankName!''}</p>
			<p>2.2	除非甲方另行通知，甲方全额收到乙方支付的债权/收益权转让价款的次日（“初始转让日”）起，乙方受让取得债权/收益权； </p>
			<p>2.3	协议生效后，协议双方以本协议为依据形成真实、合法、有效的债权/收益权转让行为。 </p>	

			<p>三、	债权/收益权回购</p>
			<p>3.1	受限于本协议第3.2条所述条件：</p>
			<p class="lt4em">（1）	乙方按本协议约定有效受让取得债权/收益权后，甲方应在${deadTime!''}（“持有到期日”）回购乙方届时所持有的债权/收益权；</p>
			<p class="lt4em">（2）	甲方按本条约定回购债权/收益权，回购价款应按如下方式计算：</p>

				<div class="xy_tab">
				<table width="100%" >
					<tbody>
						<tr>
							<td>应收日期</td>
							<td>收益权金额</td>
							<td>应收利息</td>
							<td>应收总额</td>
						</tr>
						<#if repayPlanDetailList??>
						<#list repayPlanDetailList as l>
						<tr>
							<td>${l.repayPlanTime?string('yyyy-MM-dd')}</td>
							<td>${l.repayPrincipal!''}</td>
							<td>${l.repayInterest!''}</td>
							<td>${l.repayAllmount!''}</td>
						</tr>
						</#list>
						</#if>
						<tr>
							<td colspan="3" style="text-align:right" >总计</td>
							<td>${totalAmount!''}</td>
						</tr>
						
					</tbody>
				</table>

			</div>
			<p>3.2	甲方按3.1条约定回购乙方届时所持有的债权/收益权的前提包括：</p>
			<p class="lt4em">（1）届时债权/收益权上不存在质押、乙方没有其他任何转让该债权/收益权行为等任何可能使得债权/收益权存在瑕疵、负担或可能被任何第三方追索的情形；
			</p>
			<p class="lt4em">（2）乙方根据甲方的要求配合签署债权/收益权回购（转让）事宜的相关必要文件，并根据甲方的要求通过甲方确认的途径向借款人发出债权/收益权转让给甲方的相关通知。
			</p>

			<p>四、	协议的生效及效力</p>
			<p>本协议自各方通过书面或电子协议签署本协议之日起生效。</p>
			
			<p>五、	协议的接触及终止</p>
			<p>本协议一经生效即不可解除或终止。但如乙方未在本协议约定的时间内向甲方全额支付债权/收益权转让价款的，本协议自动终止。</p>

			<p>六、	保密</p>
			<p>协议方应对在本协议签订过程中所获悉的其他方信息，包括但不限于身份、财务及商业信息等，承担保密义务。非经书面同意，任何一方均不得将上述信息向其他第三方进行披露。</p>

			<p>七、	违约责任</p>
			<p>本协议任意一方未按协议约定履行义务或违反约定的，除本协议另有约定外，还应当承担由此给守约方造成的一切损失，包括但不限于守约方为实现权利支付的律师费、诉讼费等。</p>

			<p>八、	争议解决</p>
			<p>协议方在履行本协议中发生任何争议应当友好协商解决，争议发生15个工作日后仍无法协商解决的，任何一方有权将纠纷提交一方住所地有管辖权的人民法院诉讼解决。</p>

			<p>九、	其他</p>
			<p>9.1	任何在协议履行过程中产生的争议均不影响本协议其他条款的履行及效力。 </p>
			<p>9.2	本协议各条款的标题仅为参照方便而设，并不限制或从其他角度影响本协议条款的含义和诠释。 </p>	
			<p>9.3	协议各方同意本协议可通过电子版本生成，并受其约束。</p>	


			<p>甲方：${creditorName!''}</p>
			<br>
			<p>乙方：${investorName!''}</p>
			<br>
			<p>见证人：${platformName!''} </p>
			<br>
		</div>
		 
	</div>
</div>
</body>
</html>