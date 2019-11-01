package com.bm.lobby.service.impl;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);

    private long playerStartId = 100000000;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private LobbyConfiguration lobbyConfiguration;

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

    @Override
    public Long getIncr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if (increment == null || increment == 0) {
            long configIncrement = lobbyConfiguration.getPlayerStartId();
            entityIdCounter.set(configIncrement);
            increment = entityIdCounter.getAndIncrement();
        }
        LOG.info("新注册ID产生：{}", increment);
        return increment;
    }

    @Override
    public void put(String key, String field, String val) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        ops.put(field, val);
    }

    @Override
    public String hget(String key, String field) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        return ops.get(field);
    }

    @Override
    public long hincrBy( String key, String field, long value) {
        return redisTemplate.boundHashOps(key).increment(field, value);
    }

}
