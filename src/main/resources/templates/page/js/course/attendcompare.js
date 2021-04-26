var table;
var tree;
var layer;
var util;
var form;
var layarea;
var transfer;
var laydate;
var xmSelect;

var dataTable;

//配置插件目录
layui.config({
    base: '../src/lay/mod/'
    , version: '1.0'
});
layui.use(['table', 'tree', 'util', 'layarea', 'transfer', 'laydate', 'xmSelect'], function () {
    table = layui.table
    tree = layui.tree
        , layer = layui.layer
        , transfer = layui.transfer
        , util = layui.util
        , form = layui.form
        , layarea = layui.layarea
        , laydate = layui.laydate
        , xmSelect = layui.xmSelect

    laydate.render({
        elem: '#month'
        ,type: 'month'
    });

});


let select;

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
                // select = xmSelect.render({
                //     el: document.querySelector('#sel'),
                //     prop: {
                //         name: 'name',
                //         value: 'id',
                //     },
                //     data: data.data
                // })
                let ht = ''
                for (let i = 0; i < data.data.length; i++) {
                    ht += '<option value="'+ data.data[i].id +'">'+ data.data[i].name +'</option>'
                }
                $('#teacher1').append(ht)
            } else {
                parent.layer.msg(data.message, {icon: 2})
            }
        }
    });
}

function search(isAll) {
    let value = $('#teacher1').val();
    let month = $('#month').val();

    if (month == '') {
        layer.msg('请选择月份', {icon: 2})
        return;
    }

    dataTable = table.render({
        elem: '#data_table'
        , url: '/rest/courses/checkCompare?month=' + month + '&type=' + isAll
        , totalRow: true
        ,toolbar: true
        ,defaultToolbar: ['exports']
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
        ,done: function(res, page, count){
            console.log("aaa:",res)
            for (let i = 0; i < res.data.length; i++) {
                if (res.data[i].totalHours == res.data[i].totalHoursByAdmin) {
                    // res.data[i]["LAY_CHECKED"]='true';
                    var index= res.data[i]['LAY_TABLE_INDEX'];
                    $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                    $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                }
            }
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
            , {field: 'name', title: '教师名称'}
            , {field: 'totalHours', title: '教师当月录入课时'}
            , {field: 'totalHoursByAdmin', title: '秘书当月录入课时'}
            , {field: 'isMatch', title: '课时是否匹配', templet: function(d){
                if (d.totalHours != d.totalHoursByAdmin) {
                    return '<label style="color: #f62828">不匹配</label>'
                } else {
                    return '<label style="color: #2dac66">匹配</label>'
                }
    }}
            // , {fixed: 'right', title: '操作', toolbar: '#bar'}
        ]]
    });

}





