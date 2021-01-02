$(function () {
    // getMenus();



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