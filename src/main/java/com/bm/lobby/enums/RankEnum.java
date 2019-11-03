package com.bm.lobby.enums;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/9/20 13:40
*/
public enum RankEnum {

    GOLD(0, "财富榜"),


	;

	private int code;

	private String msg;

	RankEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
