package com.bm.lobby.service.impl;

import com.bm.lobby.enums.MagicEnum;
import com.bm.lobby.enums.RedisTableEnum;
import com.bm.lobby.service.MagicService;
import com.bm.lobby.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/11/1 10:30
*/
@Service
public class MagicServiceImpl implements MagicService {

    private static final Logger LOG = LoggerFactory.getLogger(MagicServiceImpl.class);

    @Resource
    private RedisService redisService;

    @Override
    public long getOrUpMagic(MagicEnum magic, String pid, long addCount) {
        long newVal = redisService.hincrBy(RedisTableEnum.MAGIC.getCode() + pid, magic.getCode(), addCount);
        LOG.info("getOrUpMagic magic={}, pid={}, addCount={}, newVal={}", magic, pid, addCount, newVal);
        return newVal;
    }

}
