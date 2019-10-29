package com.bm.lobby.dto.base;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/3/28 17:42
 */
public class PageBaseParam extends BaseEntity {
    /**
     * 要查询的页面数
     */
    @ApiModelProperty(value = "查询页面",example = "1" )
    private int page = 1;

    /**
     * 页面数据大小
     */
    @ApiModelProperty(value = "单页数据大小",example = "10")
    private int limit = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
