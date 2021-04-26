var table;
var tree;
var layer;
var util;
var form;
var layarea;
var transfer;
var laydate;

var dataTable;

//配置插件目录
layui.config({
    base: '../src/lay/mod/'
    , version: '1.0'
});
layui.use(['table', 'tree', 'util', 'layarea', 'transfer', 'laydate'], function () {
    table = layui.table
    tree = layui.tree
        , layer = layui.layer
        , transfer = layui.transfer
        , util = layui.util
        , form = layui.form
        , layarea = layui.layarea
        , laydate = layui.laydate


    laydate.render({
        elem: '#month'
        ,type: 'month'
        ,done: function (value, date, endDate) {
            value += '-01'
            loadData(value)
        }
    })
});

function loadData(value) {
    console.log(value)
    $.ajax({
        type: "get",
        url: '/rest/attendRecords/listMonths?month=' + value,
        async: false,
        success: function (data) {
            if (data.code == 'OK') {
                dataTable = table.render({
                    elem: '#data_table'
                    , data: data.data
                    ,totalRow: true
                    , cols: [[
                         {field: 'course', title: '课程编号', templet: function(d) {
                             return d.course.no
                         }, totalRowText: '总课时总计'
                         }
                         ,{field: 'course', title: '课程名称', templet: function(d) {
                                return d.course.name
                            }
                            }
                        , {field: 'totalHours', title: '当月已上课时',totalRow: true}
                        // , {fixed: 'right', title: '操作', toolbar: '#bar'}
                    ]]
                });
            } else {
                parent.layer.msg(data.message, {icon: 2})
            }
        }
    });
}

function add(isLast) {
        $.ajax({
            type: "post",
            url: '/rest/attendWeeks?last=' + isLast,
            async: false,
            success: function (data) {
                if (data.code == 'OK') {
                    parent.layer.msg("生成成功", {icon: 1});
                    dataTable.reload()
                } else {
                    parent.layer.msg(data.message, {icon: 2})
                }
            }
        });
}





