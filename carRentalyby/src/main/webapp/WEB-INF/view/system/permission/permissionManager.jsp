<%--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>权限管理</title>
</head>
    <frameset cols="200,*" border="1"  frameborder="yes">
       <frame src="/sys/toPermissionLeft" name="left">
       <frame src="/sys/toPermissionRight" name="right">
    </frameset>
</html>--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>权限管理--右边列表</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta http-equiv="Access-Control-Allow-Origin" content="*">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${yby}/favicon.ico">
        <link rel="stylesheet" href="${yby}/static/layui/css/layui.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/css/public.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/layui_ext/dtree/dtree.css" media="all"/>
        <link rel="stylesheet" href="${yby}/static/layui_ext/dtree/font/dtreefont.css" media="all"/>

    </head>
    <body class="childrenBody">
        <!-- 查询条件开始 -->
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 5px;">
            <legend>查询条件</legend>
        </fieldset>
        <form action="" method="post" id="searchFrm" lay-filter="searchFrm" class="layui-form layui-form-pane">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">权限名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="pname" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">权限编码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="presource" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <button type="button" class="layui-btn" lay-submit="" lay-filter="doSearch">
                        <span class="layui-icon layui-icon-search"></span>
                        查询
                    </button>
                    <button type="reset" class="layui-btn layui-btn-warm">
                        <span class="layui-icon layui-icon-refresh-1"></span>
                        重置
                    </button>
                </div>

            </div>
        </form>
        <!-- 查询条件结束-->


        <!-- 数据表格开始 -->
        <div>
            <table class="layui-hide" id="permissionTable" lay-filter="permissionTable"></table>
            <div id="permissionToolBar" style="display: none;">
                <button type="button" lay-event="add" class="layui-btn layui-btn-sm">
                    <span class="layui-icon layui-icon-add-1"></span>
                    分配权限
                </button>
                <button type="button" lay-event="delete" class="layui-btn layui-btn-sm">
                    <span class="layui-icon layui-icon-add-1"></span>
                    取消分配权限
                </button>
            </div>

            <%--<div id="permissionRowBar" style="display: none;">
                <button type="button" lay-event="update" class="layui-btn layui-btn-sm">
                    <span class="layui-icon layui-icon-edit"></span>
                    更新
                </button>
                <button type="button" lay-event="delete" class="layui-btn layui-btn-sm layui-btn-danger">
                    <span class="layui-icon layui-icon-delete"></span>
                    删除
                </button>
            </div>--%>
        </div>

        <!-- 数据表格结束 -->

        <!-- 添加和修改的弹出层开始 -->
        <div style="display: none;padding: 5px" id="addOrUpdateDiv">
            <form action="" method="post" class="layui-form layui-form-pane" id="dataFrm" lay-filter="dataFrm">
                <div class="layui-form-item">
                    <label class="layui-form-label">选择角色</label>
                    <div class="layui-input-block">
                        <%--<input type="hidden" name="pid" id="pid" lay-verify="required">
                        <ul id="permissionTree" class="dtree" data-id="0"></ul>--%>

                        <div id="demo2" class="xm-select-demo"></div>

                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">权限名称</label>
                    <div class="layui-input-block">
                        <div id="demo3" class="xm-select-demo"></div>
                        <%--<input type="hidden" name="id">--%>
                        <%--<input type="text" name="pname" lay-verify="required" autocomplete="off" placeholder="请输入权限名称" class="layui-input">--%>
                    </div>
                </div>
                <%--  <div class="layui-form-item">
                      <label class="layui-form-label">权限编码</label>
                      <div class="layui-input-block">
                          <div id="demo4" class="xm-select-demo"></div>
                          &lt;%&ndash;<input type="text" name="presource" autocomplete="off" placeholder="请输入权限地址" class="layui-input">&ndash;%&gt;
                      </div>
                  </div>--%>
                <div class="layui-form-item" style="text-align: center">
                    <button type="button" class="layui-btn" lay-submit="" lay-filter="doSubmit" id="doSubmit">
                        <span class="layui-icon layui-icon-add-1"></span>
                        提交
                    </button>
                    <button type="reset" class="layui-btn layui-btn-warm">
                        <span class="layui-icon layui-icon-refresh-1"></span>
                        重置
                    </button>
                </div>
            </form>
        </div>
        <!-- 添加和修改的弹出层结束 -->




        <script type="text/javascript" src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${yby}/static/js/xm-select.js"></script>
        <script type="text/javascript" src="${yby}/static/echarts/js/jquery-3.1.1.min.js"></script>

        <script type="text/javascript">


            var demo2 = xmSelect.render({
                el: '#demo2',
                toolbar: {show: true},
                data: []
            });

            // 页面开始加载
            $.post("${yby}/permission/permissionRole", function (res) {
                /*  console.log(res.data.roles);*/
                console.log(res.data.roleUser);
                if (res != null) {
                    // 赋值角色数据
                    demo2.update({
                        data: res.data.roles
                    });
                    // 进行回显当前用户拥有的角色
                    demo2.setValue(res.data.roleUser)
                }
            });


            /* 权限名称 */
            var demo3 = xmSelect.render({
                el: '#demo3',
                toolbar: {show: true},
                data: []
            });

            $.post("${yby}/permission/permissionPname", function (res) {
                console.log(res);
                if (res != null) {
                    demo3.update({
                        data: res.data.pname
                    })
                    // 进行回显当前用户拥有的权限
                    demo3.setValue(res.data.pnameUser)
                }
            })

            /*  /!* 权限编码 *!/
              var demo4 = xmSelect.render({
                  el: '#demo4',
                  toolbar: {show: true},
                  data: []
              });

              $.post("${yby}/permission/permissionPresource", function (res) {
                console.log(res);
                if (res != null) {
                    demo4.update({
                        data: res.data
                    })
                }
            })*/


            var tableIns;
            layui.extend({
                dtree: '${yby}/static/layui_ext/dtree/dtree',   // {/}的意思即代表采用自有路径，即不跟随 base 路径
            }).use(['jquery', 'form', 'table', 'layer', 'dtree'], function () {
                var $ = layui.jquery;
                var form = layui.form;
                var table = layui.table;
                var layer = layui.layer;
                var dtree = layui.dtree;
                //加载 数据
                tableIns = table.render({
                    elem: '#permissionTable'
                    , url: '${yby}/permission/loadAllPermission'
                    , toolbar: '#permissionToolBar' //开启头部工具栏，并为其绑定左侧模板
                    , title: '权限数据表'
                    , height: 'full-220'
                    , page: true
                    , cols: [[
                        {field: 'pid', title: 'ID', align: 'center'}
                        , {field: 'pname', title: '权限名称', align: 'center'}
                        , {field: 'presource', title: '权限编码', align: 'center'}
                        // , {fixed: 'right', title: '操作', toolbar: '#permissionRowBar', align: 'center', width: '200'}
                    ]]
                    , done: function (res, curr, count) { //处理删除某一页最后一条数据的BUG
                        if (res.data.length == 0 && curr != 1) {
                            tableIns.reload({
                                page: {
                                    curr: (curr - 1)
                                }
                            });
                        }
                    }
                });

                //模糊查询
                form.on("submit(doSearch)", function (data) {
                    tableIns.reload({
                        where: data.field,
                        page: {
                            curr: 1
                        }
                    });
                    return false;
                });

                //监听工具条的事件
                table.on("toolbar(permissionTable)", function (obj) {
                    switch (obj.event) {
                        case 'add':
                            openAddLayer();
                            break;
                        case 'delete':
                            deletePermission();
                            break;
                    }
                    ;
                });

                //监听行工具条的事件
                table.on("tool(permissionTable)", function (obj) {
                    var data = obj.data; //获得当前行数据
                    switch (obj.event) {
                        case 'update':
                            openUpdatePermissionLayer(data);
                            break;

                    }
                    ;
                });

                var mainIndex;
                var url;

                //打开添加的弹出层
                function openAddLayer() {

                    mainIndex = layer.open({
                        type: 1,
                        content: $("#addOrUpdateDiv"),
                        area: ['600px', '400px'],
                        title: '添加权限',
                        success: function () {
                            $("#dataFrm")[0].reset();
                            url = "${yby}/permission/addPermission?type=add";
                        }
                    });
                }

                //打开修改的弹出层
                function openUpdatePermissionLayer(data) {
                    mainIndex = layer.open({
                        type: 1,
                        content: $("#addOrUpdateDiv"),
                        area: ['800px', '300px'],
                        title: '修改权限',
                        success: function () {
                            $("#dataFrm")[0].reset();
                            //装载新的数据
                            form.val("dataFrm", data);
                            /* 查询当前用户的角色 */
                            $.post("${yby}/permission/permissionRolePid", data, function (res) {
                                // 获取这个权限  什么角色在使用
                                console.log(res);
                                demo2.setValue(res.data)
                            })

                            url = "${yby}/permission/updatePermission";
                        }
                    });
                }

                form.on("submit(doSubmit)", function (data) {

                    //获取当前多选选中的值
                    var selectArr = demo2.getValue();
                    var selectArr3 = demo3.getValue();
                    // var selectArr4 = demo4.getValue();
                    var pidArray = [];
                    for (var i = 0; i < selectArr.length; i++) {
                        pidArray[i] = selectArr[i].value;
                    }


                    var pnamearray = [];
                    for (var i = 0; i < selectArr3.length; i++) {
                        pnamearray[i] = selectArr3[i].value;
                    }

                    $.post(url + "&ids=" + pidArray + "&pnameIds=" + pnamearray, function (res) {
                        console.log(res)
                        if (res.code == 200) {
                            tableIns.reload();
                        }
                        layer.msg(res.msg);
                        layer.close(mainIndex);
                    })
                    return false;
                })

                //删除
                function deletePermission() {
                    mainIndex = layer.open({
                        type: 1,
                        content: $("#addOrUpdateDiv"),
                        area: ['600px', '400px'],
                        title: '删除角色权限',
                        success: function () {
                            $("#dataFrm")[0].reset();
                            url = "${yby}/permission/addPermission?type=delete";
                        }
                    });

                }

            })

        </script>

    </body>
</html>





