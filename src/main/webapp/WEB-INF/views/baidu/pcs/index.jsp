<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  个人云存储接口调试
  User: xiayouxue
  Date:2014/4/2 14:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人云存储接口调试</title>
</head>
<body>
<fieldset>
    <legend>个人云存储接口调试</legend>
    <ul>
        <c:forEach items="${urls}" var="url">
            <li><a href="${url}">${url}</a></li>
        </c:forEach>
    </ul>
</fieldset>
</body>
</html>