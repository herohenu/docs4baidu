<%--
  百度编辑器
  User: xiayouxue
  Date:2014/3/31 10:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>百度编辑器</title>

    <%--<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">--%>
    <link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <%--<link href="${ctx}/static/styles/default.min.css" type="text/css" rel="stylesheet"/>--%>
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>

    <style>
        iframe{
            width: 100%;
            height: 50%;
            border: none;
        }

        .header{
            height: 20%;
            overflow: hidden;
        }
        .main{
            height: 80%;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row" >
        <iframe id="header" src="${ctx}/editor/baidu/header" class="header"></iframe>
    </div>
    <div class="row">
        <iframe src="${ctx}/editor/baidu?method=open&way=${param.way}&env=local&&name=${param.name}" class="main"></iframe>
    </div>
</div>
</body>
</html>