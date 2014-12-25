<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@messages key="app.title" /></title>
<link rel="shortcut icon" type="image/png" href="${app.img}/favicon.png">
<link rel="stylesheet" href="${app.css}/bootstrap.css">
<link rel="stylesheet" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" href="${app.css}/font-awesome.css">
<link rel="stylesheet" href="${app.css}/sign.css">
</head>
<body>

<div class="header"></div>
<div class="body">
	<div class="form">
		<form id="signInForm" method="post" action="${app}/j_spring_security_check">
			<div class="form-group">
				<label for="account" class="sr-only">email</label>
				<input id="account" name="account" type="text" autofocus="autofocus" placeholder="<@messages key="form.sign.in.account.hint" />" class="form-control">
			</div>
			<div class="form-group">
				<label for="password" class="sr-only">password</label>
				<input id="password" name="password" type="password" placeholder="<@messages key="form.sign.in.password.hint" />" class="form-control">
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-block"><@messages key="common.op.sign.in" /></button>
			</div>
		</form>
	</div>
</div>
<div class="footer">
	<span><@messages key="app.copyright" /></span>
</div>

<body>
</html>
