package com.bm.lobby.web;

import com.bm.lobby.dao.MagicBankMapper;
import com.bm.lobby.dto.base.DbCondition;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.model.MagicBank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "Test", tags = "Test")
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private MagicBankMapper magicBankMapper;

    @ApiOperation("1、test")
    @PostMapping("test")
    public RespResult<Void> test(@RequestBody MagicBank record) {
        magicBankMapper.insertSelective(record);
        return RespUtil.success(null);
    }

    @ApiOperation("2、testList")
    @PostMapping("testList")
    public RespResult<Object> testList(@RequestBody DbCondition condition) {
        MagicBank rlt = magicBankMapper.selectByCondition(condition);
        rlt.setAbleAmount(9999l);
        magicBankMapper.updateByPrimaryKeySelective(rlt);
        return RespUtil.success(rlt);
    }

}