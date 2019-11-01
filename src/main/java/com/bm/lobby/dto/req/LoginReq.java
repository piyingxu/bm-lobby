package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;
import com.bm.lobby.dto.base.PageBaseParam;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class LoginReq extends BaseEntity {

    @ApiModelProperty(value = "第三方认证类型：1-QQ 2-weichat", example = "2")
    private String authType;

    @ApiModelProperty(value = "第三方授权token", example = "4578954")
    private String authToken;

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
