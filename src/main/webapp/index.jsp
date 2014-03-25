<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
  
<body>
<form method="POST" action="file_save.do" 
	enctype="multipart/form-data" target="_self">上传文件: <input type="file" name="content" size="38"> 
<input type="hidden" name="filename" value="mydocument.doc"> 
<input type="hidden" name="saveurl" value="http://test.baihui.com/docs4baidu/file_save.do"> 
<input type="hidden" name="id" value="12345678"> 
<input type="hidden" name="format" value="doc"> 
<input type="hidden" name="persistence" value="false"> 
<input type="submit" value="EditDoc" name="submit"> 
</form>
</body>
</html>
