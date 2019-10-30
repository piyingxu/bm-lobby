package com.bm.lobby.service.impl;

import com.bm.lobby.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public String getRedisValue(String key) {
		String value = redisTemplate.opsForValue().get(key);
		return value;
	}
	
	@Override
	public Long getRedisTime(String key) {
		Long time = redisTemplate.getExpire(key);
		return time;
	}

	@Override
	public void setRedis(String token, String tokenValue, long seconds) {
		final String value = tokenValue == null ? "" : tokenValue;
		RedisCallback<String> callback = connection -> {
			byte[] key = token.getBytes();
			connection.set(key, value.getBytes());
			connection.expire(key, seconds);
			return null;
		};
		redisTemplate.execute(callback);
	}

	@Override
	public void delRedis(String key) {
		redisTemplate.delete(key);
	}

}
