<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>角色管理</title>
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
                    <label class="layui-form-label">角色名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="rolename" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">角色备注:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="roledesc" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否可用:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="available" value="1" title="可用">
                        <input type="radio" name="available" value="0" title="不可用">
                    </div>
                </div>
                <div class="layui-inline">
                    <button type="button" class="layui-btn layui-btn-normal layui-btn-sm layui-icon layui-icon-search" id="doSearch">
                        查询
                    </button>
                    <button type="reset" class="layui-btn layui-btn-warm layui-btn-sm layui-icon layui-icon-refresh">
                        重置
                    </button>
                </div>
            </div>
            </div>
        </form>
        <!-- 搜索条件结束 -->

        <!-- 数据表格开始 -->
        <table class="layui-hide" id="RoleTable" lay-filter="RoleTable"></table>
        <!-- 头部工具 -->
        <div style="display: none;" id="RoleToolBar">
            <button type="button" class="layui-btn layui-btn-sm" lay-event="add">增加</button>
            <button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deleteBatchDelete">批量删除
            </button>
        </div>
        <!-- 操作 -->
        <div id="RoleBar" style="display: none;" lay-filter="RoleBar">
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="selectRoleMenuManager">分配菜单</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </div>
        <!-- 数据表格结束 -->

        <!-- 添加和修改的弹出层开始 -->
        <div style="display: none;padding: 20px" id="saveOrUpdateDiv">
            <%-- 表单数据 --%>
            <form class="layui-form" lay-filter="dataFrm" id="dataFrm">
                <div class="layui-form-item">
                    <label class="layui-form-label">角色名称:</label>
                    <div class="layui-input-block">
                        <%--  --%>
                        <input type="hidden" name="roleid">
                        <input type="text" name="rolename" placeholder="请输入角色名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">角色备注:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="roledesc" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">是否可用:</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="available" value="1" checked="checked" title="可用">
                            <input type="radio" name="available" value="0" title="不可用">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
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

        <%-- 角色分配菜单的弹出层开始 --%>
        <div id="selctRoleMenu" style="display: none;">
            <ul id="menuTree" class="dtree" data-id="0"></ul>
        </div>
        <%-- 角色分配菜单的弹出层结束 --%>

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
                    elem: '#RoleTable' //渲染的目标对象
                    ,
                    url: '${yby}/Role/loadAllRole' //数据接口
                    ,
                    title: '角色数据表' //数据导出来的标题
                    ,
                    toolbar: "#RoleToolBar" //表格的工具条
                    ,
                    height: 'full-130',
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
                            field: 'roleid',
                            title: 'ID',
                            align: 'center'
                        }, {
                            field: 'rolename',
                            title: '角色名称',
                            align: 'center'
                        }, {
                            field: 'roledesc',
                            title: '角色备注',
                            align: 'center'
                        }
                            , {
                            field: 'available',
                            title: '是否可用',
                            align: 'center',
                            templet: function (d) {
                                return d.available == '1' ? '<font color=blue>可用</font>' : '<font color=red>不可用</font>';
                            }
                        }, {
                            fixed: 'right',
                            title: '操作',
                            toolbar: '#RoleBar',
                            width: 230,
                            align: 'center'
                        }
                        ]
                    ],
                    /* 渲染后的回调 */
                    done: function(data, curr, count) {
                        //不是第一页时，如果当前返回的数据为0那么就返回上一页
                        if(data.data.length == 0 && curr != 1) {
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
                        url: "${yby}/Role/loadAllRole?" + params,
                        page: {
                            curr: 1
                        }
                    })
                });

                //监听头部工具栏事件
                table.on("toolbar(RoleTable)", function (obj) {
                    switch (obj.event) {
                        case 'add':
                            openAddRole();
                            break;
                        case 'deleteBatchDelete':
                            deleteBatchDelete();
                            break;
                    }
                    ;
                });

                //监听行工具事件
                table.on('tool(RoleTable)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    if (layEvent === 'del') { //删除
                        layer.confirm('真的删除【' + data.rolename + '】这个角色么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/Role/deleteRole", {
                                roleid: data.roleid
                            }, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });
                    } else if (layEvent === 'edit') { //编辑
                        //编辑，打开修改界面
                        openUpdateRole(data);
                    } else if (layEvent === 'selectRoleMenuManager') {
                        openSelectRoleMenuManager(data);
                    }
                });


                /* 定义地址 */
                var url;
                /* 添加页面索引 用来关闭窗口 */
                var mainIndex;

                //打开添加页面
                function openAddRole() {
                    mainIndex = layer.open({
                        type: 1,
                        title: '添加角色',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '300px'],
                        maxmin: true,
                        success: function (index) {
                            //清空表单数据
                            $("#dataFrm")[0].reset();
                            // 添加地址
                            url = "${yby}/Role/addRole";
                        }
                    });
                    // 弹出最大化
                    // layer.full(mainIndex);
                }

                //打开修改页面
                function openUpdateRole(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '修改角色',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '440px'],
                        success: function (index) {
                            // 给表单赋值
                            form.val("dataFrm", data);
                            // 修改地址
                            url = "${yby}/Role/updateRole";
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
                    var checkStatus = table.checkStatus('RoleTable'); //RoleTable 即为基础参数 id 对应的值
                    if (checkStatus.data.length > 0) {
                        console.log(checkStatus.data) //获取选中行的数据
                        console.log(JSON.stringify(checkStatus.data)) //获取选中行的数据
                        console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
                        console.log(checkStatus.isAll) //表格是否全选
                        var ids = [];
                        for (var i = 0; i < checkStatus.data.length; i++) {
                            console.log(checkStatus.data[i].roleid);
                            ids[i] = checkStatus.data[i].roleid;
                        }
                        console.log(ids);
                        // 请求后台地址
                        $.ajax({
                            url: "${yby}/Role/deleteBatchDelete",
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
                    } else {
                        layer.msg('请选择一行数据,在进行删除!');
                    }
                }

                //打开分配菜单弹出层
                function openSelectRoleMenuManager(data) {
                    // 树实例
                    var menuTree;
                    mainIndex = layer.open({
                        type: 1,
                        title: '分配【' + data.rolename + '】的菜单',
                        content: $("#selctRoleMenu"), // 打开窗口
                        btnAlign: 'c',// 居中
                        btn: ['<div class="layui-icon layui-icon-release">保存</div>', '<div class="layui-icon layui-icon-close">关闭</div>'],
                        yes: function (index, layero) {  // 单击回调
                            // 获取选中的菜单
                            var nodes = dtree.getCheckbarNodesParam("menuTree");
                            // 获取角色id
                            var roleid = data.roleid;
                            var params = "roleid=" + roleid;
                            // 遍历选中的菜单
                            $.each(nodes, function (i, item) {
                                // 追加菜单id
                                params += "&ids=" + item.nodeId;
                            });
                            // 保存角色和菜单的关系
                            $.post("${yby}/Role/saveRoleMenu", params, function (data) {
                                layer.msg(data.msg);
                                // 关闭弹出层
                                layer.close(mainIndex);
                            })

                        }, area: ['500px', '400px'],
                        success: function (index) {
                            // 初始化树
                            menuTree = dtree.render({
                                elem: "#menuTree",
                                dataStyle: "layuiStyle", //使用layui风格的数据格式
                                response: {message: "msg", statusCode: 0}, //修改response中返回数据的定义
                                dataFormat: "list", //配置data的风格为list
                                checkbar: true,  // 开启复选框
                                checkbarType: "all", // 默认就是all，其他的值为： no-all  p-casc   self  only\
                                checkbarData: "choose", // 记录选中(默认)  父节点也要选中
                                url: "${yby}/Role/initRoleMenuTreeJson?roleid=" + data.roleid   // 根据角色id 查询该角色用有的菜单
                            });
                        }
                    });

                }


            });
        </script>
    </body>

</html>