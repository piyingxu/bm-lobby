package com.bm.lobby.service;

import com.bm.lobby.dto.base.PageBaseParam;
import com.bm.lobby.dto.base.PageDto;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.req.CheckInAwardReq;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.req.RankReq;
import com.bm.lobby.dto.req.WithDrawReq;
import com.bm.lobby.dto.res.*;

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

    RespResult<RankRes> getRankList (RankReq req);

    RespResult<PlayerInfoRes> getUserInfo();

    RespResult<Void> withDraw(WithDrawReq req);

    RespResult<PageDto<WithdrawOrderRes>> withDrawList(PageBaseParam req);

    RespResult<Integer> getActivityDay();

    RespResult<WalletRes> getMyWallet();

}
