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
    <title>编辑器管理</title>
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>

</head>
<body>

<form method="POST"
      action="https://xiexie.baihui.com/remotedoc.im?apikey=b55679812fe378dc4c288a47dbced340&output=editor"
      enctype="multipart/form-data" target="_blank">上传文件(doc文档): <input type="file" name="content" size="38">
    <input type="hidden" name="filename" value="mydocument.doc">
    <input type="hidden" name="saveurl" value="http://ceshi.baihui.com/docs4baidu/file_save.do">
    <input type="hidden" name="id" value="12345678doc">
    <input type="hidden" name="format" value="doc">
    <input type="hidden" name="persistence" value="false">
    <input type="submit" value="EditDOC" name="submit">
</form>
<form method="POST" action="https://sheet.baihui.com/remotedoc.im?apikey=b55679812fe378dc4c288a47dbced340&output=editor"
      enctype="multipart/form-data" target="_blank">上传文件(excel文档): <input type="file" name="content" size="38">
    <input type="hidden" name="filename" value="mydocument.xlsx">
    <input type="hidden" name="saveurl" value="http://ceshi.baihui.com/docs4baidu/file_save.do">
    <input type="hidden" name="id" value="12345678slxs">
    <input type="hidden" name="format" value="xlsx">
    <input type="hidden" name="persistence" value="false">
    <input type="submit" value="EditEXCEL" name="submit">
</form>
<form method="POST"
      action="https://xiuxiu.baihui.com/remotedoc.im?apikey=b55679812fe378dc4c288a47dbced340&output=editor"
      enctype="multipart/form-data" target="_blank">上传文件(ppt文档): <input type="file" name="content" size="38">
    <input type="hidden" name="filename" value="mydocument.ppt">
    <input type="hidden" name="saveurl" value="http://ceshi.baihui.com/docs4baidu/file_save.do">
    <input type="hidden" name="id" value="123456789ppt">
    <input type="hidden" name="format" value="ppt">
    <input type="hidden" name="persistence" value="false">
    <input type="submit" value="EditPPT" name="submit">
</form>

<fieldset>
    <legend>ZOHOAPI</legend>
    <fieldset>
        <legend>本地编辑</legend>
        <div>从本地上传文件到服务端，编辑后导出到本地</div>
        <form method="post"
              action="https://exportwriter.zoho.com/remotedoc.im?apikey=27b07f3e314700b408cc13a1632eb84c&output= editor"
              enctype="multipart/form-data" target="_blank">
            <input type="file" name="content" size="38">
            <input type="text" name="filename">
            <input type="text" name="format" value="doc">
            <input type="submit" name="submit" value="编辑">
            <input type="hidden" name="saveurl" value="${basePath}/file?method=save">
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
            <input type="text" name="serverUrl" value="${basePath}/file/abc.doc?method=download" style="width: 35em">
            <input type="text" name="filename">
            <input type="text" name="format" value="doc">
            <input type="submit" name="submit" value="编辑"> <span>内网|外网，本地（无法测试）|测试服务器</span>
            <input type="hidden" name="saveurl" value="${basePath}/file?method=save">
            <input type="hidden" name="id" value="12345678doc">
            <input type="hidden" name="persistence" value="false">
        </form>
    </fieldset>
</fieldset>


<fieldset>
    <legend>百会API</legend>
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
            <input type="hidden" name="saveurl" value="${basePath}/file?method=save">
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
            <input type="text" name="serverUrl" value="${basePath}/file/abc.doc?method=download" style="width: 35em">
            <input type="text" name="filename">
            <input type="text" name="format" value="doc">
            <input type="submit" name="submit" value="编辑"> <span>内网|外网，本地（无法测试）|测试服务器</span>
            <input type="hidden" name="saveurl" value="${basePath}/file?method=save">
            <input type="hidden" name="id" value="12345678doc">
            <input type="hidden" name="persistence" value="false">
        </form>
    </fieldset>
</fieldset>

<fieldset>
    <legend>文件保存</legend>
    <form method="post" action="${ctx}/file?method=save" enctype="multipart/form-data" target="_blank">
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
        <c:forEach items="${files}" var="link">
            <tr>
                <td>${link.name}</td>
                <td>
                    <form action="${ctx}/editor?method=open" method="post" target="_blank">
                        <input type="hidden" name="method" value="open">
                        <select name="com">
                            <option value="zoho">zoho</option>
                            <option value="baihui">baihui</option>
                            <option value="baidu">baidu</option>
                        </select>
                        <select name="way">
                            <option value="local">local</option>
                            <option value="remote">remote</option>
                        </select>
                        <input type="hidden" name="name" value="${link.name}">
                        <button type="submit">编辑</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</fieldset>


</body>
</html>