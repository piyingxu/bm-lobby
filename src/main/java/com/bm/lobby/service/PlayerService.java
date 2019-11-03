package com.bm.lobby.service;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.req.CheckInAwardReq;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.req.RankReq;
import com.bm.lobby.dto.res.CheckInAwardRes;
import com.bm.lobby.dto.res.CheckInRes;
import com.bm.lobby.dto.res.LoginRes;
import com.bm.lobby.dto.res.RankItemDTO;

import java.util.List;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:27
*/

public interface PlayerService {

    RespResult<LoginRes> login (LoginReq req);

    RespResult<Void> logout ();

    RespResult<Long> refreshGold ();

    RespResult<List<CheckInRes>> getCheckInStatus ();

    RespResult<CheckInAwardRes> getCheckInAward (CheckInAwardReq req);

    RespResult<List<RankItemDTO>> getRankList (RankReq req);

}
