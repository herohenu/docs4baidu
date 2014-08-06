<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>ZOHO编辑器管理</title>
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<body>
<fieldset>
    <legend>doc</legend>
    <form method="POST" action="https://exportwriter.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
          enctype="multipart/form-data" target="_blank">上传文件: <input type="file" name="content" size="38">
        <input type="hidden" name="filename" value="temp.doc">
        <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
        <input type="hidden" name="id" value="12345678">
        <input type="hidden" name="format" value="doc">
        <input type="hidden" name="persistence" value="false">
        <input type="submit" value="EditDoc" name="submit">
    </form>
    <form method="POST" action="https://exportwriter.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
          enctype="multipart/form-data" target="_blank">
        <a href="${basePath}/file/temp.doc?method=download">temp.doc</a>
        <input type="hidden" name="url" value="${basePath}/file/temp.doc?method=download">
        <input type="hidden" name="filename" value="temp.doc">
        <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
        <input type="hidden" name="id" value="12345678">
        <input type="hidden" name="format" value="doc">
        <input type="hidden" name="persistence" value="false">
        <input type="submit" value="EditDoc" name="submit">
    </form>
</fieldset>

<fieldset>
    <legend>xls</legend>
    <form method="POST" action="https://sheet.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
          enctype="multipart/form-data" target="_blank">
        上传文件：<input type="file" name="content" size="38">
        <input type="hidden" name="filename" value="temp.xls">
        <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
        <input type="hidden" name="id" value="12345678">
        <input type="hidden" name="format" value="xls">
        <input type="hidden" name="persistence" value="false">
        <input type="submit" value="EditDoc" name="submit">
    </form>
    <form method="POST"
          action="https://sheet.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
          enctype="multipart/form-data" target="_blank">
        <a href="${basePath}/file/temp.xls?method=download">temp.xls</a>
        <input type="hidden" name="url" value="${basePath}/file/temp.xls?method=download">
        <input type="hidden" name="filename" value="temp.xls">
        <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
        <input type="hidden" name="id" value="12345678">
        <input type="hidden" name="format" value="xls">
        <input type="hidden" name="persistence" value="false">
        <input type="submit" value="EditDoc" name="submit">
    </form>
</fieldset>

<fieldset>
    <legend>ppt</legend>
    <form method="POST"
          action="https://show.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
          enctype="multipart/form-data" target="_blank">
        上传文件： <input type="file" name="content" size="38">
        <input type="hidden" name="filename" value="temp.ppt">
        <%--<input type="hidden" name="saveurl" value="${basePath}/editor?method=save">--%>
        <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
        <input type="hidden" name="id" value="12345678">
        <input type="hidden" name="format" value="ppt">
        <input type="hidden" name="persistence" value="false">
        <input type="submit" value="EditDoc" name="submit">
    </form>
    <form method="POST"
          action="https://show.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
          enctype="multipart/form-data" target="_blank">
        <a href="${basePath}/file/temp.ppt?method=download">temp.ppt</a>
        <input type="hidden" name="filename" value="temp.ppt">
        <input type="hidden" name="url" value="${basePath}/file/temp.ppt?method=download">
        <%--<input type="hidden" name="saveurl" value="${basePath}/editor?method=save">--%>
        <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
        <input type="hidden" name="id" value="12345678">
        <input type="hidden" name="format" value="ppt">
        <input type="hidden" name="persistence" value="false">
        <input type="submit" value="EditDoc" name="submit">
    </form>
</fieldset>

<fieldset>
    <legend>ZOHO编辑器管理</legend>
    <fieldset>
        <legend>本地编辑</legend>
        <div>从本地上传文件到服务端，编辑后导出到本地</div>
        <form method="post"
              action="https://exportwriter.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
              enctype="multipart/form-data" target="_blank">
            <input type="file" name="content" size="38">
            <input type="text" name="filename" value="temp.doc">
            <%--<input type="text" name="format" value="doc">--%>
            <select onchange="this.form.filename.value='temp.'+this.value">
                <option value="doc">doc</option>
                <option value="xls">xls</option>
                <option value="ppt">ppt</option>
            </select>
            <input type="submit" name="submit" value="编辑">
            <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
            <input type="hidden" name="id" value="12345678doc">
            <input type="hidden" name="persistence" value="false">
        </form>
    </fieldset>

    <fieldset>
        <legend>云端编辑</legend>
        <div>指定一个云端地址，编辑后保存到云端</div>
        <form method="post"
              action="https://exportwriter.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output=editor"
              enctype="multipart/form-data" target="_blank">
            <input type="text" name="url" value="${downloadUrl}" size="120">
            <input type="text" name="filename" value="temp">
            <input type="text" name="format" value="doc">
            <input type="submit" name="submit" value="编辑">
            <input type="hidden" name="saveurl" value="${basePath}/editor?method=save">
            <input type="hidden" name="id" value="12345678doc">
            <input type="hidden" name="persistence" value="false">
        </form>
    </fieldset>
</fieldset>


</body>
</html>