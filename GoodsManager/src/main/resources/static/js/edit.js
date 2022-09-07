/* edit.js */
// import { sleep } from './util'
layui.use(['layer', 'form'], function () {
    var layer = layui.layer
        , form = layui.form

    //验证会话
    var token = sessionStorage["token"];
    if (!token) {
        layer.msg('请先登陆', { icon: 7, time: 3000 });
        window.setTimeout(window.open, 3000, '/pages/login.html', '_self');
    }

    //取旧值
    var oldRow = JSON.parse(sessionStorage['oldRow']);
    if (oldRow) {
        console.log(oldRow);
        form.val('editForm', oldRow);
    }

    //表单事件
    form.on("submit(modify)", function (data) {
        var index = layer.load(1, { shade: 0.5 });   //载入遮罩层
        var field = data.field;
        var headers = new Headers();
        headers.append('Accept', 'application/json');
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', 'Bearer ' + token);
        fetch("/api/goods", {
            method: "POST",
            headers: headers,
            body: JSON.stringify({
                modify: true,
                old_name: oldRow.name,
                name: field.name,
                price: field.price,
                from: field.from,
                entry_price: field.entry_price,
                num: field.num
            })
        })
            .then(resp => resp.json())
            .then(data => {
                switch (data.state) {
                    case 0:
                        layer.msg('修改成功！', { time: 1200 });
                        window.open("/pages/manage.html", "_self");
                        break;
                    case 1:
                        layer.msg('商品名已存在', { icon: 7, time: 1200 });
                        break;
                    case 2:
                        layer.msg('编辑失败', { icon: 7, time: 1200 });
                        break;
                    default:
                        layer.msg('远程服务器错误！', { icon: 2, time: 1200 });
                }
            })
            .catch(err => {layer.msg('远程服务器错误！', { icon: 2, time: 1200 }); console.log(err); })
            .finally(() => {layer.close(index)})
        return false;   //取消submit事件
    })

    //登出
    document.getElementById('logout').onclick = function () {
        layer.confirm('确定退出登录？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            delete sessionStorage["token"];
            window.open('login.html', '_self');
        });
    }

    //重置旧数据
    document.getElementById('resetButton').onclick = function () {
        form.val('editForm', oldRow);
    }

    //返回主界面
    document.getElementById('cancelButton').onclick = function () {
        window.open('/pages/manage.html', '_self');
    }
});