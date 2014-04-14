<%--
  编辑器测试页面
  User: xiayouxue
  Date:2014/4/9 10:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>编辑器首页</title>
</head>
<body>
<fieldset>
    <legend>编辑器首页</legend>
    <c:forEach items="${links}" var="link">
        <li><a href="${link.value}" target="_blank">${link.key}</a></li>
    </c:forEach>
</fieldset>

</body>
</html>