package com.example.goodsmanager.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.goodsmanager.auth.JwtAuthService;
import com.example.goodsmanager.domain.*;
import com.example.goodsmanager.mapper.GoodsMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
    private final JwtAuthService jwtAuthService;
    private final GoodsMapper goodsMapper;

    public GoodsController(JwtAuthService jwtAuthService, GoodsMapper goodsMapper) {
        this.jwtAuthService = jwtAuthService;
        this.goodsMapper = goodsMapper;
    }

    @GetMapping
    public GoodsTableData queryGoodsList(@RequestParam(value = "query", required = false) String query, @RequestParam("page") int page,
                                         @RequestParam("limit") int limit,
                                         @RequestHeader("Authorization") String Authorization) {
        List<GoodsDetails> goodsList = null;
        String username, tbl;
        int count = 0;  //数据总数
        try {
            String token = Authorization.split(" ")[1]; // Authorization: "Bearer xxx.xxx.xxx"
            username = jwtAuthService.verify(token);
            tbl = username + "_goods_tbl";
            if (query != null) {
                goodsList = goodsMapper.queryLimitEntries(tbl, limit, limit * (page - 1), query);
                count = goodsMapper.queryEntries(tbl, query).size();
            } else {
                goodsList = goodsMapper.getLimitEntries(tbl, limit, limit * (page - 1));
                count = goodsMapper.getEntries(tbl).size();
            }
        } catch (JWTVerificationException e) {
            return new GoodsTableData(1, count, "unauthorized", null);
        }
        return new GoodsTableData(0, count, "success", goodsList);
    }

    @PostMapping
    public SimpleState ModifyGoods(@RequestBody GoodsDetailsModification modification,
                                                @RequestHeader("Authorization") String Authorization) {
        String targetGoodsName = modification.getName(); // 要添加或者修改的新名称
        GoodsDetails existedGoods;    // 商品列表中已经存在的匹配新名称的商品
        String username, tbl;
        try {
            String token = Authorization.split(" ")[1]; // Authorization: "Bearer xxx.xxx.xxx"
            username = jwtAuthService.verify(token);
        } catch (JWTVerificationException e) {
            return new SimpleState(1);
        }
        tbl = username + "_goods_tbl";
        existedGoods = goodsMapper.getByName(tbl, targetGoodsName);
        if (modification.isModify()) {
            // 修改货物信息
            if (existedGoods != null && !existedGoods.getName().equals(modification.getOld_name())) {
                // 货物名称冲突
                return new SimpleState(1);
            }
            if(!goodsMapper.updateByName(tbl, modification, modification.getOld_name())) {
                // 更新信息失败
                return new SimpleState(2);
            }
        } else {
            // 添加货物
            if (goodsMapper.getByName(tbl, targetGoodsName) != null) {
                // 货物名称冲突
                return new SimpleState(1);
            }
            if (!goodsMapper.appendGoods(tbl, modification)) {
                // 插入失败
                return new SimpleState(2);
            }
        }
        return new SimpleState(0);
    }

    @DeleteMapping
    public SimpleState deleteGoods(@RequestParam("name") String name,
                                   @RequestHeader("Authorization") String Authorization) {
        String username;
        try {
            String token = Authorization.split(" ")[1]; // Authorization: "Bearer xxx.xxx.xxx"
            username = jwtAuthService.verify(token);
        } catch (JWTVerificationException e) {
            return new SimpleState(1);
        }
        if (!goodsMapper.deleteByName(username + "_goods_tbl", name)) {
            return new SimpleState(1);
        }
        return new SimpleState(0);
    }

    @GetMapping("/count")
    public GoodsCount getCount(@RequestParam(value="query", required = false) String query,
                               @RequestHeader("Authorization") String Authorization) {
        String username, tbl;
        List<GoodsDetails> entries = null;
        try {
            String token = Authorization.split(" ")[1]; // Authorization: "Bearer xxx.xxx.xxx"
            username = jwtAuthService.verify(token);
            tbl = username + "_goods_tbl";
        } catch (JWTVerificationException e) {
            return new GoodsCount(1, 0);
        }
        if (query == null) {
            entries = goodsMapper.getEntries(tbl);
        } else {
            // 检索关键词
            entries = goodsMapper.queryEntries(tbl, query);
        }
        return new GoodsCount(0, entries == null ? 0 : entries.size());
    }
}
