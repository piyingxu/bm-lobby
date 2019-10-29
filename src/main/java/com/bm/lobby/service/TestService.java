package com.bm.lobby.service;

import com.bm.lobby.dto.req.UssdReq;
import com.bm.lobby.dto.res.UssdRes;

public interface TestService {

    UssdRes execute(UssdReq req);
}
