package com.bm.lobby.dao;

import com.bm.lobby.model.GameConfig;
import java.util.List;

public interface GameConfigMapper {

    List<GameConfig> selectByAppId(String appId);

}