package com.bm.lobby.service.impl;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.dto.base.ServiceException;
import com.bm.lobby.dto.req.HttpHeadReq;
import com.bm.lobby.enums.HttpParamEnum;
import com.bm.lobby.enums.MagicEnum;
import com.bm.lobby.enums.RedisTableEnum;
import com.bm.lobby.enums.RespLobbyCode;
import com.bm.lobby.service.CommonService;
import com.bm.lobby.service.MagicService;
import com.bm.lobby.service.RedisService;
import com.bm.lobby.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/11/1 10:30
*/
@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger LOG = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Resource
    private LobbyConfiguration lobbyConfiguration;

    @Resource
    private RedisService redisService;

    @Override
    public HttpHeadReq getHeadParam() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String appId = req.getHeader(HttpParamEnum.APPID.getCode());
        String deviceId = req.getHeader(HttpParamEnum.DEVICE_ID.getCode());
        String deviceType = req.getHeader(HttpParamEnum.DEVICE_TYPE.getCode());
        String clientVer = req.getHeader(HttpParamEnum.CLIENT_VER.getCode());
        String token = req.getHeader(HttpParamEnum.TOKEN.getCode());
        HttpHeadReq httpHeadReq = new HttpHeadReq();
        httpHeadReq.setAppId(appId);
        httpHeadReq.setDeviceId(deviceId);
        httpHeadReq.setDeviceType(deviceType);
        httpHeadReq.setClientVer(clientVer);
        httpHeadReq.setToken(token);
        return httpHeadReq;
    }

    @Override
    public String buildToken(String deviceId, String playerId) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        String key1 = RedisTableEnum.REQUEST_TOKEN.getCode() + deviceId;
        // 删除之前的token
        String oldToken = redisService.getRedisValue(key1);
        redisService.delRedis(RedisTableEnum.CURR_PLAYERID.getCode() + oldToken);
        // 赋新值
        redisService.setRedis(key1, token, lobbyConfiguration.getToken_valid_time());
        String key2 = RedisTableEnum.CURR_PLAYERID.getCode() + token;
        redisService.setRedis(key2, playerId, lobbyConfiguration.getToken_valid_time());
        LOG.info("buildToken deviceId={}, playerId={}", deviceId, playerId);
        return token;
    }

    @Override
    public String getCurrPid() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = req.getHeader(HttpParamEnum.TOKEN.getCode());
        String pid = null;
        if (StringUtils.isNotEmpty(token)) {
            String key = RedisTableEnum.CURR_PLAYERID.getCode() + token;
            pid = redisService.getRedisValue(key);
        }
        if (StringUtils.isEmpty(pid)) {
            throw new ServiceException(RespLobbyCode.PID_ISNULL);
        }
        return pid;
    }

    @Override
    public List<Integer> getCheckInConfig() {
        List<Integer> hourList = new ArrayList<>();
        hourList.add(0);hourList.add(4);hourList.add(8);
        hourList.add(12);hourList.add(16);hourList.add(20);
        return hourList;
    }



}
