package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 15:43
 */
public class HttpHeadReq extends BaseEntity {

    private String token;

    private String appId;

    private String deviceId;

    private String deviceType;

    private String clientVer;

    private String sign;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getClientVer() {
        return clientVer;
    }

    public void setClientVer(String clientVer) {
        this.clientVer = clientVer;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
