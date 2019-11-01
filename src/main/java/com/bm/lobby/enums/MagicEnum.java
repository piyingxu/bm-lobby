package com.bm.lobby.enums;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/9/20 13:40
*/
public enum MagicEnum {

    GOLD("gold", "金币"),


	;

	private String code;

	private String msg;

	MagicEnum(String code, String msg) {
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
