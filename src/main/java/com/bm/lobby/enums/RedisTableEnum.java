package com.bm.lobby.enums;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/9/20 13:40
*/
public enum RedisTableEnum {

	REQUEST_TOKEN("lobby:token:", "请求token"),

    CURR_PLAYERID("lobby:palyerId:", "当前playerId"),

	;

	private String code;

	private String msg;

	RedisTableEnum(String code, String msg) {
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
