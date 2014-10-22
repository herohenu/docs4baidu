<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="post" action="${editor.serverUrl}?apikey=${editor.apikey}&output=editor" enctype="multipart/form-data">
    <c:choose>
        <c:when test="${editor.downloadUrl==null}">
            <input type="file" name="content" size="38">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="url" value="${editor.downloadUrl}">
        </c:otherwise>
    </c:choose>
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