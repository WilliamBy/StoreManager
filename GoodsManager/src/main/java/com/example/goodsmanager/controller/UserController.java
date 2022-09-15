package com.example.goodsmanager.controller;

import com.example.goodsmanager.auth.JwtAuthService;
import com.example.goodsmanager.domain.AuthRequest;
import com.example.goodsmanager.domain.User;
import com.example.goodsmanager.domain.UserAuthentication;
import com.example.goodsmanager.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final JwtAuthService jwtAuthService;
    private final UserMapper userMapper;
    private final JdbcTemplate jdbcTemplate;

    public UserController(UserMapper userMapper, JwtAuthService jwtAuthService, JdbcTemplate jdbcTemplate) {
        this.userMapper = userMapper;
        this.jwtAuthService = jwtAuthService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public UserAuthentication userLogin(@RequestBody AuthRequest authRequest) {
        UserAuthentication resp = new UserAuthentication();

        if (authRequest.isLogin()) {    // 登录
            User originUser = userMapper.getByUsername(authRequest.getUsername());
            if (originUser != null) {
                // 存在账户
                if (originUser.getPasswd().equals(authRequest.getPasswd())) {
                    // 密码正确
                    resp.setToken(jwtAuthService.createToken(authRequest.getUsername()));
                    resp.setState(0);
                } else {
                    resp.setState(2);
                }
            } else {
                resp.setState(1);
            }
        } else {    // 注册
            if (userMapper.getByUsername(authRequest.getUsername()) != null) {
                // 用户名已经存在
                resp.setState(1);
            } else {
                // 添加新账户到数据库
                userMapper.insert(authRequest.getUsername(), authRequest.getPasswd());
                jdbcTemplate.execute("CREATE TABLE " + authRequest.getUsername() + "_goods_tbl" +
                        "(`name` VARCHAR(20) NOT NULL UNIQUE ," +
                        "`price` INT NOT NULL ," +
                        "`entry_price` INT NOT NULL ," +
                        "`from` VARCHAR(20) NOT NULL ," +
                        "`num` INT NOT NULL)");
                resp.setState(0);
            }
        }

        return resp;
    }
}
