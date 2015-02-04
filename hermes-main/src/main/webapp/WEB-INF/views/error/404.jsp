<%@page import="com.jlfex.hermes.common.support.spring.SpringWebApp"%>
<%@page language="java" isErrorPage="true" pageEncoding="utf-8"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.jlfex.hermes.common.App"%>
<%@page import="com.jlfex.hermes.common.web.WebApp"%>
<%@page import="com.jlfex.hermes.common.Result"%>
<%@page import="com.jlfex.hermes.common.Logger"%>
<%@page import="com.jlfex.hermes.common.exception.ServiceException"%>
<%@page import="com.jlfex.hermes.common.utils.Strings"%>
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
<link rel="stylesheet" href="<%=App.current(WebApp.class).getCss()%>/style.css">
</head>
<body>
<%
	}
%>

<div class="alert alert-warning">
	<p><strong><%=App.message("common.result.warning")%></strong></p>
	<p><%=App.message("exception.not.found")%></p>
</div>

<%
	if (!Strings.equals("html", request.getParameter("hermes_request_type"))) {
%>
<script type="text/javascript" charset="utf-8" src="<%=App.current(WebApp.class).getJs()%>/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=App.current(WebApp.class).getJs()%>/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=App.current(WebApp.class).getJs()%>/hermes.js"></script>

</body>
</html>
<%
	}
}
%>
