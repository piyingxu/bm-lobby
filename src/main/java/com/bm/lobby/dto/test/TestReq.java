package com.bm.lobby.dto.test;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class TestReq extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "网络代码", required = true, example = "004")
    private String id;


    @ApiModelProperty(value = "收款手机号", required = true, example = "233244377790")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
