
var table;
var tree;
var layer;
var util;
var form;
var layarea;

var dataTable;

//配置插件目录
layui.config({
    base: '../src/lay/mod/'
    , version: '1.0'
});
layui.use(['table', 'tree', 'util','layarea'], function(){
    table = layui.table
    tree = layui.tree
        ,layer = layui.layer
        ,util = layui.util
        ,form = layui.form
        ,layarea = layui.layarea


    layarea.render({
        elem: '#area-picker',
        change: function (res) {
            //选择结果
            console.log(res);
        }
    });

    dataTable = table.render({
        elem: '#data_table'
        ,url:'/users/page'
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
            ,{field:'name', title: '用户名'}
            ,{field:'number', title: '工号'}
            ,{field:'mobile', title: '手机号'}
            ,{field:'email', title: '邮箱'}
            ,{field:'gender', title: '性别', templet: function(d){
                    if (d.gender === 'MAN')
                        return "男"
                    return "女"
                }}
            ,{field:'status', title: '登录状态', templet: function(d){
                    if (d.status === 'Yes')
                        return "正常"
                    return "不可登录"
                }}
            ,{fixed: 'right', title:'操作', toolbar: '#bar'}
        ]]
    });

    //监听行工具事件
    table.on('tool(data_table)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('确定删除？', function(index){
                $.ajax({
                    type: "post",
                    url: '/users/delete/' + obj.data.id,
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

function add() {
    $("#roleId option[vv=ADMIN]").attr("disabled", "disabled")
    layui.form.render()
    form.val('add', {
        "name": '',
        "number": '',
        "mobile": '',
        "email": '',
        "password": '',
        "passwordA": '',
        "address": '',
    });
    layer.open({
        title: '新增用户'
        ,type: 1
        ,area: ['850px','750px']
        ,content: $('#add')
        ,btn: ['新增','关闭']
        ,yes: function(index, layero){
            let addForm = form.val(`add`);
            console.log(addForm)
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(addForm.name)) {
                layer.msg("请填写正确的用户名", {icon:2})
                return;
            }
            if (addForm.mobile === '') {
                layer.msg("请填写手机号", {icon:2})
                return;
            }
            if (!new RegExp('^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\\d{8}$').test(addForm.mobile)) {
                layer.msg("请填写正确的手机号", {icon:2})
                return;
            }
            if (addForm.email === '') {
                layer.msg("请填写邮箱", {icon:2})
                return;
            }
            if (!(/^([a-z0-9]+[_|\_|\.]?)*[a-z0-9]+@([a-z0-9]+[_|\_|\.]?)*[a-z0-9]+\.[a-z]{2,3}$/.test(addForm.email))) {
                layer.msg("请填写正确的邮箱", {icon:2})
                return;
            }
            if (!new RegExp("^[\\S]{6,12}$").test(addForm.password)) {
                layer.msg("密码必须6到12位，且不能出现空格", {icon:2})
                return;
            }
            if (addForm.password !== addForm.passwordA) {
                layer.msg("两次输入的密码不一致", {icon:2})
                return;
            }
            if (addForm.roleId == '') {
                layer.msg("请选择角色", {icon:2})
                return;
            }

            $.ajax({
                type: "post",
                url: '/users',
                data: JSON.stringify(addForm),
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (data) {
                    if (data.code == 'OK') {
                        parent.layer.msg("保存成功", {icon:1});
                        dataTable.reload()
                    } else {
                        parent.layer.msg(data.message,{icon:2})
                    }
                }
            });
            $('#add').hide();
            layer.closeAll();
        },btn2: function(){
            $('#add').hide();
            layer.closeAll();
        }
        , cancel: function(index, layero){
            $('#add').hide();
            layer.closeAll();
        }
    });
}

function search() {
    let param = form.val('searchForm')
    dataTable.reload({
        url: '/users/page?name='+param.name+'&number='+param.number+'&mobile='+param.mobile+'&email='+param.email
    })
}
function reset() {
    form.val("searchForm", {
        "name": ""
        ,"number": ""
        ,"mobile": ''
        ,"email": ''
    });
    dataTable.reload({
        url: '/users/page'
    })
}


function showEdit(obj) {
    let data = obj.data
    form.val('add', {
        "name": data.name,
        "number": data.number,
        "mobile": data.mobile,
        "email": data.email,
        "password": data.password,
        "passwordA": data.password,
        "address": data.address,
        "check[gender]": true,
    });
    if (data.gender == 'MAN') {
        $("#woman").removeAttr("checked");
        $("#man").attr("checked", true);
        layui.form.render();
    } else {
        $("#man").removeAttr("checked");
        $("#woman").attr("checked",true);
        layui.form.render();
    }
    $("#roleId option[vv=ADMIN]").removeAttr("disabled")
    layui.form.render();
    let select = 'dd[lay-value="'+ data.role.id +'"]'
    $('#roleId').siblings("div.layui-form-select").find('dl').find(select).click()

    let city = 'dd[lay-value="'+ data.city +'"]'
    $('#city').siblings("div.layui-form-select").find('dl').find(city).click()
    let province = 'dd[lay-value="'+ data.province +'"]'
    $('#province').siblings("div.layui-form-select").find('dl').find(province).click()
    let district = 'dd[lay-value="'+ data.district +'"]'
    $('#district').siblings("div.layui-form-select").find('dl').find(district).click()

    $('#id').val(data.id)
    // }
    // layui.form.render();
    layer.open({
        title: '编辑'
        ,type: 1
        ,area: ['850px','750px']
        ,content: $('#add')
        ,btn: ['修改','关闭']
        ,yes: function(index, layero){
            let addForm = form.val(`add`);
            console.log(addForm)
            update(addForm)
            $('#add').hide();
            layer.closeAll();
        },btn2: function(){
            $('#add').hide();
            layer.closeAll();
        }
        , cancel: function(index, layero){
            $('#add').hide();
            layer.closeAll();
        }
    });
}

function update(addForm) {
    $.ajax({
        type: "post",
        url: '/users/update',
        data: JSON.stringify(addForm),
        contentType: "application/json; charset=utf-8",
        // dataType: "json",
        async: false,
        success: function (data) {
            if (data.code == 'OK') {
                parent.layer.msg("修改成功", {icon:1});
                dataTable.reload()
            } else {
                parent.layer.msg("修改失败",{icon:2})
            }
        }
    });
}
