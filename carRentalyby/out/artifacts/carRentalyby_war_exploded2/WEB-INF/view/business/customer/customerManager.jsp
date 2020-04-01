<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>客户管理</title>
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
        <style>
            #LAY_layedit_1 img {
                width: 50px !important;
                height: 50px !important;
            }
        </style>
    </head>

    <body>
        <!-- 搜索条件开始 -->
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>查询条件</legend>
        </fieldset>
        <form action="" class="layui-form" method="post" id="searchFrm" style="text-align: center;">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">客户姓名:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="custname" lay-verify="required" placeholder="请输入客户姓名" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">身份证号:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="identity" lay-verify="required" placeholder="请输入客户姓名" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">客户地址:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="address" placeholder="请输入客户地址" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">客户职业:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="career" placeholder="请输入客户职业" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">客户电话:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入客户电话" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">客户性别:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="sex" value="1" checked="checked" title="男">
                        <input type="radio" name="sex" value="0" title="女">
                    </div>
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
        </form>
        <!-- 搜索条件结束 -->

        <!-- 数据表格开始 -->
        <table class="layui-hide" id="customerTable" lay-filter="customerTable"></table>
        <!-- 头部工具 -->
        <div style="display: none;" id="CustomerToolBar">
            <button type="button" class="layui-btn layui-btn-sm" lay-event="add">增加</button>
            <button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deleteBatchDelete">批量删除
            </button>
        </div>
        <!-- 操作 -->
        <div id="CustomerBar" style="display: none;" lay-filter="CustomerBar">
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </div>
        <!-- 数据表格结束 -->

        <!-- 添加和修改的弹出层开始 -->
        <div style="display: none;padding: 50px" id="saveOrUpdateDiv">
            <%-- 表单数据 --%>
            <form class="layui-form" lay-filter="dataFrm" id="dataFrm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">客户姓名:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="custname" lay-verify="required" placeholder="请输入客户姓名" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">身份证号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="identity" lay-verify="required" placeholder="请输入客户姓名" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">客户地址:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="address" placeholder="请输入客户地址" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">客户职业:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="career" placeholder="请输入客户职业" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">客户电话:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入客户电话" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">客户性别:</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="sex" value="1" checked="checked" title="男">
                            <input type="radio" name="sex" value="0" title="女">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block" style="text-align: center;padding-right: 120px">
                        <button type="button" class="layui-btn layui-btn-normal layui-btn-md layui-icon layui-icon-release layui-btn-radius" lay-filter="doSubmit" lay-submit="">
                            提交
                        </button>
                        <button type="reset" class="layui-btn layui-btn-warm layui-btn-md layui-icon layui-icon-refresh layui-btn-radius" id="dataFrmResetBtn">
                            重置
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- 添加和修改的弹出层结束 -->


        <script src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript">
            var tableIns;

            layui.use(['jquery', 'layer', 'form', 'table'], function () {
                var $ = layui.jquery;
                var layer = layui.layer;
                var form = layui.form;
                var table = layui.table;

                //渲染数据表格
                tableIns = table.render({
                    elem: '#customerTable', //渲染的目标对象
                    url: '${yby}/customer/loadAllCustomer', //数据接口
                    title: '客户数据表', //数据导出来的标题
                    toolbar: "#CustomerToolBar", //表格的工具条
                    cellMinWidth: 100, //设置列的最小默认宽度
                    page: true //是否启用分页
                    , cols: [[   //列表数据
                        {type: 'checkbox', fixed: 'left'}
                        , {field: 'identity', title: '身份证号', align: 'center', width: '200'}
                        , {field: 'custname', title: '客户姓名', align: 'center', width: '125'}
                        , {field: 'address', title: '客户地址', align: 'center', width: '125'}
                        , {field: 'career', title: '客户职业', align: 'center', width: '150'}
                        , {field: 'phone', title: '手机号码', align: 'center', width: '150'}

                        , {
                            field: 'sex', title: '性别', align: 'center', width: '120', templet: function (d) {
                                return d.sex == '1' ? '<font color=blue>男</font>' : '<font color=red>女</font>';
                            }
                        }
                        , {field: 'createtime', title: '录入时间', align: 'center', width: '200'}
                        , {fixed: 'right', title: '操作', toolbar: '#CustomerBar', align: 'center', width: '150'}
                    ]],
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
                        url: "${yby}/customer/loadAllCustomer?" + params,
                        page: {
                            /* 当分页是最后一页时候  单击模糊查询 都要重新查询 */
                            curr: 1
                        }
                    })
                });

                //监听头部工具栏事件
                table.on("toolbar(customerTable)", function (obj) {
                    switch (obj.event) {
                        case 'add':
                            openAddCustomer();
                            break;
                        case 'deleteBatchDelete':
                            deleteBatchDelete();
                            break;
                    }
                });

                //监听行工具事件
                table.on('tool(customerTable)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    if (layEvent === 'del') { //删除
                        layer.confirm('真的删除【' + data.custname + '】这个客户么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/customer/deleteCustomer", {
                                identity: data.identity
                            }, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });
                    } else if (layEvent === 'edit') { //编辑
                        //编辑，打开修改界面
                        openUpdateCustomer(data);
                    }
                });


                /* 定义地址 */
                var url;
                /* 添加页面索引 用来关闭窗口 */
                var mainIndex;

                //打开添加页面
                function openAddCustomer() {
                    mainIndex = layer.open({
                        type: 1,
                        title: '添加客户',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '400px'],
                        maxmin: true,
                        success: function (index) {
                            //清空表单数据
                            $("#dataFrm")[0].reset();
                            // 添加地址
                            url = "${yby}/customer/addCustomer";
                        }
                    });
                    // 弹出最大化
                    // layer.full(mainIndex);
                }


                //打开修改页面
                function openUpdateCustomer(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '修改客户',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '400px'],
                        success: function (index) {
                            // 给表单赋值
                            form.val("dataFrm", data);
                            // 修改地址
                            url = "${yby}/customer/updateCustomer";
                        }
                    });
                }

                //保存表单
                form.on("submit(doSubmit)", function (obj) {
                    // 2.序列化表单数据
                    var params = $("#dataFrm").serialize();
                    // 3.发送请求
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
                    var checkStatus = table.checkStatus('customerTable'); //customerTable 即为基础参数 id 对应的值
                    if (checkStatus.data.length > 0) {
                        var ids = [];
                        for (var i = 0; i < checkStatus.data.length; i++) {
                            console.log(checkStatus.data[i].identity);
                            ids[i] = checkStatus.data[i].identity;
                        }
                        layer.confirm('真的删除【' + ids + '】这些客户么？', function (index) {
                            // 请求后台地址
                            $.ajax({
                                url: "${yby}/customer/deleteBatchDelete",
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

            });
        </script>
    </body>

</html>