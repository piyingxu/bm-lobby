package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class CheckInDto extends BaseEntity {

    @ApiModelProperty(value = "时间点", example = "20")
    private int hour;

    @ApiModelProperty(value = "奖励的金币", example = "100")
    private long gold;

    @ApiModelProperty(value = "翻倍比率", example = "2")
    private int doubleRate;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getDoubleRate() {
        return doubleRate;
    }

    public void setDoubleRate(int doubleRate) {
        this.doubleRate = doubleRate;
    }
}
