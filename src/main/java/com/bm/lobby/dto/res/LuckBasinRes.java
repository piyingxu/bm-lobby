package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/5 14:22
 */
public class LuckBasinRes extends BaseEntity {

    @ApiModelProperty(value = "每次金币消耗", example = "100")
    private long cost;

    @ApiModelProperty(value = "今日已玩次数", example = "1")
    private long playedNum;

    @ApiModelProperty(value = "字体颜色", example = "红色")
    private List<LuckBasinItem> item;

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getPlayedNum() {
        return playedNum;
    }

    public void setPlayedNum(long playedNum) {
        this.playedNum = playedNum;
    }

    public List<LuckBasinItem> getItem() {
        return item;
    }

    public void setItem(List<LuckBasinItem> item) {
        this.item = item;
    }
}
