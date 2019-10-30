package com.bm.lobby.service;

import com.bm.lobby.dto.base.PageDto;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.model.DishesInfo;

import java.util.List;

/**
 * @author yinqi
 * @date 2019/3/27
 * 商品(菜品)
 */
public interface ProductService {

    DishesInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<DishesInfo> findUpAll();

    /**
     * 分页查询所有在架商品列表（菜品）
     * @return
     */
    RespResult<PageDto<DishesInfo>> queryProductList(LoginReq req);

    DishesInfo save(DishesInfo productInfo);

//    //加库存
//    void increaseStock(List<CartDTO> cartDTOList);
//
//    //减库存
//    void decreaseStock(List<CartDTO> cartDTOList);
//
//    //上架
//    ProductInfo onSale(String productId);
//
//    //下架
//    ProductInfo offSale(String productId);
}
