package com.bm.lobby.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Map;
import java.util.Set;

public interface RedisService {

	String getRedisValue(String key);
	
	Long getRedisTime(String key);

	void setRedis(String key, String value, long seconds);

	void delRedis(String key);

    Long getIncr(String key);

    void hput (String key, String field, String val);

    String hget(String key, String field);

    void putAll (String key, Map<String, Object> val, long second);

    Map<String, Object> hgetAll(String key);

    long hincrBy( String key, String field, long value);

    double incrementScore(String key, String field, long score);

    Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key, int min, int max);

    Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(String key, int min, int max);

    long zCard(String key);

    long zrank(String key, String field);

    double zScore(String key, String field);
}
