package com.bm.lobby.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DishesInfo implements Serializable {

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
    private Integer dishStatus;

    @ApiModelProperty(value = "库存", example = "100")
    private Integer dishStock;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName == null ? null : dishName.trim();
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
        this.dishDescription = dishDescription == null ? null : dishDescription.trim();
    }

    public String getDishIcon() {
        return dishIcon;
    }

    public void setDishIcon(String dishIcon) {
        this.dishIcon = dishIcon == null ? null : dishIcon.trim();
    }

    public Integer getDishStatus() {
        return dishStatus;
    }

    public void setDishStatus(Integer dishStatus) {
        this.dishStatus = dishStatus;
    }

    public Integer getDishStock() {
        return dishStock;
    }

    public void setDishStock(Integer dishStock) {
        this.dishStock = dishStock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DishesInfo other = (DishesInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getDishName() == null ? other.getDishName() == null : this.getDishName().equals(other.getDishName()))
            && (this.getDishPrice() == null ? other.getDishPrice() == null : this.getDishPrice().equals(other.getDishPrice()))
            && (this.getDishDescription() == null ? other.getDishDescription() == null : this.getDishDescription().equals(other.getDishDescription()))
            && (this.getDishIcon() == null ? other.getDishIcon() == null : this.getDishIcon().equals(other.getDishIcon()))
            && (this.getDishStatus() == null ? other.getDishStatus() == null : this.getDishStatus().equals(other.getDishStatus()))
            && (this.getDishStock() == null ? other.getDishStock() == null : this.getDishStock().equals(other.getDishStock()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getDishName() == null) ? 0 : getDishName().hashCode());
        result = prime * result + ((getDishPrice() == null) ? 0 : getDishPrice().hashCode());
        result = prime * result + ((getDishDescription() == null) ? 0 : getDishDescription().hashCode());
        result = prime * result + ((getDishIcon() == null) ? 0 : getDishIcon().hashCode());
        result = prime * result + ((getDishStatus() == null) ? 0 : getDishStatus().hashCode());
        result = prime * result + ((getDishStock() == null) ? 0 : getDishStock().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", dishName=").append(dishName);
        sb.append(", dishPrice=").append(dishPrice);
        sb.append(", dishDescription=").append(dishDescription);
        sb.append(", dishIcon=").append(dishIcon);
        sb.append(", dishStatus=").append(dishStatus);
        sb.append(", dishStock=").append(dishStock);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}