package com.bm.lobby.dao;


import com.bm.lobby.model.DishCategory;

public interface DishCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(DishCategory record);

    int insertSelective(DishCategory record);

    DishCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DishCategory record);

    int updateByPrimaryKey(DishCategory record);
}