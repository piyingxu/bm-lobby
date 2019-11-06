package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/5 14:22
 */
public class LuckBasinItem extends BaseEntity {

    @ApiModelProperty(value = "奖项索引ID", example = "0")
    private int index;

    @ApiModelProperty(value = "奖项图片url", example = "http://")
    private String img;

    @ApiModelProperty(value = "奖项内容", example = "200金币")
    private String content;

    @ApiModelProperty(value = "奖励道具ID", example = "1001-金币")
    private String magicId;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMagicId() {
        return magicId;
    }

    public void setMagicId(String magicId) {
        this.magicId = magicId;
    }
}
