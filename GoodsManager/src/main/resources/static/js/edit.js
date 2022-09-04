/* edit.js */
// import { sleep } from './util'
layui.use(['layer', 'form'], function () {
    var layer = layui.layer
        , form = layui.form

    //验证会话
    var token = sessionStorage["token"];
    if (!token) {
        layer.msg('请先登陆', { icon: 7, time: 1000 });
        window.setTimeout(window.open, 1000, '../pages/login.html', '_self');
    }

    //取旧值
    var oldRow = sessionStorage['oldRow'];
    if (oldRow) {
        form.val('editForm', oldRow)
    }

    //表单事件
    form.on("submit(modify)", function (data) {
        var index = layer.load(1, { shade: 0.5 });   //载入遮罩层
        var field = data.field;
        var headers = new Headers();
        headers.append('Accept', 'application/json');
        headers.append('Authentication', 'Bearer ' + token);
        fetch("/api/goods", {
            method: "POST",
            headers: headers,
            body: JSON.stringify({
                modify: true,
                name: field.name,
                price: field.price,
                from: field.from,
                entry_price: field.entry_price,
                num: field.num
            })
        })
            .then(resp => resp.json())
            .then(data => {
                layer.close(index);
                switch (data.state) {
                    case 0:
                        layer.msg('修改成功！', { time: 1000 });
                        window.open("/pages/manage.html", "_self");
                    case 1:
                        layer.msg('商品名已存在', { icon: 7, time: 1200 });
                    default:
                        console.log("response error.");
                }
            })
            .catch(err => { layer.close(index); layer.msg('远程服务器错误！', { icon: 2, time: 1200 }); console.log(err); });
        return false;   //取消submit事件
    })

    //登出
    document.getElementById('logout').onclick = function () {
        layer.confirm('确定退出登录？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            window.open('login.html', '_self');
        });
    }

    //重置旧数据
    document.getElementById('resetButton').onclick = function () {
        form.val('editForm', oldRow);
    }
});