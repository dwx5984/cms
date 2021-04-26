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

    form.on('select()', function(data){
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        renderTable(data.value)
    });

});

$(function ()  {
    loadData()
});

function loadData() {
    $.ajax({
        type: "get",
        url: '/users/byRole/300',
        async: false,
        success: function (data) {
            if (data.code == 'OK') {
                let ht = '<option value="">直接选择或搜索选择</option>'
                for (let i = 0; i < data.data.length; i++) {
                    ht += '<option value="'+ data.data[i].id +'">'+ data.data[i].name +'</option>'
                }
                $('#teacher').append(ht);
            } else {
                parent.layer.msg(data.message, {icon: 2})
            }
        }
    });
}

function renderTable(value) {
    dataTable = table.render({
        elem: '#data_table'
        , url: '/rest/courses/pageByUser?userId=' + value
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
            , {field: 'no', title: '课程编号'}
            , {field: 'name', title: '课程名称'}
            , {field: 'credit', title: '学分'}
            , {field: 'period', title: '总课时'}
            , {field: 'attendedHours', title: '已上课时'}
            , {fixed: 'right', title: '操作', toolbar: '#bar'}
        ]]
    });

    //监听行工具事件
    table.on('tool(data_table)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'record') {
            showRecord(obj, value)
        }
    });
}

function showRecord(obj, userId) {
    console.log(obj)
    dataTable = table.render({
        elem: '#table'
        , url: '/rest/attendRecords/pageByUser?userId=' + userId+ '&courseId=' + obj.data.id
        , totalRow: true
        , page: {
            limit: 10
            , curr: 1
            , groups: 5
            , first: false
            , last: false
            // , layout: ['limit', 'prev', 'page', 'next', 'count'] //自定义分页布局
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
            , {field: 'course', title: '课程名称', templet: function(d) {
                return d.course.name
                }}
            , {field: 'time', title: '上课时间', templet: function(d) {
                    return d.attendStartTime + '-' + d.attendEndTime
                }}
            , {field: 'hour', title: '课时时长'}
        ]]
    });
    layer.open({
        title: '上课记录'
        , type: 1
        , area: ['1150px', '750px']
        , content: $('#recordDia')
        , btn: ['关闭']
        , btn1: function () {
            $('#recordDia').hide();
            layer.closeAll();
        }
        , cancel: function (index, layero) {
            $('#recordDia').hide();
            layer.closeAll();
        }
    });
}







