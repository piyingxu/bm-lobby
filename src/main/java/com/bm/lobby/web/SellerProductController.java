package com.bm.lobby.web;

import com.bm.lobby.dto.base.PageDto;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.req.ProductReq;
import com.bm.lobby.model.DishesInfo;
import com.bm.lobby.service.ProductService;
import com.bm.lobby.util.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinqi
 * @date 2019/3/28
 * 商家商品（菜品）
 */
@Api(description = "商家商品（菜品）", tags = "seller-product")
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    private static final Logger log = LoggerFactory.getLogger(SellerProductController.class);
    @Autowired
    private ProductService productService;

    /**
     * @return com.bm.lobby.dto.base.RespResult<java.lang.String>
     * @Author yinqi
     * @Description
     * @Date 10:44 2019/3/28
     * @Param [req]
     **/
    @ApiOperation("1、商品(菜品)列表")
    @Log
    @PostMapping("/list")
    public RespResult<PageDto<DishesInfo>> queryProductList(@RequestBody ProductReq productReq) {
        return productService.queryProductList(productReq);
    }

}
