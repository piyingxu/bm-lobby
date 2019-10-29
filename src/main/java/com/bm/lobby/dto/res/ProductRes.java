package com.bm.lobby.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bm.lobby.dto.base.BaseEntity;
import com.bm.lobby.model.DishesInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author yinqi
 * @date 2019/3/29
 * 商户商品（菜品）返回给前端对象
 */
public class ProductRes extends BaseEntity {
    private static final long serialVersionUID = -5802622541000980245L;

    @JsonProperty("name")
    @ApiModelProperty(value = "菜品所属类目名", example = "惹味前菜")
    private String categoryName;


    @JsonProperty("dishes")
    @ApiModelProperty(value = "菜品详情", example = "")
    private List<DishesInfo> dishesInfoList;
}
