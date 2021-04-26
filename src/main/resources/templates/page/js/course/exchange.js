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
    ,tree = layui.tree
        , layer = layui.layer
        , transfer = layui.transfer
        , util = layui.util
        , form = layui.form
        , layarea = layui.layarea
        , laydate = layui.laydate


    dataTable = table.render({
        elem: '#data_table'
        , url: '/rest/exchangeRecords/page'
        , totalRow: true
        , page: {
            limit: 10
            , curr: 1
            , groups: 5
            , first: false
            , last: false
            , layout: ['limit', 'prev', 'page', 'next', 'count'] //自定义分页布局
        }
        , response: {
            statusCode: 'OK' //重新规定成功的状态码为 200，table 组件默认为 0
        }
        , parseData: function (res) { //res 即为原始返回的数据
            console.log(res.data.records)
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.records //解析数据列表
            };
        }, cols: [[
            {type: 'checkbox'}
            , {field: 'course', title: '课程编号', templet: function(d) {
                console.log(d)
                    return d.course.no
                }}
            , {field: 'course', title: '课程名称', templet: function(d) {
                    return d.course.name
                }}
            , {field: 'user', title: '申请人', templet: function(d) {
                    return d.user.name
                }}
            , {field: 'targetUser', title: '代课人', templet: function(d) {
                    return d.targetUser.name
                }}
            , {field: 'type', title: '调课类型', templet: function(d) {
                    if (d.type === 'REPLACE') {
                        return '代课';
                    } else {
                        return '请假';
                    }
                }}
            , {field: 'startTime', title: '计划上课开始时间'}
            , {field: 'endTime', title: '计划上课结束时间'}
            , {field: 'status', title: '审核状态', templet: function(d) {
                if (d.status === 'PENDING') {
                    return '待审核';
                } else if (d.status === 'PASS') {
                    return '已通过';
                } else {
                    return '已拒绝';
                }
                }}
            , {field: 'createTime', title: '申请日期'}
            , {fixed: 'right', title: '操作', toolbar: '#bar'}
        ]]
    });

    //监听行工具事件
    table.on('tool(data_table)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'PASS') {
            if (obj.data.status !== 'PENDING') {
                layer.msg("当前状态不允许审核",{icon:2})
                return
            }
            audit(obj.data.id, 'PASS');
        } else if (obj.event === 'REJECT') {
            if (obj.data.status !== 'PENDING') {
                layer.msg("当前状态不允许审核",{icon:2})
                return
            }
            audit(obj.data.id, 'REJECT');
        }
    });

});

function audit(id, status) {
    $.ajax({
        type: "put",
        url: '/rest/exchangeRecords/audit/' + id + '?status=' + status,
        async: false,
        success: function (data) {
            console.log(data)
            if (data.code === 'OK') {
                parent.layer.msg('审核成功', {icon: 1})
                dataTable.reload()
            } else {
                parent.layer.msg(data.message, {icon: 2})
            }
        }
    });
}

function search() {
    let param = form.val('searchForm')
    dataTable.reload({
        url: '/rest/exchangeRecords/page?status=' + param.status
    })
}

function reset() {
    form.val("searchForm", {
        "status": ""
    });
    dataTable.reload({
        url: '/rest/exchangeRecords/page'
    })
}



