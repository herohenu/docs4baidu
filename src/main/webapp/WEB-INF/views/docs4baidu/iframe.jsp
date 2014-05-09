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
    <title>百度网盘iframe测试</title>
    <style>
        iframe {
            width: 90%;
            height: 90%;
            border: none;
        }
    </style>
</head>
<body>
<fieldset>
    <legend>百度网盘iframe测试</legend>
    <iframe src="${ctx}/editor/baidu?method=open&path=${param.path}&iframe=true"></iframe>
</fieldset>

</body>
</html>