package com.bm.lobby.web;

import com.bm.lobby.dto.base.PageBaseParam;
import com.bm.lobby.dto.base.PageDto;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.req.CheckInAwardReq;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.req.RankReq;
import com.bm.lobby.dto.req.WithDrawReq;
import com.bm.lobby.dto.res.*;
import com.bm.lobby.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(description = "玩家相关接口", tags = "PLAYER")
@RestController
@RequestMapping("/api/lobby/player")
public class PlayerController {

    @Resource
    private PlayerService playerService;

    @ApiOperation("1、登录")
    @PostMapping("login")
    public RespResult<LoginRes> login(@RequestBody @Valid LoginReq req) {
        return playerService.login(req);
    }

    @ApiOperation("2、退出")
    @GetMapping("logout")
    public RespResult<Void> logout() {
        return playerService.logout();
    }

    @ApiOperation("3、刷新金币")
    @GetMapping("refreshGold")
    public RespResult<Long> refreshGold() {
        return playerService.refreshGold();
    }

    @ApiOperation("4、获取签到状态列表")
    @GetMapping("getCheckInStatus")
    public RespResult<List<CheckInRes>> getCheckInStatus() {
        return playerService.getCheckInStatus();
    }

    @ApiOperation("5、领取签到奖励")
    @PostMapping("getCheckInAward")
    public RespResult<CheckInAwardRes> getCheckInAward(@RequestBody CheckInAwardReq req) {
        return playerService.getCheckInAward(req);
    }

    @ApiOperation("6、获取排行榜信息")
    @PostMapping("getRankList")
    public RespResult<RankRes> getRankList(@RequestBody @Valid RankReq req) {
        return playerService.getRankList(req);
    }

    @ApiOperation("7、获取用户个人信息")
    @GetMapping("getPlayerInfo")
    public RespResult<PlayerInfoRes> getUserInfo() {
        return playerService.getUserInfo();
    }

    @ApiOperation("8、申请提现")
    @PostMapping("withDraw")
    public RespResult<Void> withDraw(@RequestBody @Valid WithDrawReq req) {
        return playerService.withDraw(req);
    }

    @ApiOperation("9、提现记录")
    @PostMapping("withDrawList")
    public RespResult<PageDto<WithdrawOrderRes>> withDrawList(@RequestBody PageBaseParam req) {
        return playerService.withDrawList(req);
    }

    @ApiOperation("10、用户活跃度查询")
    @PostMapping("getActivityDay")
    public RespResult<Integer> getActivityDay() {
        return playerService.getActivityDay();
    }

    @ApiOperation("11、我的钱包")
    @PostMapping("getMyWallet")
    public RespResult<WalletRes> getMyWallet() {
        return playerService.getMyWallet();
    }
}