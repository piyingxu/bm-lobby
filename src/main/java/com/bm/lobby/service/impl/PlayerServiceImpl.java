package com.bm.lobby.service.impl;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.dao.PlayerInfoMapper;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.ServiceException;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.res.LoginRes;
import com.bm.lobby.enums.AuthTypeEnum;
import com.bm.lobby.enums.HttpParamEnum;
import com.bm.lobby.enums.RedisTableEnum;
import com.bm.lobby.enums.RespLobbyCode;
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
        LoginRes loginRes = new LoginRes();
        // 获取第三方昵称和头像信息
        getUserInfo(req, loginRes);
        // 关联playerId

        //playerInfoMapper.selectByPrimaryKey()

        String deviceId = getHeadParam(HttpParamEnum.DEVICE_ID);

        return null;
    }

    @Override
    public String getHeadParam(HttpParamEnum headParam) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(headParam.getCode());
    }

    private void getUserInfo(LoginReq req, LoginRes res) {
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
    }

    public String buildToken(String deviceId, String playerId) {
        String token = UUID.randomUUID().toString();
        String key1 = RedisTableEnum.REQUEST_TOKEN.getCode() + deviceId;
        redisService.setRedis(key1, token, lobbyConfiguration.getToken_valid_time());
        String key2 = RedisTableEnum.REQUEST_TOKEN.getCode() + token;
        redisService.setRedis(key2, playerId, lobbyConfiguration.getToken_valid_time());
        return token;
    }




}
