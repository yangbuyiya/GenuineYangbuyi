<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>公告管理</title>
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
        <form action="" class="layui-form" method="post" id="searchFrm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">公告标题:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="title" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">公告内容:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="content" autocomplete="off" class="layui-input">
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
        <table class="layui-hide" id="newsTable" lay-filter="newsTable"></table>
        <!-- 头部工具 -->
        <div style="display: none;" id="NewsToolBar">
            <button type="button" class="layui-btn layui-btn-sm" lay-event="add">增加</button>
            <button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deleteBatchDelete">批量删除
            </button>
        </div>
        <!-- 操作 -->
        <div id="NewsBar" style="display: none;" lay-filter="NewsBar">
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="viewNews">查看</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </div>
        <!-- 数据表格结束 -->

        <!-- 添加和修改的弹出层开始 -->
        <div style="display: none;padding: 20px" id="saveOrUpdateDiv">
            <%-- 表单数据 --%>
            <form class="layui-form" lay-filter="dataFrm" id="dataFrm">
                <div class="layui-form-item">
                    <label class="layui-form-label">公告标题:</label>
                    <div class="layui-input-block">
                        <%-- 修改id --%>
                        <input type="hidden" name="id">
                        <input type="text" name="title" placeholder="请输入公告标题" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">公告内容</label>
                    <div class="layui-input-block">
                        <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="content"></textarea>
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
                </div>
            </form>
        </div>
        <!-- 添加和修改的弹出层结束 -->

        <%-- 查看公告的div --%>
        <div id="viewNewsDiv" style="display:none;padding: 50px;box-sizing: border-box">
            <h2 id="view_title" align="center">

            </h2>
            <hr>
            <div style="text-align: right;">
                <div class="float_left" style="float: left">
                    发布人:
                    <span id="view_openname"></span>
                </div>
                发布时间:
                <span id="view_createtime"></span>
            </div>
            <hr>
            <style>
                #view_content img{
                    width: 100%;
                }
            </style>
            <div id="view_content"></div>
        </div>


        <script src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript">
            var tableIns;
            /* 引入dtree模块 */
            layui.use(['jquery', 'layer', 'form', 'table', 'layedit', 'laydate'], function () {
                var $ = layui.jquery;
                var layer = layui.layer;
                var form = layui.form;
                var table = layui.table;
                var laydate = layui.laydate;
                var layedit = layui.layedit;

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

                /* 富文本编辑器上传图片接口 */
                layedit.set({
                    uploadImage: {
                        url: '${yby}/file/upload' //接口url
                        , type: 'post' //默认post
                        , field: 'file'
                    }
                });

                // 初始化富文本编辑器
                var editIndex;


                //渲染数据表格
                tableIns = table.render({
                    elem: '#newsTable' //渲染的目标对象
                    ,
                    url: '${yby}/news/loadAllNews' //数据接口
                    ,
                    title: '公告数据表' //数据导出来的标题
                    ,
                    toolbar: "#NewsToolBar" //表格的工具条
                    ,
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
                            field: 'id',
                            title: 'ID',
                            align: 'center'
                        }, {
                            field: 'title',
                            title: '公告标题',
                            align: 'center'
                        }/*, {
                            field: 'content',
                            title: '公告内容',
                            align: 'center'
                        }*/
                            , {
                            field: 'createtime',
                            title: '公告时间',
                            align: 'center'
                        }, {
                            field: 'opername',
                            title: '法定代表人',
                            align: 'center'
                        }, {
                            fixed: 'right',
                            title: '操作',
                            toolbar: '#NewsBar',
                            width: 230,
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
                        url: "${yby}/news/loadAllNews?" + params,
                        page: {
                            /* 当分页是最后一页时候  单击模糊查询 都要重新查询 */
                            curr: 1
                        }
                    })
                });

                //监听头部工具栏事件
                table.on("toolbar(newsTable)", function (obj) {
                    switch (obj.event) {
                        case 'add':
                            openAddNews();
                            break;
                        case 'deleteBatchDelete':
                            deleteBatchDelete();
                            break;
                    }
                    ;
                });

                //监听行工具事件
                table.on('tool(newsTable)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    if (layEvent === 'del') { //删除
                        layer.confirm('真的删除【' + data.title + '】这个公告么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/news/deleteNews", {
                                id: data.id
                            }, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });
                    } else if (layEvent === 'edit') { //编辑
                        //编辑，打开修改界面
                        openUpdateNews(data);
                    } else if (layEvent === 'viewNews') { // 视图
                        viewNews(data);
                    }
                });


                /* 定义地址 */
                var url;
                /* 添加页面索引 用来关闭窗口 */
                var mainIndex;

                //打开添加页面
                function openAddNews() {
                    mainIndex = layer.open({
                        type: 1,
                        title: '添加公告',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '600px'],
                        maxmin: true,
                        success: function (index) {
                            // 显示内容
                            editIndex = layedit.build("content");// 建立编辑器对象
                            //清空表单数据
                            $("#dataFrm")[0].reset();
                            // 给表单赋值
                            layedit.setContent(editIndex, "");

                            // 添加地址
                            url = "${yby}/news/addNews";
                        }
                    });
                    // 弹出最大化
                    // layer.full(mainIndex);
                }

                // 重置
                $("#dataFrmResetBtn").click(function () {
                    layedit.setContent(editIndex, "");
                });


                //打开修改页面
                function openUpdateNews(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '修改公告',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '600px'],
                        success: function (index) {
                            // 显示内容
                            editIndex = layedit.build("content")
                            ;// 建立编辑器对象
                            // 给表单赋值
                            form.val("dataFrm", data);
                            // 重新进行赋值富文本数据
                            layedit.setContent(editIndex, data.content);
                            // 修改地址
                            url = "${yby}/news/updateNews";
                        }
                    });
                }

                //保存表单
                form.on("submit(doSubmit)", function (obj) {
                    // 1.同步富文本编辑器  要放在序列化之前 要不然赋值null
                    // 把富文本的数据 同步到 自己些的文本域当中
                    layedit.sync(editIndex);
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

                /* 查看 */
                function viewNews(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '查看公告内容',
                        content: $("#viewNewsDiv"),
                        area: ['800px', '600px'],
                        maxmin: true,
                        success: function (index) {
                            // 设置查看的值
                            $("#view_title").html(data.title);
                            $("#view_openname").html(data.opername);
                            $("#view_createtime").html(data.createtime);
                            $("#view_content").html(data.content);
                        }
                    });
                    // 弹出最大化
                    layer.full(mainIndex);
                }

                /* 批量删除 */
                function deleteBatchDelete() {
                    var checkStatus = table.checkStatus('newsTable'); //newsTable 即为基础参数 id 对应的值
                    if (checkStatus.data.length > 0) {

                        var ids = [];
                        for (var i = 0; i < checkStatus.data.length; i++) {
                            console.log(checkStatus.data[i].id);
                            ids[i] = checkStatus.data[i].id;
                        }
                        console.log(ids);
                        // 请求后台地址
                        $.ajax({
                            url: "${yby}/news/deleteBatchDelete",
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