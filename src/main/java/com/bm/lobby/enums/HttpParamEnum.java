package com.bm.lobby.enums;

public enum HttpParamEnum {

	TOKEN("TOKEN", "请求TOKEN"),

    APPID("APPID", "appid"),

	DEVICE_ID("DEVICE_ID", "设备ID"),

	DEVICE_TYPE("DEVICE_TYPE", "设备类型(ANDROID, IOS, H5)"),
	
	CLIENT_VER("CLIENT_VER", "客户端版本号"),
	
	REQ_SIGN("REQ_SIGN", "请求签名"),

    GAME_SIGN("sign", "GAME_SIGN"),

    PLAYER_ID("playerid", "PLAYER_ID"),

    GAME_ID("gameid", "GAME_ID"),

    TIMESTAMP("timestamp", "TIMESTAMP"),
	
	;

	private final String code;

	private final String desc;

	HttpParamEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
