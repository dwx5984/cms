
var layer;
var util;
var form;
var layarea;

//配置插件目录
layui.config({
    base: '../src/lay/mod/'
    , version: '1.0'
});
layui.use(['layer', 'form','layarea'], function() {
        layer = layui.layer
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

});

$(function () {
    // getMenus();

    getUserInfo();

    $('#logout').click(function (e) {
        $.ajax({
            type: "get",
            url: '/class/logout',
            // data: JSON.stringify({"loginName":data.loginName, "password":data.password}),
            // contentType: "application/json; charset=utf-8",
            // dataType: "json",
            success: function (data) {
                if (data.code === 'FAIL') {
                    layer.msg(data.message, {icon: 2})
                } else if (data.code === 'OK') {
                    layer.msg(data.message, {icon: 1, time: 1500}, function (index) {
                        window.location.href = '/class/admin'
                        layer.close(index);
                    })
                }
            }
        });
    })

    findUnread()

    self.setInterval("findUnread()",10000);

})

function findUnread() {
    $.ajax({
        type: "get",
        url: '/rest/chats/unread/count',
        async: false,
        success: function (data) {
            if (data.code == 'OK') {
                if (data.data > 0) {
                    if ($('dd a[value=chat]').parent().parent().prev().find('span[vv=unread]').length > 0) {
                        $('dd a[value=chat]').find('span[vv=unread]').text(data.data)
                        $('dd a[value=chat]').parent().parent().prev().find('span[vv=unread]').text(data.data)
                    } else {
                        $('dd a[value=chat]').append('<span vv="unread" class="layui-badge">'+ data.data +'</span>')
                        $('dd a[value=chat]').parent().parent().prev().append('<span vv="unread"  class="layui-badge">'+ data.data +'</span>')
                    }
                } else {
                    console.log($('dd a[value=chat]').find('span[vv=unread]'));
                    $('dd a[value=chat]').find('span[vv=unread]').remove();
                    $('dd a[value=chat]').parent().parent().prev().find('span[vv=unread]').remove();
                }
            } else {
                parent.layer.msg(data.message, {icon: 2})
            }
        }
    });
}

let userData;
function getUserInfo() {
    $.ajax({
        type: "get",
        url: '/users/cur',
        // dataType: "json",
        async: false,
        success: function (data) {
            if (data.code == 'OK') {
                userData = data.data
                $('#name').text(data.data.name)
                $('#role').text(data.data.role.name)
            } else {
            }
        }
    });
}


function userInfo() {
    console.log(form)
    console.log(userData)
    form.val('edit', {
        "id": userData.id,
        "name": userData.name,
        "number": userData.number,
        "mobile": userData.mobile,
        "email": userData.email,
        "password": userData.password,
        "passwordA": userData.password,
        "address": userData.address,
    });
    if (userData.gender == 'MAN') {
        $("#woman").removeAttr("checked");
        $("#man").attr("checked", true);
        layui.form.render();
    } else {
        $("#man").removeAttr("checked");
        $("#woman").attr("checked",true);
        layui.form.render();
    }
    let city = 'dd[lay-value="' + userData.city + '"]'
    $('#city').siblings("div.layui-form-select").find('dl').find(city).click()
    let province = 'dd[lay-value="' + userData.province + '"]'
    $('#province').siblings("div.layui-form-select").find('dl').find(province).click()
    let district = 'dd[lay-value="' + userData.district + '"]'
    $('#district').siblings("div.layui-form-select").find('dl').find(district).click()

    var pass = false
    layer.open({
        title: '修改信息·'
        , type: 1
        , area: ['850px', '750px']
        , content: $('#edit')
        , btn: ['修改密码','修改', '关闭']
        , btn1: function () {
            $('#pass').show();
        }
        , btn2: function (index, layero) {
            let addForm = form.val(`edit`);
            console.log(addForm)
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(addForm.name)) {
                layer.msg("请填写正确的用户名", {icon: 2})
                return;
            }
            if (addForm.mobile === '') {
                layer.msg("请填写手机号", {icon: 2})
                return;
            }
            if (!new RegExp('^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\\d{8}$').test(addForm.mobile)) {
                layer.msg("请填写正确的手机号", {icon: 2})
                return;
            }
            if (addForm.email === '') {
                layer.msg("请填写邮箱", {icon: 2})
                return;
            }
            if (!(/^([a-z0-9]+[_|\_|\.]?)*[a-z0-9]+@([a-z0-9]+[_|\_|\.]?)*[a-z0-9]+\.[a-z]{2,3}$/.test(addForm.email))) {
                layer.msg("请填写正确的邮箱", {icon: 2})
                return;
            }
            if (addForm.oldPassword != '') {
                pass = true
            }
            if (addForm.password != '') {
                if (!new RegExp("^[\\S]{6,12}$").test(addForm.password)) {
                    layer.msg("密码必须6到12位，且不能出现空格", {icon: 2})
                    return;
                }
                if (addForm.password !== addForm.passwordA) {
                    layer.msg("两次输入的密码不一致", {icon: 2})
                    return;
                }
            }
            $.ajax({
                type: "put",
                url: '/users',
                data: JSON.stringify(addForm),
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (data) {
                    if (data.code == 'OK') {
                        if (pass) {
                            parent.layer.msg("修改成功, 需要重新登录", {icon: 1}, function () {
                                window.location.href = 'login'
                            });
                        } else {
                            parent.layer.msg("修改成功", {icon: 1}, function () {
                                window.location.href = 'admin'
                            });
                        }
                    } else {
                        parent.layer.msg(data.message, {icon: 2})
                    }
                }
            });
        }, btn3: function () {
            $('#edit').hide();
            layer.closeAll();
        }
        , cancel: function (index, layero) {
            $('#edit').hide();
            layer.closeAll();
        }
    });
}

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