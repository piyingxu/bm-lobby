package com.bm.lobby.enums;

public enum BusinessTypeEnum {

    WITHDRAW("01","提现");

    private String id;

    private String desc;

    BusinessTypeEnum(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

}
