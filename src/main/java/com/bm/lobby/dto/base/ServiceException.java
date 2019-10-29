package com.bm.lobby.dto.base;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/3/27 15:17
 */
public class ServiceException extends RuntimeException {

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常消息
     */
    private String msg;

    public ServiceException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this == null) {
            return obj == this;
        }
        if (obj instanceof ServiceException) {
            ServiceException e = (ServiceException) obj;
            return e.getCode().equals(this.getCode()) && e.getMsg().equals(this.getMsg());
        } else {
            return false;
        }

    }
}
