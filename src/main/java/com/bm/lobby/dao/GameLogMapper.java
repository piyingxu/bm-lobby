package com.bm.lobby.dao;

import com.bm.lobby.dto.base.DbCondition;
import com.bm.lobby.model.GameLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameLogMapper {

    int insertSelective(GameLog record);

    List<GameLog> selectByCondition(@Param("conditions")DbCondition condition);


}