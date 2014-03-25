<%--
  文件列表
  1.上传
    上传目录：应用/upload
  2.文件列表
    读取上传目录下文件
    仅显示文件，忽略文件夹
  3.下载
    文件名称
  4.编辑
    文件名称


  User: xiayouxue
  Date: 14-3-25
  Time: 下午5:23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>文件列表</title>
</head>
<body>
.
<fieldset>
    <legend>上传文件</legend>
    <form method="POST" action="${ctx}/onlineoa/zoho/upload" enctype="multipart/form-data" target="_blank">
        <input type="file" name="file"><input type="submit" value="upload">
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
                    <a href="${ctx}/upload/${file.name}">下载</a>
                    <a href="${ctx}/upload/${file.name}">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</fieldset>

</body>
</html>