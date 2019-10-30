package com.bm.lobby.dao;

import com.bm.lobby.model.PlayerInfo;

public interface PlayerInfoMapper {

    int insert(PlayerInfo record);

    PlayerInfo selectByPrimaryKey(String condition);

    int update(PlayerInfo record);

}