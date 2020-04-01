<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

	<head>
		<meta charset="utf-8">
		<title>菜单管理</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no">
		<%--<link rel="icon" href="favicon.ico">--%>
		<link rel="stylesheet" href="${yby}/static/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="${yby}/static/css/public.css" media="all" />
		<link rel="stylesheet" href="${yby}/static/layui_ext/dtree/dtree.css">
		<link rel="stylesheet" href="${yby}/static/layui_ext/dtree/font/dtreefont.css">
        <%-- 下拉树 --%>
        <style type="text/css">
			.select-test {
				position: absolute;
				max-height: 500px;
				height: 350px;
				overflow: auto;
				width: 100%;
				z-index: 123;
				display: none;
				border: 1px solid silver;
				top: 42px;
			}

			.layui-show {
				display: block !important;
			}
		</style>
	</head>

	<body style="padding: 30px;box-sizing: border-box">
		<!-- 搜索条件开始 -->
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			<legend>查询条件</legend>
		</fieldset>
		<form action="" class="layui-form" method="post" id="searchFrm">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">菜单名称:</label>
					<div class="layui-input-inline">
						<input type="text" name="title" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<button type="button" class="layui-btn layui-btn-normal layui-btn-sm layui-icon layui-icon-search" id="doSearch">查询</button>
					<button type="reset" class="layui-btn layui-btn-warm layui-btn-sm layui-icon layui-icon-refresh">重置</button>
				</div>
			</div>
			</div>
		</form>
		<!-- 搜索条件结束 -->

		<!-- 数据表格开始 -->
		<table class="layui-hide" id="menuTable" lay-filter="menuTable"></table>
		<!-- 头部工具 -->
		<div style="display: none;" id="menuToolBar">
			<button type="button" class="layui-btn layui-btn-sm" lay-event="add">增加</button>
			<button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="batchDelete">批量删除</button>
		</div>
		<!-- 操作 -->
		<div id="menuBar" style="display: none;" lay-filter="menuBar">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		</div>
		<!-- 数据表格结束 -->

		<!-- 添加和修改的弹出层开始 -->
		<div style="display: none;padding: 20px" id="saveOrUpdateDiv">
			<form class="layui-form" lay-filter="dataFrm" id="dataFrm">
				<div class="layui-form-item">
					<label class="layui-form-label">父级菜单：</label>
					<div class="layui-input-block">
						<div class="layui-unselect layui-form-select" id="pid_div">
							<div class="layui-select-title">
								<%-- 提交到后台的  pid 父节点 --%>
								<input type="hidden" name="pid" id="pid">
								<input type="text" name="pid_str" id="pid_str" placeholder="请选择" readonly="" class="layui-input layui-unselect">
								<i class="layui-edge"></i>
							</div>
						</div>
						<div class="layui-card select-test" id="menuSelectDiv">
							<div class="layui-card-body">
								<div id="toolbarDiv">
									<ul id="menuTree" class="dtree" data-id="0" style="width: 100%;"></ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">菜单名称:</label>
					<div class="layui-input-block">
						<input type="hidden" name="id">
						<input type="text" name="title" placeholder="请输入菜单名称" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">菜单地址:</label>
					<div class="layui-input-block">
						<input type="text" name="href" placeholder="请输入菜单地址" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">菜单图标:</label>
						<div class="layui-input-inline">
							<input type="text" name="icon" placeholder="请输入菜单图标" lay-verify="required" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">TARGET:</label>
						<div class="layui-input-inline">
							<input type="text" name="target" placeholder="请输入TRAGET" autocomplete="off" class="layui-input">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">是否展开:</label>
						<div class="layui-input-inline">
							<input type="radio" name="spread" value="1" title="展开">
							<input type="radio" name="spread" value="0" title="不展开" checked="checked">
						</div>
					</div>
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
						<button type="button" class="layui-btn layui-btn-normal layui-btn-md layui-icon layui-icon-release layui-btn-radius" lay-filter="doSubmit" lay-submit="">提交
        </button>
						<button type="reset" class="layui-btn layui-btn-warm layui-btn-md layui-icon layui-icon-refresh layui-btn-radius">重置
        </button>
					</div>
				</div>
			</form>
		</div>
		<!-- 添加和修改的弹出层结束 -->

		<script src="${yby}/static/layui/layui.js"></script>
		<script type="text/javascript">
			var tableIns;
			/* 引入dtree模块 */
			layui.extend({
				dtree: '${yby}/static/layui_ext/dist/dtree'
			}).use(['jquery', 'layer', 'form', 'table', 'dtree'], function() {
				var $ = layui.jquery;
				var layer = layui.layer;
				var form = layui.form;
				var table = layui.table;
				var dtree = layui.dtree;
				//渲染数据表格
				tableIns = table.render({
					elem: '#menuTable' //渲染的目标对象
						,
					url: '${yby}/menu/loadAllMenu' //数据接口
						,
					title: '用户数据表' //数据导出来的标题
						,
					toolbar: "#menuToolBar" //表格的工具条
						,
					height: 'full-300',
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
								field: 'pid',
								title: '父节点ID',
								align: 'center'
							}, {
								field: 'title',
								title: '菜单名称',
								align: 'center'
							}, {
								field: 'href',
								title: '菜单地址',
								align: 'center'
							}, {
								field: 'spread',
								title: '是否展开',
								align: 'center',
								templet: function(d) {
									return d.spread == '1' ? '<font color=blue>展开</font>' : '<font color=red>不展开</font>';
								}
							}, {
								field: 'target',
								title: 'TARGET',
								align: 'center'
							}, {
								field: 'icon',
								title: '菜单图标',
								align: 'center',
								templet: function(d) {
									return "<div class='layui-icon'>" + d.icon + "</div>";
								}
							}, {
								field: 'available',
								title: '是否可用',
								align: 'center',
								templet: function(d) {
									return d.available == '1' ? '<font color=blue>可用</font>' : '<font color=red>不可用</font>';
								}
							}, {
								fixed: 'right',
								title: '操作',
								toolbar: '#menuBar',
								width: 120,
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
					}
                    ,text:{
                    none: 'Trouble提醒您：暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
                    }
				});

				//模糊查询
				$("#doSearch").click(function() {
					// 获取表单序列化
					var params = $("#searchFrm").serialize();
					//alert(params);
					tableIns.reload({
						url: "${yby}/menu/loadAllMenu?" + params,
						page: {
							curr: 1
						}
					})
				});

				//监听头部工具栏事件
				table.on("toolbar(menuTable)", function(obj) {
					switch(obj.event) {
						case 'add':
							openAddMenu();
							break;
                        case 'batchDelete':
                        batchDelete();
                        break;
					};
				});

				//监听行工具事件
				table.on('tool(menuTable)', function(obj) {
					var data = obj.data; //获得当前行数据
					var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
					if(layEvent === 'del') { //删除
						//判断当前菜单有没有子节点
						$.post("${yby}/menu/checkMenuHasChildren?id=" + data.id, function(obj) {
							if(obj.code >= 0) {
								layer.msg("当前菜单有子节点，请先删除子节点");
							} else {
								layer.confirm('真的删除【' + data.title + '】这个菜单么？', function(index) {
									//向服务端发送删除指令  id: data.id
									$.post("${yby}/menu/deleteMenu", {
										id: data.id
									}, function(res) {
										layer.msg(res.msg);
										//刷新数据表格
										tableIns.reload();
										//刷新左边的数
										window.parent.left.menuTree.reload();
										//刷新添加和修改的下拉树
										menuTree.reload();
									})
								});
							}
						})
					} else if(layEvent === 'edit') { //编辑
						//编辑，打开修改界面
						openUpdateMenu(data);
					}
				});

				/* 批量删除 */
                function  batchDelete(){
                    var checkStatus = table.checkStatus('menuTable'); //idTest 即为基础参数 id 对应的值
                    console.log(checkStatus.data) //获取选中行的数据
                    console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
                    console.log(checkStatus.isAll ) //表格是否全选
                    var ids = [];
                    for(var i = 0; i < checkStatus.data.length; i++) {
                        console.log(checkStatus.data[i].id);
                        ids[i] = checkStatus.data[i].id;
                    }
                    console.log(ids);

                    $.ajax({
                    url : "${yby}/menu/batchDelete",
                    type : "post",
                    dataType : "json",
                    traditional : true,//用传统方式序列化数据
                    data : {"ids[]":ids},
                    success : function(result) {
                            layer.msg(result.msg);
                            //刷新数据 表格
                            tableIns.reload();
                            //刷新左边的树
                            window.parent.left.menuTree.reload();
                            //刷新添加和修改的下拉树
                            menuTree.reload();
                    }
                    });
                }

				/* 定义地址 */
				var url;
				/* 添加页面索引 用来关闭窗口 */
				var mainIndex;

				//打开添加页面
				function openAddMenu() {
					mainIndex = layer.open({
						type: 1,
						title: '添加菜单',
						content: $("#saveOrUpdateDiv"),
						area: ['800px', '440px'],
						success: function(index) {
							//清空表单数据
							$("#dataFrm")[0].reset();
							// 关闭弹出层
							$("#menuSelectDiv").removeClass("layui-show");
							// 添加地址
							url = "${yby}/menu/addMenu";
						}
					});
				}

				//打开修改页面
				function openUpdateMenu(data) {
					mainIndex = layer.open({
						type: 1,
						title: '修改菜单',
						content: $("#saveOrUpdateDiv"),
						area: ['800px', '440px'],
						success: function(index) {
							// 给表单赋值
							form.val("dataFrm", data);
							// 修改地址
							url = "${yby}/menu/updateMenu";
							//反选下拉树
							var pid = data.pid;

							// 初始化
							var params = dtree.dataInit("menuTree", pid);
							//移除打开的样式
							$("#menuSelectDiv").removeClass("layui-show");

							//给下拉框的text框赋值
							console.log(params);
							if(params != undefined) {
								$("#pid_str").val(params.context);
							}

						}
					});
				}

				//保存表单
				form.on("submit(doSubmit)", function(obj) {
					//序列化表单数据
					var params = $("#dataFrm").serialize();
					$.post(url, params, function(obj) {
						layer.msg(obj.msg);
						//关闭弹出层
						layer.close(mainIndex);
						//刷新数据 表格
						tableIns.reload();
						//刷新左边的树
						window.parent.left.menuTree.reload();
						//刷新添加和修改的下拉树
						menuTree.reload();
					})
				});

				//初始化添加和修改页面的下拉树
				// 注意点 ： spread=1 dtree接受的为 布尔类型 而我们后台的值为 数值类型
				var menuTree = dtree.render({
					elem: "#menuTree",
					dataStyle: "layuiStyle", //使用layui风格的数据格式
					response: {
						message: "msg",
						statusCode: 0
					}, //修改response中返回数据的定义
					dataFormat: "list", //配置data的风格为list
					url: "${yby}/menu/loadMenuManagerLeftTreeJson?spread=1", // 使用url加载（可与data加载同时存在）
					icon: "2",
					accordion: true //能否下拉
				});

				/* 监听下拉菜单的点击 */
				$("#pid_div").on("click", function() {
					$(this).toggleClass("layui-form-selected");
					$("#menuSelectDiv").toggleClass("layui-show layui-anim layui-anim-upbit");
				});

				// 监听下拉菜单的点击
				dtree.on("node(menuTree)", function(obj) {
					// 给dtree 文本框赋值
					$("#pid_str").val(obj.param.context);
					/* 给隐藏域 赋值pid */
					$("#pid").val(obj.param.nodeId);

					// 添加/删除 class
					$("#pid_div").toggleClass("layui-form-selected");
					$("#menuSelectDiv").toggleClass("layui-show layui-anim layui-anim-upbit");
				});

			});

			// 这是MenuLeft的 请求
			function reloadTable(id) {
				// 重新渲染
                // 根据 left 边的 传入过来的  id 查询  指定菜单
				tableIns.reload({
					url: "${yby}/menu/loadAllMenu?id=" + id
				})
			}
		</script>
	</body>

</html>