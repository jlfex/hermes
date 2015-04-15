<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付委托授权书</title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<script type="text/javascript" src="${app.js}/jquery.js" charset="utf-8"></script>
<style type="text/css">
	.account_center{ font-size: 16px;}
	.account_center h2.title{ text-align: center; font-size: 24px; font-weight: 600; color: #323131;}
	.account_center p.time{ float: right;}
	.account_center p.time span.red{ color: #F00; font-size: 14px;}
	.xy_block{ margin: 20px 0;}
	h2.ct_title{font-size: 18px; font-weight: 600;text-align: left; color: #323131;}
	.cont_main p{ margin: 10px 0; line-height: 28px;}
	.cont_main p.lt2em{ text-indent: 2em;}
</style>
</head>
<body>
<div class="sub_main">
	<div class="account_center">
		<div class="xy_block">
			<h2 class="title">支付委托授权书</h2>
		</div>
		<div class="xy_block cont_main">
			<p>授权人姓名：${user.realName!''}</p>
			<p>证件类型：${user.idTypeName!''}</p>
			<p>证件号码：${user.idNumber}</p>
			<p>用户名：${user.user.account!''}</p>
			<p> 授权日期：${nowDate!''}</p>
			<p>被授权人：平台运营方</p>
			<p>就授权人向平台运营方用户名项下账户充值的相关事宜向平台运营方授权如下：</p>
			<p>一、授权人授权平台运营方或平台运营方指定的第三方根据授权人的充值指令从本授权书第二条所述的授权人的银行账户通过平台运营方指定的第三方支付机构主动扣收本授权书第二条所述的等值于充值金额的款项，用于向授权人平台运营方账户充值（“充值服务”）。</p>
			<p>二、	授权人的银行账户如下：</p>
			<p class="lt2em">户名：${bankAccount.name!''}</p>
			<p class="lt2em">账号：${bankAccount.account!''}</p>
			<p class="lt2em">开户银行：${bankAccount.bank.name!''}</p>
			<p class="lt2em">省份：${province!''}</p>
			<p class="lt2em">城市：${city!''}</p>
			<p class="lt2em">开户行：${bankAccount.deposit!''}</p>
			<p>三、授权人知晓并确认，本授权书系使用授权人平台运营方用户名，以网络在线点击“充值”确认的方式向平台运营方或运营方指定的第三方发出。本授权书自授权人在平台运营方网站点击确认时生效，由平台运营方或运营方指定的第三方通过第三方支付机构从授权人的银行账户中代扣相当于充值金额的款项。授权人已经通过本授权书确认代扣款项的银行账户信息，在代扣的过程中，平台运营方或运营方指定的第三方根据本授权书提供的银行账户信息进行相关操作，无需再向授权人确认银行账户信息和密码。本授权书一经生效即不可撤销。授权人确认并承诺，平台运营方根据本授权书所采取的全部行动和措施的法律后果均由授权人承担。</p>
			<p>四、授权人知晓并同意，受授权人银行账户状态、银行、第三方支付机构及网络等原因所限，平台运营方不对充值服务的资金到账时间做任何承诺。平台运营方根据本授权书所述的授权人的授权进行相关操作，平台运营方无义务对其根据本授权书所采取的全部行动和措施的时效性和结果承担任何责任。
特此授权。
</p>
		</div>
		 
	</div>
</div>

</body>
</html>
