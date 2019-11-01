package com.bm.lobby.service;

public interface RedisService {

	String getRedisValue(String key);
	
	Long getRedisTime(String key);

	void setRedis(String key, String value, long seconds);

	void delRedis(String key);

    Long getIncr(String key);

    void put (String key, String field, String val);

    String hget(String key, String field);

    long hincrBy( String key, String field, long value);
}
