
var table;
var tree;
var layer;
var util;
var form;
layui.use(['table', 'tree', 'util'], function(){
    table = layui.table
    tree = layui.tree
        ,layer = layui.layer
        ,util = layui.util
        ,form = layui.form;

    table.render({
        elem: '#roles_table'
        ,url:'/roles/page'
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
            ,{field:'name', title: '角色名称'}
            ,{field:'status', title: '角色状态', templet: function(d){
                if (d.status === 'Yes')
                    return "正常"
                return "失效"
                }}
            ,{fixed: 'right', title:'操作', toolbar: '#barRole', width:150}
        ]]
    });

    //监听行工具事件
    table.on('tool(roles_table)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'permissionShow'){
           showPermissions(obj);
        }
    });

});


function showPermissions(obj) {
    console.log(obj)
    // let data = {}
    $.ajax({
        type: "get",
        url: '/roles/findPermission/' + obj.data.id,
        // data: JSON.stringify({"loginName":data.loginName, "password":data.password}),
        // contentType: "application/json; charset=utf-8",
        // dataType: "json",
        async: false,
        success: function (data) {
            // data = data


            //渲染
            var inst1 = tree.render({
                elem: '#permissionsTree'  //绑定元素
                ,data: data
                ,showCheckbox: true
                ,showLine: false
                ,accordion: true
                ,id: 'permissionTree'
                ,checkChild:false
            });
            /**
             * 权限列表
             */
            layer.open({
                title: '权限列表'
                ,type: 1
                ,area: ['700px','450px']
                ,content: $('#permissions')
                ,btn: ['修改','关闭']
                ,yes: function(index, layero){
                    console.log(tree.getChecked('permissionTree'))
                    updatePermission(obj.data.id, tree.getChecked('permissionTree'))
                    parent.layer.msg("修改成功", {icon:1});
                    $('#permissions').hide();
                    layer.closeAll();
                },btn2: function(){
                    $('#permissions').hide();
                    layer.closeAll();
                }
                , cancel: function(index, layero){
                    // if(confirm('确定要关闭么')){ //只有当点击confirm框的确定时，该层才会关闭
                    //     layer.close(index)
                    // }
                    $('#permissions').hide();
                    layer.closeAll();
                }
            });

        }
    });

}

function updatePermission(roleId, data) {
    $.ajax({
        type: "post",
        url: '/roles/updatePermission/' + roleId,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        // dataType: "json",
        async: false,
        success: function (data) {
            console.log(data)
        }
    });
}


function add(){
    layer.open({
        title: '新增角色'
        ,type: 1
        ,area: ['700px','450px']
        ,content: $('#addForm')
        ,btn: ['新增','取消']
        ,yes: function(index, layero){
            saveRole();
        },btn2: function(){
            // $('#permissionsTree').empty();
            $('#addForm').hide();
            layer.closeAll();
        }
        , cancel: function(index, layero){
            $('#addForm').hide();
            layer.closeAll();
        }
    });
}

function saveRole() {
    //表单取值
    // layui.$('#LAY-component-form-getval').on('click', function(){
    // form.verify({
    //     name: function(value){
    //         console.log(value)
    //
    //     }
    // });
    // if(value === '' || value === null || value === 'undefined'){
    //     return '角色名不能为空';
    // }
    var data = form.val('addRole');
    if (data === {} || data.name == '') {
            layer.msg('角色名不能为空', {icon:2});
            return
    }
    alert(JSON.stringify(data));
    $.ajax({
        type: "POST",
        url: '/roles',
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            alert(data)
            $('#addForm').hide();
            layer.closeAll();
            parent.layer.msg("新增成功", {icon:1})
        }
    });
    window.location.reload()
}

$(function () {
    // getMenus();
})

// function getMenus() {
//     $.get('/menus/all', function (res) {
//         console.log(res);
//         if (res.data != null) {
//             genMenus(res.data)
//         }
//     })
// }
// function genMenus(data) {
//     var root = ''
//     var two = ''
//     var select = 'layui-nav-itemed'
//     for (var i = 0; i < data.length; i ++) {
//         root += '<li class="layui-nav-item">' +
//             '          <a class="" href="javascript:;">'+ data[i].name+'</a>' +
//             '          <dl class="layui-nav-child">'
//         if (data[i].child != null && data[i].child.length > 0) {
//             for (var j = 0; j < data[i].child.length; j++) {
//                 two += '<dd><a href="javascript:;">'+ data[i].child[j].name +'</a></dd>'
//             }
//         }
//         root += two + '</dl>' + '</li>'
//         two = ''
//     }
//     $('#menus').append(root)
// }
// <li class="layui-nav-item layui-nav-itemed">
//     <a class="" href="javascript:;">所有商品</a>
//     <dl class="layui-nav-child">
//         <dd><a href="javascript:;">列表一</a></dd>
//         <dd><a href="javascript:;">列表二</a></dd>
//         <dd><a href="javascript:;">列表三</a></dd>
//         <dd><a href="">超链接</a></dd>
//     </dl>
// </li>