<%--
  zoho在线办公功能
  1.文件列表
    读取子文件{文件类型的}
  2.文件上传
    上传目录：${ctx}/upload
  3.文件下载
    文件名称
  4.文件编辑
    文件名称
  User: xiayouxue
  Date: 14-3-25
  Time: 下午6:08
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>zoho在线办公功能</title>
</head>
<body>

<fieldset>
    <legend>文件上传</legend>
    <form method="POST" action="${ctx}/onlineoa/zoho/upload" enctype="multipart/form-data">
        <input type="file" name="filePath"><input type="submit" value="upload">
    </form>
</fieldset>

<fieldset>
    <legend>文件列表</legend>
    <table>
        <tr>
            <th>名称</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${files}" var="file">
            <tr>
                <td>${file.name}</td>
                <td>
                    <a href="${ctx}/onlineoa/zoho/download?file.name=${file.name}">下载</a>
                    <a href="${ctx}/onlineoa/zoho/edit?file.name=${file.name}">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</fieldset>
</body>
</html>