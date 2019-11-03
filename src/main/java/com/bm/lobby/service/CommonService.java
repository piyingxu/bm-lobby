package com.bm.lobby.service;

import com.bm.lobby.dto.req.HttpHeadReq;
import com.bm.lobby.dto.res.CheckInDto;

import java.util.TreeMap;

public interface CommonService {

    HttpHeadReq getHeadParam();

    String buildToken(String deviceId, String playerId);

    String getCurrPid();

    TreeMap<Integer, CheckInDto> getCheckInConfig();
}
