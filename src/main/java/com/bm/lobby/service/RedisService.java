package com.bm.lobby.service;

public interface RedisService {

	String getRedisValue(String key);
	
	Long getRedisTime(String key);

	void setRedis(String key, String value, long seconds);

	void delRedis(String key);
}
