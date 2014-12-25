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

// 初始化异常
ServiceException serviceException = null;
if (exception instanceof ServiceException) {
	serviceException = ServiceException.class.cast(exception);
} else if (exception.getCause() instanceof ServiceException) {
	serviceException = ServiceException.class.cast(exception.getCause());
} else {
	serviceException = new ServiceException("unknow exception!", exception);
}

// 打印错误日志
Logger.error(serviceException.getMessage(), serviceException);

// 判断请求类型并输出内容
if (Strings.equals("json", request.getParameter("hermes_request_type"))) {
	Result<Exception> result = new Result<Exception>(Result.Type.FAILURE, App.message(serviceException.getKey()));
	result.addMessage(App.message("exception.code", serviceException.getCode()));
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

<div class="alert alert-danger">
	<p><strong><%=App.message("common.result.failure")%></strong></p>
	<p><%=App.message(serviceException.getKey())%></p>
	<p><%=App.message("exception.code", serviceException.getCode())%></p>
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
