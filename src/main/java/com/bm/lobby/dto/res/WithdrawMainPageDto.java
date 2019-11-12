package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class WithdrawMainPageDto extends BaseEntity {

    @ApiModelProperty(value = "提现金额", example = "5")
    private int amout;

    @ApiModelProperty(value = "条件", example = "连续活跃10天")
    private String condition;

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
