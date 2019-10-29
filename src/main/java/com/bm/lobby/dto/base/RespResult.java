package com.bm.lobby.dto.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/3/27 15:48
*/
@ApiModel("返回对象")
public class RespResult<T> extends BaseEntity {

    @ApiModelProperty("返回码")
    private String respCode;

    @ApiModelProperty("返回码说明")
    private String respMsg;

    @ApiModelProperty("业务对象")
    private T data;

    public RespResult() {
    }

    public RespResult(RespCode resp) {
        this.respCode = resp.getCode();
        this.respMsg = resp.getMsg();
    }

    public RespResult(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public RespResult(String respCode, String respMsg, T data) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.data = data;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
