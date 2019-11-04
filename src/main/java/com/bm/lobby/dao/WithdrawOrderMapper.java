package com.bm.lobby.dao;

import com.bm.lobby.model.WithdrawOrder;
import java.util.List;

public interface WithdrawOrderMapper {

    int insert(WithdrawOrder record);

    List<WithdrawOrder> selectByPid(String pid);

    WithdrawOrder selectByOrderNo(String orderNo);

    int update(WithdrawOrder record);
}