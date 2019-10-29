package com.bm.lobby.dto.req;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/6/27 10:18
 */
public class UssdReq {

    private String protocolId;

    private String msisdn;

    private String param;

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
