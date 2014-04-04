<%--
  测试首页
  User: xiayouxue
  Date:2014/4/2 16:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>测试首页</title>
</head>
<body>
<c:forEach items="${urls}" var="url">
    <a href="${ctx}/test${url.value}">${url.key}</a><br/>
</c:forEach>
</body>
</html>