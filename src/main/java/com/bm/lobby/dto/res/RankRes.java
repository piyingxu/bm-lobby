package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import java.util.List;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/4 14:28
 */
public class RankRes extends BaseEntity {

    private List<RankItemDTO> rankList;

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
