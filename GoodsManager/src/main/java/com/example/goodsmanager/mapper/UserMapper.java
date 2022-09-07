package com.example.goodsmanager.mapper;

import com.example.goodsmanager.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select passwd from user_tbl where username=#{username}")
    User getByUsername(@Param("username") String username);

    @Insert("insert into user_tbl (username,passwd) values (#{username}, #{passwd})")
    void insert(@Param("username") String username, @Param("passwd") String passwd);
}
