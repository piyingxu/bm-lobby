package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/5 13:57
 */
public class WithdrawOrderRes extends BaseEntity {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "渠道：1-微信 2-支付宝")
    private Integer channel;

    @ApiModelProperty(value = "订单金额：单位")
    private Integer amount;

    @ApiModelProperty(value = "状态：1-审核中 2-成功  3-失败")
    private Integer status;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
