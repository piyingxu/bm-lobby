package com.bm.lobby.dao;


import com.bm.lobby.model.ShelfDetail;

public interface ShelfDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ShelfDetail record);

    int insertSelective(ShelfDetail record);

    ShelfDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShelfDetail record);

    int updateByPrimaryKey(ShelfDetail record);
}