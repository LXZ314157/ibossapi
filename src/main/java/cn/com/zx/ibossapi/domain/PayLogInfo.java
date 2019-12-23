package cn.com.zx.ibossapi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayLogInfo implements Serializable{
    private Integer payLogId;

    private int userId;
    private Integer orderId;
    private Integer accountBankId;

    private String sn;

    private String payChannels;

    private Integer payChannel;

    private Byte term;

    private  String terms;

    private String channelSN;

    private Byte channelStatus;

    private Date payTime;

    private Date expTime;

    private BigDecimal amount;

    private BigDecimal checkAmount;

    private BigDecimal commission;

    private BigDecimal interest;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Byte checkStatus;

    private int updateUserId;

    private int createUserId;

    private Integer payChannelGroupId;

    private Integer bankId;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getPayChannelGroupId() {
        return payChannelGroupId;
    }

    public void setPayChannelGroupId(Integer payChannelGroupId) {
        this.payChannelGroupId = payChannelGroupId;
    }

    private String checkStatusStr;

    private String statusStr;

    private String payChannelStr;

    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setCheckStatusStr(String checkStatusStr) {
        this.checkStatusStr = checkStatusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public void setPayChannelStr(String payChannelStr) {
        this.payChannelStr = payChannelStr;
    }

    public Integer getPayLogId() {
        return payLogId;
    }

    public PayLogInfo setPayLogId(Integer payLogId) {
        this.payLogId = payLogId;
        return this;
    }

    public Byte getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Byte getTerm() {
        return term;
    }

    public void setTerm(Byte term) {
        this.term = term;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public PayLogInfo setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getAccountBankId() {
        return accountBankId;
    }

    public PayLogInfo setAccountBankId(Integer accountBankId) {
        this.accountBankId = accountBankId;
        return this;
    }

    public String getSn() {
        return sn;
    }

    public PayLogInfo setSn(String sn) {
        this.sn = sn;
        return this;
    }

    public String getPayChannels() {
        return payChannels;
    }

    public void setPayChannels(String payChannels) {
        this.payChannels = payChannels;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public PayLogInfo setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
        return this;

    }


    public String getChannelSN() {
        return channelSN;
    }

    public PayLogInfo setChannelSN(String channelSN) {
        this.channelSN = channelSN;
        return this;
    }

    public Byte getChannelStatus() {
        return channelStatus;
    }

    public PayLogInfo setChannelStatus(Byte channelStatus) {
        this.channelStatus = channelStatus;
        return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public PayLogInfo setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }

    public Date getExpTime() {
        return expTime;
    }

    public PayLogInfo setExpTime(Date expTime) {
        this.expTime = expTime;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PayLogInfo setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public PayLogInfo setCommission(BigDecimal commission) {
        this.commission = commission;
        return this;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public PayLogInfo setInterest(BigDecimal interest) {
        this.interest = interest;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public PayLogInfo setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PayLogInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public PayLogInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }
}