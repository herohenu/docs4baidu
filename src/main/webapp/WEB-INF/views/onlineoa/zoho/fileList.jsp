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
    <title>zoho在线办公功能</title>
</head>
<body>

<fieldset>
    <legend>本地编辑</legend>
    <div>从本地上传文件到服务端，编辑后导出到本地</div>
    <form method="post"
          action="https://xiexie.baihui.com/remotedoc.im?apikey=b55679812fe378dc4c288a47dbced340&output=editor"
          enctype="multipart/form-data" target="_blank">
        <input type="file" name="content" size="38">
        <input type="text" name="filename">
        <input type="text" name="format" value="doc">
        <input type="submit" name="submit" value="编辑">
        <input type="hidden" name="saveurl" value="http://ceshi.baihui.com/docs4baidu/onlineoa/zoho/file/save">
        <input type="hidden" name="id" value="12345678doc">
        <input type="hidden" name="persistence" value="false">
    </form>
</fieldset>

<fieldset>
    <legend>云端编辑</legend>
    <div>指定一个云端地址，编辑后保存到云端</div>
    <form method="post"
          action="https://xiexie.baihui.com/remotedoc.im?apikey=b55679812fe378dc4c288a47dbced340&output=editor"
          enctype="multipart/form-data" target="_blank">
        <input type="text" name="url" value="${basePath}/onlineoa/zoho/file/?method=download" style="width: 40em">
        <input type="text" name="filename">
        <input type="text" name="format" value="doc">
        <input type="submit" name="submit" value="编辑"> <span>内网|外网，本地（无法测试）|测试服务器</span>
        <input type="hidden" name="saveurl" value="${basePath}/onlineoa/zoho/file?method=save">
        <input type="hidden" name="id" value="12345678doc">
        <input type="hidden" name="persistence" value="false">
    </form>
</fieldset>

<fieldset>
    <legend>文件上传</legend>
    <form method="POST" action="${ctx}/onlineoa/zoho/file?method=upload" enctype="multipart/form-data">
        <input type="file" name="file"><input type="submit" value="上传">
    </form>
</fieldset>
<fieldset>
    <legend>文件保存</legend>
    <form method="POST" action="${ctx}/onlineoa/zoho/file?method=save" enctype="multipart/form-data" target="_blank">
        <input type="file" name="content"><input type="submit" value="保存">
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
                    <a href="${ctx}/onlineoa/zoho/file/${file.name}?method=delete">删除</a>
                    <a href="${ctx}/onlineoa/zoho/file/${file.name}?method=download">下载</a>
                    <a href="${ctx}/onlineoa/zoho/file/${file.name}?method=edit" target="_blank">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</fieldset>
</body>
</html>