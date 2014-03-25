<%--
  测试请求映射
  User: xiayouxue
  Date: 14-3-25
  Time: 下午2:08
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>测试请求映射</title>
    <script type="text/javascript">
        var requestMapping = {
            "list":"列表",
            "create":"新增",
            "view":"查看",
            "update":"修改",
            "delete":"删除"
        };

        window.onload = function () {
            var linkTemplate = "<a href='${ctx}/test/requestmapping/{attr}?title={value}' target='_blank'>跳转到{value}页面</a>";
            var links = [];
            for (var attr in requestMapping) {
                links.push(linkTemplate.replace("{attr}", attr).replace(/\{value\}/g, requestMapping[attr]));
            }
            document.body.innerHTML = links.join("<br/>");
        }

    </script>
</head>
<body>

</body>
</html>