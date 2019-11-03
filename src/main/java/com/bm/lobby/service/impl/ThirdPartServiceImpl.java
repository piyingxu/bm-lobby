package com.bm.lobby.service.impl;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.service.ThirdPartService;
import com.bm.lobby.util.BmRestTemplate;
import com.bm.lobby.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 16:49
 */
@Service
public class ThirdPartServiceImpl implements ThirdPartService {

    private static final Logger LOG = LoggerFactory.getLogger(ThirdPartServiceImpl.class);

    @Autowired
    private BmRestTemplate restTemplate;

    @Resource
    private LobbyConfiguration lobbyConfiguration;

    @Override
    public Map<String, Object> getWxAccessToken(String code) {
        String url = lobbyConfiguration.getWxUrl() + "sns/oauth2/access_token?appid=" + lobbyConfiguration.getWxAppid() +
                "&secret=" + lobbyConfiguration.getWxAppscret() + "&code=" + code + "&grant_type=authorization_code";
        String result = restTemplate.getForObject(url, String.class);
        Map<String, Object> ret = GsonUtils.fromJson2Object(result, Map.class);
        return ret;
    }

    @Override
    public Map<String, Object> getWxUserInfo(String accessToken, String openId) {
        String url = lobbyConfiguration.getWxUrl() + "sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String result = restTemplate.getForObject(url, String.class);
        Map<String, Object> ret = GsonUtils.fromObject2Map(result);
        return ret;
    }

    @Override
    public Map<String, Object> getQqAccessToken(String code) {
        String url = lobbyConfiguration.getQqUrl() + "oauth2.0/me?access_token=" + code;
        String result = restTemplate.getForObject(url, String.class);
        Map<String, Object> ret = GsonUtils.fromJson2Object(result, Map.class);
        return ret;
    }

    @Override
    public Map<String, Object> getQqUserInfo(String accessToken, String openId) {
        String url = lobbyConfiguration.getQqUrl() + "/user/get_user_info?access_token="+accessToken+"&oauth_consumer_key="+lobbyConfiguration.getQqAppid()+"&openid=" + openId;
        String result = restTemplate.getForObject(url, String.class);
        Map<String, Object> ret = GsonUtils.fromJson2Object(result, Map.class);
        return ret;
    }

}
