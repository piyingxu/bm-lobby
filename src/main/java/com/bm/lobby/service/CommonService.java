package com.bm.lobby.service;

import com.bm.lobby.dto.req.HttpHeadReq;

import java.util.List;

public interface CommonService {

    HttpHeadReq getHeadParam();

    String buildToken(String deviceId, String playerId);

    String getCurrPid();

    List<Integer> getCheckInConfig();
}
