package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class RankItemDTO extends BaseEntity {

    @ApiModelProperty(value = "排行：-1 代表未上榜", example = "1")
    private int top;

    @ApiModelProperty(value = "玩家id", example = "10000001")
    private String pid;

    @ApiModelProperty(value = "姓名", example = "偷心爵爷")
    private String nickName;

    @ApiModelProperty(value = "头像", example = "")
    private String headUrl;

    @ApiModelProperty(value = "分值", example = "10003")
    private long score;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

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

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
