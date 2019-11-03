package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class RankReq extends BaseEntity {

    @ApiModelProperty(value = "排行榜类型：0-财富榜；1-赚金榜", example = "0")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
