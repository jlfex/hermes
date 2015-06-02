<%@page import="com.jlfex.hermes.common.support.spring.SpringWebApp"%>
<%@page language="java" isErrorPage="true" pageEncoding="utf-8"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.jlfex.hermes.common.App"%>
<%@page import="com.jlfex.hermes.common.web.WebApp"%>
<%@page import="com.jlfex.hermes.common.Result"%>
<%@page import="com.jlfex.hermes.common.exception.ServiceException"%>
<%@page import="com.jlfex.hermes.common.utils.Strings"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
// 重置响应状态
response.setStatus(HttpServletResponse.SC_OK);
App.set(new SpringWebApp(request, response));

// 判断请求类型并输出内容
if (Strings.equals("json", request.getParameter("hermes_request_type"))) {
	Result<Exception> result = new Result<Exception>(Result.Type.FAILURE, App.message("exception.not.found"));
	out.write(JSON.toJSONString(result));
} else {
	if (!Strings.equals("html", request.getParameter("hermes_request_type"))) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><%=App.message("exception.title", App.config("app.title"))%></title>
<link rel="shortcut icon" type="image/png" href="<%=App.current(WebApp.class).getImg()%>/favicon.png">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<style type="text/css">
.jy_bg1{background:#ffffff;}
.fl{ float: left;}
.ml_10{ margin-left: 10px;}
.mt_60{ margin-top: 60px;}
.mt_20{ margin-top: 20px;}
</style>
</head>
<body >
<div class="_container">
<%
	}
%>
<!-- header -->
<div class="header">
	<div class="top">
		<div class="u-container">
			<c:if test="${not empty cuser}">
			    您好，
			<a href="${app}/account/index">${cuser.name}</a>
			&nbsp;&nbsp;.&nbsp;&nbsp;
			<a href="${app}/userIndex/signOut">退出</a>
			</c:if>
			<c:if test="${empty cuser}">
			<a href="${app}/userIndex/skipSignIn">登录</a>
			&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="${app}/userIndex/regNow">注册</a>
			</c:if>
		</div>
	</div>
	<div class="nav">
		<div class="u-container">
			<div class="logo" ><a href="${app}/index"><img  style="vertical-align: middle;"  src="${appLogo}"><span class="logo2">${appOperationNickname}</span></a></div>
			<ul id="homeNav" class="nav-list">
				<li class="home"><a href="${app}/index">首页</a></li>
				<li class="invest"><a href="${app}/invest/index">我要理财</a></li>
				<li class="loan"><a href="${app}/loan/display">我要借款</a></li>
				<li class="account"><a href="${app}/account/index">账户中心</a></li>
				<li class="help"><a href="${app}/help-center">使用帮助</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- middle -->
<div class="m_con m_fp m_fp3">
	<div class="m_fp_box jy_bg1 clearfix">	
		<div class="fl"><img src="${app.theme}/public/images/error/400.png"></div>
        <div class="fl ml_10 mt_60">
            <p>很抱歉，您的请求中有语法错误</p>
            <p class="mt_20"><a href="${app}/index" class="q_btn1 m_bg1">返回首页</a></p>
        </div>
	</div>
</div>


<%
	if (!Strings.equals("html", request.getParameter("hermes_request_type"))) {
%>
<div class="push"><!-- not put anything here --></div>
 </div>
 <!-- foot start-->
<div class="footer _footer">
    <div class="footcenter">
        <div class="companyinfo clearfix">
            <h4>公司介绍</h4>
            <ul>
                <c:forEach var="item" items="${companyIntroductions}" varStatus="status">   
					<li><a href="${app}/help-center/${item.id}">${item.name}</a></li>
				</c:forEach>                
            </ul>
        </div>
        <div class="links clearfix">
            <h4>友情链接</h4>
            <ul>
                <c:forEach var="item" items="${friendlinkData}" varStatus="status">                    
               	   <li><a href="http://${item.link}" target="_blank">${item.name}</a></li>
                </c:forEach>            
            </ul>
        </div>
        <div class="telephone clearfix">
            <h4>客服电话</h4>
			<ul>
				<li><p>${siteServiceTel}</p></li>
				<li><span class="worktime">工作时间：${siteServiceTime}</span></li>
				<li><span class="worktime">版权所有${appCopyright}</span></li>				
			</ul>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
</body>
</html>
<%
	}
}
%>
