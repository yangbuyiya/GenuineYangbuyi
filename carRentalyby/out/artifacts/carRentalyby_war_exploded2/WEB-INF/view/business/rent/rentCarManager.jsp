<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>出租管理</title>
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

    <body class="childrenBody">

        <!-- 搜索条件开始 -->
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>查询条件</legend>
        </fieldset>

        <form class="layui-form" method="post" id="searchFrm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">身份证号:</label>
                    <div class="layui-input-inline" style="padding: 5px">
                        <input type="text" name="identity" id="identity" autocomplete="off"
                               class="layui-input layui-input-inline"
                               placeholder="请输入身份证号" style="height: 30px;">
                    </div>
                    <button type="button"
                            class="layui-btn layui-btn-normal layui-icon layui-icon-search layui-btn-radius layui-btn-sm"
                            id="doSearch" style="margin-top: 4px">查询
                    </button>
                    <button type="reset"
                            class="layui-btn layui-btn-warm layui-icon layui-icon-refresh layui-btn-radius layui-btn-sm"
                            style="margin-top: 4px">重置
                    </button>
                </div>
            </div>
        </form>

        <!-- 数据表格开始 -->
        <table class="layui-hide" id="carTable" lay-filter="carTable"></table>

        <%-- 操作工具 --%>
        <div id="carBar" style="display: none;">
            <a class="layui-btn layui-btn-xs " lay-event="rentCar">出租汽车</a>
            <a class="layui-btn layui-btn-warm layui-btn-xs " lay-event="viewImage">查看大图</a>
        </div>

        <%--添加和修改的弹出层开始--%>
        <div style="display: none;padding: 20px;" id="saveOrUpdateDiv">
            <form class="layui-form" lay-filter="dataFrm" id="dataFrm">
                <div class="layui-form-item">
                    <label class="layui-form-label">出租单号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="rentid" lay-verify="required" readonly="readonly" placeholder="请输入出租单号"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">起租时间:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="begindate" id="begindate" lay-verify="required" placeholder="请输入起租时间" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">还车时间:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="returndate" id="returndate" lay-verify="required" placeholder="请输入还车时间" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">身份证号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="identity" lay-verify="required" readonly="readonly" placeholder="请输入身份证号"
                                   class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">车牌号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="carnumber" lay-verify="required" readonly="readonly" placeholder="请输入车牌号" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">出租价格:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="price" lay-verify="required" placeholder="请输入出租价格" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">操作员:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="opername" id="opername" lay-verify="required" placeholder="请输入操作员" readonly="readonly" class="layui-input">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block" style="text-align: center;padding-right: 120px">
                        <button type="button"
                                class="layui-btn layui-btn-normal layui-btn-md layui-icon layui-icon-release layui-btn-radius"
                                lay-filter="doSubmit" lay-submit="">提交
                        </button>
                        <button type="reset"
                                class="layui-btn layui-btn-warm layui-btn-md layui-icon layui-icon-refresh layui-btn-radius">
                            重置
                        </button>
                    </div>
                </div>
            </form>
        </div>


        <%--查看大图弹出的层开始--%>
        <div id="viewCarImageDiv" style="display: none;text-align: center">
            <img alt="车辆图片" id="view_carimg">
        </div>


        <script src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript">
            var tableIns;
            layui.use(['jquery', 'layer', 'form', 'table', 'laydate'], function () {
                var $ = layui.jquery;
                var layer = layui.layer;
                var form = layui.form;
                var table = layui.table;
                var laydate = layui.laydate;

                function initCarRentData() {
                    tableIns = table.render({
                        elem: '#carTable', //渲染的目标对象
                        url: '${yby}/car/loadAllCar', //数据接口
                        method: 'post',
                        title: '车辆数据表', //数据导出来的标题
                        toolbar: "#carToolBar", //表格的工具条
                        cellMinWidth: 100, //设置列的最小默认宽度
                        height:'full-300',
                        sort: 'back',
                        page: true //是否启用分页
                        , cols: [[   //列表数据
                            {type: 'checkbox', fixed: 'left'}
                            , {field: 'carnumber', title: '车牌号', align: 'center', width: '95'}
                            , {field: 'cartype', title: '出租类型', align: 'center', width: '90'}
                            , {field: 'color', title: '出租颜色', align: 'center', width: '90'}
                            , {field: 'price', title: '汽车价格', align: 'center', width: '90'}
                            , {field: 'rentprice', title: '出租价格', align: 'center', width: '90'}
                            , {field: 'deposit', title: '出租押金', align: 'center', width: '90'}
                            , {
                                field: 'isrenting', title: '出租状态', align: 'center', width: '90', templet: function (d) {
                                    return d.isrenting == '1' ? '<font color=blue>已出租</font>' : '<font color=red>未出租</font>';
                                }
                            }
                            , {field: 'description', title: '出租描述', align: 'center', width: '150'}
                            , {
                                field: 'carimg', title: '缩略图', align: 'center', width: '80', templet: function (d) {
                                    return "<img width=40 height=40 src=${yby}/file2/downloadShowFile2?path=" + d.carimg + "/>";
                                }
                            }
                            , {field: 'createtime', title: '录入时间', align: 'center', width: '170'}
                            , {fixed: 'right', title: '操作', toolbar: '#carBar', align: 'center', width: '220'}
                        ]], text: {
                            none: 'Trouble提醒您：暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
                        }
                    });
                }


                //模糊查询
                $("#doSearch").click(function () {
                    // 获取表单序列化
                    var params = $("#searchFrm").serialize();
                    // 发送请求 根据身份证号查询
                    $.post("${yby}/rent/checkCustomerExist", params, function (data) {
                        if (data.code >= 0) {
                            /* 初始化数据 */
                            initCarRentData();
                        } else {
                            layer.msg("客户身份证号不存在,请更正后在继续!!!");
                        }
                    })
                });


                /* 时间渲染 */
                /* 出租时间 */
                laydate.render({
                    elem:'#begindate',
                    type:'datetime'
                });
                /* 还车时间 */
                laydate.render({
                    elem:'#returndate',
                    type:'datetime'
                });


                //监听头部工具栏事件
                table.on("toolbar(carTable)", function (obj) {
                    switch (obj.event) {
                        case 'add':
                            openAddCar();
                            break;
                        case 'deleteBatchDelete':
                            deleteBatchDelete();
                            break;
                    }
                });

                //监听行工具事件
                table.on('tool(carTable)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    if (layEvent === 'del') { //删除
                        layer.confirm('真的删除【' + data.carnumber + '】这个车辆么？', function (index) {
                            //向服务端发送删除指令  id: data.id
                            $.post("${yby}/car/deleteCar", {
                                carnumber: data.carnumber
                            }, function (res) {
                                layer.msg(res.msg);
                                //刷新数据表格
                                tableIns.reload();
                            })
                        });
                    } else if (layEvent === 'rentCar') {
                        // 汽车出租  打开添加汽车出租页面
                        if(data.isrenting == "1"){
                            layer.msg("该车辆已出租");
                        }else{
                            openRentCar(data);
                        }

                    } else if (layEvent === 'viewImage') {
                        // 查看大图
                        showCarImage(data);
                    }
                });

                /* 监听回车 */
                $(document).on('keydown', function (event) {
                    var event = event || window.event;
                    if (event.keyCode == 13) {
                        $("#doSearch").click();
                    }
                });

                /* 定义地址 */
                var url;
                /* 添加页面索引 用来关闭窗口 */
                var mainIndex;

                //打开添加页面
                function openRentCar(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '添加出租单',
                        content: $("#saveOrUpdateDiv"),
                        area: ['800px', '400px'],
                        maxmin: true,
                        success: function (index) {
                            //清空表单数据
                            $("#dataFrm")[0].reset();
                            //请求数据,分别拿到出租价格，身份证号，车牌号
                            /* 出租金额 */
                            var price=data.rentprice;
                            /* 身份证号 */
                            var identity=$("#identity").val();
                            /* 车牌号 */
                            var carnumber=data.carnumber;
                            $.get("${yby}/rent/initRentFrom",{
                                identity:identity,
                                price:price,
                                carnumber:carnumber
                            },function (obj) {
                                //赋值
                                form.val("dataFrm",obj);
                            })

                        }
                    });
                    // 弹出最大化
                    // layer.full(mainIndex);
                }

                //保存表单
                form.on("submit(doSubmit)", function (obj) {
                    // 2.序列化表单数据
                    var params = $("#dataFrm").serialize();
                    // 3.发送请求
                    $.post("${yby}/rent/saveRent", params, function (obj) {
                        layer.msg(obj.msg);
                        //关闭弹出层
                        layer.close(mainIndex);
                        //刷新数据表格
                        tableIns.reload();
                    })
                });

                // 查看大图
                function showCarImage(data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: data.carnumber + '的车辆图片',
                        content: $("#viewCarImageDiv"),
                        area: ['800px', '400px'],
                        success: function (index) {
                            // 显示图片
                            $("#view_carimg").attr("src", "${yby}/file2/downloadShowFile2?path=" + data.carimg);
                        }
                    });
                }

            });
        </script>
    </body>

</html>