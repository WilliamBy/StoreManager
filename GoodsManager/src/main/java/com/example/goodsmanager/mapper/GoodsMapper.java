package com.example.goodsmanager.mapper;

import com.example.goodsmanager.domain.GoodsDetails;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("select * from ${tbl} limit ${limit} offset ${offset}")
    List<GoodsDetails> getLimitEntries(@Param("tbl") String tbl, @Param("limit") int limit, @Param("offset") int offset);

    @Select("select * from ${tbl} where name like '%${query}%' limit ${limit} offset ${offset}")
    List<GoodsDetails> queryLimitEntries(@Param("tbl") String tbl, @Param("limit") int limit, @Param("offset") int offset, @Param("query") String query);

    @Select("select * from ${tbl} where name=#{name}")
    GoodsDetails getByName(@Param("tbl") String tbl, @Param("name") String name);

    @Delete("delete from ${tbl} where name=#{name}")
    boolean deleteByName(@Param("tbl") String tbl, @Param("name") String name);

    @Update("update ${tbl} set `name`=#{goods.name},`price`=${goods.price},`from`=#{goods.from},`entry_price`=${goods.entry_price},`num`=${goods.num} where `name`=#{old_name}")
    boolean updateByName(@Param("tbl") String tbl,
                         @Param("goods") GoodsDetails goods,
                         @Param("old_name") String old_name);

    @Insert("insert into ${tbl} (`name`,`price`,`num`,`from`,`entry_price`) values (#{goods.name},${goods.price},${goods.num},#{goods.from},${goods.entry_price})")
    boolean appendGoods(@Param("tbl") String tbl, @Param("goods") GoodsDetails goods);

    @Select("select * from ${tbl}")
    List<GoodsDetails> getEntries(@Param("tbl") String tbl);

    @Select("select * from ${tbl} where name like '%${query}%'")
    List<GoodsDetails> queryEntries(@Param("tbl") String tbl, @Param("query") String query);

}
