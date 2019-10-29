package com.bm.lobby.dao;

import com.bm.lobby.model.MerchantInfo;

public interface MerchantInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MerchantInfo record);

    int insertSelective(MerchantInfo record);

    MerchantInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MerchantInfo record);

    int updateByPrimaryKey(MerchantInfo record);
}