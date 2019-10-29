package com.bm.lobby.dao;

import com.bm.lobby.dto.req.DishesInfoReq;
import com.bm.lobby.dto.req.ProductReq;
import com.bm.lobby.model.DishesInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DishesInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(DishesInfo record);

    int insertSelective(DishesInfo record);

    DishesInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DishesInfo record);

    int updateByPrimaryKey(DishesInfo record);

    List<DishesInfo> selectConditions(@Param("conditions") ProductReq conditions);
}