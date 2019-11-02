package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class CheckInAwardReq extends BaseEntity {

    @ApiModelProperty(value = "是否看广告：0-无；1-有(奖励double)", example = "0")
    private int watchAd;

    @ApiModelProperty(value = "对应的时间点:0,4,8,12,16,20", example = "20")
    private int hour;

    public int getWatchAd() {
        return watchAd;
    }

    public void setWatchAd(int watchAd) {
        this.watchAd = watchAd;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
