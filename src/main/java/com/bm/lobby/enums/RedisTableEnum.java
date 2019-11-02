package com.bm.lobby.enums;

import com.bm.lobby.util.DateUtil;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/9/20 13:40
*/
public enum RedisTableEnum {

	REQUEST_TOKEN("game:player:token:", "请求token"),

    CURR_PLAYERID("game:player:palyerId:", "当前playerId"),

    AUTO_INCREMENT("game:player:increment:", "当前playerId"),

    MAGIC("game:player:magic:", "道具"),

	CHECKIN("game:player:checkIn:", "签到"),

	;

	private String code;

	private String msg;

	RedisTableEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getCheckInKey(String pid) {
		return RedisTableEnum.CHECKIN.getCode() + pid + ":" + DateUtil.getCurrDay();
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
