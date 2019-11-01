package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:29
*/

public class LoginRes extends BaseEntity {

    @ApiModelProperty(value = "请求token", example = "werd342312")
    private String token;

    @ApiModelProperty(value = "姓名", example = "偷心爵爷")
    private String nickName;

    @ApiModelProperty(value = "头像", example = "")
    private String headUrl;

    @ApiModelProperty(value = "功能开关", example = "")
    private Map<String, Boolean> funSwitch;

    @ApiModelProperty(value = "金币", example = "")
    private Long gold;

    @ApiModelProperty(value = "玩家ID", example = "1000001")
    private String pid;

    @ApiModelProperty(value = "玩家ID", example = "1000001")
    private List<GameDto> gameList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Map<String, Boolean> getFunSwitch() {
        return funSwitch;
    }

    public void setFunSwitch(Map<String, Boolean> funSwitch) {
        this.funSwitch = funSwitch;
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

    public List<GameDto> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameDto> gameList) {
        this.gameList = gameList;
    }
}
