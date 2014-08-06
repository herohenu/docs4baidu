<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
    <title>403 - 没有访问权限</title>
</head>

<body>
<div class="error_img"><img src="${ctx}/static/images/img403.png" alt=""/></div>
</body>
</html>