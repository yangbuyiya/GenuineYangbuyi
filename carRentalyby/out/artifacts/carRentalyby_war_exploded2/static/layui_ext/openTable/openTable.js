/**
 @ Name：表格冗余列可展开显示
 @ Author：hbm
 @ License：MIT
 */

layui.define(['form', 'table'], function (exports) {
  var $ = layui.$
      , table = layui.table
      , form = layui.form
      , VERSION = 1.0, MOD_NAME = 'openTable', ELEM = '.layui-openTable', ON = 'on', OFF = 'off'

      //外部接口
      , openTable = {
        index: layui.openTable ? (layui.openTable.index + 10000) : 0

        //设置全局项
        , set: function (options) {
          var that = this;
          that.config = $.extend({}, that.config, options);
          return that;
        }

        //事件监听
        , on: function (events, callback) {
          return layui.onevent.call(this, MOD_NAME, events, callback);
        }
      }

      //操作当前实例
      , thisIns = function () {
        var that = this
            , options = that.config
            , id = options.id || options.index;

        return {
          reload: function (options) {
            that.reload.call(that, options);
          }
          , config: options
        }
      }

      //构造器
      , Class = function (options) {
        var that = this;
        that.index = ++openTable.index;
        that.config = $.extend({}, that.config, openTable.config, options);
        that.render();
      };

  //默认配置
  Class.prototype.config = {
    openType: 0
  };

  //渲染视图
  Class.prototype.render = function () {
    var that = this
        , options = that.config
        , colArr = options.cols[0]
        , openCols = options.openCols || []
        , done = options.done;

    delete options["done"];
    //  1、在第一列 插入可展开操作
    colArr.splice(0, 0, {
      align: 'left',
      width: 50,
      templet: function (item) {
        return "<i class='openTable-i-table-open ' status='off'  data='"
            //  把当前列的数据绑定到控件
            + (JSON.stringify(item))
            + "' title='展开'></i>";
      }
    });


    //  2、表格Render
    table.render(
        $.extend({
          done: function (res, curr, count) {
            initExpandedListener();
            if (done) {
              done(res, curr, count)
            }
          }
        }, options));


    // 3、展开事件
    function initExpandedListener() {


      //扩大点击事件范围 为父级div
      $(".openTable-i-table-open")
          .parent()
          .unbind()
          .click(function () {
            var that = $(this).children();

            // 关闭类型
            if (options.openType === 0) {
              var sta = $(".openTable-open-dow").attr("status"),
                  isThis = (that.attr("data") === $(".openTable-open-dow").attr("data"));
              //1、关闭展开的
              $(".openTable-open-dow")
                  .addClass("openTable-open-up")
                  .removeClass("openTable-open-dow")
                  .attr("status", OFF);

              //2、如果当前 = 展开 && 不等于当前的 关闭
              if (sta === ON && isThis) {
                $(".openTable-open-td").slideUp(100, function () {
                  $(".openTable-open-td").remove();
                });

                return;
              } else {
                that.attr("status", OFF)
                $(".openTable-open-td").remove();
              }


            }


            var _this = this
                , item = JSON.parse(that.attr("data"))
                , status = that.attr("status") === 'on';

            //  1、如果当前为打开，再次点击则关闭
            if (status) {
              that.removeClass("openTable-open-dow");
              that.attr("status", 'off');
              this.addTR.find("div").slideUp(100, function () {
                _this.addTR.remove();
              });
              return;
            }

            var html = ["<div style='margin-left: 50px;display: none'>"];

            //  2、从左到右依次排列 Item
            openCols.forEach(function (val, index) {
              //  1、自定义模板
              if (val.templet) {
                html.push("<div class='openTable-open-item-div'>")
                html.push(val.templet(item));
                html.push("</div>")
                //  2、可下拉选择类型
              } else if (val.type && val.type === 'select') {
                var child = ["<div id='" + val.field + "' class='openTable-open-item-div' >"];
                child.push("<span style='color: #99a9bf'>" + val["title"] + "：</span>");
                child.push("<div class='layui-input-inline'><select  lay-filter='" + val.field + "'>");
                val.items.forEach(function (it) {
                  it = val.onDraw(it, item);
                  child.push("<option value='" + it.id + "' ");
                  child.push(it.isSelect ? " selected='selected' " : "");
                  child.push(" >" + it.value + "</option>");
                });
                child.push("</select></div>");
                child.push("</div>");
                html.push(child.join(""));
                setTimeout(function () {
                  layui.form.render();
                  //  监听 select 修改
                  layui.form.on('select(' + val.field + ')', function (data) {
                    if (options.edit && val.onSelect(data, item)) {
                      var json = {};
                      json.value = data.value;
                      json.field = val.field;
                      item[val.field] = data.value;
                      json.data = JSON.parse(JSON.stringify(item));
                      options.edit(json);
                    }
                  });
                }, 20);
              } else {
                // 3、默认类型
                html.push("<div class='openTable-open-item-div'>");
                html.push("<span class='openTable-item-title'>" + val["title"] + "：</span>");
                html.push((val.edit ?
                        ("<input  class='openTable-exp-value openTable-exp-value-edit' autocomplete='off' name='" + val["field"] + "' value='" + item[val["field"]] + "'/>")
                        : ("<span class='openTable-exp-value' >" + item[val["field"]] + "</span>")
                ));
                html.push("</div>");
              }

            });

            that.addClass("openTable-open-dow");

            // 把添加的 tr 绑定到当前 移除时使用
            this.addTR = $([
              "<tr><td class='openTable-open-td'  colspan='" + (colArr.length + 1) + "'>"
              , html.join("") + "</div>"
              , "</td></tr>"].join("")
            );
            that.parent().parent().parent().after(this.addTR);
            this.addTR.find("div").slideDown(200);
            that.attr("status", 'on');

            $(".openTable-exp-value-edit")
                .blur(function () {
                  var that = $(this), name = that.attr("name"), val = that.val();
                  // 设置了回调 &&发生了修改
                  if (options.edit && item[name] + "" !== val) {
                    var json = {};
                    json.value = that.val();
                    json.field = that.attr("name");
                    item[name] = val;
                    json.data = item;
                    options.edit(json);
                  }
                }).keypress(function (even) {
              even.which === 13 && $(this).blur()
            })


          });

    }

    //  4、监听排序事件
    var elem = $(options.elem).attr("lay-filter");

    //  5、监听表格排序
    table.on('sort(' + elem + ')', function (obj) {
      if (options.sort) {
        options.sort(obj)
      }
      // 重新绑定事件
      initExpandedListener();
    });

    //  6、单元格编辑
    layui.table.on('edit(' + elem + ')', function (obj) {
      if (options.edit) {
        options.edit(obj)
      }
    });

  };

  //核心入口
  openTable.render = function (options) {
    var ins = new Class(options);
    return thisIns.call(ins);
  };

  //加载组件所需样式
  layui.link(layui.cache.base + 'openTable/openTable.css?v=1' + VERSION, function () {
    //此处的“openTable”要对应 openTable.css 中的样式： html #layuicss-openTable{}
  }, 'openTable');

  exports('openTable', openTable);
});
