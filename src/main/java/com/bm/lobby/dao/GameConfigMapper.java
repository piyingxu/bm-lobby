package com.bm.lobby.dao;


import com.bm.lobby.model.GameConfig;

public interface GameConfigMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GameConfig record);

    int insertSelective(GameConfig record);

    GameConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GameConfig record);

    int updateByPrimaryKey(GameConfig record);
}