//JS
// import { sleep } from './util'
layui.use(['layer', 'form', 'table'], function () {
    let layer = layui.layer
        , form = layui.form
        , table = layui.table

    //验证会话
    let token = sessionStorage["token"];
    if (!token) {
        layer.msg('请先登陆', {icon: 7, time: 3000});
        window.setTimeout(window.open, 3000, '/pages/login.html', '_self');
    }

    // 获取货物总数
    function getGoodsCount(query) {
        let count = 0;
        let headers = new Headers();
        headers.append('Accept', 'application/json');
        headers.append('Authorization', 'Bearer ' + token);
        fetch("/api/goods/count?query=" + form.val('queryForm').keyword,
            {method: "GET", headers: headers})
            .then(resp => resp.json())
            .then(data => {
                count = data.count;
            })
            .catch(err => {
                console.log(err);
            })
        return count;
    }

    //第一个实例
    let tableIns = table.render({
        elem: '#goodsTable'
        , url: '/api/goods' //数据接口
        , page: {
            cur: 1
        }
        , headers: {Authorization: 'Bearer ' + token}
        , cols: [[ //表头
            {field: 'name', title: '商品名', width: 300}
            , {field: 'price', title: '单价', width: 150, sort: true}
            , {field: 'num', title: '数量', width: 150}
            , {field: 'from', title: '来源', width: 300}
            , {field: 'entry_price', title: '进价', width: 150, sort: true}
            , {fixed: 'right', width: 150, align: 'center', toolbar: '#operation'}
        ]]
    });

    //表单查询事件
    form.on("submit(query)", function (data) {
        console.log("hello3");
        console.log(form.val('queryForm').keyword);
        tableIns.reload({where: {
                query: form.val('queryForm').keyword
                , page: {
                    cur: 1
                }}});
        return false;   //取消submit事件
    });

    //登出
    document.getElementById('logout').onclick = function logout() {
        layer.confirm('确定退出登录？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            delete sessionStorage["token"];
            window.open('login.html', '_self');
        });
    };

    //工具条事件
    table.on('tool(goods)', function (obj) { //注：tool 是工具条事件名，goods 是 table 原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        console.log(data);
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let headers = new Headers();
        headers.append('Accept', 'application/json');
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', 'Bearer ' + token);

        if (layEvent === 'detail') { //查看
            //do somehing
        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                //向服务端发送删除指令
                fetch("/api/goods?name=" + data.name, {
                    method: "DELETE",
                    headers: headers
                })
                    .then(resp => resp.json())
                    .then(data => {
                        layer.close(index);
                        switch (data.state) {
                            case 0: //删除成功
                            case 1:
                            default:
                                layer.msg('服务器删除资源失败！', {icon: 7, time: 1200});
                                console.log("response error.");
                                break;
                        }
                    })
                    .catch(err => {
                        layer.close(index);
                        layer.msg('远程服务器错误！', {icon: 2, time: 1200});
                        console.log(err);
                    })
                    .finally(() => {layer.close(index);});
            });
        } else if (layEvent === 'edit') { //编辑
            sessionStorage['oldRow'] = JSON.stringify(data);
            window.open('/pages/edit.html', '_self');
        }
    });
});