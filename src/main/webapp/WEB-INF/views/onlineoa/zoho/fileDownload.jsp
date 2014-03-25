<%--
  文件列表
  User: xiayouxue
  Date: 14-3-25
  Time: 下午5:23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件选择</title>
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

</body>
</html>