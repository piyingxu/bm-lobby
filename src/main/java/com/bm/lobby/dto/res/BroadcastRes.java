package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/5 14:22
 */
public class BroadcastRes extends BaseEntity {

    @ApiModelProperty(value = "内容", example = "")
    private String content;

    @ApiModelProperty(value = "字体颜色", example = "红色")
    private String color;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
