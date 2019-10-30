package com.bm.lobby.web;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.req.LoginReq;
import com.bm.lobby.dto.res.LoginRes;
import com.bm.lobby.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "登录相关接口", tags = "LOGIN")
@RestController
@RequestMapping("/api/lobby/player")
public class LoginController {

    @Resource
    private PlayerService playerService;

    @ApiOperation("1、登录")
    @PostMapping("login")
    public RespResult<LoginRes> login(@RequestBody LoginReq req) {
        return  playerService.login(req);
    }


}