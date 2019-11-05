package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class GoldHistoryDto extends BaseEntity {

    @ApiModelProperty(value = "金币变动金额，可正负", example = "-1000或+1000")
    private long gold;

    @ApiModelProperty(value = "变动类型", example = "游戏收益")
    private String earnType;

    @ApiModelProperty(value = "变动明细", example = "玩连连看通过")
    private String earnDetail;

    @ApiModelProperty(value = "变动时间")
    private Date createTime;

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public String getEarnType() {
        return earnType;
    }

    public void setEarnType(String earnType) {
        this.earnType = earnType;
    }

    public String getEarnDetail() {
        return earnDetail;
    }

    public void setEarnDetail(String earnDetail) {
        this.earnDetail = earnDetail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
