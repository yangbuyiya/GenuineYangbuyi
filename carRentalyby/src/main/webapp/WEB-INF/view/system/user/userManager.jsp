<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sihro" uri="http://shiro.apache.org/tags" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>用户管理</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta http-equiv="Access-Control-Allow-Origin" content="*">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="format-detection" content="telephone=no">
        <%--<link rel="icon" href="favicon.ico">--%>
        <link rel="stylesheet" href="${yby}/static/layui/css/layui.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/css/public.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/layui_ext/dtree/dtree.css">
        <link rel="stylesheet" href="${yby}/static/layui_ext/dtree/font/dtreefont.css">
    </head>

    <body>

        <!-- 搜索条件开始 -->
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>查询条件</legend>
        </fieldset>
        <form action="" class="layui-form" method="post" id="searchFrm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="realname" placeholder="请输入用户名称" autocomplete="off" class="layui-input layui-input-inline">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">登录名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="loginname" placeholder="请输入登录名称" autocomplete="off" class="layui-input layui-input-inline">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户地址:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="address" autocomplete="off" placeholder="请输入用户地址" class="layui-input layui-input-inline">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户电话:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" placeholder="请输入用户电话" autocomplete="off" class="layui-input layui-input-inline">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">身份证号:</label>
                    <div class="layui-input-inline" style="padding: 5px">
                        <input type="text" name="identity" autocomplete="off" class="layui-input layui-input-inline "
                               placeholder="请输入身份证号">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">性别:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="sex" value="1" title="男">
                        <input type="radio" name="sex" value="0" title="女">
                    </div>
                </div>
                <div class="layui-form-item" style="text-align: center;padding-top: 10px;">
                    <button type="button" class="layui-btn layui-btn-normal layui-btn-sm layui-icon layui-icon-search" id="doSearch">
                        查询
                    </button>
                    <button type="reset" class="layui-btn layui-btn-warm layui-btn-sm layui-icon layui-icon-refresh">
                        重置
                    </button>
                </div>
            </div>
        </form>
        <!-- 搜索条件结束 -->

        <!-- 数据表格开始 -->
        <table class="layui-hide" id="UserTable" lay-filter="UserTable"></table>
        <!-- 头部工具 -->
        <div style="display: none;" id="UserToolBar">
            <shiro:hasPermission name="user:addUser">
                <button type="button" class="layui-btn layui-btn-sm" lay-event="add">增加</button>
            </shiro:hasPermission>
            <sihro:hasPermission name="user:deleteBatchDelete">
                <button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deleteBatchDelete">
                    批量删除
                </button>
            </sihro:hasPermission>
        </div>
        <!-- 操作 -->
        <div id="UserBar" style="display: none;" lay-filter="UserBar">
            <shiro:hasPermission name="user:updateUser">
                <a class="layui-btn  layui-btn-xs " lay-event="edit">编辑</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="user:addRole">
                <a class="layui-btn layui-btn-warm layui-btn-xs " lay-event="selectUserMenuManager">分配角色</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="user:resetUserPwd">
                <a class="layui-btn layui-btn-normal layui-btn-xs " lay-event="resetUserPwd">重置密码</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="user:deleteUser">
                <a class="layui-btn layui-btn-danger layui-btn-xs " lay-event="del">删除</a>
            </shiro:hasPermission>
        </div>

        <!-- 数据表格结束 -->

        <!-- 添加和修改的弹出层开始 -->
        <div style="display: none;padding: 20px" id="saveOrUpdateDiv">
            <%-- 表单数据 --%>
            <form class="layui-form" lay-filter="dataFrm" id="dataFrm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">用户姓名:</label>
                        <div class="layui-input-inline">
                            <%--// 修改用的值--%>
                            <input type="hidden" name="userid"/>

                            <input type="text" name="realname" lay-verify="required" placeholder="请输入用户姓名" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">用户地址:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="address" placeholder="请输入用户地址" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">登录名称:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="loginname" lay-verify="required" placeholder="请输入登录名称" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label">用户电话:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入用户电话" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">用户职位:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="position" placeholder="请输入用户职位" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">身份证号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="identity" placeholder="请输入身份证号" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label">用户性别:</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="sex" value="1" title="男">
                            <input type="radio" name="sex" value="0" title="女">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">是否可用:</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="available" value="1" title="可用">
                            <input type="radio" name="available" value="0" title="不可用">
                        </div>
                    </div>
                    <div class="layui-input-block" style="text-align: center;padding-right: 120px">
                        <button type="button" class="layui-btn layui-btn-normal layui-btn-md layui-icon layui-icon-release layui-btn-radius" lay-filter="doSubmit" lay-submit="">
                            提交
                        </button>
                        <button type="reset" class="layui-btn layui-btn-warm layui-btn-md layui-icon layui-icon-refresh layui-btn-radius">
                            重置
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- 添加和修改的弹出层结束 -->

        <%-- 用户分配角色的弹出层开始 --%>
        <div id="selectUserRole" style="display: none;padding: 20px">
            <%-- 整一个树进来 --%>
            <table class="layui-hide" id="roleTable" lay-filter="roleTable"></table>
        </div>
        <%-- 用户分配角色的弹出层结束 --%>


        <script src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript">
            var tableIns;
            /* 引入dtree模块 */
            layui.extend({
                dtree: '${yby}/static/layui_ext/dist/dtree'
            }).use(['jquery', 'layer', 'form', 'table', 'dtree'], function () {
                var $ = layui.jquery;
                var layer = layui.layer;
                var form = layui.form;
                var table = layui.table;
                var dtree = layui.dtree;
                //渲染数据表格
                tableIns = table.render({
                    elem: '#UserTable' //渲染的目标对象
                    ,
                    url: '${yby}/User/loadAllUser' //数据接口
                    ,
                    title: '用户数据表' //数据导出来的标题
                    ,
                    toolbar: "#UserToolBar" //表格的工具条
                    ,
                    height: 'full-250',
                    cellMinWidth: 100 //设置列的最小默认宽度
                    ,
                    page: true //是否启用分页
                    ,
                    cols: [
                        [ //列表数据
                            {
                                type: 'checkbox',
                                fixed: 'left'
                            }, {
                            field: 'userid',
                            title: 'ID',
                            align: 'center'
                        }, {
                            field: 'loginname',
                            title: '登录用户名',
                            align: 'center'
                        }, {
                            field: 'realname',
                            title: '用户姓名',
                            align: 'center'
                        }, {
                            field: 'identity',
                            title: '身份证号',
                            align: 'center'
                        }, {
                            field: 'sex',
                            title: '性别',
                            align: 'center',
                            templet: function (d) {
                                return d.sex == '1' ? '<font >男</font>' : '<font >女</font>';
                            }
                        }, {
                            field: 'address',
                            title: '省份',
                            align: 'center'
                        }, {
                            field: 'phone',
                            title: '手机号码',
                            align: 'center'
                        }, {
                            field: 'position',
                            title: '用户职务',
                            align: 'center'
                        }, {
                            field: 'available',
                            title: '是否可用',
                            align: 'center',
                            templet: function (d) {
                                return d.available == '1' ? '<font color=blue>可用</font>' : '<font color=red>不可用</font>';
                            }
                        }, {
                            fixed: 'right',
                            title: '操作',
                            width: 255,
                            toolbar: '#UserBar',
                            align: 'center'
                        }
                        ]
                    ],
                    /* 渲染后的回调 */
                    done: function (data, curr, count) {
                        //不是第一页时，如果当前返回的数据为0那么就返回上一页
                        if (data.data.length == 0 && curr != 1) {
                            tableIns.reload({
                                page: {
                                    curr: curr - 1
                                }
                            })
                        }
                    }, text: {
                        none: 'Trouble提醒您：暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
                    }
                });

                //模糊查询
                $("#doSearch").click(function () {
                    // 获取表单序列化
                    var params = $("#searchFrm").serialize();
                    //alert(params);
                    // 重新加载数据  并且指定请求 携带 当前页数 1
                    tableIns.reload({
                        url: "${yby}/User/loadAllUser?" + params,
                        page: {
                            curr: 1
                        }
                    })
                });

                //监听头部工具栏事件
                table.on("toolbar(UserTable)", function (obj) {
                    switch (obj.event) {
                        case 'add':
                            openAddUser();
                            break;
                        case 'deleteBatchDelete':
                            deleteBatchDelete();
                            break;
                    }
                    ;
                });

                //监听行工具事件
                layui.table.on('tool(UserTable)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    if (layEvent === 'del') { //删除
                        layer.confirm('真的删除【' + data.realname + '】这个用户么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/User/deleteUser", {
                                userid: data.userid
                            }, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });
                    } else if (layEvent === 'edit') { //编辑
                        //编辑，打开修改界面
                        openUpdateUser(data);
                    } else if (layEvent === 'resetUserPwd') { // 重置密码
                        layer.confirm('是否要重置【' + data.realname + '】这个用户的密码么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/User/resetUserPwd",{loginname:data.loginname}, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });

                    } else if (layEvent === 'selectUserMenuManager') { // 分配角色
                        openSelectUserRoleManager(data);
                    }
                });


                /* 定义地址 */
                var url;
                /* 添加页面索引 用来关闭窗口 */
                var mainIndex;

                //打开添加页面
                function openAddUser() {
                    mainIndex = layer.open({
                        type: 1,
                        title: '添加用户',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '300px'],
                        maxmin: true,
                        success: function (index) {
                            //清空表单数据
                            $("#dataFrm")[0].reset();
                            // 添加地址
                            url = "${yby}/User/addUser";
                        }
                    });
                    // 弹出最大化
                    // layer.full(mainIndex);
                }

                //打开修改页面
                function openUpdateUser(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '修改用户',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '300px'],
                        success: function (index) {
                            // 给表单赋值
                            form.val("dataFrm", data);
                            // 修改地址
                            url = "${yby}/User/updateUser";
                        }
                    });
                }

                //保存表单
                form.on("submit(doSubmit)", function (obj) {
                    //序列化表单数据
                    var params = $("#dataFrm").serialize();
                    $.post(url, params, function (obj) {
                        layer.msg(obj.msg);
                        //关闭弹出层
                        layer.close(mainIndex);
                        //刷新数据 表格
                        tableIns.reload();
                    })
                });

                /* 批量删除 */
                function deleteBatchDelete() {
                    var checkStatus = table.checkStatus('UserTable'); //UserTable 即为基础参数 id 对应的值
                    console.log(checkStatus.data) //获取选中行的数据
                    console.log(JSON.stringify(checkStatus.data)) //获取选中行的数据
                    console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
                    console.log(checkStatus.isAll) //表格是否全选
                    if (checkStatus.data.length > 0) {

                        var arr = checkStatus.data;
                        var realname = "";
                        for (var i = 0; i < arr.length; i++) {
                            realname += arr[i].realname + ",";
                        }

                        layer.confirm('是否要删除【' + realname + '】这些用户么？', function (index) {
                            var ids = [];
                            for (var i = 0; i < checkStatus.data.length; i++) {
                                console.log(checkStatus.data[i].userid);
                                ids[i] = checkStatus.data[i].userid;
                            }
                            console.log(ids);
                            // 请求后台地址
                            $.ajax({
                                url: "${yby}/User/deleteBatchDelete",
                                type: "post",
                                dataType: "json",
                                traditional: true,//用传统方式序列化数据
                                data: {"ids": ids},
                                success: function (result) {
                                    layer.msg(result.msg);
                                    //刷新数据 表格
                                    tableIns.reload();
                                }
                            });
                        });

                    } else {
                        layer.msg('请选择一行数据,在进行删除!');
                    }
                }

                //打开分配角色 弹出层
                function openSelectUserRoleManager(data) {
                    // 树实例
                    var menuTree;
                    mainIndex = layer.open({
                        type: 1,
                        title: '分配【' + data.realname + '】的角色',
                        content: $("#selectUserRole"), // 打开窗口
                        btnAlign: 'c',// 居中
                        btn: ['<div class="layui-icon layui-icon-release">确认分配角色</div>', '<div class="layui-icon layui-icon-close">关闭</div>'],
                        yes: function (index, layero) {  // 保存单击回调
                            var checkStatus = table.checkStatus('roleTable'); //roleTable 即为基础参数 id 对应的值

                            var roleData = checkStatus.data;
                            // 当前用户
                            var params = "userid=" + data.userid;
                            // 拼接当前用户的  角色id
                            $.each(roleData, function (i, item) {
                                params += "&ids=" + item.roleid;
                            });

                            // 发送 保存角色 请求到客户端
                            $.post("${yby}/User/saveUserRole", params, function (obj) {
                                layer.msg(obj.msg);
                                //关闭弹出层
                                layer.close(mainIndex);
                            })


                        }, area: ['800px', '400px'],
                        success: function (index) {
                            //渲染数据表格
                            var roleTable = table.render({
                                elem: '#roleTable', //渲染的目标对象
                                url: '${yby}/User/initUserRole?userid=' + data.userid, //数据接口
                                title: '角色列表', //数据导出来的标题,
                                cellMinWidth: 100, //设置列的最小默认宽度
                                cols: [[   //列表数据
                                    {type: 'checkbox', fixed: 'left'}
                                    , {field: 'roleid', title: 'ID', align: 'center'}
                                    , {field: 'rolename', title: '角色名称', align: 'center'}
                                    , {field: 'roledesc', title: '角色备注', align: 'center'}
                                ]],
                                text: {
                                    none: 'Trouble提醒您：暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
                                }
                            });
                        }
                    });
                }

            });
        </script>
    </body>

</html>