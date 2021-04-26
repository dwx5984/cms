
var table;
var tree;
var layer;
var util;
var form;

var dataTable;
layui.use(['table', 'tree', 'util'], function(){
    table = layui.table
    tree = layui.tree
        ,layer = layui.layer
        ,util = layui.util
        ,form = layui.form;

    dataTable = table.render({
        elem: '#menus_table'
        ,url:'/menus/page'
        ,totalRow: true
        ,page: {
            limit: 10
            ,curr: 1
            ,groups: 5
            ,first: false
            ,last: false
            ,layout: ['limit', 'prev', 'page', 'next', 'count'] //自定义分页布局
        }
        ,response: {
            statusCode: 'OK' //重新规定成功的状态码为 200，table 组件默认为 0
        }
        ,parseData: function(res){ //res 即为原始返回的数据
            console.log(res.data.records)
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.records //解析数据列表
            };
        },cols: [[
            {type:'checkbox'}
            ,{field:'name', title: '菜单名称'}
            ,{field:'status', title: '菜单状态', templet: function(d){
                    if (d.status === 'Yes')
                        return "显示"
                    return "隐藏"
                }}
            ,{fixed: 'right', title:'操作', toolbar: '#bar'}
        ]]
    });

    //监听行工具事件
    table.on('tool(menus_table)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    type: "post",
                    url: '/menus/delete/' + obj.data.id,
                    async: false,
                    success: function (data) {
                        if (data.data) {
                            layer.msg('删除成功', {icon:1})
                            dataTable.reload({page: {curr: 1}})
                        } else {
                            layer.msg('删除失败', {icon:2})
                        }
                    }
                });
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
           showEdit(obj);
        } else if (obj.event === 'child'){
            if (data.child == null || data.child.length < 1) {
                layer.msg("无下级菜单")
                return
            }
            // 加载子级数据
            dataTable.reload({
                url: '/menus/page?parentId=' + data.id
                ,page: {curr: 1}
            })
        } else if (obj.event === 'root') {
            dataTable.reload({
                url: '/menus/page'
            })
        }
    });

});


function showEdit(obj) {
    let data = obj.data
    $("#edit input[name='name']").val(data.name)
    form.val('layForm', {
        "check[status]": true
    });
    layer.open({
        title: '编辑'
        ,type: 1
        ,area: ['700px','450px']
        ,content: $('#edit')
        ,btn: ['修改','关闭']
        ,yes: function(index, layero){
            let name = $("#edit input[name='name']").val();
            let status = $("#edit input[name='status']").val();
            update(obj.data.id, {name: name, status: status})
            $('#eidt').hide();
            layer.closeAll();
        },btn2: function(){
            $('#eidt').hide();
            layer.closeAll();
        }
        , cancel: function(index, layero){
            $('#eidt').hide();
            layer.closeAll();
        }
    });
}

function update(roleId, data) {
    $.ajax({
        type: "put",
        url: '/menus/' + roleId,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        // dataType: "json",
        async: false,
        success: function (data) {
            if (data.data == 'Yes') {
                parent.layer.msg("修改成功", {icon:1});
                dataTable.reload()
            } else {
                parent.layer.msg("修改失败",{icon:2})
            }
        }
    });
}
