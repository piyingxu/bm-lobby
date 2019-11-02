package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class CheckInRes extends BaseEntity {

    @ApiModelProperty(value = "时间点", example = "20")
    private int hour;

    @ApiModelProperty(value = "状态：0-不可领取(时间未到)；1-可领取(正点)；2-可领取(超点-补签)；3-已领取", example = "2")
    private int status;

    @ApiModelProperty(value = "可补签等待时间(秒)", example = "180")
    private long waitTime;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

}
