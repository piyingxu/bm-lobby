package com.bm.lobby.dto.req;

import com.bm.lobby.dto.base.BaseEntity;
import com.bm.lobby.dto.base.PageBaseParam;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/10/30 14:54
*/
public class WithDrawReq extends BaseEntity {

    @ApiModelProperty(value = "渠道类型：1-微信 2-支付宝", example = "1")
    @Range(min = 1,max = 2,message = "Channel invalid")
    private int channel;

    @NotBlank(message = "AccountNum can not be null")
    @ApiModelProperty(value = "账户", example = "1")
    private String accountNum;

    @NotBlank(message = "AccountName can not be null")
    @ApiModelProperty(value = "账户实名姓名", example = "1")
    private String accountName;

    @ApiModelProperty(value = "金额", example = "1")
    @Range(min = 1,max = 1000000,message = "Amount invalid")
    private int amount;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
