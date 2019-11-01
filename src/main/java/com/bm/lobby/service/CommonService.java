package com.bm.lobby.service;

import com.bm.lobby.dto.req.HttpHeadReq;

public interface CommonService {

    HttpHeadReq getHeadParam();

    String buildToken(String deviceId, String playerId);

    String getCurrPid();
}
