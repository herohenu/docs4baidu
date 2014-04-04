<%--
  接口调试功能
  User: xiayouxue
  Date:2014/4/2 14:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>百度接口调试</title>
</head>
<body>
<fieldset>
    <legend>百度接口调试</legend>
    <div>${error}</div>
    <a href="${codeUrl}">Authorization Code</a><br/>

    <div>
        Authorization Code->${code}<br/>
        Access Token->${token}
    </div>
    <%--<a href="${tokenUrl}">Access Token->${token}</a><br/>
    <a href="#">文件下载</a><br/>
    <a href="#">文件上传</a><br/>--%>
</fieldset>
</body>
</html>