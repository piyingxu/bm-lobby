package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class PlayerInfoRes extends BaseEntity {

    @ApiModelProperty(value = "姓名", example = "偷心爵爷")
    private String nickName;

    @ApiModelProperty(value = "头像", example = "")
    private String headUrl;

    @ApiModelProperty(value = "金币", example = "")
    private Long gold;

    @ApiModelProperty(value = "玩家ID", example = "1000001")
    private String pid;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}