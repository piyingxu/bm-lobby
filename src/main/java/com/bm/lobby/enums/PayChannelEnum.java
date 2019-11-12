package com.bm.lobby.enums;

public enum PayChannelEnum {

    WECHAT(1,"微信"),

    ALIPAY(2,"支付宝");

    private int type;

    private String desc;

    PayChannelEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
