package com.bm.lobby.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/5/7 16:42
 */
public class OrderSell  implements Serializable {

    @ApiModelProperty(value = "商户id", example = "1")
    private String merchantId;

    @ApiModelProperty(value = "栏目Id", example = "惹味前菜")
    private String categoryId;

    @ApiModelProperty(value = "栏目名称", example = "惹味前菜")
    private String categoryName;

    @ApiModelProperty(value = "菜名", example = "红烧排骨")
    private String dishName;

    @ApiModelProperty(value = "菜价", example = "80.00")
    private BigDecimal dishPrice;

    @ApiModelProperty(value = "菜品描述", example = "酸辣")
    private String dishDescription;

    @ApiModelProperty(value = "图片地址", example = "http://xxx.jpg")
    private String dishIcon;

    @ApiModelProperty(value = "库存", example = "100")
    private Integer dishStock;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishIcon() {
        return dishIcon;
    }

    public void setDishIcon(String dishIcon) {
        this.dishIcon = dishIcon;
    }

    public Integer getDishStock() {
        return dishStock;
    }

    public void setDishStock(Integer dishStock) {
        this.dishStock = dishStock;
    }
}
