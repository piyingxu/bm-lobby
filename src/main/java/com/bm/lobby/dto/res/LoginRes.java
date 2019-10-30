package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

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
    private Map<String, Boolean> funSwtich;

    @ApiModelProperty(value = "金币", example = "")
    private String gold;

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

    public Map<String, Boolean> getFunSwtich() {
        return funSwtich;
    }

    public void setFunSwtich(Map<String, Boolean> funSwtich) {
        this.funSwtich = funSwtich;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }
}
