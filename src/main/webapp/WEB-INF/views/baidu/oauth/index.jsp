<%@ page import="com.baihui.baidu.oauth.Constant" %>
<%--
  百度开放授权接口调试
  User: xiayouxue
  Date:2014/4/2 14:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>百度开放授权接口调试</title>
</head>
<body>
<fieldset>
    <legend>百度开放授权接口调试</legend>
    <p>code:<%=Constant.VS_BAIDU_OAUTH_AUTHCODE%>${BAIDU_OAUTH_AUTHCODE}</p>

    <p>token:<%=Constant.VS_BAIDU_OAUTH_TOKEN%>${BAIDU_OAUTH_TOKEN}</p>
</fieldset>
</body>
</html>