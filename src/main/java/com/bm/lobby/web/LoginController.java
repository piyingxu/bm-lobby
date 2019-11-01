package com.bm.lobby.web;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.res.LoginRes;
import com.bm.lobby.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(description = "大厅相关接口", tags = "Lobby")
@RestController
@RequestMapping("/api/lobby/player")
public class LoginController {

    @Resource
    private PlayerService playerService;

    @ApiOperation("1、登录")
    @PostMapping("login")
    public RespResult<LoginRes> login(@RequestBody LoginReq req) {
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


}