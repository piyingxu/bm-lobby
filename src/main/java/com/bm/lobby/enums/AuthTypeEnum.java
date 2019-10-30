package com.bm.lobby.enums;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/9/20 13:40
*/
public enum AuthTypeEnum {

	QQ("1", "QQ"),

    WECHAT("2", "WECHAT"),

	;

	private String code;

	private String msg;

	AuthTypeEnum(String code, String msg) {
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
