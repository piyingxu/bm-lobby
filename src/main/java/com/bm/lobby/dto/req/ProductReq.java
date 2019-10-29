package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.PageBaseParam;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品(包含类目)
 * Created by yinqi
 * 2019-03-28
 */
public class ProductReq extends PageBaseParam {

    private static final long serialVersionUID = 6486413067728884469L;

    @ApiModelProperty(value = "商户id", example = "7866619", required = true)
    private String merchantId;

    @ApiModelProperty(value = "菜名", example = "红烧排骨")
    private String dishName;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
