<%--
  Created by IntelliJ IDEA.
  User: TeouBle
  Date: 2020/2/9
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>首页--layui后台管理模板 2.0</title>
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
        <blockquote class="layui-elem-quote layui-bg-green">
            <div id="nowTime"></div>
        </blockquote>
        <div class="layui-row layui-col-space10">
            <div class="layui-col-lg6 layui-col-md6">
                <blockquote class="layui-elem-quote title">最新文章
                    <i class="layui-icon layui-red">&#xe756;</i>
                </blockquote>
                <table class="layui-table mag0" lay-skin="line">
                    <colgroup>
                        <col>
                        <col width="110">
                    </colgroup>
                    <tbody class="hot_news"></tbody>
                </table>
            </div>
        </div>

        <%-- 查看公告的div --%>
        <div id="indexViewNewsDiv" style="display:none;padding: 50px;box-sizing: border-box">
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
            <div id="view_content"></div>
        </div>


        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="main" style="width: 600px;height:400px;"></div>

        <script type="text/javascript" src="${yby}/static/layui/layui.js"></script>
        <script type="text/javascript">
            //获取系统时间
            var newDate = '';
            getLangDate();

            //值小于10时，在前面补0
            function dateFilter(date) {
                if (date < 10) {
                    return "0" + date;
                }
                return date;
            }

            function getLangDate() {
                var dateObj = new Date(); //表示当前系统时间的Date对象
                var year = dateObj.getFullYear(); //当前系统时间的完整年份值
                var month = dateObj.getMonth() + 1; //当前系统时间的月份值
                var date = dateObj.getDate(); //当前系统时间的月份中的日
                var day = dateObj.getDay(); //当前系统时间中的星期值
                var weeks = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
                var week = weeks[day]; //根据星期值，从数组中获取对应的星期字符串
                var hour = dateObj.getHours(); //当前系统时间的小时值
                var minute = dateObj.getMinutes(); //当前系统时间的分钟值
                var second = dateObj.getSeconds(); //当前系统时间的秒钟值
                var timeValue = "" + ((hour >= 12) ? (hour >= 18) ? "晚上" : "下午" : "上午"); //当前时间属于上午、晚上还是下午
                newDate = dateFilter(year) + "年" + dateFilter(month) + "月" + dateFilter(date) + "日 " + " " + dateFilter(hour) + ":" + dateFilter(minute) + ":" + dateFilter(second);
                document.getElementById("nowTime").innerHTML = "亲爱的${user.realname}，" + timeValue + "好！ 欢迎使用汽车租赁系统。当前时间为： " + newDate + "　" + week;
                setTimeout("getLangDate()", 1000);
            }

            layui.use(['form', 'element', 'layer', 'jquery'], function () {
                var form = layui.form,
                    layer = parent.layer === undefined ? layui.layer : top.layer,
                    element = layui.element;
                $ = layui.jquery;
                //上次登录时间【此处应该从接口获取，实际使用中请自行更换】
                $(".loginTime").html(newDate.split("日")[0] + "日</br>" + newDate.split("日")[1]);
                //icon动画
                $(".panel a").hover(function () {
                    $(this).find(".layui-anim").addClass("layui-anim-scaleSpring");
                }, function () {
                    $(this).find(".layui-anim").removeClass("layui-anim-scaleSpring");
                });
                $(".panel a").click(function () {
                    parent.addTab($(this));
                });

                //最新文章列表
                $.get("${yby}/news/loadAllNews?page=1&limit=10", function (data) {
                    var hotNewsHtml = '';
                    for (var i = 0; i < 5; i++) {
                        hotNewsHtml += '<tr ondblclick="viewNews(' + data.data[i].id + ')">'
                            + '<td align="left"><a href="javascript:;"> ' + data.data[i].title + '</a></td>'
                            + '<td>' + data.data[i].createtime.substring(0, 10) + '</td>'
                            + '</tr>';
                    }
                    $(".hot_news").html(hotNewsHtml);
                    $(".userAll span").text(data.length);
                });

            });
            // indexViewNewsDiv
            /*
            * 公告
            * */
            var mainIndex;

            function viewNews(id) {
                $.get("${yby}/news/loadNewsById", {id: id}, function (data) {
                    mainIndex = layer.open({
                        type: 1,
                        title: '查看公告内容',
                        content: $("#indexViewNewsDiv"),
                        area: ['800px', '600px'],
                        maxmin: true,
                        success: function (index) {
                            console.log(data.data.opername);
                            // 设置查看的值
                            $("#view_title").html(data.data.title);
                            $("#view_openname").html(data.data.opername);
                            $("#view_createtime").html(data.data.createtime);
                            $("#view_content").html(data.data.content);
                        }
                    });
                    // 弹出最大化
                    layer.full(mainIndex);
                })
            }

        </script>

        <%-- 导入echarts --%>
        <script src="${yby}/static/js/echarts.min.js"></script>
        <script>



            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            option = {
                title: {
                    text: '测试饼状图像',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
                },
                series: [
                    {
                        name: '杨不易',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: [
                            {value: 335, name: '直接访问'},
                            {value: 310, name: '邮件营销'},
                            {value: 234, name: '联盟广告'},
                            {value: 135, name: '视频广告'},
                            {value: 1548, name: '搜索引擎'}
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };


            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        </script>

    </body>
</html>
