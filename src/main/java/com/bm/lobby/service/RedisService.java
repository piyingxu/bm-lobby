package com.bm.lobby.service;

import java.util.Map;

public interface RedisService {

	String getRedisValue(String key);
	
	Long getRedisTime(String key);

	void setRedis(String key, String value, long seconds);

	void delRedis(String key);

    Long getIncr(String key);

    void put (String key, String field, String val);

    String hget(String key, String field);

    void putAll (String key, Map<String, Object> val, long second);

    Map<String, Object> hgetAll(String key);

    long hincrBy( String key, String field, long value);
}
