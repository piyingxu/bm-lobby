package com.bm.lobby.dao;


import com.bm.lobby.model.EvaluationInfo;

public interface EvaluationInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(EvaluationInfo record);

    int insertSelective(EvaluationInfo record);

    EvaluationInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EvaluationInfo record);

    int updateByPrimaryKey(EvaluationInfo record);
}