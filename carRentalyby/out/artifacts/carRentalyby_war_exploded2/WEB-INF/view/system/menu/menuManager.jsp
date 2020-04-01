<%--
  Created by IntelliJ IDEA.
  User: TeouBle
  Date: 2020/2/9
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单管理</title>
</head>
    <%--如果使用frameset 包含页面 主页面不能有body--%>
    <frameset cols="266,*" border="1">
    <frame src="${yby}/sys/toMenuLeft" name="left">
    <frame src="${yby}/sys/toMenuRight" name="right">
    </frameset>
</html>
