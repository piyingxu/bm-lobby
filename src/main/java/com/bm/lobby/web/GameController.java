package com.bm.lobby.web;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.req.GameEndReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "游戏相关接口", tags = "GAME")
@RestController
@RequestMapping("/api/game/third")
public class GameController {

    @ApiOperation("1、注册")
    @PostMapping("register")
    public RespResult<Void> register() {
        return RespUtil.success(null);
    }

    @ApiOperation("2、开局")
    @PostMapping("gamestart")
    public RespResult<String> gameStart() {
        return RespUtil.success(String.valueOf(System.currentTimeMillis()));
    }

    @ApiOperation("3、结算")
    @PostMapping("gameend")
    public RespResult<Void> gameEnd(@RequestBody GameEndReq req) {
        return RespUtil.success(null);
    }

}