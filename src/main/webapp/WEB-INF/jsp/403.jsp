<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>403 - 操作权限不足</title>
    <%--<%@include file="/WEB-INF/views/include/head.jsp" %>--%>
    <script type="text/javascript" src="${fns:getConfig('forwarding.url')}/build/js/jquery.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="page-header"><h1>操作权限不足.</h1></div>
    <div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>
    <script>try{top.$.jBox.closeTip();}catch(e){}</script>
</div>
</body>
</html>