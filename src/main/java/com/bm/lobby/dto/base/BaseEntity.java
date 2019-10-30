package com.bm.lobby.dto.base;

import java.io.Serializable;
import com.alibaba.fastjson.JSONObject;
import com.bm.lobby.util.GsonUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseEntity implements Serializable {

    @Override
    public String toString() {
        return GsonUtils.fromObject2Json(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj);
    }
}
