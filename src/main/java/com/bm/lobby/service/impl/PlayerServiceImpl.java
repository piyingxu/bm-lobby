package com.bm.lobby.service.impl;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.dao.PlayerInfoMapper;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.base.ServiceException;
import com.bm.lobby.dto.req.HttpHeadReq;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.res.LoginRes;
import com.bm.lobby.enums.AuthTypeEnum;
import com.bm.lobby.enums.HttpParamEnum;
import com.bm.lobby.enums.RedisTableEnum;
import com.bm.lobby.enums.RespLobbyCode;
import com.bm.lobby.model.PlayerInfo;
import com.bm.lobby.service.PlayerService;
import com.bm.lobby.service.RedisService;
import com.bm.lobby.service.ThirdPartService;
import com.bm.lobby.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 16:41
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Resource
    private PlayerInfoMapper playerInfoMapper;

    @Resource
    private ThirdPartService thirdPartService;

    @Resource
    private LobbyConfiguration lobbyConfiguration;

    @Resource
    private RedisService redisService;

    @Override
    public RespResult<LoginRes> login(LoginReq req) {
        if (!req.getAuthType().equals(AuthTypeEnum.WECHAT.getCode())) {
            // 目前仅支持微信
            throw new ServiceException(RespLobbyCode.PARAM_ERROR);
        }
        HttpHeadReq httpHeadReq = getHeadParam();
        LoginRes loginRes = new LoginRes();
        // 获取第三方昵称和头像信息
        String authId = getUserInfo(req, loginRes);
        String playerId = null;
        // 关联playerId
        PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(authId);
        if (playerInfo == null) {
            playerId = buildPlayId();
            // 新注册用户
            PlayerInfo newPlayer = new PlayerInfo();
            newPlayer.setAppId(httpHeadReq.getAppId());
            newPlayer.setNickName(loginRes.getNickName());
            newPlayer.setAuthId(authId);
            newPlayer.setAuthType(req.getAuthType());
            newPlayer.setClientType(httpHeadReq.getDeviceType());
            newPlayer.setClientVersion(httpHeadReq.getClientVer());
            newPlayer.setPlayerId(playerId);
            playerInfoMapper.insert(newPlayer);
        } else {
            playerId = playerInfo.getPlayerId();
        }
        String token = buildToken(httpHeadReq.getDeviceId(), playerId);
        loginRes.setToken(token);
        //设置开关
        Map<String, Boolean> funSwitch = new HashMap<>();
        funSwitch.put("Task", true);
        funSwitch.put("Challenge", false);
        loginRes.setFunSwitch(funSwitch);
        //获取金币
        //redisService.getRedisValue()

        return RespUtil.success(loginRes);
    }

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

    private String getUserInfo(LoginReq req, LoginRes res) {
        Map<String, Object> wxAccessToken = thirdPartService.getWxAccessToken(req.getAuthToken());
        Object wxATErrorCode = wxAccessToken.get("errcode");
        if (wxATErrorCode != null) {
            throw new ServiceException(RespLobbyCode.AUTH_ERROR.getCode(), String.valueOf(wxATErrorCode));
        }
        String accessToken = String.valueOf(wxAccessToken.get("access_token"));
        String openId = String.valueOf(wxAccessToken.get("openid"));
        if (StringUtil.objIsNull(openId, accessToken)) {
            throw new ServiceException(RespLobbyCode.AUTH_ERROR.getCode(), String.valueOf(wxATErrorCode));
        }
        Map<String, Object> wxUserInfo = thirdPartService.getWxUserInfo(accessToken, openId);
        Object nickNameObj = wxUserInfo.get("nickname");
        Object headImgUrlObj = wxUserInfo.get("headimgurl");
        if (StringUtil.objIsNull(nickNameObj, headImgUrlObj)) {
            throw new ServiceException(RespLobbyCode.AUTH_ERROR.getCode(), String.valueOf(wxATErrorCode));
        }
        res.setNickName(String.valueOf(nickNameObj));
        res.setHeadUrl(String.valueOf(nickNameObj));
        return openId;
    }

    public String buildToken(String deviceId, String playerId) {
        String token = UUID.randomUUID().toString();
        String key1 = RedisTableEnum.REQUEST_TOKEN.getCode() + deviceId;
        redisService.setRedis(key1, token, lobbyConfiguration.getToken_valid_time());
        String key2 = RedisTableEnum.REQUEST_TOKEN.getCode() + token;
        redisService.setRedis(key2, playerId, lobbyConfiguration.getToken_valid_time());
        return token;
    }

    public String buildPlayId() {
        return UUID.randomUUID().toString();

    }


}
