package com.bm.lobby.web;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.req.UssdReq;
import com.bm.lobby.dto.res.UssdRes;
import com.bm.lobby.dto.test.TestReq;
import com.bm.lobby.service.TestService;
import com.bm.lobby.util.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "USSD接口", tags = "USSD")
@RestController
@RequestMapping("ussd/")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Resource
    private TestService testService;

    @ApiOperation("1、USSD接口")
    @Log
    @PostMapping("index")
    public RespResult<UssdRes> excute(@RequestBody UssdReq req) {
        return  RespUtil.success(testService.execute(req));
    }


}