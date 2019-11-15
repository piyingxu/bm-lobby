package com.bm.lobby.model;

import com.bm.lobby.dto.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class MagicBank extends BaseEntity {

    private Long id;

    private String playerId;

    private String magicId;

    private Long ableAmount;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId == null ? null : playerId.trim();
    }

    public String getMagicId() {
        return magicId;
    }

    public void setMagicId(String magicId) {
        this.magicId = magicId == null ? null : magicId.trim();
    }

    public Long getAbleAmount() {
        return ableAmount;
    }

    public void setAbleAmount(Long ableAmount) {
        this.ableAmount = ableAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}