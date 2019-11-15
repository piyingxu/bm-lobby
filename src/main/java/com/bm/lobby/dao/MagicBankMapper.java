package com.bm.lobby.dao;

import com.bm.lobby.dto.base.DbCondition;
import com.bm.lobby.model.MagicBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MagicBankMapper {

    int insertSelective(MagicBank record);

    MagicBank selectByCondition(@Param("conditions")DbCondition condition);

    List<MagicBank> selectByPid(String pid);

    int updateByPrimaryKeySelective(MagicBank record);

}