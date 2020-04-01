<%--
  Created by IntelliJ IDEA.
  User: YQF
  Date: 2019/9/28
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="loginHtml">
    <head>
        <meta charset="utf-8">
        <title>登录--汽车出租系统</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${yby}/static/favicon.ico">
        <link rel="stylesheet" href="${yby}/static/layui/css/layui.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/css/public.css" media="all"/>
    </head>
    <body class="loginBody">
        <form class="layui-form" id="loginFrm" method="post" action="${yby}/login/login">  <%-- /login/login --%>
            <div class="login_face">
                <img src="${yby}/static/images/face.jpg" class="userAvatar">
            </div>
            <div class="layui-form-item input-item">
                <label for="loginname">用户名</label>
                <input type="text" placeholder="请输入用户名" autocomplete="off" name="loginname" id="loginname" class="layui-input" lay-verify="required">
            </div>
            <div class="layui-form-item input-item">
                <label for="pwd">密码</label>
                <input type="password" placeholder="请输入密码" autocomplete="off" name="pwd" id="pwd" class="layui-input" lay-verify="required">
            </div>
            <div class="layui-form-item input-item" id="imgCode">
                <label for="code">验证码</label>
                <input type="text" placeholder="请输入验证码" autocomplete="off" name="code" id="code" lay-verify="required" class="layui-input">
                <%--   onclick="this.src=this.src+'?' 获取当前src拼接重新来一遍 --%>
                <img src="${yby}/login/getCode" onclick="this.src=this.src+'?'">
            </div>
            <div class="layui-form-item input-item" style="left: 0" id="rememberMe">
                <div class="">
                    <input type="checkbox" name="rememberMe"  value="1" title="七天免登陆">
                </div>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
            </div>
            <div class="layui-form-item layui-row" style="text-align: center;color: red;">
                <%-- 错误提示 --%>
                ${error}
                <%--<a href="javascript:;" class="seraph icon-qq layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
                <a href="javascript:;" class="seraph icon-wechat layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
                <a href="javascript:;" class="seraph icon-sina layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>--%>
            </div>
        </form>
        <script type="text/javascript" src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${yby}/static/js/cache.js"></script>
        <script type="text/javascript">
            layui.use(['form', 'layer', 'jquery'], function () {
                var form = layui.form,
                    layer = parent.layer === undefined ? layui.layer : top.layer
                $ = layui.jquery;

                $(".loginBody .seraph").click(function(){
                    layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
                        time:5000
                    });
                })

                //登录按钮
                form.on("submit(login)",function(data){
                    $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
                    setTimeout(function(){
                        $("#loginFrm").submit();
                    },1000);
                    return false;
                })

              /*  //登录按钮
                form.on("submit(login)", function (data) {
                    $(this).text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
                    setTimeout(function () {
                        $.post("${yby}/toLogin", $("#loginFrm").serialize(), function (data) {
                              /!*!/!* 转换为Json数据 *!/!*!/
                data = $.parseJSON(data);
                if (data.success) {
                    // /!* 跳转到首页 *!/
                    window.location.href = "${yby}/login/toIndex";
                } else {
                    alert(data.msg);
                }
            });
            }, 1000);
            return false;
            })*/



                //表单输入效果
                $(".loginBody .input-item").click(function (e) {
                    e.stopPropagation();
                    $(this).addClass("layui-input-focus").find(".layui-input").focus();
                })
                $(".loginBody .layui-form-item .layui-input").focus(function () {
                    $(this).parent().addClass("layui-input-focus");
                })
                $(".loginBody .layui-form-item .layui-input").blur(function () {
                    $(this).parent().removeClass("layui-input-focus");
                    if ($(this).val() != '') {
                        $(this).parent().addClass("layui-input-active");
                    } else {
                        $(this).parent().removeClass("layui-input-active");
                    }
                })
            })

        </script>
    </body>
</html>
