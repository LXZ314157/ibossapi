package cn.com.zx.ibossapi.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by huyong on 2017/10/20.
 */
public class CallbackResult implements Serializable {
    private String sn;
    private ChannelPayStatusEnum status;
    private String channelStatus;
    private String message;
    private String channelSN;//商户流水号
    private BigDecimal amount;//金额

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ChannelPayStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ChannelPayStatusEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannelSN() {
        return channelSN;
    }

    public void setChannelSN(String channelSN) {
        this.channelSN = channelSN;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
    }
}
