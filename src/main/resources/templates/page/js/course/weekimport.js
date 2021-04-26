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
        elem: '#week'
        ,range: true
    });

    dataTable = table.render({
        elem: '#data_table'
        , url: '/rest/attendWeeks/findUserWithHours'
        // , page: {
        //     limit: 10
        //     , curr: 1
        //     , groups: 5
        //     , first: false
        //     , last: false
        //     , layout: ['limit', 'prev', 'page', 'next', 'count'] //自定义分页布局
        // }
        , response: {
            statusCode: 'OK' //重新规定成功的状态码为 200，table 组件默认为 0
        }
        // , parseData: function (res) { //res 即为原始返回的数据
        //     console.log(res.data.records)
        //     return {
        //         "code": res.code, //解析接口状态
        //         "msg": res.message, //解析提示文本
        //         "count": res.data.total, //解析数据长度
        //         "data": res.data.records //解析数据列表
        //     };
        // }
        , cols: [[
            {type: 'checkbox'}
            , {field: 'name', title: '教师名称'}
            , {field: 'hours', title: '本周教师已记录课时'}
            , {field: 'totalHours', title: '教师已记录总课时'}
            , {field: 'totalHoursByAdmin', title: '秘书已记录周课时总计'}
            , {fixed: 'right', title: '操作', toolbar: '#bar'}
        ]]
    });

    //监听行工具事件
    table.on('tool(data_table)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'edit') {
            weekImport(obj);
        }
    });

});

function genWeek() {
    $.ajax({
        type: "post",
        url: '/rest/attendWeeks/genWeek?range=' + $('#week').val(),
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


function weekImport(obj) {
    $('week').val('')
    $('#userId').val(obj.data.id)
    layer.open({
        title: '录入教师周课时'
        , type: 1
        , area: ['700px', '450px']
        , content: $('#add')
        , btn: ['录入', '关闭']
        , yes: function (index, layero) {
            let hour = $("#add input[name='hour']").val()
            let time = $("#add input[name='time']").val()
            let userId = obj.data.id
            $.ajax({
                type: "post",
                url: '/rest/attendWeeks/importWeek?range=' + time + '&hour=' + hour + '&userId=' + userId,
                // data: JSON.stringify(data),
                // contentType: "application/json; charset=utf-8",
                // dataType: "json",
                async: false,
                success: function (data) {
                    if (data.data) {
                        parent.layer.msg("录入成功", {icon: 1});
                        dataTable.reload()
                    } else {
                        parent.layer.msg("录入失败", {icon: 2})
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




function update(id, data) {

}
