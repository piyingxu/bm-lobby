package com.bm.lobby.service;

import java.util.Map;

public interface ThirdPartService {

    Map<String, Object> getWxAccessToken(String code);

    Map<String, Object> getWxUserInfo(String accessToken, String openId);

    Map<String, Object> getQqAccessToken(String code);

    Map<String, Object> getQqUserInfo(String accessToken, String openId);
}
