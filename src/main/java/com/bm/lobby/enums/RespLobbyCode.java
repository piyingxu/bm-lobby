package com.bm.lobby.enums;

import com.bm.lobby.dto.base.RespCode;

public enum RespLobbyCode implements RespCode {

    //系统繁忙
    SYSTEM_BUSY("LOBBY_000000", "The system is busy, please try again later"),
    //验签错误
    VERIFY_SIGN_FAIL("LOBBY_000001", "Verify sign fail"),
    //Token失效
    TOKEN_TIMEOUT("LOBBY_000002", "Token time out"),
    // 参数为空
    PARAM_NULL("LOBBY_000003", "Param null"),
    // 安全校验失败
    SECURITY_VALIDATE_ERROR("LOBBY_000004", "Security validate fail"),
    // 数据异常
    DATA_ERROR("LOBBY_000005", "Data error"),
    // 重复请求
    REPEAT_REQUEST("LOBBY_000006", "Repeat request"),
    // 参数异常
    PARAM_ERROR("LOBBY_000007", "Request param error"),
    // 第三方授权失败
    AUTH_ERROR("LOBBY_000008", "ThirdPart auth failure"),




    MAX("LOBBY_999999", "Max");

    private String code;
	
    private String msg;

    RespLobbyCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
