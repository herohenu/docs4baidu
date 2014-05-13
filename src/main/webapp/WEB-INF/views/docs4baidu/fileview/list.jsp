<%--
  文件访问信息列表页
  User: xiayouxue
  Date:2014/5/12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>文件访问信息列表页</title>
</head>
<body>
<table border="1" cellspacing="0" style="border-collapse: collapse">
    <thead>
    <tr>
        <th>文件路径</th>
        <th>文件大小</th>
        <th>编辑器类型</th>
        <th>百度ACCESSTOKEN</th>
        <th>访问时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${fileviews}" var="item">
        <tr>
            <td>${item.path}</td>
            <td>${item.size}</td>
            <td>${item.type}</td>
            <td>${item.accesstoken}</td>
            <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" var="createTime"/>
            <td>${createTime}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>