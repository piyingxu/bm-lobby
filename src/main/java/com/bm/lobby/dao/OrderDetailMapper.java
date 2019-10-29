package com.bm.lobby.dao;

import com.bm.lobby.model.OrderDetail;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}