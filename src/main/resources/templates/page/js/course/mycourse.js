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
        , url: '/rest/courses/pageByUser'
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
        if (obj.event === 'exchange') {
            showEdit(obj);
        }
    });

});

$(function () {
    $.ajax({
        type: "get",
        url: '/users/byRole/300?excludeMe=true',
        async: false,
        success: function (data) {
            console.log(data)
            if (data.code === 'OK') {
                let ht = '<option value="">直接选择或搜索选择</option>'
                for (let i = 0; i < data.data.length; i++) {
                    ht += '<option value="'+ data.data[i].id +'">'+ data.data[i].name +'</option>'
                }
                $('#teacher').append(ht)

            } else {
                parent.layer.msg(data.message, {icon: 2})
            }
        }
    });

})

function showEdit(obj) {
    laydate.render({
        elem: '#startTime'
        ,type: 'datetime'
        ,min: 0
    });
    laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
        ,min: 0
    });
    layer.open({
        title: '调课'
        , type: 1
        , area: ['1150px', '750px']
        , content: $('#add')
        , btn: ['确定','关闭']
        , btn1: function () {
            let addForm = form.val(`add`);
            addForm.courseId=obj.data.id

            console.log(addForm)
            // TODO 校验
            $.ajax({
                type: "post",
                url: '/rest/exchangeRecords',
                data: JSON.stringify(addForm),
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (data) {
                    console.log(data)
                    if (data.code === 'OK') {
                        parent.layer.msg("申请成功", {icon: 1});
                        $('#add').hide();
                        layer.closeAll();
                    } else {
                        parent.layer.msg(data.message, {icon: 2})
                        $('#add').hide();
                        layer.closeAll();
                    }
                }
            });
        }
        , btn2: function () {
            $('#add').hide();
            layer.closeAll();
        }
        , cancel: function (index, layero) {
            $('#add').hide();
            layer.closeAll();
        }
    });

}


function search() {
    let param = form.val('searchForm')
    dataTable.reload({
        url: '/rest/courses/pageByUser?name=' + param.name
    })
}

function reset() {
    form.val("searchForm", {
        "name": ""
    });
    dataTable.reload({
        url: '/rest/courses/pageByUser'
    })
}



