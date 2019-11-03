package com.bm.lobby.service.impl;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    public void hput(String key, String field, String val) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        ops.put(field, val);
    }

    @Override
    public String hget(String key, String field) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        return ops.get(field);
    }

    @Override
    public void putAll(String key, Map<String, Object> val, long second) {
        BoundHashOperations<String, String, Object> ops = redisTemplate.boundHashOps(key);
        ops.putAll(val);
        if (second > 0) {
            ops.expire(second, TimeUnit.SECONDS);
        }
    }

    @Override
    public Map<String, Object> hgetAll(String key) {
        BoundHashOperations<String, String, Object> ops = redisTemplate.boundHashOps(key);
        return ops.entries();
    }

    @Override
    public long hincrBy(String key, String field, long value) {
        return redisTemplate.boundHashOps(key).increment(field, value);
    }

    @Override
    public double incrementScore(String key, String field, long score) {
        return redisTemplate.boundZSetOps(key).incrementScore(field, score);
    }

    /**
     * 从大到小排序
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key, int min, int max) {
        return redisTemplate.boundZSetOps(key).rangeWithScores(min, max);
    }

    /**
     *  从小到大排序
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(String key, int min, int max) {
        return redisTemplate.boundZSetOps(key).reverseRangeWithScores(min, max);
    }

    /**
     * 计算集合中元素的数量
     * @param key
     * @return
     */
    @Override
    public long zCard(String key) {
        return redisTemplate.boundZSetOps(key).zCard();
    }

    /**
     * 获取field的排名(从大到小)
     * @param key
     * @param field
     * @return
     */
    @Override
    public long zrank(String key, String field) {
        Long tempRank = redisTemplate.boundZSetOps(key).reverseRank(key);
        if (tempRank == null) {
            tempRank = -1l;
        }
        return tempRank;
    }

    /**
     * 获取field的得分
     * @param key
     * @param field
     * @return
     */
    @Override
    public double zScore(String key, String field) {
        Double tempScore = redisTemplate.boundZSetOps(key).score(field);
        if (tempScore == null) {
            tempScore = 0d;
        }
        return tempScore;
    }
}
