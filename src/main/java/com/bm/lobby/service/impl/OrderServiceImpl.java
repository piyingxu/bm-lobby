package com.bm.lobby.service.impl;

import com.bm.lobby.dao.OrderMasterMapper;
import com.bm.lobby.model.OrderSell;
import com.bm.lobby.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/5/7 17:20
*/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Override
    public List<OrderSell> selectByOrderSell(String merchantId) {
        return orderMasterMapper.selectByOrderSell(merchantId);
    }
}
