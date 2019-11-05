package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/5 14:22
 */
public class WalletRes extends BaseEntity {

    @ApiModelProperty(value = "金币", example = "1000")
    private long gold;

    @ApiModelProperty(value = "今日获得", example = "100")
    private long earnToday;

    @ApiModelProperty(value = "累计获得", example = "1000000")
    private long earnTotal;

    @ApiModelProperty(value = "近3日收支明细", example = "1000000")
    private List<GoldHistoryDto> goldHisList;

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getEarnToday() {
        return earnToday;
    }

    public void setEarnToday(long earnToday) {
        this.earnToday = earnToday;
    }

    public long getEarnTotal() {
        return earnTotal;
    }

    public void setEarnTotal(long earnTotal) {
        this.earnTotal = earnTotal;
    }

    public List<GoldHistoryDto> getGoldHisList() {
        return goldHisList;
    }

    public void setGoldHisList(List<GoldHistoryDto> goldHisList) {
        this.goldHisList = goldHisList;
    }
}
