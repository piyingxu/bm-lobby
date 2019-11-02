package com.bm.lobby.enums;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/9/20 13:40
*/
public enum CheckInStatusEnum {

	UNREACHED_TIME(0, "不可领取(时间未到)"),

	ONTIME_CAN(1, "可领取(正点)"),

	REISSUE_CAN(2, "可领取(超点-补签)"),

	ALREADY_RECEIVE(3, "已领取"),

	;

	private int code;

	private String msg;

	CheckInStatusEnum(int code, String msg) {
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
