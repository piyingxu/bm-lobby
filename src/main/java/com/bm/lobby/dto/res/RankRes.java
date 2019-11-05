package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/4 14:28
 */
public class RankRes extends BaseEntity {

    @ApiModelProperty(value = "排行榜信息")
    private List<RankItemDTO> rankList;

    @ApiModelProperty(value = "我的排名信息")
    private RankItemDTO mine;

    public List<RankItemDTO> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankItemDTO> rankList) {
        this.rankList = rankList;
    }

    public RankItemDTO getMine() {
        return mine;
    }

    public void setMine(RankItemDTO mine) {
        this.mine = mine;
    }
}
