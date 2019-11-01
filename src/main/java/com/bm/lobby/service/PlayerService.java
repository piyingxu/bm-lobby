package com.bm.lobby.service;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.res.LoginRes;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:27
*/

public interface PlayerService {

    RespResult<LoginRes> login (LoginReq req);

    RespResult<Void> logout ();

    RespResult<Long> refreshGold ();

}
