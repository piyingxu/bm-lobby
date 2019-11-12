package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/5 13:57
 */
public class WithdrawMainPageRes extends BaseEntity {

    @ApiModelProperty(value = "金币", example = "")
    private Long gold;

    @ApiModelProperty(value = "支持的渠道：1-微信 2-支付宝", example = "")
    private List<Integer> payChannel;

    @ApiModelProperty(value = "当前活跃度", example = "2")
    private int activity;

    @ApiModelProperty(value = "提现金额配置", example = "")
    private List<WithdrawMainPageDto> withDrawAmont;

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public List<Integer> getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(List<Integer> payChannel) {
        this.payChannel = payChannel;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public List<WithdrawMainPageDto> getWithDrawAmont() {
        return withDrawAmont;
    }

    public void setWithDrawAmont(List<WithdrawMainPageDto> withDrawAmont) {
        this.withDrawAmont = withDrawAmont;
    }
}
