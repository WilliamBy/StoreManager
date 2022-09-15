//JS 
layui.use(['layer', 'form'], function () {
    var layer = layui.layer
        , form = layui.form
        , $ = layui.$;

    //表单验证
    form.verify({
        username: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
            if (value.length < 3 || value.length > 16) {
                return '用户名必须6到12位，且不能出现空格'
            }
        }

        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        , passwd: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ]
    });
    //表单事件
    form.on("submit(login)", function (data) {
        var index = layer.load(1, { shade: 0.5, title: "登入中..." });   //载入遮罩层
        var field = data.field;
        var headers = new Headers();
        headers.append('Accept', 'application/json');
        headers.append('Content-Type', 'application/json');
        fetch("/api/user", {
            method: "POST",
            mode: "same-origin",
            headers: headers,
            body: JSON.stringify({
                login: true,
                username: field.username,
                passwd: field.passwd
            })
        })
            .then(resp => resp.json())
            .then(data => {
                switch (data.state) {
                    case 0:
                        sessionStorage["token"] = data.token;   //存储会话token
                        layer.msg('登陆成功！', { time: 1000 });
                        window.open("/pages/manage.html", "_self");
                        break;
                    case 1:
                        layer.msg('用户名未注册！', { icon: 7, time: 1200 });
                        break;
                    case 2:
                        layer.msg('用户名或密码错误！', { icon: 7, time: 1200 });
                        break;
                    default:
                        layer.msg('远程服务器错误！', { icon: 2, time: 1200 });
                }
            })
            .catch(err => { layer.msg('远程服务器错误！', { icon: 2, time: 1200 }); console.log(err); })
            .finally(() => {layer.close(index);})
        return false;   //取消submit事件
    })
});