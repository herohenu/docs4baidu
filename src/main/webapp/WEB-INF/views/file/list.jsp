<%--
  zoho在线办公
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
    <title>文件管理</title>
    <meta charset="UTF-8">
</head>
<body>



<fieldset>
    <legend>文件上传</legend>
    <form method="post" action="${ctx}/file?method=upload" enctype="multipart/form-data">
        <input type="file" name="file"><input type="submit" value="上传">
    </form>
</fieldset>

<fieldset>
    <legend>文件列表</legend>
    <table>
        <tr>
            <th>名称</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${files}" var="link">
            <tr>
                <td>${link.name}</td>
                <td>
                    <a href="${ctx}/file/${link.name}?method=download">下载</a>
                    <a href="${ctx}/file/${link.name}?method=edit">编辑</a>
                    <a href="${ctx}/file/${link.name}?method=delete">删除</a><%--//TODO 中文乱码--%>
                </td>
            </tr>
        </c:forEach>
    </table>
</fieldset>
</body>
</html>