package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/12 15:18
 */
public class GameEndReq extends BaseEntity {

    @ApiModelProperty(value = "对局ID", example = "201911120145")
    private String playid;

    @ApiModelProperty(value = "对局得分", example = "-100")
    private int score ;

    @ApiModelProperty(value = "当前关卡", example = "1")
    private String chapter;

    public String getPlayid() {
        return playid;
    }

    public void setPlayid(String playid) {
        this.playid = playid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
