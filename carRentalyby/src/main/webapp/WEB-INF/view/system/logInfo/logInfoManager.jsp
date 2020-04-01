<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>日志管理</title>
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
    </head>

    <body>
        <!-- 搜索条件开始 -->
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>查询条件</legend>
        </fieldset>
        <form action="" class="layui-form" method="post" id="searchFrm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">日志名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="loginname" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">登录IP:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="loginip" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">开始时间:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="startTime" id="startTime" readonly="readonly" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">结束时间</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="endTime" readonly="readonly" id="endTime" placeholder="2020-10-15">
                    </div>
                    <div class="layui-form-mid layui-word-aux">
                        这里以控制在只能选择当前之后的
                    </div>
                </div>
                <div class="layui-form-item" style="text-align: center;padding-top: 20px;">
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
            </div>
        </form>
        <!-- 搜索条件结束 -->

        <!-- 数据表格开始 -->
        <table class="layui-hide" id="LogInfoTable" lay-filter="LogInfoTable"></table>
        <!-- 头部工具 -->
        <div style="display: none;" id="LogInfoToolBar">
            <button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deleteBatchDelete">批量删除
            </button>
        </div>
        <!-- 操作 -->
        <div id="LogInfoBar" style="display: none;" lay-filter="LogInfoBar">
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </div>
        <!-- 数据表格结束 -->

        <script src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript">
            var tableIns;
            /* 引入dtree模块 */
            layui.use(['jquery', 'layer', 'form', 'table', 'laydate', 'element'], function () {
                var $ = layui.jquery;
                var layer = layui.layer;
                var element = layui.element;
                var form = layui.form;
                var table = layui.table;
                var laydate = layui.laydate; // 时间模块
                //渲染数据表格
                tableIns = table.render({
                    elem: '#LogInfoTable' //渲染的目标对象
                    ,
                    url: '${yby}/logInfo/loadAllLogInfo' //数据接口
                    ,
                    title: '日志数据表' //数据导出来的标题
                    ,
                    toolbar: "#LogInfoToolBar" //表格的工具条
                    , cellMinWidth: 100 //设置列的最小默认宽度
                    ,
                    page: true //是否启用分页
                    ,
                    cols: [
                        [ //列表数据
                            {
                                type: 'checkbox',
                                fixed: 'left'
                            }, {
                            field: 'id',
                            title: 'ID',
                            align: 'center'
                        }, {
                            field: 'loginname',
                            title: '日志名称',
                            align: 'center'
                        }, {
                            field: 'loginip',
                            title: '登录IP',
                            align: 'center'
                        }, {
                            field: 'logintime',
                            title: '日志时间',
                            align: 'center'
                        }, {
                            fixed: 'right',
                            title: '操作',
                            width: 80,
                            toolbar: '#LogInfoBar',
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

                /* 渲染时间 */
                laydate.render({
                    elem: '#startTime',
                    type: 'datetime'
                });

                /* 结束时间 */
                laydate.render({
                    elem: '#endTime', //指定元素
                    type: 'datetime'
                    /* , type: 'date'
                     , min: '1900-1-1'  // 最小时间
                     , max: new Date().getDate() + ""  // 限定最大时间*/
                });

                //模糊查询
                $("#doSearch").click(function () {
                    // 获取表单序列化
                    var params = $("#searchFrm").serialize();
                    //alert(params);
                    // 重新加载数据  并且指定请求 携带 当前页数 1
                    tableIns.reload({
                        url: "${yby}/logInfo/loadAllLogInfo?" + params,
                        page: {
                            curr: 1
                        }
                    })
                });

                //监听头部工具栏事件
                table.on("toolbar(LogInfoTable)", function (obj) {
                    switch (obj.event) {
                        case 'deleteBatchDelete':
                            deleteBatchDelete();
                            break;
                    }
                });

                //监听行工具事件
                table.on('tool(LogInfoTable)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    if (layEvent === 'del') { //删除
                        layer.confirm('真的删除【' + data.loginname + '】这个日志么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/logInfo/deleteLogInfo", {
                                id: data.id
                            }, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });
                    }
                });


                /* 批量删除 */
                function deleteBatchDelete() {
                    var checkStatus = table.checkStatus('LogInfoTable'); //logInfoTable 即为基础参数 id 对应的值
                    if (checkStatus.data.length > 0) {
                        var ids = [];
                        for (var i = 0; i < checkStatus.data.length; i++) {
                            ids[i] = checkStatus.data[i].id;
                        }
                        console.log(ids);
                        // 请求后台地址
                        $.ajax({
                            url: "${yby}/logInfo/deleteBatchDelete",
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
            });
        </script>
    </body>

</html>