package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

public class DishesInfoReq extends BaseEntity {

    @ApiModelProperty(value = "菜品id", example = "2312321")
    private String id;

    @ApiModelProperty(value = "商户id", example = "2312321")
    private String merchantId;

    @ApiModelProperty(value = "菜名", example = "红烧排骨")
    private String dishName;

    @ApiModelProperty(value = "菜价", example = "80")
    private BigDecimal dishPrice;

    @ApiModelProperty(value = "菜品描述", example = "酸辣")
    private String dishDescription;

    @ApiModelProperty(value = "图片地址", example = "http://xxx.jpg")
    private String dishIcon;

    @ApiModelProperty(value = "菜品状态,0正常1下架", example = "1")
    private Boolean dishStatus;

    @ApiModelProperty(value = "库存", example = "100")
    private Integer dishStock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getDishStatus() {
        return dishStatus;
    }

    public void setDishStatus(Boolean dishStatus) {
        this.dishStatus = dishStatus;
    }

    public Integer getDishStock() {
        return dishStock;
    }

    public void setDishStock(Integer dishStock) {
        this.dishStock = dishStock;
    }
}