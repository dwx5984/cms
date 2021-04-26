var table;
var tree;
var layer;
var util;
var form;
var layarea;
var transfer;

var dataTable;

//配置插件目录
layui.config({
    base: '../src/lay/mod/'
    , version: '1.0'
});
layui.use(['table', 'tree', 'util', 'layarea', 'transfer'], function () {
    table = layui.table
    tree = layui.tree
        , layer = layui.layer
        , transfer = layui.transfer
        , util = layui.util
        , form = layui.form
        , layarea = layui.layarea



    let isTeacher = $('#isTeacher').val()
    let tableOpt;
    if (isTeacher == 'true') {
     tableOpt = {
         elem: '#data_table'
         , url: '/rest/chats/page?teacher=' + $('#isTeacher').val()
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
             , {field: 'sender', title: '发送人', templet: function(d) {
                     return d.sender.name
                 }}
             , {field: 'content', title: '内容'}
             , {field: 'createTime', title: '发送时间'}
         ]]
     }
    } else {
        tableOpt = {
            elem: '#data_table'
                , url: '/rest/chats/page?teacher=' + $('#isTeacher').val()
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
            , {field: 'senderId', title: '发送人', templet: '<div>{{d.sender.name}}</div>'}
            , {field: 'senderRole', title: '发送人角色'}
            , {field: 'content', title: '内容'}
            , {field: 'createTime', title: '发送时间'}
            // , {field: 'status', title: '消息状态'}
            , {fixed: 'right', title: '操作', templet: function(d) {
                if (d.senderRole == '教师') {
                    return '<a class="layui-btn layui-btn-xs" onclick="recall('+ d.teacherId + ')">回复</a><a class="layui-btn layui-btn-xs layui-btn-danger" onclick="del('+ d.id + ')">删除</a>'
                } else {
                    return '<a></a><a class="layui-btn layui-btn-xs layui-btn-danger" onclick="del('+ d.id + ')">删除</a>'
                }
            }}
        ]]
        }
    }

    dataTable = table.render(tableOpt);
});

function del(id) {
    let a = layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "delete",
            url: '/rest/chats/delete/'+ id,
            // data: JSON.stringify(obj),
            // contentType: "application/json; charset=utf-8",
            async: false,
            success: function (data) {
                if (data.code == 'OK') {
                    parent.layer.msg('删除成功', {icon: 1})
                    dataTable.reload();
                    layer.close(a)
                } else {
                    parent.layer.msg(data.message, {icon: 2})
                    layer.close(a)
                }
            }
        });
    })
}

function recall(teacherId) {
    console.log(teacherId)
    layer.open({
        title: '回复问题'
        , type: 1
        , area: ['650px', '550px']
        , content: $('#add')
        , btn: ['回复', '关闭']
        , yes: function (index, layero) {
            let addForm = form.val(`add`);
            console.log(addForm)
            if (addForm.content == '') {
                layer.msg("回复内容不能为空", {icon: 2})
                return;
            }
            let obj = {teacherId: teacherId, content: addForm.content}
            $.ajax({
                type: "post",
                url: '/rest/chats',
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (data) {
                    if (data.code == 'OK') {
                        parent.layer.msg('回复成功', {icon: 1})
                        dataTable.reload();
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


function search() {
    let param = form.val('searchForm')
    dataTable.reload({
        url: '/rest/chats/page?teacher='+ $('#isTeacher').val() +'&content=' + param.name
    })
}

function reset() {
    form.val("searchForm", {
        "name": ""
    });
    dataTable.reload({
        url: '/rest/chats/page?teacher='+ $('#isTeacher').val()
    })
}
