//JS 
// import { sleep } from './util'
layui.use(['layer', 'form', 'table'], function () {
    var layer = layui.layer
        , form = layui.form
        , table = layui.table

    //验证会话
    var token = sessionStorage["token"];
    if (!token) {
        layer.msg('请先登陆', { icon: 7, time: 1000 });
        // window.setTimeout(window.open, 1000, '../pages/login.html', '_self');
    }
    
    //数据表首次渲染
    var tableIns = table.render({
        elem: '#goods'
        , url: '/api/goods' //数据接口
        , contentType: 'application/json'
        , headers: { Authentication: 'Bearer ' + token }
        , page: true //开启分页
        , cols: [[ //表头
            { field: 'name', title: '商品名', width: 300 }
            , { field: 'price', title: '单价', width: 100, sort: true }
            , { field: 'num', title: '数量', width: 100 }
            , { field: 'from', title: '来源', width: 300 }
            , { field: 'entry_price', title: '进价', width: 100, sort: true }
            , { fixed: 'right', width: 200, align: 'center', toolbar: '#operation' }
        ]]
    });

    //表单查询事件
    form.on("submit(query)", function (data) {
        tableIns.reload({ where: { query: form.val('search').keyword } });
        return false;   //取消submit事件
    })

    //登出
    document.getElementById('logout').onclick = function logout() {
        layer.confirm('确定退出登录？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            window.open('login.html', '_self');
        });
    }

    //工具条事件
    table.on('tool(goods)', function (obj) { //注：tool 是工具条事件名，goods 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'detail') { //查看
            //do somehing
        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
                fetch("/api/goods", {
                    method: "DELETE",
                    headers: headers
                })
                    .then(resp => resp.json())
                    .then(data => {
                        layer.close(index);
                        switch (data.state) {
                            case 0: //删除成功
                            case 1:
                                layer.msg('服务器删除资源失败！', { icon: 7, time: 1200 });
                            default:
                                console.log("response error.");
                        }
                    })
                    .catch(err => { layer.close(index); layer.msg('远程服务器错误！', { icon: 2, time: 1200 }); console.log(err); });
            });
        } else if (layEvent === 'edit') { //编辑
            sessionStorage['oldRow'] = form.val('editForm');
            window.open('./edit.js', '_self');
        }
    });
});