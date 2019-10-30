package com.bm.lobby.enums;

import com.bm.lobby.dto.base.RespCode;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 17:24
*/
public enum RespSystemCode implements RespCode {
    /**
     * 成功
     */
    SUCCESS("00000000", "success"),

    /**
     * 未知结果
     */
    UNKNOWN_RESULT("99999999","unknown result"),

    /**
     * 服务器异常
     */
    SYSTEM_ERROR("11", "system error"),

    /**
     * 参数异常
     */
    PARAM_ERROR("12", "request params error"),

    /**
     * 返回结果为null
     */
    RESPONSE_IS_NULL("13","response is null"),

    /**
     * 值不能为empty
     */
    VALUE_NOT_EMPTY("14","can`t be blank"),

    /**
     * feignclient调用服务异常
     */
    INNER_SERVER_ERROR("15","inner server access error"),

    /**
     * feignclient读取超时
     */
    FEIGN_READ_TIMEOUT("16","read timeout"),


    /**
     * 访问外部渠道接口读取超时
     */
    SOCKET_READ_TIMEOUT("17","read timeout"),

    /**
     * 访问失败
     */
    ACCESS_FAIL("18","process fail,please try again later"),

    /**
     * 国家暂不支持
     */
    NOT_SUPPORT_COUNTRY("19","not support the country"),


    /**
     * 未知货币
     */
    UNKNOWN_CURRENCY("20","unkown currency"),

    /**
     * 国家码为空
     */
    COUNTRY_NULL("21","countryCode is null"),
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

