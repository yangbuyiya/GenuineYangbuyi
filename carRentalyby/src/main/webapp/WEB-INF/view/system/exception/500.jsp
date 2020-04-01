<%--
  Created by IntelliJ IDEA.
  User: TeouBle
  Date: 2020/2/14
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>500</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="format-detection" content="telephone=no">
        <link rel="stylesheet" href="${yby}/static/layui/css/layui.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/css/public.css" media="all"/>
    </head>
    <body class="childrenBody">
        <div class="noFind">
            <div class="ufo">
                <i class="seraph icon-test ufo_icon"></i>
                <%--<i class="layui-icon page_icon">&#xe638;</i>--%>
                <%--<i class="layui-icon page_icon">&#xe609;</i>--%>
            </div>
            <div class="page404">
                <%--<i class="layui-icon">&#xe664;</i>--%>
                <i class="layui-icon">
                    <img src="${yby}/static/images/404.svg" alt="">
                </i>
                <p>服务器内部资源错误，请联系管理员!!!</p>
            </div>
        </div>
        <script type="text/javascript" src="${yby}/static/layui/layui.js"></script>
    </body>
</html>