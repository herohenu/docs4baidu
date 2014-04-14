<%--
  首页
  User: xiayouxue
  Date:2014/4/9 11:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>首页</title>
</head>
<body>
<fieldset>
    <legend>首页</legend>
    <ul>
        <c:forEach items="${files}" var="link">
            <li><a href="${ctx}${link.value}" target="_blank">${link.key}</a></li>
        </c:forEach>
    </ul>
</fieldset>
</body>
</html>