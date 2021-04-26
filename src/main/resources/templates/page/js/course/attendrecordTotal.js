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


    layarea.render({
        elem: '#area-picker',
        change: function (res) {
            //选择结果
            console.log(res);
        }
    });

    dataTable = table.render({
        elem: '#data_table'
        , url: '/rest/attendRecords/pageByUser?userId=' + $('#userId')
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
        if (obj.event === 'add') {
            add(obj);
        }
    });

});

function add(obj) {
    console.log(obj)
    $("#no").val(obj.data.no)
    $("#name").val(obj.data.name)
    $('#attendStartTime').val('')
    $('#attendEndTime').val('')
    laydate.render({
        elem: '#attendStartTime'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#attendEndTime'
        ,type: 'datetime'
    });
    layer.open({
        title: '添加上课记录'
        , type: 1
        , area: ['850px', '750px']
        , content: $('#add')
        , btn: ['新增', '关闭']
        , yes: function (index, layero) {
            let addForm = form.val(`add`);

            if (addForm.startTime == '') {
                layer.msg("上课时间不能为空", {icon: 2})
                return;
            }
            if (addForm.endTime == '') {
                layer.msg("上课时间不能为空", {icon: 2})
                return;
            }
            let o = {attendStartTime: addForm.startTime, attendEndTime: addForm.endTime, courseId: obj.data.id}
            $.ajax({
                type: "post",
                url: '/rest/attendRecords',
                data: JSON.stringify(o),
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (data) {
                    if (data.code == 'OK') {
                        parent.layer.msg("保存成功", {icon: 1});
                        dataTable.reload()
                    } else {
                        parent.layer.msg(data.message, {icon: 2})
                    }
                }
            });
            $('#add').hide();
            layer.closeAll();
        }, btn2: function () {
            $('#add').hide();
            layer.closeAll();
        }
        , cancel: function (index, layero) {
            $('#add').hide();
            layer.closeAll();
        }
    });
}

function back() {
    window.history.go(-1);
}


