<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="post" action="${editor.serverUrl}?apikey=${editor.apikey}&output=editor" enctype="multipart/form-data">
    <input type="hidden" name="url" value="${editor.downloadUrl}">
    <input type="hidden" name="filename" value="${editor.fileName}">
    <input type="hidden" name="filepath" value="${editor.filePath}">
    <input type="hidden" name="saveurl" value="${editor.saveUrl}">
    <input type="hidden" name="id" value="${editor.id}">
    <input type="hidden" name="format" value="${editor.format}">
    <input type="hidden" name="persistence" value="${editor.persistence}">
</form>
<script>
    document.forms[0].submit();
</script>