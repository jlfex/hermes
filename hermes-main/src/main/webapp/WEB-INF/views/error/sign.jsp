<%@page language="java" isErrorPage="true" pageEncoding="utf-8"%>
<%
response.setStatus(HttpServletResponse.SC_OK);
%>

<script type="text/javascript">
<!--
window.location.href = '<%= String.format("%s/userIndex/skipSignIn", request.getContextPath()) %>';
//-->
</script>
