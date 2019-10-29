package com.bm.lobby.dto.base;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/3/27 15:25
 */
public enum RespSystemCode implements RespCode {
    /**
     * 成功
     */
    SUCCESS("00000000", "success"),

    /**
     * 未知结果
     */
    UNKNOWN_RESULT("99999999", "unknown result"),

    /**
     * 服务器异常
     */
    SYSTEM_ERROR("-11", "system error"),

    /**
     * 参数异常
     */
    PARAM_ERROR("-12", "request params error"),

    /**
     * 返回结果为null
     */
    RESPONSE_IS_NULL("-13", "response is null"),

    /**
     * 值不能为empty
     */
    VALUE_NOT_EMPTY("-14", "can`t be blank"),

    /**
     * feignclient调用服务异常
     */
    INNER_SERVER_ERROR("-15", "inner server access error"),

    /**
     * feignclient读取超时
     */
    FEIGN_READ_TIMEOUT("-16", "read timeout"),



    SOCKET_READ_TIMEOUT("17", "read timeout"),


    ;

    private String code;

    private String msg;

    RespSystemCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
