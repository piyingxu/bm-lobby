package com.bm.lobby.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bm.lobby.dao.DishesInfoMapper;
import com.bm.lobby.dto.base.PageDto;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.req.ProductReq;
import com.bm.lobby.model.DishesInfo;
import com.bm.lobby.service.ProductService;
import com.bm.lobby.util.BeanUtilsCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private DishesInfoMapper dishesInfoMapper;

    @Override
    public DishesInfo findOne(String productId) {
        return dishesInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<DishesInfo> findUpAll() {
        return null;
    }

    @Override
    public RespResult<PageDto<DishesInfo>> queryProductList(ProductReq req) {
        PageHelper.startPage(req.getPage(), req.getLimit());
        List<DishesInfo> list = dishesInfoMapper.selectConditions(req);
        PageInfo<DishesInfo> pageInfo = new PageInfo<>(list);
        PageDto<DishesInfo> target = new PageDto<>();
        BeanUtilsCopy.copyProperties(pageInfo, target);
        target.setList(list);
        return RespUtil.success(target);
    }

    @Override
    public DishesInfo save(DishesInfo productInfo) {
        return null;
    }


}
