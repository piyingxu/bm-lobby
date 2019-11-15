package com.bm.lobby.dao;

import com.bm.lobby.dto.base.DbCondition;
import com.bm.lobby.model.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginLogMapper {

    int insertSelective(LoginLog record);

    List<LoginLog> selectByCondition(@Param("conditions")DbCondition condition);

}