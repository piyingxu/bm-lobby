package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class CheckInAwardRes extends BaseEntity {

    @ApiModelProperty(value = "奖励金币", example = "100")
    private long gold;

    @ApiModelProperty(value = "下一次补签需等待时间(秒)", example = "180")
    private long waitTime;

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
